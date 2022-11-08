package com.open.juc.atomic;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author liuxiaowei
 * @date 2022年11月08日 10:23
 * @Description
 */
public class AtomicLongDemo {

    public static void main(String[] args) {
        Counter[] threads = new Counter[50];
        for(int i = 0; i < 50; i++){
            threads[i] = new Counter();
        }
        for(int i = 0; i < 50; i++){
            new Thread(threads[i]).start();
        }

    }
}

class Counter implements Runnable{
    private static AtomicLong atomicLong = new AtomicLong(0);
    @Override
    public void run() {
        System.out.println(atomicLong.incrementAndGet());
    }
}
