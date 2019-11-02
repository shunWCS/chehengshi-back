package com.dingguan.cheHengShi.material.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.material.entity.FlashView;
import com.dingguan.cheHengShi.material.entity.Journalism;

import java.util.List;

/**
 * Created by zyc on 2018/12/20.
 */
public interface JournalismService {

    Journalism findByPrimaryKey(String id,String openId);

    List<Journalism> findList();

    Journalism insertSelective(Journalism journalism);

    void deleteByPrimaryKey(String id);

    Journalism updateByPrimaryKeySelective(Journalism journalism) throws CustomException;

    Integer findMaxSort();

    void updateProductClicks(String id, Integer num);

    List<Journalism> findByRecommendOrderBySort(String recommend);

    List<Journalism> findListAll(String type);
}