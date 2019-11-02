package com.dingguan.cheHengShi.product.repository;

import com.dingguan.cheHengShi.product.entity.ProductParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by zyc on 2018/12/3.
 */
public interface ProductParameterRepository extends JpaRepository<ProductParameter,String> {

    List<ProductParameter> findByProductIdOrderBySort(String productId);


    @Query(value = "select max(sort) from product_type",nativeQuery = true)
    Integer findMaxSort();

}