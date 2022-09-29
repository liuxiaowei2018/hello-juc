package com.open.project.oversold.optimisticlock;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author liuxiaowei
 * @date 2022年09月29日 14:48
 * @Description
 */
@TableName("goods")
@Data
public class Goods {
    private int id;
    private String name;
    private int stock;
    private int version;
}
