package com.open.project.delayedretry;

import java.util.Date;

/**
 * @author liuxiaowei
 * @date 2022年11月07日 16:45
 * @Description 默认重试间隔
 */
public class DefaultRetryInterval implements RetryInterval{

    @Override
    public Date interval(Date date) {
        long time = date.getTime();
        time += 5 * 1000;
        return new Date(time);
    }
}
