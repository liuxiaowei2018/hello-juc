package com.open.project.delayedretry;

import java.util.Date;

/**
 * @author liuxiaowei
 * @date 2022年11月07日 16:45
 * @Description 重试间隔
 */
public interface RetryInterval {

    Date interval(Date date);
}
