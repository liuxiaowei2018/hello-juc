package com.open.juc;

import com.open.juc.locks.DeadLockDemo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author liuxiaowei
 * @date 2022年03月25日 20:24
 * @Description
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloConcurrentApplication.class)
public class DeadLockTest {

    @Test
    public void test() {
        Object lock1 = new Object();
        Object lock2 = new Object();
        new Thread(new DeadLockDemo(lock1,lock2,true),"线程1").start();
        new Thread(new DeadLockDemo(lock1,lock2,false),"线程2").start();
        System.out.println("测试完成");
    }
}
