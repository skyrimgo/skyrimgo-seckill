package com.szy.mapper;

import com.szy.entity.SeckillOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

/**
 * @Author: Skyrimgo
 * @Date: 2020/7/17 19:03
 */

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:application.yml")
@SpringBootTest
public class SeckillOrderMapperTest {
    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @Test
    public void insert() {
        int stat = seckillOrderMapper.insert(1, 1830512346, BigDecimal.valueOf(100.00));
        System.out.println(stat);
    }

    @Test
    public void findById() {
        SeckillOrder seckillOrder = seckillOrderMapper.findById(1, 1830512345);
        System.out.println(seckillOrder);
    }

}