package com.open.juc;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author liuxiaowei
 * @date 2022年09月26日 15:38
 * @Description
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloConcurrentApplication.class)
public class CompletableFutureTest {

    /**
     * Future测试
     *
     * @date 2022/9/29 12:13
     */
    @Test
    public void test() throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> cf = executorService.submit(() -> {
            System.out.println(Thread.currentThread() + " start,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            if (true) {
                throw new RuntimeException("test");
            } else {
                System.out.println(Thread.currentThread() + " exit,time->" + System.currentTimeMillis());
                return 1;
            }
        });
        System.out.println("main thread start,time->" + System.currentTimeMillis());
        // 等待子任务执行完成,如果已完成则直接返回结果
        // 如果执行任务异常，则get方法会把之前捕获的异常重新抛出
        System.out.println("run result->" + cf.get());
        System.out.println("main thread exit,time->" + System.currentTimeMillis());

    }

    @Test
    public void test01() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            try {
                //Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("supplyAsync " + Thread.currentThread().getName());
            return "1";
        }, executor).thenApplyAsync(s -> {
            System.out.println(s + "thenApplyAsync1");
            return "2";
        }, executor);

        cf.thenRunAsync(() -> {
            System.out.println("thenRunAsync2");
        });
        cf.thenRun(() -> {
            System.out.println("thenRun1");
        });
        cf.thenRun(() -> {
            System.out.println(Thread.currentThread());
            System.out.println("thenRun2");
        });
    }
}
