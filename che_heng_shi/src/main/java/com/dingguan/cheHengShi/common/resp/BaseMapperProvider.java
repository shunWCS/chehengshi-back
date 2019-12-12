package com.dingguan.cheHengShi.common.resp;

import com.dingguan.cheHengShi.common.utils.Util;
import com.dingguan.cheHengShi.home.enumerate.BaseEnum;
import com.google.common.base.Preconditions;

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

    public static  <T extends BaseEnum>  String enumToCaseWhenString  (Class<T > enu , String fieldName , String defualtValue , String aliasName  ){
        Preconditions.checkArgument(Util.isNotEmpty(fieldName),"枚举转case sql时, 字段名为空");
        StringBuffer sql = new StringBuffer(" case ").append( fieldName );
        T[] enums = enu.getEnumConstants();
        for (T anEnum : enums) {
            sql.append(" when ").append(jointequal(anEnum.getCode())).append(" then ").append(jointequal(anEnum.getMessage()));
        }
        sql.append(" else  ");
        if (Util.isEmpty(defualtValue)){
            sql.append(jointequal(""));
        }else {
            sql.append(jointequal(defualtValue));
        }
        sql.append(" end  as ");
        if (Util.isEmpty(aliasName)) {
            sql.append(camelName(fieldName));
        }else {
            sql.append(aliasName);
        }
        sql.append(" ");
        return  sql.toString();
    }

    public static String camelName(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母小写
            return name.substring(0, 1).toLowerCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String camels[] = name.split("_");
        for (String camel :  camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 处理真正的驼峰片段
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel.toLowerCase());
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

}
