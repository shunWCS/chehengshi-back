package com.dingguan.cheHengShi.product.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Created by zyc on 2018/12/3.
 */
public interface ProductService {

    Product findByPrimaryKey(String id);
    Product findByPrimaryKey(String id,String openId) throws CustomException;

    ApiResult<List<Product>> findList(Product product, PageRequest pageRequest);

    List<Product> findList(Product product, Sort sort);

    Product insertSelective(Product product) throws CustomException;

    void deleteByPrimaryKey(String id);

    Product updateByPrimaryKeySelective(Product product) throws CustomException;

    Integer findMaxSort();


    Page<Product> findByPoductTypeId(String productTypeId, Integer pageNo, Integer pageSize);

    List<Product> findByProductTypeId(String productTypeId);




    //修改库存和销量
    void updateStockAndSales(String id, Integer stock, Integer sales);

    void updateClicks(String id, Integer num);





}