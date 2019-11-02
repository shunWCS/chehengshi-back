package com.dingguan.cheHengShi.user.repository;

import com.dingguan.cheHengShi.user.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by zyc on 2018/12/21.
 */
public interface ManufacturerRepository extends JpaRepository<Manufacturer,String> {

    @Query(value = "select max(sort) from `manufacturer` ", nativeQuery=true)
    Integer findMaxSort();



}