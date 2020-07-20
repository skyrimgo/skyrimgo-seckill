package com.szy.exception;

/**
 * @Author: Skyrimgo
 * @Date: 2020/7/18 8:46
 */
public class RepeatSeckillException extends SeckillException {
    /**
     * 构造带有详细信息的RepeatSeckillException
     * @param message
     */
    public RepeatSeckillException(String message) {
        super(message);
    }

    /**
     * 构造带有详细信息和新Throwable的RepeatSeckillException
     * @param message
     * @param cause
     */
    public RepeatSeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
