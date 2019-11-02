package com.dingguan.cheHengShi.product.repository;

import com.dingguan.cheHengShi.product.entity.FileType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by zyc on 2018/12/20.
 */
public interface FileTypeRepository extends JpaRepository<FileType,String> {

    @Query(value = "select max(sort) from file_type", nativeQuery=true)
    Integer findMaxSort();






}