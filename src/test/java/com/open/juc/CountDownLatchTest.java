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
import com.open.HelloConcurrentApplication;

/**
 * @author liuxiaowei
 * @date 2022年09月26日 13:59
 * @Description
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloConcurrentApplication.class)
public class CountDownLatchTest {

    /**
     * 号令攻城
     *
     * @date 2022/9/26 14:27 
     */
    @Test
    public void test001() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        // 构造方法传入一个 整形数字 N
        // 之后调用 countDown() 方法会对 N 减 1，直到 N = 0 ，当前调用 await 方法的线程继续执行，否则会被阻塞。
        CountDownLatch countDownLatch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            executorService.execute(new CountDownLatchDemo(countDownLatch));
        }
        System.out.println("所有士兵就位，听我号令");
        // 当 CountDownLatchDemo.countDownLatch 每次执行 countDown - 1 操作，变成了 0 之后所有线程唤醒执行
        countDownLatch.await();
        System.out.println("攻城成功!!!!!");
        executorService.shutdown();
    }

    /**
     * 跑步比赛，计时开始
     * 运动员进行跑步比赛时，假设有 5 个运动员参与比赛，裁判员在终点会为这 5 个运动员分别计时
     * @date 2022/9/26 14:26 
     */
    @Test
    public void test002() throws InterruptedException {
        // 裁判开始信号
        CountDownLatch startSignal = new CountDownLatch(1);
        // 运动员跑步完成信号
        CountDownLatch doneSignal = new CountDownLatch(5);
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            final int c = i;
            executorService.execute(() -> {
                try {
                    // 让所有的运动员等待阻塞在这里，直到信号发出
                    startSignal.await();
                    System.out.println(Thread.currentThread().getName() + "迈开步子使劲跑");
                    TimeUnit.SECONDS.sleep(c);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // 跑完后
                    doneSignal.countDown();
                    System.out.println(Thread.currentThread().getName() + "到达终点");
                }
            });
        }
        System.out.println("裁判员发号施令啦！！！");
        startSignal.countDown();
        //等待所有的运动员跑完
        doneSignal.await();
        System.out.println("所有运动员到达终点，比赛结束!");
        executorService.shutdown();
    }
}
