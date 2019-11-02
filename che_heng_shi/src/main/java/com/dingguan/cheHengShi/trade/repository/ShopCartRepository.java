package com.dingguan.cheHengShi.trade.repository;

import com.dingguan.cheHengShi.trade.entity.ShopCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by zyc on 2018/11/29.
 */
public interface ShopCartRepository extends JpaRepository<ShopCart,String> {


    List<ShopCart> findByOpenId(String openId);

    ShopCart findByOpenIdAndProductIdAndSkuId(String openId, String productId,String skuId);

    @Query(nativeQuery = true,
            value = "select   s.product_id        FROM shop_cart  s  " +
                    " where  s.id=?1 ")
    String  findById(String id);


    List<ShopCart> findBySkuId(String skuId);
    List<ShopCart> findByProductId(String productId);



}