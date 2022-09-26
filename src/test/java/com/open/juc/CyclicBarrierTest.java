package com.open.juc;

import com.open.juc.CyclicBarrier.CyclicBarrierDemo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CyclicBarrier;

/**
 * @author liuxiaowei
 * @date 2022年09月26日 14:34
 * @Description
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloConcurrentApplication.class)
public class CyclicBarrierTest {

    @Test
    public void testRun() throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4, () -> System.out.println("所有运动员准备就绪，裁判发令..."));
        CyclicBarrierDemo cyclicBarrierDemo = new CyclicBarrierDemo(cyclicBarrier);
        cyclicBarrierDemo.run();
        Thread.currentThread().join();
    }
}
