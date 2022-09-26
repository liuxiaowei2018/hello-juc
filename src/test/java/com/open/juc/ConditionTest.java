package com.open.juc;

import com.open.juc.CountDownLatch.CountDownLatchDemo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liuxiaowei
 * @date 2022年09月26日 13:59
 * @Description
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloConcurrentApplication.class)
public class ConditionTest {

    /**
     * 号令攻城
     *
     * @date 2022/9/26 14:27 
     */
    @Test
    public void test001() throws Exception {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        new Thread(() -> {
            // 和synchronized一样，必须持有锁的情况下才能使用await
            lock.lock();
            System.out.println("线程1进入等待状态！");
            try {
                //进入等待状态
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程1等待结束！");
            lock.unlock();
        }).start();

        // 防止线程2先跑
        Thread.sleep(100);

        new Thread(() -> {
            lock.lock();
            System.out.println("线程2开始唤醒其他等待线程");
            condition.signal();   // 唤醒线程1，但是此时线程1还必须要拿到锁才能继续运行
            System.out.println("线程2结束");
            lock.unlock();   // 这里释放锁之后，线程1就可以拿到锁继续运行了
        }).start();
    }

}
