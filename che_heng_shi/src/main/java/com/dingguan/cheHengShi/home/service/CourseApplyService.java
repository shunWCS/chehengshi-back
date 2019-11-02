package com.dingguan.cheHengShi.home.service;

import com.dingguan.cheHengShi.home.dto.CommonDetail;
import com.dingguan.cheHengShi.home.dto.CourseVo;
import com.dingguan.cheHengShi.home.dto.FileSearchVo;
import com.dingguan.cheHengShi.home.dto.HomePage;
import com.dingguan.cheHengShi.home.entity.CourseApply;
import com.dingguan.cheHengShi.material.entity.Journalism;
import com.dingguan.cheHengShi.product.entity.Course;

import java.util.List;
import java.util.Map;

/**
 * @author: czh
 * @Date: 2019/9/18 21:34
 */
public interface CourseApplyService {

    List<HomePage> findCourseNews(FileSearchVo fileSearchVo);

    List<Course> findCourseByTypeId(String typeId);

    String saveCourseApply(CourseApply courseApply);

    Map<String, Object> getCourseApplyById(Integer id);

    List<Course> searchCourse(CourseVo courseVo);

    CommonDetail findJournalismById(String id);

    void deleteByPrimaryKey(String id);
}
