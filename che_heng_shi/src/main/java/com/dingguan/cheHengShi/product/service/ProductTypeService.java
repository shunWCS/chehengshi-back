package com.dingguan.cheHengShi.product.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.product.entity.FileType;
import com.dingguan.cheHengShi.product.entity.ProductType;

import java.util.List;

/**
 * Created by zyc on 2018/12/20.
 */
public interface ProductTypeService {

    ProductType findByPrimaryKey(String id);

    List<ProductType> findList();

    ProductType insertSelective(ProductType fileType);

    void deleteByPrimaryKey(String id);

    ProductType updateByPrimaryKeySelective(ProductType fileType) throws CustomException;

    Integer findMaxSort();


}