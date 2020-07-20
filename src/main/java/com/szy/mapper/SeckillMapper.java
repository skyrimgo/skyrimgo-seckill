package com.szy.mapper;

import com.szy.entity.Seckill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Mapper映射接口
 * findAll()方法：查询所有商品seckill封装于List中
 * findById(long seckillId)方法：根据商品id查询商品
 * reduceStock(long seckillId,Date seckillTime)：根据seckillId减库存根据seckillTime判断是否减库存,返回减库存的状态
 *
 * @Author: Skyrimgo
 * @Date: 2020/7/17 14:30
 */
@Mapper
@Component
public interface SeckillMapper {
    /**
     * @return
     */
    List<Seckill> findAll();

    /**
     * @param seckillId
     * @return
     */
    Seckill findById(@Param("seckillId") long seckillId);

    /**
     * @param seckillId
     * @param seckillTime
     * @return
     */
    int reduceStock(@Param("seckillId") long seckillId, @Param("seckillTime") Date seckillTime);
}
