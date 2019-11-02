package com.dingguan.cheHengShi.product.repository;

import com.dingguan.cheHengShi.product.entity.VideoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by zyc on 2018/12/20.
 */
public interface VideoTypeRepository extends JpaRepository<VideoType,String> {
    @Query(value = "select max(sort) from video_type", nativeQuery=true)
    Integer findMaxSort();

}