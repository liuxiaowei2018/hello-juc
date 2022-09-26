package com.open.juc.CountDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * @author liuxiaowei
 * @date 2022年09月26日 14:06
 * @Description
 */
public class CountDownLatchDemo implements Runnable{

    private CountDownLatch countDownLatch;

    public CountDownLatchDemo(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            System.out.println("name = " + Thread.currentThread().getName() + "上阵杀敌，冲鸭");
        } finally {
            countDownLatch.countDown();
        }
    }
}
