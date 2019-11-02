package com.dingguan.cheHengShi.material.repository;

import com.dingguan.cheHengShi.material.entity.Journalism;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zyc on 2018/12/20.
 */
public interface JournalismRepository extends JpaRepository<Journalism,String> {

    @Query(value = "select max(sort) from journalism", nativeQuery=true)
    Integer findBySort();



    @Query(value = "update journalism j set j.clicks=clicks + ?2 where j.id=?1 ", nativeQuery = true)
    @Modifying
    @Transactional
    public void updateProductClicks(String id, Integer num);

    @Query(value = "update journalism j set j.favorite=?2 where j.id=?1 ", nativeQuery = true)
    @Modifying
    @Transactional
    public void updateFavorite(String id, Integer favorite);

    List<Journalism> findByRecommendOrderBySort(String recommend);


}