package com.dingguan.cheHengShi.product.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.utils.DateUtil;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.UpdateTool;
import com.dingguan.cheHengShi.common.utils.Util;
import com.dingguan.cheHengShi.home.entity.CommonPoster;
import com.dingguan.cheHengShi.home.mapper.VideoMapper;
import com.dingguan.cheHengShi.product.entity.PosterBanner;
import com.dingguan.cheHengShi.product.mapper.PosterBannerMapper;
import com.dingguan.cheHengShi.product.repository.PosterBannerRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
        PosterBanner posterBanner = posterBannerMapper.selectPosterBannerByRefId(tempRefId);
        //如果为空，这个回显是新增的时候回显
        if(Util.isEmpty(posterBanner)){
            posterBanner= new PosterBanner();
            posterBanner.setRefId(tempRefId);
            posterBanner.setTypeValue(typeValue);
            return posterBanner;
        }else {
            //这个时修改的时候回显
            return posterBanner;
        }
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
        PosterBanner save = posterBannerRepository.save(posterBanner);
        return save;
    }

    @Override
    public void deletePosterBannerByRefId(String refId) {
        Integer integer = posterBannerMapper.deletePosterBannerByRefId(refId);
    }

    @Override
    public Integer updateByPrimaryKeySelective(PosterBanner posterBanner) throws CustomException {
        //PosterBanner source = posterBannerRepository.findOne(posterBanner.getId());
        PosterBanner source = posterBannerMapper.selectPosterBannerByRefId(posterBanner.getRefId());
        Integer insert = 0;
        Integer update = 0;
        //如果为空那么做新增操作
        if(Util.isEmpty(source)){
            if(StringUtils.isBlank(posterBanner.getId())){
                posterBanner.setId(Sequences.get());
            }
            posterBanner.setCreateTime(DateUtil.getFormatDateTime(new Date(),DateUtil.fullFormat));
            PosterBanner save = posterBannerRepository.save(posterBanner);
        }else {
            //如果不为空那么做修改操作
            UpdateTool.copyNullProperties(source, posterBanner);
            PosterBanner save = posterBannerRepository.save(posterBanner);
        }
        return insert + update;
    }

    @Override
    public List<CommonPoster> findListForBanner(String typeValue, String title) {
        List<CommonPoster> lists = null;
        //如果typeValue为空那么列表默认显示视频的数据
        if(Util.isEmpty(typeValue)){
            lists = videoMapper.selectVideo(title);
        }
        if("video".equals(typeValue)){
           lists = videoMapper.selectVideo(title);
        }
        if("file".equals(typeValue)){
            lists = videoMapper.selectForFile(title);
        }
        if("journalism".equals(typeValue)){
            lists = videoMapper.selectForJournalism(title);
        }
        if("course".equals(typeValue)){
            lists = videoMapper.selectForCourse(title);
        }
        if("store".equals(typeValue)){
            lists = videoMapper.selectForProduct(title);
        }
        return lists;
    }
}
