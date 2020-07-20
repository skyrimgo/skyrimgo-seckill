package com.szy.service.impl;

import com.szy.dto.Exposer;
import com.szy.dto.SeckillExecution;
import com.szy.entity.Seckill;
import com.szy.entity.SeckillOrder;
import com.szy.enums.SeckillStatEnum;
import com.szy.exception.CloseSeckillException;
import com.szy.exception.RepeatSeckillException;
import com.szy.exception.SeckillException;
import com.szy.mapper.SeckillMapper;
import com.szy.mapper.SeckillOrderMapper;
import com.szy.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: Skyrimgo
 * @Date: 2020/7/18 9:32
 */
@Service
@Slf4j
public class SeckillServiceImpl implements SeckillService {
    /**
     * 秒杀商品表映射接口
     */
    @Autowired
    private SeckillMapper seckillMapper;
    /**
     * 秒杀订单表映射接口
     */
    @Autowired
    private SeckillOrderMapper seckillOrderMapper;
    /**
     * 盐值，用于混淆md5码
     */
    private final String salt = "#&)$*fd$%&";
    /**
     * redisTemplate模板
     */
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 缓存的key
     */
    private final String key = "seckill";

    /**
     * 查询所有的秒杀商品表
     *
     * @return
     */
    @Override
    public List<Seckill> findAll() {
        //从缓存中查询
        List<Seckill> seckillList = redisTemplate.boundHashOps("seckill").values();
        //如果缓存中没有取到
        if (seckillList.isEmpty()) {
            //从数据库中查询
            seckillList = seckillMapper.findAll();
            //更新缓存
            for (Seckill seckill : seckillList) {
                redisTemplate.boundHashOps(key).put(seckill.getSeckillId(), seckill);
                log.info("findAll->从数据库中读取放到缓存中");
            }
        } else {
            log.info("findAll->从缓存中读取");
        }
        return seckillList;
    }

    /**
     * 根据秒杀id查询商品
     * 可用热地私有化
     *
     * @param seckillId
     * @return
     */
    @Override
    public Seckill findById(long seckillId) {
        //从缓存中查询
        Seckill seckill = (Seckill) redisTemplate.boundHashOps(key).get(seckillId);
        //如果没有取到
        if (seckill == null) {
            seckill = seckillMapper.findById(seckillId);
            redisTemplate.boundHashOps(key).put(seckillId, seckill);
            log.info("findById->从数据库中读取放到缓存中");
        } else {
            log.info("findById->从缓存中读取");
        }
        return seckill;
    }

    /**
     * 根据传进来的seckillId查询seckill表中对应数据，
     * 如果没有查询到就直接返回Exposer(false,seckillId)标识,没有查询到该商品的秒杀接口信息，可能是用户非法输入的数据；
     * 如果查询到了，就获取秒杀开始时间和秒杀结束时间以及new一个当前系统时间进行判断当前秒杀商品是否正在进行秒杀活动，
     * 还没有开始或已经结束都直接返回Exposer；
     * 如果上面两个条件都符合了就证明该商品存在且正在秒杀活动中，那么我们需要暴露秒杀接口地址。
     *
     * @param seckillId
     * @return
     */
    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        /**
         * 此处可先查询缓存，优化查询
         * 缓存中没有则查询数据库，查询结果更新到缓存中
         */
        Seckill seckill = (Seckill) redisTemplate.boundHashOps(key).get(seckillId);
        if (seckill == null) {
            seckill = seckillMapper.findById(seckillId);
            if (seckill == null) {
                return new Exposer(false, seckillId);
            } else {
                redisTemplate.boundHashOps(key).put(seckillId, seckill);
                log.info("findById->从数据库中读取放到缓存中");
            }
        } else {
            log.info("findById->从缓存中读取");
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        String md5 = getMD5(seckillId);
        return new Exposer(true, seckillId, md5);

    }

    /**
     * 生成md5码
     *
     * @param seckillId
     * @return
     */
    public String getMD5(long seckillId) {
        return DigestUtils.md5DigestAsHex((seckillId + "/" + salt).getBytes());
    }

    /**
     * md5码验证，验证失败则秒杀数据被重写，抛出SeckillException异常：数据被重写
     * 更新秒杀订单表，更新失败则出现了重复秒杀的情况，抛出RepeatSeckillException异常：重复秒杀
     * 减库存，减库存失败则表示秒杀关闭，抛出异常CloseSeckillException异常：秒杀结束
     * 秒杀成功,返回SeckillExecution
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
    @Override
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5, BigDecimal money)
            throws SeckillException, CloseSeckillException, RepeatSeckillException {
        //md5码验证失败，数据被篡改
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("异常：数据篡改");
        }
        try {
            int insertCount = seckillOrderMapper.insert(seckillId, userPhone, money);
            //没有完成创建订单，重复秒杀
            if (insertCount <= 0) {
                throw new RepeatSeckillException("异常：重复秒杀");
            } else {
                int updateCount = seckillMapper.reduceStock(seckillId, new Date());
                //没有完成减库存，秒杀结束
                if (updateCount <= 0) {
                    throw new CloseSeckillException("异常：秒杀结束");
                } else {
                    //秒杀成功，完成订单减库存
                    SeckillOrder seckillOrder = seckillOrderMapper.findById(seckillId, userPhone);
                    //此处可更新缓存，优化查询
                    //更新缓存（更新库存数量）
                    Seckill seckill = (Seckill) redisTemplate.boundHashOps(key).get(seckillId);
                    seckill.setStockCount(seckill.getSeckillId() - 1);
                    redisTemplate.boundHashOps(key).put(seckillId, seckill);

                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, seckillOrder);
                }
            }
        } catch (CloseSeckillException | RepeatSeckillException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new SeckillException("秒杀系统内部异常：" + e);
        }
    }
}
