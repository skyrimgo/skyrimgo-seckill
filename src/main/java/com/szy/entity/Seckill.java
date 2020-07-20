package com.szy.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 秒杀商品表
 * i 标图，3 时2 价1 库存
 *
 * @Author: Skyrimgo
 * @Date: 2020/7/17 13:55
 */
@Data
@ToString
public class Seckill implements Serializable {
    /**
     * 商品id
     */
    private long seckillId;
    /**
     * 商品标题
     */
    private String title;
    /**
     * 商品图片
     */
    private String image;
    /**
     * 商品原价
     */
    private BigDecimal price;
    /**
     * 商品秒杀价格
     */
    private BigDecimal costPrice;
    /**
     * 秒杀开始时间
     * 注解分别为入参格式化和出参格式化
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    /**
     * 秒杀结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    /**
     * 秒杀创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 库存
     */
    private long stockCount;

}
