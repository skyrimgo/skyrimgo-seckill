package com.szy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 秒杀详情表
 * 联合（商品id+用户电话），购买价格和订单创建时间，订单状态和秒杀商品（一种商品对应多个订单）
 *
 * @Author: Skyrimgo
 * @Date: 2020/7/17 14:12
 */
@Data
@ToString
public class SeckillOrder implements Serializable {
    /**
     * 商品id
     */
    private long seckillId;
    /**
     * 用户电话
     */
    private long userPhone;
    /**
     * 订单创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 购买金额
     */
    private BigDecimal money;
    /**
     * 订单状态
     */
    private boolean state;
    /**
     * 商品
     */
    private Seckill seckill;

}
