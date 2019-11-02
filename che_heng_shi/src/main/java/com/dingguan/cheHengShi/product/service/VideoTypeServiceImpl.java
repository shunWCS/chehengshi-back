package com.dingguan.cheHengShi.product.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.UpdateTool;
import com.dingguan.cheHengShi.material.entity.Journalism;
import com.dingguan.cheHengShi.material.repository.JournalismRepository;
import com.dingguan.cheHengShi.product.entity.VideoType;
import com.dingguan.cheHengShi.product.repository.VideoTypeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zyc on 2018/12/20.
 */
@Service
public class VideoTypeServiceImpl implements VideoTypeService {

    @Autowired
    private VideoTypeRepository videoTypeRepository;


    @Override
    public VideoType findByPrimaryKey(String id) {
        VideoType journalism = videoTypeRepository.findOne(id);

        return journalism;
    }

    @Override
    public List<VideoType> findList() {
        Sort sort =new Sort(Sort.Direction.ASC,"sort");
        return videoTypeRepository.findAll(sort);
    }




    @Override
    public VideoType insertSelective(VideoType videoType) {
        if(StringUtils.isBlank(videoType.getId())){
            videoType.setId(Sequences.get());
        }
        if(videoType.getSort()==null){
            Integer sort = videoTypeRepository.findMaxSort();
            sort=sort==null?1:++sort;
            videoType.setSort(sort);
        }



        return  videoTypeRepository.save(videoType);


    }

    @Override
    public void deleteByPrimaryKey(String id) {
        videoTypeRepository.delete(id);
    }

    @Override
    public VideoType updateByPrimaryKeySelective(VideoType videoType) throws CustomException {
        VideoType source = videoTypeRepository.findOne(videoType.getId());
        UpdateTool.copyNullProperties(source,videoType);

        return videoTypeRepository.save(videoType);
    }

    @Override
    public Integer findMaxSort() {
        return videoTypeRepository.findMaxSort();
    }




}