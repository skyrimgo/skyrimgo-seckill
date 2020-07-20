package com.szy.service.impl;

import com.szy.service.RabbitMqService;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Author: Skyrimgo
 * @Date: 2020/7/19 19:24
 */
@Service
@Slf4j
public class RabbitMqServiceImpl implements RabbitMqService, RabbitTemplate.ConfirmCallback {
    /**
     * 消息发送该队列中，等待监听它的消费者进行消费
     */
    @Value("${rabbitmq.queue.msg}")
    private String msgRouting = null;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     *
     * @param msg
     */
    @Override
    public void sendMsg(String msg) {
        //设置回调
        rabbitTemplate.setConfirmCallback(this);
        //发送消息，通过msgRouting确定队列
        rabbitTemplate.convertAndSend(msgRouting, msg);
    }

    /**
     * 回调确认方法
     *
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.info("消息消费成功");
        } else {
            log.info("消息消费失败" + cause);
        }
    }
}
