package com.dingguan.cheHengShi.product.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.product.entity.ProductParameter;

import java.util.List;

/**
 * Created by zyc on 2018/12/3.
 */
public interface ProductParameterService {


    ProductParameter findByPrimaryKey(String id);

    List<ProductParameter> findList(String productId);

    ProductParameter insertSelective(ProductParameter productType);

    void deleteByPrimaryKey(String id);

    ProductParameter updateByPrimaryKeySelective(ProductParameter productType) throws CustomException;

    Integer findMaxSort();


}