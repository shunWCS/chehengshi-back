package com.dingguan.cheHengShi.material.service;


import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.material.entity.FlashView;

import java.util.List;

/**
 * Created by zyc on 2018/11/22.
 */
public interface FlashViewService {


    FlashView findByPrimaryKey(String id);

    List<FlashView> findList();

    FlashView insertSelective(FlashView flashView);

    void deleteByPrimaryKey(String id);

    FlashView updateByPrimaryKeySelective(FlashView flashView) throws CustomException;

    Integer findMaxSort();


}