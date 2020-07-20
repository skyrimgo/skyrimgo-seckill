package com.szy.rabbit;

import com.szy.dto.SeckillExecution;
import com.szy.dto.SeckillResult;
import com.szy.entity.Seckill;
import com.szy.enums.SeckillStatEnum;
import com.szy.exception.CloseSeckillException;
import com.szy.exception.RepeatSeckillException;
import com.szy.exception.SeckillException;
import com.szy.service.SeckillService;
import com.szy.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @Author: Skyrimgo
 * @Date: 2020/7/19 19:45
 */
@Component
@Slf4j
public class RabbitMessageReceiver {
    @Autowired
    private SeckillService seckillService;

    /**
     * 定义监听消息队列的名称
     *
     * @param msg
     * @return
     */
    @RabbitListener(queues = "${rabbitmq.queue.msg}")
    public void receiveMsg(String msg) {
        //消息队列压力测试
        log.info(msg);
        //压力测试，随机生成11位电话号码和md5码
        long userPhone = Util.getRandomNumber(11);
        long seckillId = Long.parseLong(msg);
        String md5 = Util.getMD5(Integer.parseInt(msg));
        BigDecimal money = new BigDecimal(100);
        //return new SeckillResult<SeckillExecution>(false, "未注册");

        try {
            SeckillExecution execution = seckillService.executeSeckill(seckillId, userPhone, md5, money);
        } catch (RepeatSeckillException e) {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
        } catch (CloseSeckillException e) {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.END);
        } catch (SeckillException e) {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
        }

    }
}
