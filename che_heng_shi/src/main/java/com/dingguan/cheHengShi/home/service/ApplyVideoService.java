package com.dingguan.cheHengShi.home.service;

import com.dingguan.cheHengShi.home.entity.ShareVideo;

import java.util.List;

/**
 * @author: czh
 * @Date: 2019/9/29 22:52
 */
public interface ApplyVideoService {

    Integer save(ShareVideo shareVideo);

    List<ShareVideo> findByOpenId(String openId);

    List<ShareVideo> findList();

    ShareVideo findById(String id);

    Integer applyVideo(ShareVideo shareVideo);

    Integer deleteByPrimaryKey(String id);

    List<ShareVideo> findPassList(String title,String phone,String name);

    ShareVideo findVideoPassById(String id);
}
