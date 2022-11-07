package com.open.project.delayedretry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liuxiaowei
 * @date 2022年11月07日 17:02
 * @Description
 */
@Slf4j
@Data
@AllArgsConstructor
public class TslSyncTask implements Task {

    private String url;
    private String tslJson;
    private String productId;

    @Override
    public void onceRetry() throws Exception {
        log.info("url: {}, productId: {}", url, productId);
        if (productId.equals("2")) {
            throw new RetryException("主动重试");
        }
        System.out.println("hi");
    }

    @Override
    public void exceedTimesRetry() throws Exception {

    }

    @Override
    public void interrupt() throws Exception {

    }

    @Override
    public void fire() throws Exception {

    }

    @Override
    public String toString() {
        return productId;
    }
}
