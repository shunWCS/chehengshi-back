package com.dingguan.cheHengShi.product.repository;

import com.dingguan.cheHengShi.product.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: czh
 * @Date: 2019/9/16 19:42
 */
public interface CourseRepository extends JpaRepository<Course,String> {

    @Query(value = "select * from `course` f where id =?1", nativeQuery=true)
    Course findByPrimaryKey(String id);

    @Query(value = "update `course` c set c.clicks=clicks + ?2 where c.id=?1 ", nativeQuery = true)
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    void updateClicks(String id, Integer num);

    @Query(value = "select * from `course` f  order By sort", nativeQuery=true)
    List<Course> findListAll();

    @Query(value = "select * from `course` f where type_id=?1 and title like %?2% order By sort", nativeQuery=true)
    List<Course> findByTypeIdAndTitleLikeOrderBySort(String typeId, String title);

    @Query(value = "select * from `course` f where type_id=?1 order By sort", nativeQuery=true)
    List<Course> findByTypeIdOrderBySort(String typeId);

    @Query(value = "select * from `course` f where  title like %?1% order By sort", nativeQuery=true)
    List<Course> findByTitleLikeOrderBySort(String title);

    @Query(value = "select max(sort) from `course` ", nativeQuery=true)
    Integer findMaxSort();

    @Query(value = "delete  from `course`  where id =?1", nativeQuery=true)
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    void deleteByPrimaryKey(String id);

    @Query(value = "update course c set c.favorite=?2 where c.id=?1 ", nativeQuery = true)
    @Modifying
    @Transactional
    public void updateFavorite(String id, Integer favorite);

    @Query(value = "update course c set c.apply_count=?2 where c.id=?1 ", nativeQuery = true)
    @Modifying
    @Transactional
    void updateApplyCount(String id, Integer applyCount);
}
