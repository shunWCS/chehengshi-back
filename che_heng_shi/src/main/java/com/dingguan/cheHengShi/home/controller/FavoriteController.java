package com.dingguan.cheHengShi.home.controller;

import com.alibaba.fastjson.JSONObject;
import com.dingguan.cheHengShi.common.constants.BaseVo;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.common.resp.PageInfo;
import com.dingguan.cheHengShi.home.entity.Favorite;
import com.dingguan.cheHengShi.home.service.HomeService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;

/**
 * @author: czh
 * @Date: 2019/9/8 8:16
 */
@Api(description = "我的收藏相关接口")
@RestController
@RequestMapping("")
@Slf4j
public class FavoriteController {

    @Autowired
    private HomeService homeService;

    @GetMapping("/favoriteList")
    @ApiOperation(value = "个人中心我的收藏列表")
    @ApiImplicitParam(name="openId",value="查阅者openId",required=true,paramType="query")
    public ApiResult<List<Favorite>> myFavoriteList(BaseVo baseVo,String openId){
        log.info(MessageFormat.format("当前查阅人：openId:{0}", JSONObject.toJSONString(openId)));
        PageHelper.startPage(baseVo.getPageIndex(),baseVo.getPageSize());
        List<Favorite> favoriteList = homeService.myFavoriteList(openId);
        PageInfo<Favorite> pageInfo = new PageInfo<>(favoriteList);
        log.info(MessageFormat.format("查询结果：{0}", JSONObject.toJSONString(pageInfo)));
        ApiResult result = ApiResult.returnData(pageInfo);
        return result;
    }

    @PostMapping("/favorite")
    @ApiOperation(value = "点击收藏")
    public ApiResult<String> favorite(Favorite favorite){
        log.info(MessageFormat.format("收藏参数：{0}", JSONObject.toJSONString(favorite)));
        log.info(MessageFormat.format("准备收藏====================>：当前阅览人openId：{0},类型type: {1},id:{2}",
                favorite.getOpenId(),favorite.getType(),favorite.getId()));
        String str = homeService.saveFavorite(favorite);
        ApiResult result = ApiResult.returnData(str);
        return result;
    }

    @PostMapping("/cancel")
    @ApiOperation(value = "取消收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name="type",value="收藏的类型(1表示视频,2表示文档,3表示新闻,4表示课程)不能为空",required=true,paramType="form"),
            @ApiImplicitParam(name="id",value="文档id，新闻id，视频id,课程id,不能为空",required=true,paramType="form"),
            @ApiImplicitParam(name="openId",value="当前查阅人不能为空",required=true,paramType="form")
    })
    public ApiResult<String> cancel(String type,String id,String openId){
        log.info(MessageFormat.format("取消收藏参数====================>：类型type: {0}，id: {1},openId: {2}",
                type,id,openId));
        String cancel = homeService.cancelFavorite(type,id,openId);
        ApiResult result = ApiResult.returnData(cancel);
        return result;
    }


}
