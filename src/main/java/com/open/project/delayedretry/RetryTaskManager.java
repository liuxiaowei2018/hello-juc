package com.open.project.delayedretry;

import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.DelayQueue;

/**
 * @author liuxiaowei
 * @date 2022年11月07日 16:46
 * @Description 重试任务管理器
 */
@Slf4j
public class RetryTaskManager {

    private static DelayQueue<RetryTask> retryTaskQueue = new DelayQueue<>();

    /**
     * 添加任务到队列
     *
     * @param retryTask
     */
    private static void putRetryTask(RetryTask retryTask) {
        RetryTaskManager.getRetryTaskQueue().put(retryTask);
    }

    /**
     * 将传过来的对象进行通知次数判断，之后决定是否放在任务队列中
     *
     * @param retryTask
     * @throws Exception
     */
    public static void addRetryTask(RetryTask retryTask) {

        if (retryTask == null) {
            log.info("retry task is null, return;");
            return;
        }

        if (RetryQueueStarter.getInstance().isShutdown()) {
            log.info("retry thread pool is shutdown, retry task {} put failed", retryTask.toString());
            retryTask.interrupt();
            return;
        }

        if (!retryTask.hasChance()) {
            log.info("retry task {} has no chance, return;", retryTask.toString());
            return;
        }

        retryTask.nextTime();
        putRetryTask(retryTask);
        log.info("retry task {} put success", retryTask.toString());
    }

    public static DelayQueue<RetryTask> getRetryTaskQueue() {
        return retryTaskQueue;
    }

}
