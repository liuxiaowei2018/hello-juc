package com.open.project.delayedretry;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liuxiaowei
 * @date 2022年11月07日 17:04
 * @Description
 */
@Slf4j
public class TslSyncTaskTest {

    public static void main(String[] args) throws InterruptedException {
        String url = "http://localhost:10086/create";
        String json = "json data";
        RetryQueueStarter retryQueueStarter = RetryQueueStarter.getInstance();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, 20, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2000),
                new ThreadFactory() {
                    final AtomicInteger threadNumber = new AtomicInteger(1);
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r,
                                "retry-" + threadNumber.getAndIncrement());
                        if (t.getPriority() != Thread.NORM_PRIORITY) {
                            t.setPriority(Thread.NORM_PRIORITY);
                        }
                        return t;
                    }
                }, new ThreadPoolExecutor.CallerRunsPolicy());
        retryQueueStarter.setRetryQueuePool(threadPoolExecutor);
        retryQueueStarter.init();
        RetryTaskManager.addRetryTask(new RetryTask(new TslSyncTask(url, json, "2")));
        RetryTaskManager.addRetryTask(new RetryTask(new TslSyncTask(url, json, "1")));
        TimeUnit.SECONDS.sleep(6);
        retryQueueStarter.destroy();
        TimeUnit.SECONDS.sleep(1);
        RetryTaskManager.addRetryTask(new RetryTask(new TslSyncTask(url, json, "3")));

        synchronized (TslSyncTaskTest.class){
            TslSyncTaskTest.class.wait();
        }
    }

}