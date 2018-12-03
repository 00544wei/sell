package com.weizhang.util;

import java.util.Random;

public class GenerateKeyUtils {

    /**
     * 生成一个唯一的字符串
     * @return
     */
    public static synchronized String generateUniqueKey(){
        Random random = new Random();
        int number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}
