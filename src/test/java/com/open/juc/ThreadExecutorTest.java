package com.open.juc;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.*;
import java.util.stream.IntStream;
import com.open.HelloConcurrentApplication;

/**
 * @author liuxiaowei
 * @date 2022年09月26日 13:18
 * @Description
 * 1.线程池中线程中异常尽量手动捕获
 * 2.通过设置ThreadFactory的UncaughtExceptionHandler可以对未捕获的异常做保底处理，
 *   通过execute提交任务，线程依然会中断，而通过submit提交任务，可以获取线程执行结果，线程异常会在get执行结果时抛出。
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloConcurrentApplication.class)
public class ThreadExecutorTest {

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1,
            60, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(200),
            new ThreadFactoryBuilder().setNamePrefix("customThread-")
                    // 捕获异常
                    .setUncaughtExceptionHandler((t, e) -> System.out.println("UncaughtExceptionHandler捕获到：" + t.getName() + "发生异常" + e.getMessage())).build());

    @Test
    public void test001() {
        // 新建一个只有一个线程的线程池，每隔0.1s提交一个任务，任务中是一个1/0的计算
        IntStream.rangeClosed(1, 5).forEach(i -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadPoolExecutor.execute(() -> {
                int j = 1 / 0;
            });
        });
        // 可见每次执行的线程都不一样，之前的线程都没有复用。原因是出现了未捕获的异常。
    }

    @Test
    public void test002() {
        // 与 test001() 相比 catch住了异常
        IntStream.rangeClosed(1, 5).forEach(i -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadPoolExecutor.execute(() -> {
                try {
                    int j = 1 / 0;
                } catch (Exception e) {
                    System.out.println(Thread.currentThread().getName() + " " + e.getMessage());
                }
            });
        });
        // 可见当异常捕获了，线程就可以复用了
    }

    @Test
    public void test003() {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            threadPoolExecutor.execute(() -> {
                System.out.println("线程" + Thread.currentThread().getName() + "执行");
                int j = 1 / 0;
            });
        });
        // 通过UncaughtExceptionHandler想将异常吞掉使线程复用不可行
    }

    @Test
    public void test() {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Future<?> future = threadPoolExecutor.submit(() -> {
                System.out.println("线程" + Thread.currentThread().getName() + "执行");
                int j = 1 / 0;
            });
            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        // 通过submit提交线程可以屏蔽线程中产生的异常，达到线程复用。当get()执行结果时异常才会抛出
    }

}
