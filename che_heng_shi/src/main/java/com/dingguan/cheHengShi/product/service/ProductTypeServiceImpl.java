package com.dingguan.cheHengShi.product.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.UpdateTool;
import com.dingguan.cheHengShi.product.entity.ProductType;
import com.dingguan.cheHengShi.product.repository.ProductTypeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zyc on 2018/12/20.
 */
@Service
public class ProductTypeServiceImpl implements ProductTypeService {



    @Autowired
    private ProductTypeRepository productTypeRepository;


    @Override
    public ProductType findByPrimaryKey(String id) {
        ProductType productType = productTypeRepository.findOne(id);

        return productType;
    }

    @Override
    public List<ProductType> findList() {
        Sort sort =new Sort(Sort.Direction.ASC,"sort");
        return productTypeRepository.findAll(sort);
    }




    @Override
    public ProductType insertSelective(ProductType productType) {
        if(StringUtils.isBlank(productType.getId())){
            productType.setId(Sequences.get());
        }
        if(productType.getSort()==null){
            Integer sort = productTypeRepository.findMaxSort();
            sort=sort==null?1:++sort;
            productType.setSort(sort);
        }



        return  productTypeRepository.save(productType);


    }

    @Override
    public void deleteByPrimaryKey(String id) {
        productTypeRepository.delete(id);
    }

    @Override
    public ProductType updateByPrimaryKeySelective(ProductType productType) throws CustomException {
        ProductType source = productTypeRepository.findOne(productType.getId());
        UpdateTool.copyNullProperties(source,productType);
        return productTypeRepository.save(productType);
    }

    @Override
    public Integer findMaxSort() {
        return productTypeRepository.findMaxSort();
    }






}