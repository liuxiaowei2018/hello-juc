package com.open.project.oversold.optimisticlock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author liuxiaowei
 * @date 2022年09月29日 14:51
 * @Description
 */
@Slf4j
@Service
public class GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 扣减库存
     * @param gid 商品id
     * @param uid 用户id
     * @return SUCCESS-1 FAILURE-0
     */
    @Transactional(rollbackFor = Exception.class)
    public int sellGoods(int gid, int uid) {
        // 获取库存
        Goods goods = goodsMapper.getStock(gid);
        if (goods.getStock() > 0) {
            // 乐观锁更新库存
            int update = goodsMapper.decreaseStockForVersion(gid, goods.getVersion());
            // 更新失败，说明其他线程已经修改过数据，本次扣减库存失败，可以重试一定次数或者返回
            if (update == 0) {
                return 0;
            }
            log.info("库存扣减成功，生成订单");
            // 库存扣减成功，生成订单
            Order order = new Order();
            order.setUid(uid);
            order.setGid(gid);
            int result = orderMapper.insertOrder(order);
            return result;
        }
        // 失败返回
        return 0;
    }
}
