package com.open.juc.BlockingQueue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author liuxiaowei
 * @date 2022年11月08日 11:03
 * @Description
 * 延迟队列DelayQueue是一个无界阻塞队列，它的队列元素只能在该元素的延迟已经结束（或者说过期）才能被出队。
 * DelayQueue队列元素必须是实现了Delayed接口的实例，该接口有一个getDelay方法需要实现，延迟队列就是通过实时的调用元素的该方法来判断当前元素是否延迟已经结束
 * DelayQueue是基于优先级队列来实现的，那肯定元素也要实现Comparable接口。因为Delayed接口继承了Comparable接口，所以实现Delayed的队列元素也必须要实现Comparable的compareTo方法。
 * 延迟队列就是以时间作为比较基准的优先级队列，这个时间即延迟时间，这个时间大都在构造元素的时候就已经设置好，随着程序的运行时间的推移，队列元素的延迟时间逐步到期，DelayQueue就能够基于延迟时间运用优先级队列并配合getDelay方法达到延迟队列中的元素在延迟结束时精准出队
 */
public class DelayQueueDemo {

    public static void main(String[] args) {
        DelayQueue<DelayTask> dq = new DelayQueue<>();

        // 入队四个元素，注意它们的延迟时间单位不一样。
        dq.offer(new DelayTask(5, TimeUnit.SECONDS));
        dq.offer(new DelayTask(2, TimeUnit.MINUTES));
        dq.offer(new DelayTask(700, TimeUnit.MILLISECONDS));
        dq.offer(new DelayTask(1000, TimeUnit.NANOSECONDS));

        while (dq.size() > 0) {
            try {
                System.out.println(dq.take());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}

class DelayTask implements Delayed {

    private long delay; // 延迟多少纳秒开始执行
    private TimeUnit unit;

    public DelayTask(long delay, TimeUnit unit){
        this.unit = unit;
        this.delay = TimeUnit.NANOSECONDS.convert(delay, unit);// 统一转换成纳秒计数
    }

    @Override
    public String toString() {
        return "DelayTask{" +
                "delay=" + delay +
                ", unit=" + unit +
                '}';
    }


    @Override
    public long getDelay(TimeUnit unit) {
        // 延迟剩余时间，单位unit指定
        return unit.convert(delay - System.currentTimeMillis(), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        if(this.getDelay(TimeUnit.NANOSECONDS) < o.getDelay(TimeUnit.NANOSECONDS)) // 都换算成纳秒计算
            return -1;
        else if(this.getDelay(TimeUnit.NANOSECONDS) > o.getDelay(TimeUnit.NANOSECONDS)) // 都换算成纳秒计算
            return 1;
        else
            return 0;

    }
}
