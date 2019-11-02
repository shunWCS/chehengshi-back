package com.dingguan.cheHengShi.material.service;


import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.UpdateTool;
import com.dingguan.cheHengShi.material.entity.FlashView;
import com.dingguan.cheHengShi.material.repository.FlashViewReposition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zyc on 2018/11/22.
 */
@Service
public class FlashViewServiceImpl implements FlashViewService{

    @Autowired
    private FlashViewReposition flashViewReposition;


    @Override
    public FlashView findByPrimaryKey(String id) {
        return flashViewReposition.findOne(id);
    }

    @Override
    public List<FlashView> findList() {


        Sort sort =new Sort(Sort.Direction.ASC,"sort");
        return flashViewReposition.findAll(sort);
    }




    @Override
    public FlashView insertSelective(FlashView flashView) {
        if(StringUtils.isBlank(flashView.getId())){
            flashView.setId(Sequences.get());
        }
        if(flashView.getSort()==null){
            Integer sort = flashViewReposition.findBySort();
            sort=sort==null?1:++sort;
            flashView.setSort(sort);
        }
        return  flashViewReposition.save(flashView);


    }

    @Override
    public void deleteByPrimaryKey(String id) {
        flashViewReposition.delete(id);
    }

    @Override
    public FlashView updateByPrimaryKeySelective(FlashView flashView) throws CustomException {
        FlashView source = flashViewReposition.findOne(flashView.getId());
        UpdateTool.copyNullProperties(source,flashView);

        return flashViewReposition.save(flashView);
    }

    @Override
    public Integer findMaxSort() {
        return flashViewReposition.findBySort();
    }


}