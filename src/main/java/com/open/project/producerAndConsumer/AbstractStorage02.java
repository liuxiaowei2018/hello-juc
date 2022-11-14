package com.open.project.producerAndConsumer;

/**
 * @author liuxiaowei
 * @date 2022年11月14日 11:46
 * @Description
 */
public interface AbstractStorage02 {

    void consume(int num);
    void produce(int num);
}
