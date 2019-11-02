package com.dingguan.cheHengShi.product.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.product.entity.CourseType;

import java.util.List;

/**
 * @author: czh
 * @Date: 2019/9/16 19:46
 */
public interface CourseTypeService {

    CourseType findByPrimaryKey(String id);

    CourseType insertSelective(CourseType courseType);

    void deleteByPrimaryKey(String id);

    CourseType updateByPrimaryKeySelective(CourseType courseType)throws CustomException;

    List<CourseType> findList();
}
