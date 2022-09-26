package com.open.juc.CyclicBarrier;

import java.util.concurrent.*;

/**
 * @author liuxiaowei
 * @date 2022年09月26日 14:28
 * @Description
 */
public class CyclicBarrierDemo {

    private ExecutorService executorService;
    private CyclicBarrier cyclicBarrier;

    public CyclicBarrierDemo(CyclicBarrier cyclicBarrier) {
        executorService = Executors.newFixedThreadPool(cyclicBarrier.getParties());
        this.cyclicBarrier = cyclicBarrier;
    }

    public void run() {
        for (int i = 0; i < cyclicBarrier.getParties() * 3; i++) {
            int current = i;
            executorService.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "运动员，准备就绪");
                    // 每个运动员执行到这就会 对 N - 1，变为 0 则放一波线程运行，然后重置 N
                    cyclicBarrier.await();
                    System.out.println(Thread.currentThread().getName() + "运动员，开跑");
                    TimeUnit.SECONDS.sleep(current);
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
    }

    //等到所有的线程都到达指定的临界点
    //await() throws InterruptedException, BrokenBarrierException

    //与上面的await方法功能基本一致，只不过这里有超时限制，阻塞等待直至到达超时时间为止
    //await(long timeout, TimeUnit unit) throws InterruptedException,BrokenBarrierException, TimeoutException

    //获取当前有多少个线程阻塞等待在临界点上
    //int getNumberWaiting()

    //用于查询阻塞等待的线程是否被中断
    //boolean isBroken()

    //将屏障重置为初始状态。如果当前有线程正在临界点等待的话，将抛出BrokenBarrierException。
    //void reset()

}
