package com.open.juc.locks;

/**
 * @author liuxiaowei
 * @date 2022年09月26日 15:29
 * @Description
 */
public class DeadLockDemo implements Runnable{

    private boolean flag;
    private Object lock1;
    private Object lock2;

    public DeadLockDemo(Object lock1, Object lock2, boolean flag) {
        this.lock1 = lock1;
        this.lock2 = lock2;
        this.flag = flag;
    }

    @Override
    public void run() {
        if (flag) {
            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName()+"->拿到锁1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"等待锁2释放...");
                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName()+"->拿到锁2");
                }
            }
        }
        if (!flag) {
            synchronized (lock2) {
                System.out.println(Thread.currentThread().getName()+"->拿到锁2");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"等待锁1释放...");
                synchronized (lock1) {
                    System.out.println(Thread.currentThread().getName()+"->拿到锁1");
                }
            }
        }
    }
}
