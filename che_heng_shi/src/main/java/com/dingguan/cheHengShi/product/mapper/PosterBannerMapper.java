package com.dingguan.cheHengShi.product.mapper;

import com.dingguan.cheHengShi.common.resp.MyMapper;
import com.dingguan.cheHengShi.home.entity.CommonPoster;
import com.dingguan.cheHengShi.product.entity.PosterBanner;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PosterBannerMapper extends MyMapper<PosterBanner> {

    @Select(" select *from poster_banner where ref_id = #{refId}")
    PosterBanner selectPosterBannerByRefId(String refId);

    @Select(" select *from poster_banner where id = #{id} and type_value = #{typeValue}")
    PosterBanner selectPosterBannerByRefIdAndType(@Param("id") String id, @Param("typeValue") String typeValue);

    @Delete("delete from poster_banner where ref_id = #{refId}")
    Integer deletePosterBannerByRefId(String refId);

    @SelectProvider(type = PosterBannerMapperProvider.class,method = "selectCommonPosterByTypeValue")
    List<CommonPoster> selectCommonPosterByTypeValue(String typeValue);

    @SelectProvider(type = PosterBannerMapperProvider.class,method = "getHomePhoto")
    List<PosterBanner> getHomePhoto(String isFirst);

    @SelectProvider(type = PosterBannerMapperProvider.class,method = "getImages")
    List<PosterBanner> getImages(String type);
}
