package com.szy.mapper;

import com.szy.entity.SeckillOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * insert()方法：插入订单信息,返回状态信息
 * findById()方法：根据联合主键查询表,返回Seckill
 *
 * @Author: Skyrimgo
 * @Date: 2020/7/17 18:45
 */
@Mapper
@Component
public interface SeckillOrderMapper {
    /**
     * @param seckillId
     * @param userPhone
     * @param money
     * @return
     */
    int insert(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone, @Param("money") BigDecimal money);

    /**
     * @param seckillId
     * @param userPhone
     * @return
     */
    SeckillOrder findById(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

}
