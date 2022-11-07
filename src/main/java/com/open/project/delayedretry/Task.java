package com.open.project.delayedretry;

/**
 * @author liuxiaowei
 * @date 2022年11月07日 16:44
 * @Description 自定义Task接口
 */
public interface Task extends RetryTaskCallback{

    /**
     * 业务逻辑
     *
     * @throws Exception
     */
    void fire() throws Exception;

    /**
     * 重写此方法，输出有效的日志信息，例如id等，
     * 此方法会在任务执行时被调用，方便查看日志
     *
     * @return
     */
    @Override
    String toString();
}
