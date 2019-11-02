package com.dingguan.cheHengShi.home.service;

import com.alibaba.fastjson.JSON;
import com.dingguan.cheHengShi.common.utils.DateUtil;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.Util;
import com.dingguan.cheHengShi.home.entity.ShareVideo;
import com.dingguan.cheHengShi.home.mapper.ShareVideoMapper;
import com.dingguan.cheHengShi.home.mapper.VideoMapper;
import com.dingguan.cheHengShi.product.entity.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: czh
 * @Date: 2019/9/29 22:52
 */
@Service
public class ApplyVideoServiceImpl implements ApplyVideoService {

    @Autowired
    private ShareVideoMapper shareVideoMapper;
    @Autowired
    private VideoMapper videoMapper;

    @Override
    public Integer save(ShareVideo shareVideo) {
        shareVideo.setTime(DateUtil.getFormatDateTime(new Date(),DateUtil.fullFormat));
        if(Util.isEmpty(shareVideo.getBanner())){
            shareVideo.setBanner(" ");
        }
        if(Util.isEmpty(shareVideo.getVideo())){
            shareVideo.setVideo(" ");
        }
        if(Util.isEmpty(shareVideo.getIntroduce())){
            shareVideo.setIntroduce(" ");
        }
        if(Util.isEmpty(shareVideo.getApplyRemark())){
            shareVideo.setApplyRemark(" ");
        }
        int selective = shareVideoMapper.insertSelective(shareVideo);
        return selective;
    }

    @Override
    public List<ShareVideo> findByOpenId(String openId) {
        List<ShareVideo> shareVideoList = shareVideoMapper.findByOpenId(openId);
        return shareVideoList;
    }

    @Override
    public List<ShareVideo> findList() {
        List<ShareVideo> shareVideoList = shareVideoMapper.findListAll();
        return shareVideoList;
    }

    @Override
    public ShareVideo findById(String id) {
        ShareVideo shareVideo = shareVideoMapper.selectById(id);
        return shareVideo;
    }

    @Transactional
    @Override
    public Integer applyVideo(ShareVideo shareVideo) {
        ShareVideo shareVideoTemp = shareVideoMapper.selectByPrimaryKey(shareVideo.getId());
        shareVideoTemp.setState("1");//审核通过
        shareVideoTemp.setApplyRemark(shareVideo.getApplyRemark());
        if(Util.isEmpty(shareVideo.getVideo())){
            throw new RuntimeException("您还没有上传视频，请上传视频再操作!");
        }else {
            shareVideoTemp.setVideo(shareVideo.getVideo());
        }
        int i = shareVideoMapper.updateByPrimaryKey(shareVideoTemp);
        return i;
    }

    @Override
    public Integer deleteByPrimaryKey(String id) {
        int delete = shareVideoMapper.deleteByPrimaryKey(id);
        return delete;
    }

    @Override
    public List<ShareVideo> findPassList(String title,String phone,String name) {
        List<ShareVideo> shareVideoList = shareVideoMapper.findPassListAll(title,phone,name);
        return shareVideoList;
    }

    @Override
    public ShareVideo findVideoPassById(String id) {
        ShareVideo shareVideo = shareVideoMapper.findVideoPassById(id);
        return shareVideo;
    }
}
