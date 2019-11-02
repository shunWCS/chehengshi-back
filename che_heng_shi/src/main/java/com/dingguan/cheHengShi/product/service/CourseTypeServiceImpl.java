package com.dingguan.cheHengShi.product.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.UpdateTool;
import com.dingguan.cheHengShi.product.entity.CourseType;
import com.dingguan.cheHengShi.product.entity.FileType;
import com.dingguan.cheHengShi.product.repository.CourseTypeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: czh
 * @Date: 2019/9/16 19:46
 */
@Service
public class CourseTypeServiceImpl implements CourseTypeService {
    @Autowired
    private CourseTypeRepository courseTypeRepository;

    @Override
    public CourseType findByPrimaryKey(String id) {
        CourseType courseType = courseTypeRepository.findOne(id);
        return courseType;
    }

    @Override
    public CourseType insertSelective(CourseType courseType) {
        if(StringUtils.isBlank(courseType.getId())){
            courseType.setId(Sequences.get());
        }
        if(courseType.getSort()==null){
            Integer sort = courseTypeRepository.findMaxSort();
            sort=sort==null?1:++sort;
            courseType.setSort(sort);
        }
        return courseTypeRepository.save(courseType);
    }

    @Override
    public void deleteByPrimaryKey(String id) {
        courseTypeRepository.delete(id);
    }

    @Override
    public CourseType updateByPrimaryKeySelective(CourseType courseType) throws CustomException {
        CourseType source = courseTypeRepository.findOne(courseType.getId());
        UpdateTool.copyNullProperties(source,courseType);
        return courseTypeRepository.save(courseType);
    }

    @Override
    public List<CourseType> findList() {
        Sort sort =new Sort(Sort.Direction.ASC,"sort");
        return courseTypeRepository.findAll(sort);
    }
}
