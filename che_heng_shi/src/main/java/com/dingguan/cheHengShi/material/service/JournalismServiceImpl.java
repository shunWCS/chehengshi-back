package com.dingguan.cheHengShi.material.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.utils.DateUtil;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.UpdateTool;
import com.dingguan.cheHengShi.home.mapper.CourseApplyMapper;
import com.dingguan.cheHengShi.material.entity.FlashView;
import com.dingguan.cheHengShi.material.entity.Journalism;
import com.dingguan.cheHengShi.material.repository.FlashViewReposition;
import com.dingguan.cheHengShi.material.repository.JournalismRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zyc on 2018/12/20.
 */
@Service
public class JournalismServiceImpl implements JournalismService {

    @Autowired
    private JournalismRepository journalismRepository;
    @Autowired
    private CourseApplyMapper courseApplyMapper;


    @Override
    public Journalism findByPrimaryKey(String id,String openId) {
        Journalism journalism = journalismRepository.findOne(id);
        if(journalism!=null){
            updateProductClicks(id,1);
        }
        String beginTime = DateUtil.getFormatDateTime(journalism.getTime(), DateUtil.yyyyMMddFormat);
        journalism.setBeginTime(beginTime);
        String tempType = "3";
        Integer count2 = courseApplyMapper.findIsFavorite(id,openId,tempType);
        if(count2>0){
            journalism.setIsFavorite(1);
        }else {
            journalism.setIsFavorite(0);
        }
        return journalism;
    }

    @Override
    public List<Journalism> findList() {
        Sort sort =new Sort(Sort.Direction.ASC,"sort");
        List<Journalism> all = journalismRepository.findAll(sort);

        return all;
    }




    @Override
    public Journalism insertSelective(Journalism journalism) {
        if(StringUtils.isBlank(journalism.getId())){
            journalism.setId(Sequences.get());
        }
        if(journalism.getSort()==null){
            Integer sort = journalismRepository.findBySort();
            sort=sort==null?1:++sort;
            journalism.setSort(sort);
        }

        if(journalism.getTime()==null){
            journalism.setTime(new Date());
        }
        if(journalism.getClicks()==null){
            journalism.setClicks(0);
        }
        if(journalism.getRecommend()==null){
            journalism.setRecommend("1");
        }
        if(journalism.getFavorite() == null){
            journalism.setFavorite(0);
        }
        return  journalismRepository.save(journalism);


    }

    @Override
    public void deleteByPrimaryKey(String id) {
        journalismRepository.delete(id);
    }

    @Override
    public Journalism updateByPrimaryKeySelective(Journalism journalism) throws CustomException {
        Journalism source = journalismRepository.findOne(journalism.getId());
        UpdateTool.copyNullProperties(source,journalism);

        return journalismRepository.save(journalism);
    }

    @Override
    public Integer findMaxSort() {
        return journalismRepository.findBySort();
    }

    @Override
    public void updateProductClicks(String id, Integer num) {
        journalismRepository.updateProductClicks(id,num);
    }

    @Override
    public List<Journalism> findByRecommendOrderBySort(String recommend) {
        return journalismRepository.findByRecommendOrderBySort(recommend);
    }

    @Override
    public List<Journalism> findListAll(String type) {
        List<Journalism> journalismList = courseApplyMapper.findListAll(type);
        return journalismList;
    }


}