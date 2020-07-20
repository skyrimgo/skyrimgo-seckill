package com.szy.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: Skyrimgo
 * @Date: 2020/7/18 20:27
 */
@Data
@ToString
public class SeckillResult<T> {
    private boolean success;

    private T data;

    private String error;

    public SeckillResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public SeckillResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }
}
