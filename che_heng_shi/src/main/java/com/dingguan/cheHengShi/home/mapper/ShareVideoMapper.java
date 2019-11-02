package com.dingguan.cheHengShi.home.mapper;

import com.dingguan.cheHengShi.common.resp.MyMapper;
import com.dingguan.cheHengShi.home.entity.ShareVideo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author: czh
 * @Date: 2019/9/29 22:47
 */
@Mapper
public interface ShareVideoMapper extends MyMapper<ShareVideo> {

    @Select("SELECT s.*,CASE s.state when '0' THEN '待审核' when '1' THEN '已审核' END stateName FROM share_video s WHERE s.open_id = #{openId} ORDER BY s.time DESC")
    List<ShareVideo> findByOpenId(String openId);

    @Select("SELECT * FROM share_video s where state = '0' ORDER BY s.time DESC")
    List<ShareVideo> findListAll();

    @Select("SELECT s.*,CASE s.state when '0' THEN '待审核' when '1' THEN '已审核' END stateName FROM share_video s WHERE s.id = #{id}")
    ShareVideo selectById(String id);

    @SelectProvider(type = HomeMapperProvider.class,method = "findPassListAll")
    List<ShareVideo> findPassListAll(String title,String phone,String name);

    @Select("SELECT * FROM share_video s where state = '1' and id = #{id}")
    ShareVideo findVideoPassById(String id);
}
