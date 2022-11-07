package com.open.project.delayedretry;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liuxiaowei
 * @date 2022年11月07日 16:41
 * @Description 重试实体
 */
@Data
public class RetryBean implements Serializable {

    private static final long serialVersionUID = 234755720013778545L;

    public RetryBean() {
        this.createTime = new Date();
    }

    public boolean hasChance() {
        return this.retryTimes < leastRetryTimes;
    }

    public void reduceChange() {
        this.retryTimes++;
    }

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 下次重试时间
     **/
    private Date nextRetryTime;

    /**
     * 重试次数
     **/
    private Integer retryTimes = 0;

    /**
     * 限制重试次数
     **/
    private Integer leastRetryTimes = 5;
}
