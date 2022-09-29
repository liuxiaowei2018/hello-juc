package com.open.juc;

import com.open.juc.locks.ReentrantLock.ReentrantLockDemo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.open.HelloConcurrentApplication;

/**
 * @author liuxiaowei
 * @date 2022年09月26日 14:51
 * @Description
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloConcurrentApplication.class)
public class ReentrantLockTest {

    @Test
    public void testRun() throws InterruptedException {
        new ReentrantLockDemo().run();
    }
}
