package com.dingguan.cheHengShi.product.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.home.entity.CourseApply;
import com.dingguan.cheHengShi.product.entity.Course;

import java.util.List;

/**
 * @author: czh
 * @Date: 2019/9/16 19:45
 */
public interface CourseService {

    Course findByPrimaryKey(String id, String openId);

    void updateClicks(String id, Integer num);

    List<Course> findList(String typeId, String title);

    Course insertSelective(Course course)throws CustomException;

    void deleteByPrimaryKey(String id);

    Course updateByPrimaryKeySelective(Course course)throws CustomException;

    List<CourseApply> applyPersonList();

}
