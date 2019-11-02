package com.dingguan.cheHengShi.home.mapper;

import com.dingguan.cheHengShi.home.dto.CommonVo;
import com.dingguan.cheHengShi.home.dto.FileSearchVo;
import com.dingguan.cheHengShi.home.dto.HomePage;
import com.dingguan.cheHengShi.home.dto.QueryType;
import com.dingguan.cheHengShi.home.dto.SearchVo;
import com.dingguan.cheHengShi.home.entity.ProductPoster;
import com.dingguan.cheHengShi.material.entity.Material;
import com.dingguan.cheHengShi.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author: czh
 * @Date: 2019/9/6 19:28
 */
@Mapper
public interface HomeMapper {

    @SelectProvider(type = HomeMapperProvider.class,method = "getHomeList")
    public List<HomePage> getHomeList(CommonVo commonVo,String showPrice);

    @Select("select *from user where consultant= #{consultant} and recommend= #{recommend}")
    List<User> findByConsultantAndRecommend(@Param("consultant") String consultant, @Param("recommend") String recommend);

    @Select("select d.value type,d.value_text typeName from dictionary d where value_code= #{valueCode}")
    List<QueryType> findQueryType(String valueCode);

    @SelectProvider(type = HomeMapperProvider.class,method = "search")
    List<HomePage> search(SearchVo searchVo,String showPrice);

    @SelectProvider(type = HomeMapperProvider.class,method = "searchByType")
    List<HomePage> searchByType(SearchVo searchVo,String showPrice);

    @Select("select id,`name`,banner_t,poster,time from product where poster ='2' and type='1' and `status`='2' ORDER BY time DESC limit 0,4 ")
    List<ProductPoster> getPoster();

    @SelectProvider(type = HomeMapperProvider.class,method = "findFile")
    List<HomePage> findFile(FileSearchVo fileSearchVo,String showPrice);

    @SelectProvider(type = HomeMapperProvider.class,method = "findVideo")
    List<HomePage> findVideo(FileSearchVo fileSearchVo,String showPrice);

    @SelectProvider(type = HomeMapperProvider.class,method = "findJournalism")
    List<HomePage> findJournalism(FileSearchVo fileSearchVo);

    @SelectProvider(type = HomeMapperProvider.class,method = "getOpenId")
    List<User> getOpenId(String nickName, String phone);

    @Select("select * from material where id = '121544853500001' ")
    Material findMaterialById();
}
