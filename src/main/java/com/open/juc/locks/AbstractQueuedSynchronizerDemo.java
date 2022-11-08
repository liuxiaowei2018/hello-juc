package com.open.juc.locks;

/**
 * @author liuxiaowei
 * @date 2022年11月08日 12:05
 * @Description AQS
 * 基于CLH队列，用volatile修饰共享变量state，线程通过CAS去改变状态，成功则获取锁成功，失败则进入等待队列，等待被唤醒
 * CLH: CLH队列是一个虚拟的双向队列，虚拟的双向队列即不存在队列实例，仅存在节点之间的关联关系。AQS将每一条请求共享资源的线程封装成一个CLH锁队列的一个结点（Node），来实现锁的分配
 */
public class AbstractQueuedSynchronizerDemo {

//    AQS 定义了两种资源共享方式：
//    1.Exclusive
//      独占，只有一个线程能执行。
//    如：ReentrantLock
//    2.hare
//      共享，多个线程可以同时执行。
//    如：Semaphore、CountDownLatch、ReadWriteLock，CyclicBarrier
//    不同的自定义的同步器争用共享资源的方式也不同。
//
//    AQS底层使用了模板方法模式，如果需要自定义同步器一般的方式是这样（模板方法模式很经典的一个应用）：
//    使用者继承AbstractQueuedSynchronizer并重写指定的方法。（这些重写方法很简单，无非是对于共享资源state的获取和释放）
//    将AQS组合在自定义同步组件的实现中，并调用其模板方法，而这些模板方法会调用使用者重写的方法。
//    这和我们以往通过实现接口的方式有很大区别，这是模板方法模式很经典的一个运用。
//    自定义同步器在实现的时候只需要实现共享资源state的获取和释放方式即可，至于具体线程等待队列的维护，AQS已经在顶层实现好了。自定义同步器实现的时候主要实现下面几种方法：
//
//    isHeldExclusively()：该线程是否正在独占资源。只有用到condition才需要去实现它。
//    tryAcquire(int)：独占方式。尝试获取资源，成功则返回true，失败则返回false。
//    tryRelease(int)：独占方式。尝试释放资源，成功则返回true，失败则返回false。
//    tryAcquireShared(int)：共享方式。尝试获取资源。负数表示失败；0表示成功，但没有剩余可用资源；正数表示成功，且有剩余资源。
//    tryReleaseShared(int)：共享方式。尝试释放资源，如果释放后允许唤醒后续等待结点返回true，否则返回false。

}
