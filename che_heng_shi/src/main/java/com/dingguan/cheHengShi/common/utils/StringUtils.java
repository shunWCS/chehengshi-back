package com.dingguan.cheHengShi.common.utils;

/**
 * Created by zyc on 2018/10/12.
 */
public class StringUtils {


    public static boolean isNumeric(Object obj){
        if(obj==null){
            return false;
        }
        String str=obj.toString();

        if(org.apache.commons.lang3.StringUtils.isBlank(str)){
            return false;
        }

        for(int i=str.length();--i>=0;){
            int chr=str.charAt(i);
            if(chr<48 || chr>57)
                return false;
        }
        return true;
    }






}