package com.open.project.delayedretry;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author liuxiaowei
 * @date 2022年11月07日 16:59
 * @Description
 */
@Slf4j
public class RetryQueueStarter {

    private static final RetryQueueStarter retryQueueStarter;

    static {
        retryQueueStarter = new RetryQueueStarter();
    }

    private RetryQueueStarter() {}

    public static RetryQueueStarter getInstance() {
        return retryQueueStarter;
    }

    private ThreadPoolExecutor retryQueuePool;

    /**
     * 初始化队列监听
     *
     * @throws Exception
     */
    public void init() {
        startThread();
    }

    /**
     * 销毁线程池
     *
     * @throws Exception
     */
    public void destroy() {
        retryQueuePool.shutdown();
        storeTask();
    }

    public void storeTask() {
        Object[] objects = RetryTaskManager.getRetryTaskQueue().toArray();
        for (int i = 0; i < objects.length; i++) {
            Object object = objects[i];
            log.info("remain task: {}", object.toString());
        }
    }

    private void startThread() {
        log.info("init retry task delay queue ...");
        final DelayQueue<RetryTask> retryTaskQueue = RetryTaskManager.getRetryTaskQueue();
        new Thread(() -> {
            while (true) {
                try {
                    if (!isShutdown()) {
                        RetryTask task = retryTaskQueue.take();
                        if (task != null) {
                            if (!isShutdown()) {
                                retryQueuePool.submit(task);
                            } else {
                                log.info("retry thread pool is shutdown, task: {}", task.toString());
                            }
                        }
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    log.error("task exec error: {}", e.getMessage());
                }
            }
        }, "RetryQueueStarter-Thread").start();
    }

    public void setRetryQueuePool(ThreadPoolExecutor retryQueuePool) {
        this.retryQueuePool = retryQueuePool;
    }

    public boolean isShutdown() {
        return this.retryQueuePool.isShutdown();
    }
}
