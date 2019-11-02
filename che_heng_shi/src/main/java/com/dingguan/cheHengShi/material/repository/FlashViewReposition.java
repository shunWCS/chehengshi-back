package com.dingguan.cheHengShi.material.repository;

import com.dingguan.cheHengShi.material.entity.FlashView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * Created by zyc on 2018/11/22.
 */
public interface FlashViewReposition extends JpaRepository<FlashView, String> {


    FlashView findFirstByOrderBySortDesc();

    @Query(value = "select max(sort) from flash_view", nativeQuery=true)
    Integer findBySort();


}