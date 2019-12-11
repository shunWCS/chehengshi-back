package com.dingguan.cheHengShi.product.mapper;

import com.dingguan.cheHengShi.common.resp.MyMapper;
import com.dingguan.cheHengShi.product.entity.PosterBanner;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PosterBannerMapper extends MyMapper<PosterBanner> {

    @Select(" select *from poster_banner where ref_id = #{refId}")
    PosterBanner selectPosterBannerByRefId(String refId);

    @Delete("delete from posert_banner where ref_id = #{refId}")
    Integer deletePosterBannerByRefId(String refId);

}
