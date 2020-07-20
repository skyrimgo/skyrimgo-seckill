package com.szy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Skyrimgo
 * @Date: 2020/7/18 10:24
 */
@AllArgsConstructor
public enum SeckillStatEnum {
    /**
     * 1，秒杀成功
     * 0，秒杀结束
     * -1，重复秒杀
     * -2，系统异常
     * -3，数据篡改
     */
    SUCCESS(1, "秒杀成功"),
    END(0, "秒杀结束"),
    REPEAT_KILL(-1, "重复秒杀"),
    INNER_ERROR(-2, "系统异常"),
    DATA_REWRITE(-3, "数据串改");
    @Getter
    private int state;
    @Getter
    private String stateInfo;

    /**
     * 根据index返回结果状态
     * @param index
     * @return
     */
    public static SeckillStatEnum statOf(int index) {
        for (SeckillStatEnum seckillStatEnum : values()) {
            if (seckillStatEnum.getState() == index) {
                return seckillStatEnum;
            }
        }
        return null;
    }
}
