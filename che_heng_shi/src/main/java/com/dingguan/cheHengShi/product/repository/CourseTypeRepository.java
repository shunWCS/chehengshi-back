package com.dingguan.cheHengShi.product.repository;

import com.dingguan.cheHengShi.product.entity.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author: czh
 * @Date: 2019/9/16 19:43
 */
public interface CourseTypeRepository extends JpaRepository<CourseType,String> {

    @Query(value = "select max(sort) from course_type", nativeQuery=true)
    Integer findMaxSort();
}
