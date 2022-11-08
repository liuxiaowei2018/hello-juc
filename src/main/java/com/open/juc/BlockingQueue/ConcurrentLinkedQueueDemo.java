package com.open.juc.BlockingQueue;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author liuxiaowei
 * @date 2022年11月08日 11:12
 * @Description ConcurrentLinkedQueue
 * 非阻塞（使用CAS） 性能高(特别是多个消费者时）
 */
public class ConcurrentLinkedQueueDemo {

    public static void main(String[] args) throws InterruptedException {

        int peopleNum = 10000; // 吃饭人数
        int tableNum = 10; // 饭桌数量

        ConcurrentLinkedQueue<String> linkedQueue = new ConcurrentLinkedQueue<>();
        CountDownLatch countDownLatch = new CountDownLatch(tableNum);

        for (int i = 1; i <= peopleNum; i++) {
            linkedQueue.offer("消费者_"+i);
        }
        // 执行10个线程从队列取出元素（10个桌子开始供饭）
        System.out.println("-----------------------------------开饭了-----------------------------------");
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(tableNum);
        for(int i=0;i<tableNum;i++) {
            executorService.submit(new Dinner("00" + (i+1), linkedQueue, countDownLatch));
        }
        // 计数器等待，知道队列为空（所有人吃完）
        countDownLatch.await();
        long time = System.currentTimeMillis() - start;
        System.out.println("-----------------------------------所有人已经吃完-----------------------------------");
        System.out.println("共耗时：" + time);
        // 停止线程池
        executorService.shutdown();
    }

    private static class Dinner implements Runnable{
        private String name;
        private ConcurrentLinkedQueue<String> queue;
        private CountDownLatch count;

        public Dinner(String name, ConcurrentLinkedQueue<String> queue, CountDownLatch count) {
            this.name = name;
            this.queue = queue;
            this.count = count;
        }

        @Override
        public void run() {
            //while (queue.size() > 0){
            while (!queue.isEmpty()){
                // 从队列取出一个元素 排队的人少一个
                System.out.println("【" +queue.poll() + "】----已吃完...， 饭桌编号：" + name);
            }
            count.countDown();//计数器-1
        }
    }

}
