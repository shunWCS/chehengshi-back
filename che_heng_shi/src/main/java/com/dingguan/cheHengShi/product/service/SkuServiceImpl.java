package com.dingguan.cheHengShi.product.service;

import com.dingguan.cheHengShi.common.constants.Constants;
import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.UpdateTool;
import com.dingguan.cheHengShi.job.AsynchronousJob;
import com.dingguan.cheHengShi.product.entity.Product;
import com.dingguan.cheHengShi.product.entity.Sku;
import com.dingguan.cheHengShi.product.repository.SkuRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

;

/**
 * Created by zyc on 2018/11/27.
 */
@Service
public class SkuServiceImpl implements SkuService {


    @Autowired
    private SkuRepository skuReposition;
    @Autowired
    private ProductService productService;
    @Autowired
    @Lazy
    private AsynchronousJob asynchronousJob;



    @Override
    public Sku findByPrimaryKey(String id) {
        return skuReposition.findOne(id);
    }

    @Override
    public List<Sku> findList(  String productId ) {
        if(productId==null){
            return skuReposition.findAll();
        }else {
            return skuReposition.findByProductId(productId);
        }
     }




    @Override
    public Sku insertSelective(Sku sku) throws CustomException {
        if(StringUtils.isBlank(sku.getId())){
            sku.setId(Sequences.get());
        }
        Product product  =  productService.findByPrimaryKey(sku.getProductId());

         if(product==null){
            throw new CustomException(Constants.RESP_STATUS_BADREQUEST,"参数瞎填");
        }
        if(sku.getBanner()==null){
            sku.setBanner(product.getBanner());
        }

        if(sku.getIntroduce()==null){
            sku.setIntroduce(product.getIntroduce());
        }

        if(sku.getSales()==null){
            sku.setSales(0);
        }



        sku=skuReposition.save(sku);

        asynchronousJob.synchronousProduct(product,null);
        return  sku;


    }

    @Override
    public void deleteByPrimaryKey(String id) throws CustomException {

        String productId = skuReposition.findById(id);

        skuReposition.delete(id);
        asynchronousJob.synchronousProduct(null,productId);


    }

    @Override
    public Sku updateByPrimaryKeySelective(Sku sku) throws CustomException {
        Sku source = skuReposition.findOne(sku.getId());
        UpdateTool.copyNullProperties(source,sku);

        sku=skuReposition.save(sku);


        String productId = sku.getProductId();
        if(StringUtils.isBlank(productId)){
            productId=skuReposition.findById(sku.getId());
        }

        asynchronousJob.synchronousProduct(null,productId);
        asynchronousJob.synchronousShopCart(null,sku);
        return sku;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void updateStockAndSales(String id, Integer stock,Integer sales) {
        skuReposition.updateSkuStockAndSales(id,stock,sales);
    }


}