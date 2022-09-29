package com.open.project.oversold.optimisticlock;

import org.apache.ibatis.annotations.*;

/**
 * @author liuxiaowei
 * @date 2022年09月29日 14:49
 * @Description
 */
@Mapper
public interface OrderMapper {

    /**
     * 插入订单
     * 注意: order表是关键字，需要`order`
     * @param order
     */
    @Insert("INSERT INTO `order` (uid, gid) VALUES (#{uid}, #{gid})")
    //@Options(useGeneratedKeys = true, keyProperty = "id")
    int insertOrder(Order order);
}
