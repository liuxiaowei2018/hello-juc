package com.open.project.delayedretry;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author liuxiaowei
 * @date 2022年11月07日 16:48
 * @Description 基于Delayed的延时任务
 */
@Slf4j
public class RetryTask implements Delayed,Runnable {

    private RetryBean retryBean = new RetryBean();
    private RetryInterval retryInterval;
    private Task task;
    private long execTime;

    /**
     * 当前任务是否被中断
     */
    private volatile AtomicBoolean isInterrupted = new AtomicBoolean(false);

    public RetryTask(Task task) {
        this(task, new DefaultRetryInterval());
    }

    public RetryTask(Task task, RetryInterval retryInterval) {
        this.task = task;
        this.retryInterval = retryInterval;
    }

    /**
     * 设置最大重试次数
     *
     * @param times
     */
    public void setMaxRetryTimes(int times) {
        if (times > 0) {
            this.retryBean.setLeastRetryTimes(times);
        }
    }

    /**
     * 剩余时间
     *
     * @param unit
     * @return long
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.execTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        long anotherExecTime = ((RetryTask) o).getRetryBean().getNextRetryTime().getTime();
        return this.execTime > anotherExecTime ? 1 : (this.execTime < anotherExecTime ? -1 : 0);
    }

    @Override
    public void run() {
        try {
            log.info("task {} start", this.task.toString());
            task.fire();
            log.info("task {} exec success", this.task.toString());
            task.onceRetry();
        } catch (Exception e) {
            tryAgain();
            log.error("task {} exec error: {}", this.task.toString(), e.getMessage());
        } finally {
            log.info("task {} end", this.task.toString());
        }
    }

    private void tryAgain() {
        RetryTaskManager.addRetryTask(this);
    }

    private RetryBean getRetryBean() {
        return retryBean;
    }

    /**
     * 判断当前任务是否还有重试机会
     *
     * @return
     */
    public boolean hasChance() {
        boolean result = this.retryBean.hasChance();
        if (!result) {
            log.info("task {} has retry {} times", this.task.toString(), this.getRetryBean().getLeastRetryTimes());
            try {
                task.exceedTimesRetry();
            } catch (Exception e) {
                log.error("task {} has retry {} times, callback error: {}", this.task.toString(),
                        this.getRetryBean().getLeastRetryTimes(), e.getMessage());
            }
        }
        return result;
    }

    /**
     * 下次执行任务时间
     */
    public void nextTime() {
        Date interval = retryInterval.interval(new Date());
        retryBean.setNextRetryTime(interval);
        this.execTime = interval.getTime();
        this.retryBean.reduceChange();
        log.info("task {} retry {} times, next exec time: {}, timestamp: {}", this.task.toString(), this.getRetryBean().getRetryTimes(),
                interval, this.execTime);
    }

    /**
     * 任务中断
     */
    public void interrupt() {
        try {
            boolean b = isInterrupted.compareAndSet(false, true);
            if (b) {
                task.interrupt();
                log.info("task {} interrupt success", this.task.toString());
            }
        } catch (Exception e) {
            log.error("task {} interrupt error: {} ", this.task.toString(), e.getMessage());
        }
    }

    @Override
    public String toString() {
        return task.toString();
    }
}
