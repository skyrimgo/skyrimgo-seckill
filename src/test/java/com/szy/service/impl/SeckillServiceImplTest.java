package com.szy.service.impl;

import com.szy.dto.Exposer;
import com.szy.dto.SeckillExecution;
import com.szy.entity.Seckill;
import com.szy.exception.CloseSeckillException;
import com.szy.exception.RepeatSeckillException;
import com.szy.service.SeckillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: Skyrimgo
 * @Date: 2020/7/18 14:52
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SeckillServiceImplTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void findAll() {
        List<Seckill> all = seckillService.findAll();
        logger.info("all={}", all);
    }


    @Test
    public void exportSeckillUrl() {
        Exposer exposer = seckillService.exportSeckillUrl(1l);
        logger.info("exposer={}", exposer);
        //Exposer{exposed=true, md5='35465a0864a9faf95bcd402f3ffb5f66', seckillId=1, now=0, start=0, end=0}
    }

    @Test
    public void executeSeckill() {
        long id = 1;
        BigDecimal money = BigDecimal.valueOf(200.00);
        long userPhone = 1830512344;
        String md5="bd0b4b2d398820ce4e55e90c8d152ba3";
        SeckillExecution seckillExecution = seckillService.executeSeckill(id, userPhone, md5, money);
        logger.info("seckillExecution={}", seckillExecution);
        //SeckillExecution{seckillId=1, state=1, stateInfo='秒杀成功', seckillOrder=SeckillOrder{seckillId=1, money=200.00, createTime=null, status=false, seckill=Seckill{seckillId=1, title='Apple/苹果 iPhone 6s Plus 国行原装苹果6sp 5.5寸全网通4G手机', image='null', price=null, costPrice=1100.00, createTime=null, startTime=Sat Oct 06 16:30:00 CST 2018, endTime=Wed Oct 17 16:30:00 CST 2018, stockCount=7}}}

    }

    //集成测试上述两个方法
    @Test
    public void testSeckillLogic() throws Exception {
        Exposer exposer = seckillService.exportSeckillUrl(1l);
        if (exposer.isExposed()) {
            long id = exposer.getSeckillId();
            BigDecimal money = BigDecimal.valueOf(200.00);
            long userPhone = 1830512345;
            String md5 = exposer.getMd5();
            try {
                SeckillExecution seckillExecution = seckillService.executeSeckill(id, userPhone, md5, money);
                logger.info("result={}", exposer);
            } catch (CloseSeckillException e) {
                logger.error(e.getMessage());
            } catch (RepeatSeckillException e1) {
                logger.error(e1.getMessage());
            }
        } else {
            //秒杀未开启
            logger.warn("exposer={}", exposer);
        }
    }

}