package com.open.juc.locksupport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author liuxiaowei
 * @date 2022年09月26日 14:55
 * @Description
 */
public class LockSupportDemo {

    public void run() throws InterruptedException {
        // 主线程的Thread对象
        Thread t = Thread.currentThread();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("主线程继续运行");
                LockSupport.unpark(t);
                //t.interrupt(); 发送中断信号也可以恢复运行
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println("主线程被挂起");
        LockSupport.park();
        System.out.println("主线程继续运行!!");
    }
}
