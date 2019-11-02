package com.dingguan.cheHengShi.trade.service;


import com.dingguan.cheHengShi.common.constants.Constants;
import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.UpdateTool;
import com.dingguan.cheHengShi.product.entity.Product;
import com.dingguan.cheHengShi.product.entity.Sku;
import com.dingguan.cheHengShi.product.service.ProductService;
import com.dingguan.cheHengShi.product.service.SkuService;
import com.dingguan.cheHengShi.trade.entity.ShopCart;
import com.dingguan.cheHengShi.trade.repository.ShopCartRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by zyc on 2018/11/29.
 */
@Service
public class ShopCartServiceImpl implements ShopCartService {

    @Autowired
    private ShopCartRepository shopCartRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private SkuService skuService;


    @Override
    public ShopCart findByPrimaryKey(String id) {
        return shopCartRepository.findOne(id);
    }


    @Override
    public List<ShopCart> findList(String openId) {
        if (StringUtils.isBlank(openId)) {
            return shopCartRepository.findAll();
        }
        return shopCartRepository.findByOpenId(openId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ShopCart insertSelective(ShopCart shopCart) throws CustomException {
        ShopCart byOpenIdAndProductId = shopCartRepository.findByOpenIdAndProductIdAndSkuId(shopCart.getOpenId(), shopCart.getProductId(), shopCart.getSkuId());
        if (byOpenIdAndProductId == null) {
            if (StringUtils.isBlank(shopCart.getId())) {
                shopCart.setId(Sequences.get());
            }
            shopCart.setTime(new Date());

            Product product = productService.findByPrimaryKey(shopCart.getProductId());
            shopCart.setProductBanner(product.getBanner());
            shopCart.setProductIntroduce(product.getIntroduce());
            shopCart.setProductName(product.getName());

            Sku sku = skuService.findByPrimaryKey(shopCart.getSkuId());
            shopCart.setSkuBanner(sku.getBanner());
            shopCart.setSkuIntegral(sku.getIntegral());
            shopCart.setSkuName(sku.getName());
            shopCart.setSkuPrice(sku.getPrice());
            shopCart.setSkuIntroduce(sku.getIntroduce());


            shopCart.setManufacturerId(product.getManufacturerId());
            shopCart.setManufacturerBanner(product.getManufacturerBanner());
            shopCart.setManufacturerName(product.getManufacturerName());
            shopCart.setPostage(product.getPostage());
            shopCart = shopCartRepository.save(shopCart);
            return shopCart;
        } else {
            //+++  ---
            Integer num = shopCart.getNum();
            int i = byOpenIdAndProductId.getNum() + num;
            if (i == 0) {
                deleteByPrimaryKey(byOpenIdAndProductId.getId());
                return null;
            } else if (i < 0) {
                throw new CustomException(Constants.RESP_STATUS_BADREQUEST, "购物车商品数量不能为负数");
            } else {
                shopCart.setNum(i);
                shopCart.setId(byOpenIdAndProductId.getId());

                return updateByPrimaryKeySelective(shopCart);
            }
        }


    }

    @Override
    public void deleteByPrimaryKey(String id) {
        shopCartRepository.delete(id);
    }

    @Override
    public ShopCart updateByPrimaryKeySelective(ShopCart shopCart) throws CustomException {
        ShopCart source = shopCartRepository.findOne(shopCart.getId());
        UpdateTool.copyNullProperties(source, shopCart);
        return shopCartRepository.save(shopCart);
    }

    @Override
    public List<ShopCart> findBySkuId(String skuId) {
        return shopCartRepository.findBySkuId(skuId);
    }

    @Override
    public List<ShopCart> findByProductId(String productId) {
        return shopCartRepository.findByProductId(productId);
    }


}