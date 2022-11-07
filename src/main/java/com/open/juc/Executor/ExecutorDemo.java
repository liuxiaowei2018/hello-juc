package com.open.juc.Executor;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author liuxiaowei
 * @date 2022年11月07日 14:55
 * @Description
 */
@Slf4j
public class ExecutorDemo {

    private int corePool = 20;
    private int maxPool = 50;
    private int keepAliveSeconds = 300;
    private int queueCapacity = 500;

    /**
     * Java线程池
     * @param corePoolSize 核心线程数(即便是线程池里没有任何任务，也会有corePoolSize个线程在候着等任务)
     * @param maximumPoolSize 最大线程数(超过此数量，会触发拒绝策略)
     * @param keepAliveTime 线程的存活时间(当线程池里的线程数大于corePoolSize时，如果等了keepAliveTime时长还没有任务可执行，则线程退出)
     * @param unit 指定keepAliveTime的单位
     * @param workQueue 阻塞队列，提交的任务将会被放到这个队列里
     * @param threadFactory 线程工厂，用来创建线程(默认工厂的线程名字：pool-1-thread-1)
     * @param handler 拒绝策略(默认拒绝策略为AbortPolicy。即：不执行此任务，而且抛出一个运行时异常)
     * @return ThreadPoolExecutor
     */
    public ThreadPoolExecutor executor(int corePoolSize,
                                                 int maximumPoolSize,
                                                 long keepAliveTime,
                                                 TimeUnit unit,
                                                 BlockingQueue<Runnable> workQueue,
                                                 ThreadFactory threadFactory,
                                                 RejectedExecutionHandler handler) {
        return new ThreadPoolExecutor(
                corePool, maxPool,
                keepAliveSeconds, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(queueCapacity),
                new ThreadFactoryBuilder().setNamePrefix("pool-thread-").build());
    }

    public static void main(String[] args) {
        // 执行线程
        //execute();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10,
                200, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(4));
        //for (int i = 0; i < 15; i++) { 15>10+4 线程默认拒绝策略 java.util.concurrent.RejectedExecutionException
        for (int i = 0; i < 14; i++) {
                MyTask myTask = new MyTask(i);
                executor.execute(myTask);
                // 线程池中线程数目：5，队列中等待执行的任务数目：1，已执行完别的任务数目：0
                // -->当线程池中线程的数目大于5时，便将任务放入任务缓存队列里面，当任务缓存队列满了之后，便创建新的线程
                System.out.println("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" +
                        executor.getQueue().size() + "，已执行完别的任务数目：" + executor.getCompletedTaskCount());
        }
        executor.shutdown();
    }




    static class MyTask implements Runnable {

        private int taskNum;

        public MyTask(int num) {
            this.taskNum = num;
        }

        @Override
        public void run() {
            System.out.println("正在执行task " + taskNum);
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("-----------------------task " + taskNum + "执行完毕");
        }
    }


    /**
     * 执行线程
     */
    private static void execute() {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        // 提交线程调用如下任一方法即可:
        // void execute(Runnable command);
        // Future<?> submit(Runnable task)
        // <T> Future<T> submit(Callable<T> task)
        // <T> Future<T> submit(Runnable task, T result)
        executor.execute(ExecutorDemo::myRun);
    }

    public static void myRun() {
        System.out.println("hello world");
    }

}
