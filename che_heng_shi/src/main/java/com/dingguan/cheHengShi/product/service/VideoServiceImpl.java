package com.dingguan.cheHengShi.product.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.utils.DateUtil;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.UpdateTool;
import com.dingguan.cheHengShi.home.mapper.CourseApplyMapper;
import com.dingguan.cheHengShi.product.entity.File;
import com.dingguan.cheHengShi.product.entity.Video;
 import com.dingguan.cheHengShi.product.repository.VideoRepository;
import com.dingguan.cheHengShi.user.entity.BrowseRecord;
import com.dingguan.cheHengShi.user.service.BrowseRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by zyc on 2018/12/20.
 */
@Service
public class VideoServiceImpl implements VideoService {


    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private BrowseRecordService browseRecordService;
    @Autowired
    private CourseApplyMapper courseApplyMapper;

    @Override
    public Video findByPrimaryKey(String id,String openId) {
        Video video = videoRepository.findByPrimaryKey(id);
        video.setBeginTime(DateUtil.getFormatDateTime(video.getTime(),DateUtil.fullFormat));
        String tempType = "1";
        Integer count2 = courseApplyMapper.findIsFavorite(id,openId,tempType);
        if(count2>0){
            video.setIsFavorite(1);
        }else {
            video.setIsFavorite(0);
        }
        if(video!=null){
            updateClicks(id,1);
            if(StringUtils.isNotBlank(openId)){
                BrowseRecord build = BrowseRecord.builder()
                        .id(Sequences.get())
                        .openId(openId)
                        .banner(video.getBanner())
                        .introduce(video.getIntroduce())
                        .productId(id)
                        .productType("2")
                        .time(new Date())
                        .Title(video.getTitle())
                        .price(video.getPrice())
                        .sales(video.getSales())
                        .clicks(video.getClicks())
                        .build();
                browseRecordService.insertSelective(build);

            }


        }
        return video;
    }

    @Override
    public List<Video> findList(String tyepId,String title) {



        List<Video> videoList = null;
        if (StringUtils.isBlank(tyepId) && StringUtils.isBlank(title)) {
            videoList = videoRepository.findList();
        } else if (StringUtils.isNotBlank(tyepId) && StringUtils.isNotBlank(title)) {
            videoList = videoRepository.findByTypeIdAndTitleLikeOrderBySort(tyepId, title);
        } else if (StringUtils.isNotBlank(tyepId) && StringUtils.isBlank(title)) {
            videoList = videoRepository.findByTypeIdOrderBySort(tyepId);
        } else {
            videoList = videoRepository.findByTitleLikeOrderBySort(title);
        }


        return videoList;










    }




    @Override
    public Video insertSelective(Video video) {
        if(StringUtils.isBlank(video.getId())){
            video.setId(Sequences.get());
        }
        if(video.getSort()==null){
            Integer sort = videoRepository.findMaxSort();
            sort=sort==null?1:++sort;
            video.setSort(sort);
        }

        if(video.getTime()==null){
            video.setTime(new Date());
        }
        if(video.getClicks()==null){
            video.setClicks(0);
        }
        if(video.getSales()==null){
            video.setSales(0);
        }
        if(video.getFavorite() == null){
            video.setFavorite(0);
        }
        if(StringUtils.isBlank(video.getFlashVideo())){
            video.setFlashVideo("1");
        }
        if(video.getRecommend()==null){
            video.setRecommend("1");
        }

        return  videoRepository.save(video);


    }

    @Override
    @Transactional
    public void deleteByPrimaryKey(String id) {
        videoRepository.deleteByPrimaryKey(id);
    }

    @Override
    public Video updateByPrimaryKeySelective(Video video) throws CustomException {
        Video source = videoRepository.findByPrimaryKey(video.getId());
        UpdateTool.copyNullProperties(source,video);

        return videoRepository.save(video);
    }

    @Override
    public Integer findMaxSort() {
        return videoRepository.findMaxSort();
    }

    @Override
    public void updateClicks(String id, Integer num) {
        videoRepository.updateClicks(id,num);
    }

    @Override
    public void updateSales(String id, Integer num) {
        videoRepository.updateSales(id,num);
    }
     @Override
    public List<Video> findByFlashVideoAndRecommendOrderBySort(String flashVideo,String recommend) {
        return videoRepository.findByFlashVideoAndRecommendOrderBySort(flashVideo,recommend);
    }

    @Override
    public List<Video> findByFlashVideoOrderBySort(String flashVideo) {
        return videoRepository.findByFlashVideoOrderBySort(flashVideo);
    }


}