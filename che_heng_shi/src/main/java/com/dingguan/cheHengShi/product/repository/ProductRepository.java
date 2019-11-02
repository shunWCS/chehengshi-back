package com.dingguan.cheHengShi.product.repository;

import com.dingguan.cheHengShi.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zyc on 2018/12/3.
 */
public interface ProductRepository extends JpaRepository<Product,String> {

    @Query(value = "select max(sort) from product", nativeQuery = true)
    Integer findMaxSort();



    Page<Product> findByProductTypeId(String productTypeId, Pageable page);

    List<Product> findByProductTypeIdOrderBySort(String productType);


    @Query(value = "update product p set p.clicks=clicks + ?2 where p.id=?1 ", nativeQuery = true)
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    public void updateClicks(String id, Integer num);









    @Query(value = "update product p set  p.stock=stock+?2 , p.sales=sales + ?3 where p.id=?1 ",
            nativeQuery = true)
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    public void updateProductStockAndSales(String id, Integer stock, Integer houseNum);









}