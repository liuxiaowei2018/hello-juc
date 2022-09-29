package com.open.project.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;
import java.util.Random;

/**
 * @author liuxiaowei
 * @date 2022年09月29日 16:17
 * @Description
 */
@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateStudentsTransaction(PlatformTransactionManager transactionManager,
                                          List<TransactionStatus> transactionStatuses,
                                          List<Student> students) {
        // 使用这种方式将事务状态都放在同一个事务里面
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        // 事物隔离级别，开启新事务，这样会比较安全些。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        // 获得事务状态
        TransactionStatus status = transactionManager.getTransaction(def);
        transactionStatuses.add(status);
        students.forEach(s -> {
            // 更新教师信息
            // String teacher = s.getTeacher();
            String newTeacher = "TNOz_" + new Random().nextInt(100);
            s.setTeacher(newTeacher);
            studentMapper.updateById(s);
        });
    }
}
