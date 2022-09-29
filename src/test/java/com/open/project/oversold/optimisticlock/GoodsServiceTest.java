package com.open.project.oversold.optimisticlock;

import com.open.HelloConcurrentApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author liuxiaowei
 * @date 2022年09月29日 14:53
 * @Description
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloConcurrentApplication.class)
public class GoodsServiceTest {

    @Autowired
    GoodsService goodsService;

    @Test
    public void seckill() throws InterruptedException {
        // 库存初始化为10
        // 这里通过CountDownLatch和线程池模拟100个并发
        int threadTotal = 100;

        ExecutorService executorService = Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch = new CountDownLatch(threadTotal);
        for (int i = 0; i < threadTotal ; i++) {
            int uid = i;
            executorService.execute(() -> {
                try {
                    goodsService.sellGoods(1, uid);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        executorService.shutdown();
    }

}