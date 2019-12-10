package com.dingguan.cheHengShi.product.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.utils.DateUtil;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.UpdateTool;
import com.dingguan.cheHengShi.product.entity.PosterBanner;
import com.dingguan.cheHengShi.product.repository.PosterBannerRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PosterBannerServiceImpl implements PosterBannerService {

    @Autowired
    private PosterBannerRepository posterBannerRepository;

    @Override
    public PosterBanner findByPrimaryKey(String id) {
        PosterBanner posterBanner = posterBannerRepository.findOne(id);
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
        PosterBanner save = posterBannerRepository.save(posterBanner);
        return save;
    }

    @Override
    public void deleteByPrimaryKey(String id) {
        posterBannerRepository.delete(id);
    }

    @Override
    public PosterBanner updateByPrimaryKeySelective(PosterBanner posterBanner) throws CustomException {
        PosterBanner source = posterBannerRepository.findOne(posterBanner.getId());
        UpdateTool.copyNullProperties(source, posterBanner);
        return posterBannerRepository.save(posterBanner);
    }
}