package com.dingguan.cheHengShi.product.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.product.entity.PosterType;

import java.util.List;

public interface PosterTypeService {

    PosterType findByPrimaryKey(String id);

    List<PosterType> findList();

    PosterType insertSelective(PosterType posterType);

    void deleteByPrimaryKey(String id);

    PosterType updateByPrimaryKeySelective(PosterType posterType)throws CustomException;
}
