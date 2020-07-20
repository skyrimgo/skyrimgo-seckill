package com.szy.exception;

/**
 * @Author: Skyrimgo
 * @Date: 2020/7/18 8:48
 */
public class CloseSeckillException extends SeckillException {
    /**
     * 构造带有详细信息的CloseSeckillException
     *
     * @param message
     */
    public CloseSeckillException(String message) {
        super(message);
    }

    /**
     * 构造带有详细信息和新Throwable的CloseSeckillException
     *
     * @param message
     * @param cause
     */
    public CloseSeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
