package com.open.juc.locks.ReentrantLock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author liuxiaowei
 * @date 2022年11月08日 10:51
 * @Description
 * ReentrantReadWriteLock 是 ReadWriteLock 的实现类，最主要的有两个方法：readLock() 和 writeLock() 用来获取读锁和写锁
 * 读读共享、其他都互斥（写写互斥、读写互斥、写读互斥）
 */
public class ReentrantReadWriteLockDemo {

    private static final ReentrantReadWriteLock READ_WRITE_LOCK = new ReentrantReadWriteLock(false);
    private static final ReentrantReadWriteLock.ReadLock READ_LOCK = READ_WRITE_LOCK.readLock();
    private static final ReentrantReadWriteLock.WriteLock WRITE_LOCK = READ_WRITE_LOCK.writeLock();

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> read()).start();
        new Thread(() -> read()).start();
        new Thread(() -> write()).start();
        new Thread(() -> write()).start();
    }

    private static void read() {
        READ_LOCK.lock();
        try {
            System.out.println(Thread.currentThread().getName()+ "得到读锁，正在读取");
            Thread.sleep(500);
        } catch (InterruptedException  e) {
            e.printStackTrace();
        } finally {
            READ_LOCK.unlock();
            System.out.println(Thread.currentThread().getName() + "释放读锁");
        }
    }

    private static void write() {
        WRITE_LOCK.lock();
        try {
            System.out.println(Thread.currentThread().getName()+ "得到写锁，正在写入");
            Thread.sleep(500);
        } catch (InterruptedException  e) {
            e.printStackTrace();
        } finally {
            WRITE_LOCK.unlock();
            System.out.println(Thread.currentThread().getName() + "释放写锁");
        }
    }
}
