package com.szy.dto;

import com.szy.entity.SeckillOrder;
import com.szy.enums.SeckillStatEnum;
import lombok.Data;

/**
 * 执行秒杀
 *
 * @Author: Skyrimgo
 * @Date: 2020/7/18 9:01
 */
@Data
public class SeckillExecution {
    /**
     * 执行秒杀的方法
     */
    private long seckillId;
    /**
     * 执行秒杀的状态
     */
    private int state;
    /**
     * 状态的中文解释
     */
    private String stateInfo;
    /**
     * 执行秒杀成功时的秒杀订单表
     */
    private SeckillOrder seckillOrder;

    /**
     * 执行秒杀失败，返回秒杀id，秒杀状态码和秒杀状态
     *
     * @param seckillId
     * @param seckillStatEnum
     */
    public SeckillExecution(long seckillId, SeckillStatEnum seckillStatEnum) {
        this.seckillId = seckillId;
        this.state = seckillStatEnum.getState();
        this.stateInfo = seckillStatEnum.getStateInfo();
    }

    /**
     * 执行秒杀成功，返回秒杀id，秒杀状态码和秒杀状态和秒杀订单
     *
     * @param seckillId
     * @param seckillStatEnum
     * @param seckillOrder
     */
    public SeckillExecution(long seckillId, SeckillStatEnum seckillStatEnum, SeckillOrder seckillOrder) {
        this.seckillId = seckillId;
        this.state = seckillStatEnum.getState();
        this.stateInfo = seckillStatEnum.getStateInfo();
        this.seckillOrder = seckillOrder;
    }
}
