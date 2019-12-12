package com.dingguan.cheHengShi.home.mapper;

import com.dingguan.cheHengShi.common.resp.MyMapper;
import com.dingguan.cheHengShi.home.entity.CommonPoster;
import com.dingguan.cheHengShi.product.entity.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface VideoMapper extends MyMapper<Video> {

    @SelectProvider(type = CourseApplyMapperProvider.class,method = "selectVideo")
    List<CommonPoster> selectVideo(String title);

    @SelectProvider(type = CourseApplyMapperProvider.class,method = "selectForFile")
    List<CommonPoster> selectForFile(String title);

    @SelectProvider(type = CourseApplyMapperProvider.class,method = "selectForJournalism")
    List<CommonPoster> selectForJournalism(String title);

    @SelectProvider(type = CourseApplyMapperProvider.class,method = "selectForCourse")
    List<CommonPoster> selectForCourse(String title);

    @SelectProvider(type = CourseApplyMapperProvider.class,method = "selectForProduct")
    List<CommonPoster> selectForProduct(String title);

    @Select(" select v.id refId,v.title,'视频' typeName,'video' typeValue from video v ")
    List<CommonPoster> selectVideoForPull();

    @Select(" select f.id refId,f.title,'资料' typeName,'file' typeValue from file f ")
    List<CommonPoster> selectForFilePull();

    @Select(" select j.id refId,j.title,'新闻' typeName,'journalism' typeValue from journalism j ")
    List<CommonPoster> selectForJournalismPull();

    @Select(" select c.id refId,c.title,'课程' typeName,'course' typeValue from course c ")
    List<CommonPoster> selectForCoursePull();

    @Select(" select pt.id refId,pt.type_name title,'商城' typeName,'store' typeValue from product_type pt ")
    List<CommonPoster> selectForProductPull();

}
