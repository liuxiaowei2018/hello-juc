package com.open.project.producerAndConsumer;

import lombok.Data;

/**
 * @author liuxiaowei
 * @date 2022年11月14日 11:45
 * @Description
 */
@Data
public class Consumer02 extends Thread {

    // 每次生产的数量
    private int num;

    //所属的仓库
    public AbstractStorage02 abstractStorage;

    public Consumer02(AbstractStorage02 abstractStorage) {
        this.abstractStorage = abstractStorage;
    }

    @Override
    public void run() {
        consume(num);
    }

    // 调用仓库Storage的生产函数
    public void consume(int num) {
        abstractStorage.consume(num);
    }
}
