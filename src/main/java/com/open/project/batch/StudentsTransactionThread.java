package com.open.project.batch;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @author liuxiaowei
 * @date 2022年09月29日 16:15
 * @Description
 */
@Service
public class StudentsTransactionThread {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private StudentService studentService;
    @Autowired
    private PlatformTransactionManager transactionManager;

    List<TransactionStatus> transactionStatuses = Collections.synchronizedList(new ArrayList<>());

    /**
     * 需要等待线程执行完成后才会提交事务，所有任会占用Jdbc连接池，如果线程数量超过连接池最大数量会产生连接超时。
     * 所以在使用过程中任要控制线程数量(在2-5个线程时操作时间最快)
     *
     * @date 2022/9/29 16:27
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateStudentWithThreadsAndTrans(int threadCount) throws InterruptedException {
        //查询总数据
        List<Student> allStudents = studentMapper.selectList(new QueryWrapper<>());

        //每个线程处理的数据量
        final Integer dataPartionLength = (allStudents.size() + threadCount - 1) / threadCount;

        // 创建多线程处理任务
        ExecutorService studentThreadPool = Executors.newFixedThreadPool(threadCount);
        CountDownLatch threadLatchs = new CountDownLatch(threadCount);
        AtomicBoolean isError = new AtomicBoolean(false);
        try {
            for (int i = 0; i < threadCount; i++) {
                // 每个线程处理的数据
                List<Student> threadDatas = allStudents.stream()
                        .skip(i * dataPartionLength)
                        .limit(dataPartionLength)
                        .collect(Collectors.toList());
                studentThreadPool.execute(() -> {
                    try {
                        try {
                            studentService.updateStudentsTransaction(transactionManager, transactionStatuses, threadDatas);
                        } catch (Throwable e) {
                            e.printStackTrace();
                            isError.set(true);
                        } finally {
                            threadLatchs.countDown();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        isError.set(true);
                    }
                });
            }
            // 倒计时锁设置超时时间 30s
            boolean await = threadLatchs.await(30, TimeUnit.SECONDS);
            // 判断是否超时
            if (!await) {
                isError.set(true);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            isError.set(true);
        }

        if (!transactionStatuses.isEmpty()) {
            if (isError.get()) {
                System.out.println("批量回滚");
                transactionStatuses.forEach(s -> transactionManager.rollback(s));
            } else {
                System.out.println("批量提交");
                transactionStatuses.forEach(s -> transactionManager.commit(s));
            }
        }
        System.out.println("主线程完成");
    }

}
