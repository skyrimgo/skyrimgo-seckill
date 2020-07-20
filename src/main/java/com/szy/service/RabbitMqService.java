package com.szy.service;

/**
 * 定义RabbitMQ服务接口
 *
 * @Author: Skyrimgo
 * @Date: 2020/7/19 19:22
 */
public interface RabbitMqService {
    /**
     * 发送字符串消息
     */
    public void sendMsg(String msg);
}
