package com.open.juc.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author liuxiaowei
 * @date 2022年09月26日 14:54
 * @Description
 */
public class LockDemo implements Lock {

    @Override
    public void lock() {
        // 获取锁，拿不到锁会阻塞，等待其他线程释放锁，获取到锁后返回
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        // 同上，但是等待过程中会响应中断
    }

    @Override
    public boolean tryLock() {
        // 尝试获取锁，但是不会阻塞，如果能获取到会返回true，不能返回false
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        // 尝试获取锁，但是可以限定超时时间，如果超出时间还没拿到锁返回false，否则返回true，可以响应中断
        return false;
    }

    @Override
    public void unlock() {
        // 释放锁
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
