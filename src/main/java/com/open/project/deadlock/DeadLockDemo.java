package com.open.project.deadlock;

/**
 * @author liuxiaowei
 * @date 2022年11月14日 11:54
 * @Description
 */
public class DeadLockDemo implements Runnable {

    int flag = 1;
    static Object o1 = new Object();
    static Object o2 = new Object();

    public static void main(String[] args) {
        DeadLockDemo deadLock1 = new DeadLockDemo();
        DeadLockDemo deadLock2 = new DeadLockDemo();
        deadLock1.flag = 1;
        deadLock2.flag = 0;
        Thread t1 = new Thread(deadLock1);
        Thread t2 = new Thread(deadLock2);
        t1.start();
        t2.start();
    }


    @Override
    public void run() {
        System.out.println("flag = " + flag);
        if (flag == 1) {
            synchronized (o1) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o2) {
                    System.out.println("线程1成功拿到俩把锁");
                }
            }
        }

        if (flag == 0) {
            synchronized (o2) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o1) {
                    System.out.println("线程2成功拿到俩把锁");
                }
            }
        }
    }
}
