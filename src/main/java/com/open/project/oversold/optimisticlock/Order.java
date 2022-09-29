package com.open.project.oversold.optimisticlock;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author liuxiaowei
 * @date 2022年09月29日 14:48
 * @Description
 */
@TableName("order")
@Data
public class Order {
    private int id;
    private int uid;
    private int gid;
}
