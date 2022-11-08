package com.open.juc.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author liuxiaowei
 * @date 2022年11月08日 10:19
 * @Description AtomicIngeter的常用方法如下：
 * int addAndGet(int delta): 以原子的方式将输入的数值与实例中的值相加，并返回结果。
 * boolean compareAndSet(int expect, int update): 如果输入的值等于预期值，则以原子方式将该值设置为输入的值。
 * int getAndIncrement(): 以原子的方式将当前值加 1，注意，这里返回的是自增前的值，也就是旧值。
 * void lazySet(int newValue): 最终会设置成newValue,使用lazySet设置值后，可能导致其他线程在之后的一小段时间内还是可以读到旧的值。
 * int getAndSet(int newValue): 以原子的方式设置为newValue,并返回旧值。
 */
public class AtomicIntegerDemo {

    static AtomicInteger ai = new AtomicInteger(1);

    public static void main(String[] args) {
        System.out.println(ai.getAndIncrement()); // 1
        System.out.println(ai.get()); // 2

    }
}


