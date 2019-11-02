package com.dingguan.cheHengShi.material.service;


import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.UpdateTool;
import com.dingguan.cheHengShi.material.entity.Material;
import com.dingguan.cheHengShi.material.repository.MaterialRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zyc on 2018/12/1.
 */
@Service
public class MaterialServiceImpl implements MaterialService {



    @Autowired
    private MaterialRepository materialReposotory;


    @Override
    public Material findByPrimaryKey(String id) {
        return materialReposotory.findOne(id);
    }

    @Override
    public List<Material> findList(String type) {

        if(StringUtils.isBlank(type)){
            Sort sort =new Sort(Sort.Direction.ASC,"sort");
            return materialReposotory.findAll(sort);
        }else {
            return materialReposotory.findMaterialsByTypeOrderBySort(type);
        }
    }




    @Override
    public Material insertSelective(Material material) {
        if(StringUtils.isBlank(material.getId())){
            material.setId(Sequences.get());
        }
        if(material.getSort()==null){
            Integer sort = materialReposotory.findBySort(material.getType());
            sort=sort==null?1:++sort;
            material.setSort(sort);
        }

        material.setTime(new Date());

        return  materialReposotory.save(material);


    }

    @Override
    public void deleteByPrimaryKey(String id) {
        materialReposotory.delete(id);
    }

    @Override
    public Material updateByPrimaryKeySelective(Material material) throws CustomException {
        Material source = materialReposotory.findOne(material.getId());
        UpdateTool.copyNullProperties(source,material);
        return materialReposotory.save(material);
    }

    @Override
    public Integer findMaxSort(String type) {
        return materialReposotory.findBySort(type);
    }

    @Override
    public Material findByType(String type) {
        return materialReposotory.findTopByType(type);
    }

    @Override
    public List<Material> findByTypeIn(List<String> typeList) {
        return materialReposotory.findByTypeIn(typeList);
    }


}