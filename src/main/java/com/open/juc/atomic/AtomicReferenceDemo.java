package com.open.juc.atomic;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author liuxiaowei
 * @date 2022年11月08日 10:25
 * @Description 原子更新引用类型
 * Atomic包提供了以下类：
 *  AtomicReference:  原子更新引用类型。
 *  AtomicStampedReference： 原子更新引用类型（带时间戳，避免ABA问题）
 *  AtomicReferenceFieldUpdater:  原子更新引用类型的字段。
 *  AtomicMarkableReferce:  原子更新带有标记位的引用类型，可使用构造方法更新一个布尔类型的标记位和引用类型。
 */
public class AtomicReferenceDemo {

    private static AtomicReference<User> atomicReference = new AtomicReference<>();

    public static void main(String[] args) {
        User u1 = new User("Hello", 18);
        atomicReference.set(u1);
        System.out.println(atomicReference.get().getName() + " " + atomicReference.get().getAge());
        User u2 = new User("World", 15);
        atomicReference.compareAndSet(u1, u2);
        System.out.println(atomicReference.get().getName() + " " + atomicReference.get().getAge());
        System.out.println(u1.getName() + " " + u1.getAge());
    }


}

@Data
@AllArgsConstructor
class User {
    private String name;
    private int age;
}
