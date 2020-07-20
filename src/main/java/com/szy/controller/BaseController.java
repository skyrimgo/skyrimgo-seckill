package com.szy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 控制页面重定向
 * @Author: Skyrimgo
 * @Date: 2020/7/18 19:26
 */
@Controller
public class BaseController {
    /**
     * 跳转到商品详情页面
     * @return
     */
    @RequestMapping("/seckill")
    public String seckillGoods() {
        return "redirect:/seckill/list";
    }
}
