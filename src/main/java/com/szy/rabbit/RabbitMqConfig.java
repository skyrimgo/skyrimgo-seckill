package com.szy.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Author: Skyrimgo
 * @Date: 2020/7/19 19:39
 */
@Configuration
public class RabbitMqConfig {
    //消息队列名称
    @Value("${rabbitmq.queue.msg}")
    private String msgQueueName = null;

    @Bean
    public Queue createQueuemsg() {
        return new Queue(msgQueueName, true);
    }
}
