package com.dingguan.cheHengShi.product.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.UpdateTool;
import com.dingguan.cheHengShi.common.utils.Util;
import com.dingguan.cheHengShi.home.entity.CourseApply;
import com.dingguan.cheHengShi.home.mapper.CourseApplyMapper;
import com.dingguan.cheHengShi.product.entity.Course;
import com.dingguan.cheHengShi.product.repository.CourseRepository;
import com.dingguan.cheHengShi.user.entity.BrowseRecord;
import com.dingguan.cheHengShi.user.service.BrowseRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author: czh
 * @Date: 2019/9/16 19:45
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private BrowseRecordService browseRecordService;
    @Autowired
    private CourseApplyMapper courseApplyMapper;

    @Override
    public Course findByPrimaryKey(String id, String openId) {
        String type = "4";
        Course course = courseApplyMapper.findByPrimaryKey(id);
        Integer count = courseApplyMapper.findCourseByCourseIdAndOpenId(course.getId(),openId);
        Integer count1 = courseApplyMapper.findIsFavorite(course.getId(),openId,type);
        if(count > 0){
            course.setIsApply("YES");
        }else {
            course.setIsApply("NO");
        }
        if(count1 > 0){
            course.setIsFavorite(1);
        }else {
            course.setIsFavorite(0);
        }
        if (course != null) {
            updateClicks(id, 1);
            if (StringUtils.isNotBlank(openId)) {
                BrowseRecord build = BrowseRecord.builder()
                        .id(Sequences.get())
                        .openId(openId)
                        .banner(course.getBanner())
                        .introduce(course.getIntroduce())
                        .productId(id)
                        .productType("3")
                        .time(new Date())
                        .Title(course.getTitle())
                        .price(course.getPrice())
                        .clicks(course.getClicks())
                        .build();

                browseRecordService.insertSelective(build);
            }
        }
        return course;
    }

    @Override
    public void updateClicks(String id, Integer num) {
        courseRepository.updateClicks(id,num);
    }

    @Override
    public List<Course> findList(String typeId, String title) {
        List<Course> courseList = null;
        if (StringUtils.isBlank(typeId) && StringUtils.isBlank(title)) {
            courseList = courseRepository.findListAll();
        } else if (StringUtils.isNotBlank(typeId) && StringUtils.isNotBlank(title)) {
            courseList = courseRepository.findByTypeIdAndTitleLikeOrderBySort(typeId, title);
        } else if (StringUtils.isNotBlank(typeId) && StringUtils.isBlank(title)) {
            courseList = courseRepository.findByTypeIdOrderBySort(typeId);
        } else {
            courseList = courseRepository.findByTitleLikeOrderBySort(title);
        }
        return courseList;
    }

    @Override
    public Course insertSelective(Course course) throws CustomException{
        String introduce = course.getIntroduce();
        introduce = introduce.replaceAll("<.*?>", "").replaceAll("", "");
        introduce = introduce.replaceAll("<.*?", "");
        course.setIntroduce(introduce);
        course.setApplyCount(0);
        if(StringUtils.isBlank(course.getId())){
           course.setId(Sequences.get());
        }
        if(course.getSort() == null){
            Integer maxSort = courseRepository.findMaxSort();
            if(maxSort == null){
                maxSort =1;
            }else {
                ++maxSort;
            }
            course.setSort(maxSort);
        }

        if (course.getClicks() == null) {
            course.setClicks(0);
        }
        if(course.getFavorite() == null){
            course.setFavorite(0);
        }
        if(Util.isEmpty(course.getAddress())){
            course.setAddress(" ");
        }
        if(Util.isEmpty(course.getPerson())){
            course.setPerson(" ");
        }
        if(Util.isEmpty(course.getPhone())){
            course.setPhone(" ");
        }
        if(Util.isEmpty(course.getRemark())){
            course.setRemark(" ");
        }
        return courseRepository.save(course);
    }

    @Override
    public void deleteByPrimaryKey(String id) {
        courseRepository.deleteByPrimaryKey(id);
    }

    @Override
    public Course updateByPrimaryKeySelective(Course course) throws CustomException {
        Course source = courseRepository.findByPrimaryKey(course.getId());
        UpdateTool.copyNullProperties(source, course);
        return courseRepository.save(course);
    }

    @Override
    public List<CourseApply> applyPersonList() {
        List<CourseApply> applyList = courseApplyMapper.findApplyPersonList();
        return applyList;
    }
}
