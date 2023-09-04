package com.su.utils;

import java.util.Random;

public class RandomUtil {

    public static String getCode(){
        Random random = new Random();
        String ch = "0123456789qwertyuiopasdfghjklzxcvbnm";
        int length = ch.length();
        String str = "";
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(length);
            str += ch.substring(index, index + 1);
        }
        return str;
    }

}
