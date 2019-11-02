package com.dingguan.cheHengShi.product.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.material.entity.Journalism;
import com.dingguan.cheHengShi.product.entity.Video;
import com.dingguan.cheHengShi.product.entity.VideoType;

import java.util.List;

/**
 * Created by zyc on 2018/12/20.
 */
public interface VideoTypeService {


    VideoType findByPrimaryKey(String id);

    List<VideoType> findList();

    VideoType insertSelective(VideoType video);

    void deleteByPrimaryKey(String id);

    VideoType updateByPrimaryKeySelective(VideoType video) throws CustomException;

    Integer findMaxSort();


}