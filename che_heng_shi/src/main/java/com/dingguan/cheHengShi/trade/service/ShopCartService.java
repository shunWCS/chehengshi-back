package com.dingguan.cheHengShi.trade.service;


import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.trade.entity.ShopCart;

import java.util.List;

/**
 * Created by zyc on 2018/11/29.
 */
public interface ShopCartService {

    ShopCart findByPrimaryKey(String id);

    List<ShopCart> findList(String openId);

    ShopCart insertSelective(ShopCart shopCart) throws CustomException;

    void deleteByPrimaryKey(String id);

    ShopCart updateByPrimaryKeySelective(ShopCart shopCart) throws CustomException;


    List<ShopCart> findBySkuId(String skuId);
    List<ShopCart> findByProductId(String productId);



}