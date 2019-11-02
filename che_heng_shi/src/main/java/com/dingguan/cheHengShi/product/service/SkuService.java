package com.dingguan.cheHengShi.product.service;



import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.product.entity.Sku;

import java.util.List;

/**
 * Created by zyc on 2018/11/27.
 */
public interface SkuService {

    Sku findByPrimaryKey(String id);

    List<Sku> findList(String productId);

    Sku insertSelective(Sku sku) throws CustomException;

    void deleteByPrimaryKey(String id) throws CustomException;

    Sku updateByPrimaryKeySelective(Sku sku) throws CustomException;

    void updateStockAndSales(String id, Integer stock, Integer sales);





}