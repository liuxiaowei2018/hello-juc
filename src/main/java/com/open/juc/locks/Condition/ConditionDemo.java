package com.open.juc.locks.Condition;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * @author liuxiaowei
 * @date 2022年09月26日 14:39
 * @Description
 */
public class ConditionDemo implements Condition {

    /**
     * 与调用锁对象的wait方法一样，会进入到等待状态
     * 但是这里需要调用Condition的signal或signalAll方法进行唤醒
     * 同时，等待状态下是可以响应中断的
     */
    @Override
    public void await() throws InterruptedException {

    }

    @Override
    public void awaitUninterruptibly() {

    }

    /**
     * 等待指定时间，如果在指定时间（纳秒）内被唤醒，会返回剩余时间
     * 如果超时，会返回0或负数，可以响应中断
     * @param nanosTimeout
     * @return long
     */
    @Override
    public long awaitNanos(long nanosTimeout) throws InterruptedException {
        return 0;
    }

    /**
     * 等待指定时间（可以指定时间单位），如果等待时间内被唤醒，返回true，否则返回false，可以响应中断
     * @param time
     * @param unit
     * @return boolean
     */
    @Override
    public boolean await(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    /**
     * 可以指定一个明确的时间点，如果在时间点之前被唤醒，返回true，否则返回false，可以响应中断
     * @param deadline
     * @return boolean
     */
    @Override
    public boolean awaitUntil(Date deadline) throws InterruptedException {
        return false;
    }

    /**
     * 唤醒一个处于等待状态的线程，注意还得获得锁才能接着运行
     */
    @Override
    public void signal() {

    }

    /**
     * 同上，但是是唤醒所有等待线程
     */
    @Override
    public void signalAll() {

    }
}
