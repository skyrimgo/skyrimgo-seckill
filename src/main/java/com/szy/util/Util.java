package com.szy.util;

import org.springframework.util.DigestUtils;

/**
 * @Author: Skyrimgo
 * @Date: 2020/7/19 11:24
 */
public class Util {
    /**
     * 盐值，用于混淆md5码
     */
    private final static String salt = "#&)$*fd$%&";

    public static long getRandomNumber(int i) {
        return (long) ((Math.random() * 9 + 1) * Math.pow(10, i - 1));
    }

    public static String getMD5(long seckillId) {
        return DigestUtils.md5DigestAsHex((seckillId + "/" + salt).getBytes());
    }
}
