package com.dingguan.cheHengShi.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Random;

/**
 *
 */
public class RandomNumberCode {

    public static String verCode(){
        Random random =new Random();
        return StringUtils.substring(String.valueOf(random.nextInt()*-10), 2, 6);
    }
    public static String randomNo(){
        Random random =new Random();
        return String.valueOf(Math.abs(random.nextInt()*-10));
    }


    /**
     * StringUtils工具类方法
     * 获取一定长度的随机字符串，范围0-9，a-z
     * @param length：指定字符串长度
     * @return 一定长度的随机字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }




    public static String randomNo(Integer num){
        num=num==null?1:num;
        Random random =new Random();
        StringBuffer ran=new StringBuffer("");
        for (int i=0;i<num;i++){
            ran.append(random.nextInt(10));

        }
        return ran.toString();
    }



}
