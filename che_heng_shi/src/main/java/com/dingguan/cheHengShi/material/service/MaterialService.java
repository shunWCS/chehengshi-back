package com.dingguan.cheHengShi.material.service;


import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.material.entity.Material;

import java.util.List;

/**
 * Created by zyc on 2018/12/1.
 */
public interface MaterialService {

    Material findByPrimaryKey(String id);

    List<Material> findList(String type);

    Material insertSelective(Material material);

    void deleteByPrimaryKey(String id);

    Material updateByPrimaryKeySelective(Material material) throws CustomException;

    Integer findMaxSort(String type);

    Material findByType(String type);
    List<Material> findByTypeIn(List<String> typeList);



}