package com.dingguan.cheHengShi.product.repository;

import com.dingguan.cheHengShi.product.entity.Sku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zyc on 2018/12/3.
 */
public interface SkuRepository extends JpaRepository<Sku,String > {

    List<Sku> findByProductId(String productId);


    @Query(value = "select product_id from sku where id= ?1",nativeQuery = true)
    String findById(String id);





    @Query(value = "update sku s set s.stock=stock + ?2  ,s.sales=sales+?3   where s.id=?1 ", nativeQuery = true)
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    void updateSkuStockAndSales(String id, Integer stock, Integer sales);



}