package com.dingguan.cheHengShi.product.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.UpdateTool;
import com.dingguan.cheHengShi.product.entity.ProductParameter;
import com.dingguan.cheHengShi.product.repository.ProductParameterRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zyc on 2018/12/3.
 */
@Service
public class ProductParameterServiceImpl implements ProductParameterService {

    @Autowired
    private ProductParameterRepository productParameterRepository;


    @Override
    public ProductParameter findByPrimaryKey(String id) {
        return productParameterRepository.findOne(id);
    }

    @Override
    public List<ProductParameter> findList(String productId) {
        if(StringUtils.isBlank(productId)){
            return productParameterRepository.findAll();
        }
         return productParameterRepository.findByProductIdOrderBySort(productId);
    }




    @Override
    public ProductParameter insertSelective(ProductParameter productParameter) {
        if(StringUtils.isBlank(productParameter.getId())){
            productParameter.setId(Sequences.get());
        }
        if(productParameter.getSort()==null){
            Integer sort = findMaxSort();
            sort=sort==null?1:++sort;
            productParameter.setSort(sort);
        }
        return  productParameterRepository.save(productParameter);


    }

    @Override
    public void deleteByPrimaryKey(String id) {
        productParameterRepository.delete(id);
    }

    @Override
    public ProductParameter updateByPrimaryKeySelective(ProductParameter productParameter) throws CustomException {
        ProductParameter source = productParameterRepository.findOne(productParameter.getId());
        UpdateTool.copyNullProperties(source,productParameter);
        return productParameterRepository.save(productParameter);
    }

    @Override
    public Integer findMaxSort() {
        return productParameterRepository.findMaxSort();
    }


}