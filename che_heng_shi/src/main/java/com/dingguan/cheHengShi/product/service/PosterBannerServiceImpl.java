package com.dingguan.cheHengShi.product.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.utils.DateUtil;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.UpdateTool;
import com.dingguan.cheHengShi.common.utils.Util;
import com.dingguan.cheHengShi.home.entity.CommonPoster;
import com.dingguan.cheHengShi.home.enumerate.IsFirstEnum;
import com.dingguan.cheHengShi.home.mapper.VideoMapper;
import com.dingguan.cheHengShi.product.entity.PosterBanner;
import com.dingguan.cheHengShi.product.mapper.PosterBannerMapper;
import com.dingguan.cheHengShi.product.repository.PosterBannerRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PosterBannerServiceImpl implements PosterBannerService {

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private PosterBannerMapper posterBannerMapper;

    @Autowired
    private PosterBannerRepository posterBannerRepository;

    @Override
    public PosterBanner selectPosterBannerByRefId(String refId,String typeValue) {
        String[] typeValues = refId.split(",");
        String tempRefId = typeValues[0];
        typeValue = typeValues[1];
        PosterBanner posterBanner = posterBannerMapper.selectPosterBannerByRefIdAndType(tempRefId,typeValue);
        return posterBanner;
    }

    @Override
    public List<PosterBanner> findList(String typeValue, String title) {

        List<PosterBanner> list = null;
        if (StringUtils.isBlank(typeValue) && StringUtils.isBlank(title)) {
            list = posterBannerRepository.findListAll();
        } else if (StringUtils.isNotBlank(typeValue) && StringUtils.isNotBlank(title)) {
            list = posterBannerRepository.findByTypeIdAndTitleLike(typeValue, title);
        } else if (StringUtils.isNotBlank(typeValue) && StringUtils.isBlank(title)) {
            list = posterBannerRepository.findByTypeId(typeValue);
        } else {
            list = posterBannerRepository.findByTitleLike(title);
        }
        return list;
    }

    @Override
    public PosterBanner insertSelective(PosterBanner posterBanner) {
        if(StringUtils.isBlank(posterBanner.getId())){
            posterBanner.setId(Sequences.get());
        }
        posterBanner.setCreateTime(DateUtil.getFormatDateTime(new Date(),DateUtil.fullFormat));
        posterBanner.setEditTime(DateUtil.getFormatDateTime(new Date(),DateUtil.fullFormat));
        PosterBanner save = posterBannerRepository.save(posterBanner);
        return save;
    }

    @Override
    public void deletePosterBannerByRefId(String refId) {
        Integer integer = posterBannerMapper.deletePosterBannerByRefId(refId);
    }

    @Override
    public PosterBanner updateByPrimaryKeySelective(PosterBanner posterBanner) throws CustomException {
        PosterBanner source = posterBannerMapper.selectPosterBannerByRefId(posterBanner.getRefId());
        if(Util.isEmpty(source)){
            throw new RuntimeException("该数据不存在!");
        }
        UpdateTool.copyNullProperties(source, posterBanner);
        posterBanner.setEditTime(DateUtil.getFormatDateTime(new Date(),DateUtil.fullFormat));
        PosterBanner posterBanner1 = posterBannerRepository.save(posterBanner);
        return posterBanner1;
    }

    @Override
    public List<CommonPoster> findListForBanner(String typeValue, String title) {
        List<CommonPoster> lists = posterBannerMapper.selectCommonPosterByTypeValue(typeValue);
        return lists;
    }

    @Override
    public List<CommonPoster> findListForPullList(String typeValue) {
        List<CommonPoster> lists = null;
        if("video".equals(typeValue)){
            lists = videoMapper.selectVideoForPull();
        }
        if("file".equals(typeValue)){
            lists = videoMapper.selectForFilePull();
        }
        if("journalism".equals(typeValue)){
            lists = videoMapper.selectForJournalismPull();
        }
        if("course".equals(typeValue)){
            lists = videoMapper.selectForCoursePull();
        }
        if("store".equals(typeValue)){
            lists = videoMapper.selectForProductPull();
        }
        return lists;
    }

    @Override
    public List<PosterBanner> getHomePhoto() {
        List<PosterBanner> homePhotos = posterBannerMapper.getHomePhoto(IsFirstEnum.YES.getCode());
        return homePhotos;
    }

    @Override
    public List<PosterBanner> getImages(Integer type) {
        String typeValue = null;
        if(type == 0){
            typeValue = "video";
        }
        if(type == 1){
            typeValue = "file";
        }
        if(type == 2){
            typeValue = "journalism";
        }
        if(type == 3){
            typeValue = "course";
        }
        if(type == 4){
            typeValue = "store";
        }
        List<PosterBanner> images = posterBannerMapper.getImages(typeValue);
        return images;
    }
}
