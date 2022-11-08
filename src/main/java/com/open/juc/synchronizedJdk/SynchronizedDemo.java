package com.open.juc.synchronizedJdk;

/**
 * @author liuxiaowei
 * @date 2022年11月08日 12:15
 * @Description Synchronized关键字
 */
public class SynchronizedDemo {

    public static void main(String[] args) {
        //test1();
        //test2();
        //test3();
    }

    /**
     * synchronized修饰Class对象，用不同对象访问
     * 结果同步:因为synchronized修饰的是一个类对象，是惟一的
     */
    private static void test3() {
        new Thread(new MyThread2()).start();
        new Thread(new MyThread2()).start();
    }

    /**
     * synchronized修饰普通方法，用不同对象访问
     * 结果不同步:因为synchronized修饰普通方法时锁对象是this对象，使用两个对象去访问，不是同一把锁
     */
    private static void test2() {
        new Thread(new MyThread()).start();
        new Thread(new MyThread()).start();
        // result: 1 1 2 2
    }

    /**
     * synchronized修饰普通方法，用同一对象访问
     * 结果同步:因为synchronized修饰普通方法时锁对象是this对象，使用一个对象去访问，是同一把锁
     */
    private static void test1() {
        MyThread myThread = new MyThread();
        new Thread(myThread).start();
        new Thread(myThread).start();
        // result: 1 2 1 2
    }
}

class MyThread implements Runnable{

    @Override
    public synchronized void run() {
        System.out.println(1);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(2);
    }
}

class MyThread2 implements Runnable{

    @Override
    public void run() {
        synchronized (MyThread2.class) {
            System.out.println(1);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(2);
        }
    }
}


