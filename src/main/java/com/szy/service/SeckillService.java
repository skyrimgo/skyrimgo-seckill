package com.szy.service;

import com.szy.dto.Exposer;
import com.szy.dto.SeckillExecution;
import com.szy.entity.Seckill;
import com.szy.exception.CloseSeckillException;
import com.szy.exception.RepeatSeckillException;
import com.szy.exception.SeckillException;

import java.math.BigDecimal;
import java.util.List;

/**
 * 秒杀接口的业务
 * 查询全部商品
 * 根据id查询商品
 * 暴露url地址
 * 执行秒杀(传入商品id，用户手机，md5，金额)
 *
 * @Author: Skyrimgo
 * @Date: 2020/7/18 7:49
 */
public interface SeckillService {
    /**
     * 返回秒杀商品的信息
     *
     * @return
     */
    List<Seckill> findAll();

    /**
     * 根据秒杀id查询商品
     *
     * @param seckillId
     * @return
     */
    Seckill findById(long seckillId);

    /**
     * 未开始前返回相关事件信息
     * 开始后暴露接口
     * 结束后返回状态和商品id
     * Tips单独创建方法获取秒杀地址，保证接口防刷
     *
     * @param seckillId
     * @return
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀方法，传参（秒杀id，用户手机，md5地址，支付金额）捕获可能出现的异常，重复秒杀异常，关闭秒杀异常
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     * @param money
     * @return
     * @throws SeckillException
     * @throws CloseSeckillException
     * @throws RepeatSeckillException
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5, BigDecimal money) throws SeckillException, CloseSeckillException, RepeatSeckillException;


}
