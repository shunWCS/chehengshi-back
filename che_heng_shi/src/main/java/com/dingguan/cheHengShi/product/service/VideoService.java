package com.dingguan.cheHengShi.product.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.product.entity.File;
import com.dingguan.cheHengShi.product.entity.Video;

import java.util.List;

/**
 * Created by zyc on 2018/12/20.
 */
public interface VideoService  {


    Video findByPrimaryKey(String id,String openId);

    List<Video> findList(String tyepId,String title);

    Video insertSelective(Video video);

    void deleteByPrimaryKey(String id);

    Video updateByPrimaryKeySelective(Video video) throws CustomException;

    Integer findMaxSort();

    void updateClicks(String id, Integer num);

    void updateSales(String id, Integer num);
    List<Video> findByFlashVideoAndRecommendOrderBySort(String flashVideo,String recommend);

    List<Video> findByFlashVideoOrderBySort(String flashVideo);
}