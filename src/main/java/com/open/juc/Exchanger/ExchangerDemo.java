package com.open.juc.Exchanger;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Exchanger;

/**
 * @author liuxiaowei
 * @date 2022年09月26日 15:51
 * @Description
 */
public class ExchangerDemo {

    private static final Exchanger<Set<String>> exchanger = new Exchanger<>();

    public static void main(String[] args) {

        // result -> bSetAbSetBbSetCaSet1aSet2aSet3

        new Thread(() -> {
            Set<String> aSet = new HashSet<>();
            aSet.add("A");
            aSet.add("B");
            aSet.add("C");
            try {
                Set<String> exchange = exchanger.exchange(aSet);
                for (String s : exchange) {
                    System.out.println("aSet"+s);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            Set<String> bSet = new HashSet<>();
            bSet.add("1");
            bSet.add("2");
            bSet.add("3");
            try {
                Set<String> exchange = exchanger.exchange(bSet);
                for (String s : exchange) {
                    System.out.println("bSet"+s);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }
}