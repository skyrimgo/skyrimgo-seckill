package com.szy.exception;

/**
 * @Author: Skyrimgo
 * @Date: 2020/7/18 8:40
 */

public class SeckillException extends RuntimeException {
    /**
     * 构造带有详细信息的SeckillException
     * @param message
     */
    public SeckillException(String message) {
        super(message);
    }

    /**
     * 构造带有详细信息和新Throwable的SeckillException
     * @param message
     * @param cause
     */
    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
