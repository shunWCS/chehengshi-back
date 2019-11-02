package com.dingguan.cheHengShi.home.service;

import com.dingguan.cheHengShi.home.dto.CommonVo;
import com.dingguan.cheHengShi.home.dto.FileSearchVo;
import com.dingguan.cheHengShi.home.dto.HomePage;
import com.dingguan.cheHengShi.home.dto.QueryType;
import com.dingguan.cheHengShi.home.dto.SearchVo;
import com.dingguan.cheHengShi.home.entity.Favorite;
import com.dingguan.cheHengShi.home.entity.ProductPoster;
import com.dingguan.cheHengShi.material.entity.Material;
import com.dingguan.cheHengShi.user.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author: czh
 * @Date: 2019/9/5 19:50
 */
public interface HomeService {
    List<Map<String, Object>> findByAllList(Integer clicks, String recommend);

    List<HomePage> getHomeList(CommonVo commonVo,String showPrice);

    List<User> findByConsultantAndRecommend(String consultant,String recommend);

    List<QueryType> findQueryType(String valueCode);

    List<HomePage> search(SearchVo searchVo,String showPrice);

    String saveFavorite(Favorite favorite);

    String cancelFavorite(String type, String id,String openId);

    List<Favorite> myFavoriteList(String openId);

    List<ProductPoster> getPoster();

    List<HomePage> findFile(FileSearchVo fileSearchVo,String showPrice);

    List<HomePage> findVideo(FileSearchVo fileSearchVo,String showPrice);

    List<HomePage> findJournalism(FileSearchVo fileSearchVo);

    User getOpenId(String nickName, String phone);

    Material findMaterialById();
}
