package com.open.juc.locks.ReentrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liuxiaowei
 * @date 2022年09月26日 14:51
 * @Description
 */
public class ReentrantLockDemo {

    private static int i = 0;

    public void run() throws InterruptedException {
        //可重入锁ReentrantLock
        Lock testLock = new ReentrantLock();
        Runnable action = () -> {
            for (int j = 0; j < 100; j++) {
                testLock.lock();
                i++;
                System.out.println(i);
                testLock.unlock();
            }
        };
        //线程1
        new Thread(action).start();
        //线程2
        new Thread(action).start();
        //等上面两个线程跑完
        Thread.sleep(1000);
        System.out.println("最终结果: "+i);
    }
}
