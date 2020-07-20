package com.szy.controller;

import com.szy.service.RabbitMqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Skyrimgo
 * @Date: 2020/7/19 19:50
 */
@Controller
public class RabbitMqController {
    @Autowired
    private RabbitMqService rabbitMqService;

    /**
     * 控制器测试
     *
     * @param message
     * @return
     */
    @ResponseBody
    @RequestMapping("/msg")
    public Map<String, Object> msg(@RequestParam("message") String message) {
        rabbitMqService.sendMsg(message);
        return resultMap("message", message);
    }

    /**
     * 结果Map
     *
     * @param key
     * @param obj
     * @return
     */
    private Map<String, Object> resultMap(String key, Object obj) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put(key, obj);
        return map;
    }

}
