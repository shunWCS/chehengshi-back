package com.dingguan.cheHengShi.home.mapper;

import com.dingguan.cheHengShi.product.entity.File;
import com.dingguan.cheHengShi.product.entity.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UpdateUrlMapper {

    @Select(" select *from file where id = #{id} ")
    File selectFileUrl(String id);

    @Update(" update file set banner = #{banner} where id = #{id}")
    int updateUrlFile(File file);

    @Select(" select *from file")
    List<File> selectFileUrlALL();

    @Select(" select *from video where id = #{id} ")
    Video selectVideoUrl(String id);

    @Update(" update file set banner = #{banner},video = #{video} where id = #{id}")
    Integer updateUrlvideo(Video video);

    @Select(" select *from video")
    List<Video> selectVideoUrlALL();

}
