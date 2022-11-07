package com.open.juc.ThreadLocal;

/**
 * @author liuxiaowei
 * @date 2022年11月07日 14:48
 * @Description
 * InheritableThreadLocal将 ThreadLocal 中的 threadLocals 换成了 inheritableThreadLocals，这两个变量都是ThreadLocalMap类型，并且都是Thread类的属性
 */
public class InheritableThreadLocalDemo {

    private static InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();

    // if (inheritThreadLocals && parent.inheritableThreadLocals != null)
    //            this.inheritableThreadLocals =
    //                ThreadLocal.createInheritedMap(parent.inheritableThreadLocals);

    public static void main(String[] args) {
        inheritableThreadLocal.set("hello");
        System.out.println("主线程获取的value:" + inheritableThreadLocal.get());

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String value = inheritableThreadLocal.get();
                System.out.println("子线程获取的value:" + value);
                // 一定要remove，不然可能导致内存泄漏
                inheritableThreadLocal.remove();
            }
        });
        thread.start();
    }

}
