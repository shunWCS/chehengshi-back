package com.dingguan.cheHengShi.product.repository;

import com.dingguan.cheHengShi.product.entity.PosterType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PosterTypeRepository extends JpaRepository<PosterType,String> {

    @Query(value = "select max(sort) from poster_type", nativeQuery=true)
    Integer findMaxSort();
}
