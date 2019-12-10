package com.dingguan.cheHengShi.home.mapper;

import com.dingguan.cheHengShi.common.resp.MyMapper;
import com.dingguan.cheHengShi.product.entity.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface VideoMapper extends MyMapper<Video> {

    @SelectProvider(type = CourseApplyMapperProvider.class,method = "selectVideo")
    List<Map<String, String>> selectVideo(String title);
}
