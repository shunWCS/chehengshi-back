package com.dingguan.cheHengShi.home.mapper;

import com.dingguan.cheHengShi.common.resp.MyMapper;
import com.dingguan.cheHengShi.home.entity.Favorite;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: czh
 * @Date: 2019/9/7 15:29
 */
@Mapper
public interface FavoriteMapper extends MyMapper<Favorite> {

    @Select("select * from favorite where open_id=#{openId} and status = 1")
    List<Favorite> myFavoriteList(String openId);

    @Select("select * from favorite where id = #{id} and open_id = #{openId} and type = #{type} ")
    Favorite selectByTypeId(@Param("type") String type,@Param("id") String id,@Param("openId") String openId);

    @Delete(" delete from favorite where type= #{type} and id = #{id} and open_id = #{openId}")
    Integer deleteByMyParam(@Param("type") String type,@Param("id") String id,@Param("openId") String openId);
}
