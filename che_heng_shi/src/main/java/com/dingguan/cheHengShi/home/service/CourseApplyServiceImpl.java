package com.dingguan.cheHengShi.home.service;

import com.dingguan.cheHengShi.common.utils.DateUtil;
import com.dingguan.cheHengShi.common.utils.Util;
import com.dingguan.cheHengShi.home.dto.CommonDetail;
import com.dingguan.cheHengShi.home.dto.CourseVo;
import com.dingguan.cheHengShi.home.dto.FileSearchVo;
import com.dingguan.cheHengShi.home.dto.HomePage;
import com.dingguan.cheHengShi.home.entity.CourseApply;
import com.dingguan.cheHengShi.home.mapper.CourseApplyMapper;
import com.dingguan.cheHengShi.material.entity.Journalism;
import com.dingguan.cheHengShi.product.entity.Course;
import com.dingguan.cheHengShi.product.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author: czh
 * @Date: 2019/9/18 21:34
 */
@Service
public class CourseApplyServiceImpl implements CourseApplyService {
    @Autowired
    private CourseApplyMapper courseApplyMapper;
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<HomePage> findCourseNews(FileSearchVo fileSearchVo) {
        List<HomePage> courseNews = courseApplyMapper.findCourseNews(fileSearchVo);
        return courseNews;
    }

    @Override
    public List<Course> findCourseByTypeId(String typeId) {
        if(Util.isEmpty(typeId)){
            throw new RuntimeException("课程类型不能为空!");
        }
        List<Course> courseList = courseApplyMapper.findCourseByTypeId(typeId);
        return courseList;
    }

    @Override
    public String saveCourseApply(CourseApply courseApply) {
        courseApply.setCreateTime(DateUtil.curDateStr());
        int selective = courseApplyMapper.insertSelective(courseApply);
        Course course = courseRepository.findByPrimaryKey(courseApply.getCourseId());
        if(Util.isEmpty(course.getApplyCount())){
            course.setApplyCount(1);
        }else {
            course.setApplyCount(course.getApplyCount() + 1);
        }
        courseRepository.updateApplyCount(course.getId(),course.getApplyCount());
        if(selective > 0){
            return "报名成功!";
        }else {
            return "报名失败!";
        }
    }

    @Override
    public Map<String, Object> getCourseApplyById(Integer id) {
        Map<String,Object> objectMap = courseApplyMapper.getCourseApplyById(id);
        return objectMap;
    }

    @Override
    public List<Course> searchCourse(CourseVo courseVo) {
        List<Course> courseList = courseApplyMapper.searchCourse(courseVo);
        return courseList;
    }

    @Override
    public CommonDetail findJournalismById(String id) {
        CommonDetail commonDetail = courseApplyMapper.findJournalismById(id);
        return commonDetail;
    }

    @Override
    public void deleteByPrimaryKey(String id) {
        CourseApply courseApply = courseApplyMapper.selectByPrimaryKey(id);
        int i = 0;
        if(Util.isNotEmpty(courseApply)){
             i = courseApplyMapper.deleteByPrimaryKey(id);
        }
    }
}
