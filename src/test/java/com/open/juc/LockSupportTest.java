package com.open.juc;

import com.open.juc.locksupport.LockSupportDemo;
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
public class LockSupportTest {

    @Test
    public void testRun() throws InterruptedException {
        new LockSupportDemo().run();
        //主线程被挂起
        //主线程继续运行
        //主线程继续运行!!
    }
}
