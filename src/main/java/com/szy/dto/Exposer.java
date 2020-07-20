package com.szy.dto;

import lombok.Data;

/**
 * @Author: Skyrimgo
 * @Date: 2020/7/18 7:53
 */
@Data
public class Exposer {
    /**
     * 是否开启秒杀
     */
    private boolean exposed;
    /**
     * 秒杀id
     */
    private long seckillId;
    /**
     * 加密地址，防止用户拿到秒杀地址
     */
    private String md5;
    /**
     * 系统当前时间
     */
    private long now;
    /**
     * 秒杀开始时间
     */
    private long start;
    /**
     * 秒杀结束时间
     */
    private long end;

    /**
     * 秒杀正在进行中，返回状态true，秒杀id和秒杀地址
     *
     * @param exposed
     * @param seckillId
     * @param md5
     */

    public Exposer(boolean exposed, long seckillId, String md5) {
        this.exposed = exposed;
        this.seckillId = seckillId;
        this.md5 = md5;
    }

    /**
     * 秒杀未开始,秒杀已经结束，返回状态false，秒杀id和相关时间用于前端倒计时
     *
     * @param exposed
     * @param seckillId
     * @param now
     * @param start
     * @param end
     */

    public Exposer(boolean exposed, long seckillId, long now, long start, long end) {
        this.exposed = exposed;
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    /**
     * 未查询到秒杀id对应的商品，返回状态false和当前秒杀id
     *
     * @param exposed
     * @param seckillId
     */
    public Exposer(boolean exposed, long seckillId) {
        this.exposed = exposed;
        this.seckillId = seckillId;
    }

    /**
     * 是否保留url
     *
     * @return
     */
    public boolean isExposed() {
        return exposed;
    }
}
