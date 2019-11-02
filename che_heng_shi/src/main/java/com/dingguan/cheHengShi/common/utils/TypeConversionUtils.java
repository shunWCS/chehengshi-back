package com.dingguan.cheHengShi.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Created by zyc on 2018/4/20.
 * Use Fastjson To Do Type Conversion
 */
public class TypeConversionUtils {
    public static  <T> T  JsonStrinToJavaBean(String json, Class<T> clazz){

        T t = JSON.parseObject(json, clazz);
       // System.out.println(t);
        return t;

    }



    public static  <T> T  JavaBeanToJavaBean(Object o, Class<T> clazz){

         String json = JSON.toJSONString(o);
        T t = JSON.parseObject(json, clazz);
        //System.out.println(t);
        return t;

    }

    public static  Object  disableCircularReferenceDetect(Object o ){

        Object parse = com.alibaba.fastjson.JSONObject.parse(
                com.alibaba.fastjson.JSON.toJSONString(o, SerializerFeature.DisableCircularReferenceDetect));
        return parse;
    }

    public static void main(String[] args) {
        JsonStrinToJavaBean("{\"id\":1}",Object.class);
    }
}