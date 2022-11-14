package com.open.project.producerAndConsumer;

import java.util.LinkedList;

/**
 * @author liuxiaowei
 * @date 2022年11月14日 11:38
 * @Description
 */
public class ProducerAndConsumerDemo02 implements AbstractStorage02 {

    public static void main(String[] args) {
        // 仓库对象
        AbstractStorage02 abstractStorage = new ProducerAndConsumerDemo02();

        // 生产者对象
        Producer02 p1 = new Producer02(abstractStorage);
        Producer02 p2 = new Producer02(abstractStorage);
        Producer02 p3 = new Producer02(abstractStorage);
        Producer02 p4 = new Producer02(abstractStorage);
        Producer02 p5 = new Producer02(abstractStorage);
        Producer02 p6 = new Producer02(abstractStorage);
        Producer02 p7 = new Producer02(abstractStorage);

        // 消费者对象
        Consumer02 c1 = new Consumer02(abstractStorage);
        Consumer02 c2 = new Consumer02(abstractStorage);
        Consumer02 c3 = new Consumer02(abstractStorage);

        // 设置生产者产品生产数量
        p1.setNum(10);
        p2.setNum(10);
        p3.setNum(10);
        p4.setNum(10);
        p5.setNum(10);
        p6.setNum(10);
        p7.setNum(80);

        // 设置消费者产品消费数量
        c1.setNum(50);
        c2.setNum(20);
        c3.setNum(30);

        // 线程开始执行
        c1.start();
        c2.start();
        c3.start();

        p1.start();
        p2.start();
        p3.start();
        p4.start();
        p5.start();
        p6.start();
        p7.start();
    }

    /**
     * 仓库最大容量
     */
    private final int MAX_SIZE = 100;

    /**
     * 仓库存储的载体
     */
    private LinkedList warehouse = new LinkedList();

    /**
     * 生产
     *
     * @param num
     */
    @Override
    public void produce(int num) {
        // 同步
        synchronized (warehouse) {
            // 仓库剩余的容量不足以存放即将要生产的数量，暂停生产
            while (warehouse.size() + num > MAX_SIZE) {
                System.out.println("【要生产的产品数量】:" + num + "\t【库存量】:"
                        + warehouse.size() + "\t暂时不能执行生产任务!");
                try {
                    warehouse.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < num; i++) {
                warehouse.add(new Object());
            }
            System.out.println("【已经生产产品数】:" + num + "\t【现仓储量为】:" + warehouse.size());
            warehouse.notifyAll();
        }
    }

    /**
     * 消费
     *
     * @param num
     */
    @Override
    public void consume(int num) {
        synchronized (warehouse) {
            // 不满足消费条件
            while (num > warehouse.size()) {
                System.out.println("【要消费的产品数量】:" + num + "\t【库存量】:"
                        + warehouse.size() + "\t暂时不能执行生产任务!");
                try {
                    warehouse.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 消费条件满足，开始消费
            for (int i = 0; i < num; i++) {
                warehouse.remove();
            }
            System.out.println("【已经消费产品数】:" + num + "\t【现仓储量为】:" + warehouse.size());
            warehouse.notifyAll();
        }
    }

}
