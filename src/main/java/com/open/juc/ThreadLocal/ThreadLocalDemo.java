package com.open.juc.ThreadLocal;

/**
 * @author liuxiaowei
 * @date 2022年11月07日 14:46
 * @Description
 */
public class ThreadLocalDemo {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        threadLocal.set("hello");
        System.out.println("主线程获取的value:" + threadLocal.get());

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String value = threadLocal.get();
                System.out.println("子线程获取的value:" + value);
                // 一定要remove，不然可能导致内存泄漏
                threadLocal.remove();
            }
        });
        thread.start();
        // result: 子线程无法获取父线程设置的值
        // 主线程获取的value:hello    子线程获取的value:null
    }
}
