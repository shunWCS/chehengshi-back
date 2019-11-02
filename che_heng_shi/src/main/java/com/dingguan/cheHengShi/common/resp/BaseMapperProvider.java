package com.dingguan.cheHengShi.common.resp;

public class BaseMapperProvider {
    /**
     * 拼接like sql
     * @param obj
     * @return
     */
    protected String jointlike(String obj){
        return String.join(obj,"'%","%'");
    }

    /**
     * 拼接equals sql
     * @param obj
     * @return
     */
    protected  String jointequals(String obj){

        return String.join(obj,"'","'");
    }

    private static String jointequal(String obj){

        return String.join(obj,"'","'");
    }
}
