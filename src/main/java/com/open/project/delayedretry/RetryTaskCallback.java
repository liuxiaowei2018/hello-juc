package com.open.project.delayedretry;

/**
 * @author liuxiaowei
 * @date 2022年11月07日 16:43
 * @Description 重试任务回调接口
 */
public interface RetryTaskCallback {

    /**
     * 每次重试回调
     *
     * @throws Exception
     */
    void onceRetry() throws Exception;

    /**
     * 超过重试次数回调
     *
     * @throws Exception
     */
    void exceedTimesRetry() throws Exception;

    /**
     * 中断回调
     *
     * @throws Exception
     */
    void interrupt() throws Exception;
}
