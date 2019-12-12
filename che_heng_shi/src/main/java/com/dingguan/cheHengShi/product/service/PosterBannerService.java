package com.dingguan.cheHengShi.product.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.home.entity.CommonPoster;
import com.dingguan.cheHengShi.product.entity.PosterBanner;

import java.util.List;
import java.util.Map;

public interface PosterBannerService {
    PosterBanner selectPosterBannerByRefId(String id,String typeValue);

    List<PosterBanner> findList(String typeValue, String title);

    PosterBanner insertSelective(PosterBanner posterBanner);

    void deletePosterBannerByRefId(String id);

    PosterBanner updateByPrimaryKeySelective(PosterBanner posterBanner)throws CustomException;

    List<CommonPoster> findListForBanner(String typeValue, String title);

    List<CommonPoster> findListForPullList(String typeValue);

    List<PosterBanner> getHomePhoto();

    List<PosterBanner> getImages(Integer type);
}
