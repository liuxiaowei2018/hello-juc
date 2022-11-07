package com.open.juc.BlockingQueue;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.concurrent.*;

/**
 * @author liuxiaowei
 * @date 2022年11月07日 16:02
 * @Description BlockingQueue有这几种类型：ArrayBlockingQueue、LinkedBlockingQueue、SynchronousQueue、PriorityBlockingQueue、DelayedWorkQueue。
 */
@Slf4j
public class BlockingQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        //testBlockingQueue();
    }

    /**
     * ArrayBlockingQueue与线程池
     */
    private static void arrayQueue() {
        System.out.println("=======ArrayBlockingQueue======");
        Executor executors = new ThreadPoolExecutor(
                2, 3, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(2));
        // 创建一个corePoolSize为2，maximumPoolSize为3的线程池。执行6个任务则过程如下:
        // 1.任务1和2在核心线程中执行；
        // 2.任务3和4进来时，放到ArrayBlockingQueue缓存队列中，并且只能放2个（ArrayBlockingQueue设置的大小为2）；
        // 3.任务5和6进来的时候，任务5新建线程来执行任务，已经达到最大线程数3，所以任务6拒绝；
        // 4.当有线程执行完的时候，再将任务3和4从队列中取出执行
    }

    /**
     * BlockingQueue常用方法测试
     */
    private static void testBlockingQueue() throws InterruptedException {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);

        in(queue);
        out(queue);
        other(queue);
    }

    /**
     * 其它方法
     * @param queue
     */
    private static void other(ArrayBlockingQueue<Integer> queue) {
        // 获取队列中剩余的空间
        queue.remainingCapacity();
        // 判断队列中是否拥有该值
        queue.contains(1);
        // 从队列中移除指定的值
        queue.remove(1);
        // 获得队列中有多少值
        queue.size();
    }

    /**
     * 获取数据的方法
     * @param queue
     */
    private static void out(ArrayBlockingQueue<Integer> queue) throws InterruptedException {
        // 获取当前队列头部元素并从队列里面移除它(如果队列为空则返回null)
        queue.poll();
        queue.poll(1,TimeUnit.SECONDS);

        // 获取当前队列头部元素并从队列里面移除它
        // 如果队列为空则阻塞当前线程直到队列不为空然后返回元素
        // 如果在阻塞时被其他线程设置了中断标志，则被阻塞线程会抛出InterruptedException异常而返回
        queue.take();

        // 一次性从BlockingQueue获取（会删除对象）所有可用的数据对象（可指定获取数据的个数）
        queue.drainTo(Arrays.asList(1,2));
    }

    /**
     * 放入数据的方法
     * @param queue
     */
    private static void in(ArrayBlockingQueue<Integer> queue) throws InterruptedException {
        // 向 队列尾部 插入一个元素。该方法是非阻塞的。
        // 如果队列中有空闲：插入成功后返回 true。
        // 如果队列己满：丢弃当前元素然后返回false。
        // 如果e元素为null：抛出NullPointerException异常。
        queue.offer(1);

        // 在指定的时间内，还不能往队列中加入BlockingQueue，则返回失败
        queue.offer(1,1,TimeUnit.SECONDS);

        // 内部调用offer方法(add失败时抛出异常)
        queue.add(2);

        // 向队列尾部插入一个元素。
        // 如果队列中有空闲：插入后直接返回。
        // 如果队列己满：阻塞当前线程，直到队列有空闲插入成功后返回。
        // 如果在阻塞时被其他线程设置了中断标志：被阻塞线程会抛出InterruptedException异常而返回。
        // 如果e元素为null：抛出NullPointerException异常。
        queue.put(3);
    }

    /**
     * ArrayBlockingQueue
     * 基于数组的FIFO队列；有界；创建时必须指定大小；
     * 入队和出队共用一个可重入锁。默认使用非公平锁。
     *
     * @return java.util.concurrent.ArrayBlockingQueue
     */
    public ArrayBlockingQueue arrayBlockingQueue() {
        return new ArrayBlockingQueue(10);
    }

    /**
     * LinkedBlockingQueue
     * 基于链表的FIFO队列；有/无界；默认大小是 Integer.MAX_VALUE（无界），可自定义（有界）
     * 两个重入锁分别控制元素的入队和出队，用Condition进行线程间的唤醒和等待。
     * 吞吐量通常要高于ArrayBlockingQueue
     * 默认大小的LinkedBlockingQueue将导致所有 corePoolSize 线程都忙时新任务在队列中等待.这样，创建的线程不会超过 corePoolSize。（因此，maximumPoolSize 的值也就无效了）
     * 当每个任务相互独立时，适合使用无界队列；例如， 在 Web 页服务器中。这种排队可用于处理瞬态突发请求，当命令以超过队列所能处理的平均数连续到达时，此策略允许无界线程具有增长的可能性。
     *
     * @return java.util.concurrent.LinkedBlockingQueue
     */
    public LinkedBlockingQueue linkedBlockingQueue() {
        return new LinkedBlockingQueue(10);
    }

    /**
     * SynchronousQueue
     *  无缓存的等待队列；无界；可认为大小为0。
     *  不保存提交任务，直接提交出去。若超出corePoolSize个任务，直接创建新线程来执行任务，直到(corePoolSize＋新建线程)> maximumPoolSize。
     *  吞吐量通常要高于LinkedBlockingQueue
     *
     * @return java.util.concurrent.SynchronousQueue
     */
    public SynchronousQueue synchronousQueue() {
        return new SynchronousQueue(true);
    }

    /**
     * PriorityBlockingQueue
     *  基于链表的优先级队列；有/无界；默认大小是 Integer.MAX_VALUE，可自定义；
     *  类似于LinkedBlockingQueue，但是其所含对象的排序不是FIFO，而是依据对象的自然顺序或者构造函数的Comparator决定。
     *
     * @return java.util.concurrent.PriorityBlockingQueue
     */
    public PriorityBlockingQueue priorityBlockingQueue() {
        return new PriorityBlockingQueue(10);
    }

    /**
     * DelayQueue
     *
     * @return java.util.concurrent.DelayQueue
     */
    public DelayQueue delayQueue() {
        return new DelayQueue(Arrays.asList("1","2","3"));
    }
}
