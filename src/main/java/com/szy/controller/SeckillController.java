package com.szy.controller;

import com.szy.dto.Exposer;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * @Author: Skyrimgo
 * @Date: 2020/7/18 19:29
 */
@Controller
@RequestMapping("/seckill")
@Slf4j
public class SeckillController {
    @Autowired
    private SeckillService seckillService;

    /**
     * 查询所有商品列表
     *
     * @param model
     * @return
     */
    @RequestMapping("/list")
    public String findSeckillList(Model model) {
        List<Seckill> list = seckillService.findAll();
        model.addAttribute("list", list);
        return "page/seckill";
    }

    /**
     * 根据秒杀id查询商品信息
     *
     * @param seckillId
     * @return
     */
    @ResponseBody
    @RequestMapping("/findById")
    public Seckill findById(@RequestParam("id") long seckillId) {
        return seckillService.findById(seckillId);
    }

    /**
     * 商品详情页
     *
     * @param seckillId
     * @param model
     * @return
     */
    @RequestMapping("/{seckillId}/detail")
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
        if (seckillId == null) {
            return "page/seckill";
        }
        Seckill seckill = seckillService.findById(seckillId);
        model.addAttribute("seckill", seckill);
        if (seckill == null) {
            return "page/seckill";
        }
        return "page/seckill_detail";
    }

    /**
     * 暴露秒杀接口
     *
     * @param seckillId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{seckillId}/exposer",
            method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId) {
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true, exposer);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new SeckillResult<Exposer>(false, e.getMessage());
        }
        return result;
    }

    /**
     * 执行秒杀操作
     *
     * @param seckillId
     * @param md5
     * @param money
     * @param userPhone
     * @return
     */
    @RequestMapping(value = "/{seckillId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @PathVariable("md5") String md5,
                                                   @RequestParam("money") BigDecimal money,
                                                   @CookieValue(value = "killPhone", required = false) Long userPhone) {
        if (userPhone == null) {
            //压力测试，随机生成11位电话号码和md5码
            userPhone = Util.getRandomNumber(11);
            md5 = Util.getMD5(seckillId);
            //return new SeckillResult<SeckillExecution>(false, "未注册");
        }
        try {
            SeckillExecution execution = seckillService.executeSeckill(seckillId, userPhone, md5, money);
            return new SeckillResult<SeckillExecution>(true, execution);
        } catch (RepeatSeckillException e) {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
            return new SeckillResult<SeckillExecution>(true, seckillExecution);
        } catch (CloseSeckillException e) {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.END);
            return new SeckillResult<SeckillExecution>(true, seckillExecution);
        } catch (SeckillException e) {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
            return new SeckillResult<SeckillExecution>(true, seckillExecution);
        }
    }

    @ResponseBody
    @GetMapping(value = "/time/now")
    public SeckillResult<Long> time() {
        Date now = new Date();
        return new SeckillResult(true, now.getTime());
    }
}
