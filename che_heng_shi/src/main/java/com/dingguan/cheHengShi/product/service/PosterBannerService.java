package com.dingguan.cheHengShi.product.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.product.entity.PosterBanner;

import java.util.List;
import java.util.Map;

public interface PosterBannerService {
    PosterBanner findByPrimaryKey(String id);

    List<PosterBanner> findList(String typeValue, String title);

    PosterBanner insertSelective(PosterBanner posterBanner);

    void deleteByPrimaryKey(String id);

    PosterBanner updateByPrimaryKeySelective(PosterBanner posterBanner)throws CustomException;

    List<Map<String, String>> findListForBanner(String title,String typeValue);
}
