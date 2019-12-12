package com.dingguan.cheHengShi.product.mapper;

import com.dingguan.cheHengShi.common.resp.BaseMapperProvider;
import com.dingguan.cheHengShi.common.utils.Util;
import com.dingguan.cheHengShi.home.enumerate.BannerTypeEnum;
import com.dingguan.cheHengShi.home.enumerate.IsFirstEnum;

public class PosterBannerMapperProvider extends BaseMapperProvider {

    public String selectCommonPosterByTypeValue(String typeValue){
        StringBuffer sql = new StringBuffer("select pb.ref_id,pb.type_value,pb.title,pb.create_time,pb.edit_time,pb.banner,");
        sql.append(enumToCaseWhenString(BannerTypeEnum.class,"type_value","","typeName")).append(",");
        sql.append(enumToCaseWhenString(IsFirstEnum.class,"is_first","","isFirst"));
        sql.append("from poster_banner pb where 1 = 1");
        if(Util.isNotEmpty(typeValue)){
            sql.append(" and pb.type_value = #{typeValue}");
        }
        return sql.toString();
    }

    public String getHomePhoto(String isFirst){
        StringBuffer sql = new StringBuffer("select pb.*,");
        sql.append(enumToCaseWhenString(BannerTypeEnum.class,"type_value","","typeName"));
        sql.append(" from poster_banner pb where pb.is_first = #{isFirst} ORDER BY edit_time DESC limit 0,4");
        return sql.toString();
    }

    public String getImages(String type){
        StringBuffer sql = new StringBuffer("select pb.*,");
        sql.append(enumToCaseWhenString(BannerTypeEnum.class,"type_value","","typeName"));
        sql.append(" from poster_banner pb where pb.type_value = #{type} ORDER BY edit_time DESC limit 0,4");
        return sql.toString();
    }

}
