package com.dingguan.cheHengShi.product.repository;

import com.dingguan.cheHengShi.product.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by zyc on 2018/12/20.
 */
public interface ProductTypeRepository extends JpaRepository<ProductType,String> {

    @Query(value = "select max(sort) from product_type ", nativeQuery=true)
    Integer findMaxSort();

}