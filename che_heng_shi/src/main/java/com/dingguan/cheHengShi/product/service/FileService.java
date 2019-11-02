package com.dingguan.cheHengShi.product.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.material.entity.Journalism;
import com.dingguan.cheHengShi.product.entity.File;

import java.util.List;

/**
 * Created by zyc on 2018/12/20.
 */
public interface FileService {

    File findByPrimaryKey(String id,String openId);

    List<File> findList(String tyepId,String title);

    File insertSelective(File file);

    void deleteByPrimaryKey(String id);

    File updateByPrimaryKeySelective(File file) throws CustomException;

    Integer findMaxSort();

    void updateClicks(String id, Integer num);

    void updateSales(String id, Integer num);

    List<File> findByRecommendOrderBySort(String recommend);

}