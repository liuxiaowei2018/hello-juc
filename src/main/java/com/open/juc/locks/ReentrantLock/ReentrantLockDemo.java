package com.open.juc.locks.ReentrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liuxiaowei
 * @date 2022年09月26日 14:51
 * @Description
 *  ReentrantLock主要利用AQS队列来实现。它支持公平锁和非公平锁。
 *  AQS队列使用了CAS，所以ReentrantLock有CAS的优缺点。优点：性能高。缺点：CPU占用高。
 *  ReentrantLock的流程:
 *    state初始化为0，表示未锁定状态
 *    A线程lock()时，会调用tryAcquire()获取锁并将state+1
 *    其他线程tryAcquire获取锁会失败，直到A线程unlock() 到state=0，其他线程才有机会获取该锁。
 *    A释放锁之前，自己可以重复获取此锁（state累加），这就是可重入的概念。
 * 注意：获取多少次锁就要释放多少次锁，保证state能回到0
 *
 */
public class ReentrantLockDemo {

    private static int i = 0;

    public void run() throws InterruptedException {
        //可重入锁ReentrantLock
        Lock testLock = new ReentrantLock();
        Runnable action = () -> {
            for (int j = 0; j < 100; j++) {
                testLock.lock();
                try {
                    i++;
                    System.out.println(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    testLock.unlock();
                }
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
