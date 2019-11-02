package com.dingguan.cheHengShi.home.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingguan.cheHengShi.common.constants.BaseVo;
import com.dingguan.cheHengShi.common.constants.Constants;
import com.dingguan.cheHengShi.common.constants.Parameters;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.common.resp.PageInfo;
import com.dingguan.cheHengShi.home.dto.*;

import com.dingguan.cheHengShi.home.entity.ProductPoster;
import com.dingguan.cheHengShi.home.service.HomeService;
import com.dingguan.cheHengShi.material.entity.Material;
import com.dingguan.cheHengShi.user.entity.User;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spring.web.json.Json;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;

/**
 * @author: czh
 * @Date: 2019/9/5 19:47
 */
@Api(description = "小程序首页相关接口")
@RestController
@Slf4j
public class HomeController {

    @Autowired
    private HomeService homeService;

    @Autowired
    private Parameters parameters;

    @GetMapping("/home/poster")
    @ApiOperation(value="小程序首页海报轮播图")
    public ApiResult<List<ProductPoster>> getPoster(){
        List<ProductPoster> posterList = homeService.getPoster();
        ApiResult result = ApiResult.returnData(posterList);
        return result;
    }


    @GetMapping("/home/list")
    @ApiOperation(value="小程序首页列表(包括新闻、文档、视频)(热门推介)")
    public ApiResult<List<HomePage>> homePageList(CommonVo commonVo){
        log.info(MessageFormat.format(" 是否推荐：{0} 点击量：{1}", commonVo.getRecommend(),commonVo.getClicks()));
        String showPrice = getMaterial().getData();
        PageHelper.startPage(commonVo.getPageIndex(),commonVo.getPageSize());
        List<HomePage> homeList = homeService.getHomeList(commonVo, showPrice);
        PageInfo<HomePage> pageInfo = new PageInfo<>(homeList);
        log.info(MessageFormat.format("查询结果：{0}",JSONObject.toJSONString(pageInfo)));
        ApiResult result = ApiResult.returnData(pageInfo);
        return result;
    }



    @GetMapping("/home/queryType")
    @ApiOperation(value = "小程序首页顶部搜索类型下拉选")
    public ApiResult<List<QueryType>> findQueryType(){
        List<QueryType> queryType = homeService.findQueryType("query_type");
        return ApiResult.returnData(queryType);
    }

    @PostMapping("/home/search")
    @ApiOperation(value = "小程序首页顶部搜索功能")
    public ApiResult<List<HomePage>> search(SearchVo searchVo){
        String showPrice = getMaterial().getData();
        log.info(MessageFormat.format("查询参数：{0}",JSONObject.toJSONString(searchVo)));
        PageHelper.startPage(searchVo.getPageIndex(),searchVo.getPageSize());
        List<HomePage> dataInfo = homeService.search(searchVo,showPrice);
        PageInfo<HomePage> pageInfo = new PageInfo<>(dataInfo);
        log.info(MessageFormat.format("查询结果：{0}",JSONObject.toJSONString(pageInfo)));
        ApiResult result = ApiResult.returnData(pageInfo);
        return result;
    }

    @PostMapping("/fileList")
    @ApiOperation(value = "点击小程序首页查询维修资料列表(包含搜索功能)")
    public ApiResult<List<HomePage>> findFile(FileSearchVo fileSearchVo){
        String showPrice = getMaterial().getData();
        log.info(MessageFormat.format("维修资料查询参数：{0}",JSONObject.toJSONString(fileSearchVo)));
        PageHelper.startPage(fileSearchVo.getPageIndex(),fileSearchVo.getPageSize());
        List<HomePage> fileList = homeService.findFile(fileSearchVo,showPrice);
        PageInfo<HomePage> pageInfo = new PageInfo<>(fileList);
        log.info(MessageFormat.format("维修资料查询结果：{0}",JSONObject.toJSONString(pageInfo)));
        return ApiResult.returnData(pageInfo);
    }

    @PostMapping("/videoList")
    @ApiOperation(value = "点击小程序首页查询培训视频列表(包含搜索功能)")
    public ApiResult<List<HomePage>> findVideo(FileSearchVo fileSearchVo){
        String showPrice = getMaterial().getData();
        log.info(MessageFormat.format("视频查询参数：{0}",JSONObject.toJSONString(fileSearchVo)));
        PageHelper.startPage(fileSearchVo.getPageIndex(),fileSearchVo.getPageSize());
        List<HomePage> videoList = homeService.findVideo(fileSearchVo,showPrice);
        PageInfo<HomePage> pageInfo = new PageInfo<>(videoList);
        log.info(MessageFormat.format("视频查询结果：{0}",JSONObject.toJSONString(pageInfo)));
        return ApiResult.returnData(pageInfo);
    }

    @PostMapping("/journalismList")
    @ApiOperation(value = "点击小程序首页新闻公告列表数据")
    public ApiResult<List<HomePage>> findJournalism(FileSearchVo fileSearchVo){
        log.info(MessageFormat.format("新闻公告查询参数：{0}",JSONObject.toJSONString(fileSearchVo)));
        PageHelper.startPage(fileSearchVo.getPageIndex(),fileSearchVo.getPageSize());
        List<HomePage> journalismList =  homeService.findJournalism(fileSearchVo);
        PageInfo<HomePage> pageInfo = new PageInfo<>(journalismList);
        log.info(MessageFormat.format("新闻公告查询结果：{0}",JSONObject.toJSONString(pageInfo)));
        return ApiResult.returnData(pageInfo);
    }

    @GetMapping("/counselor")
    @ApiOperation(value = "点击小程序专业咨询查看客服人员列表")
    public ApiResult<List<User>> findByConsultantAndRecommend(BaseVo baseVo){
        PageHelper.startPage(baseVo.getPageIndex(),baseVo.getPageSize());
        List<User> userList = homeService.findByConsultantAndRecommend("2","2");
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        ApiResult result = ApiResult.returnData(pageInfo);
        return result;
    }

    @GetMapping("/get_openId")
    @ApiOperation(value="根据昵称获取openId")
    @ApiImplicitParams({
            @ApiImplicitParam(name="nickName",value="微信昵称",required=true,paramType="query"),
            @ApiImplicitParam(name="phone",value="手机号",required=true,paramType="query")
    })
    public ApiResult<User> getOpenId(String nickName,String phone){
        User user = homeService.getOpenId(nickName,phone);
        ApiResult result = ApiResult.returnData(user);
        return result;
    }

    @GetMapping("/show")
    @ApiOperation(value = "是否开放商城选项和是否显示有价格的产品，返回show显示，hide隐藏")
    public ApiResult<String> showPrice(){
        String showPrice = getMaterial().getData();
        if("ALL".equals(showPrice)){
            return ApiResult.returnData("show");
        }else {
            return ApiResult.returnData("hide");
        }

    }

    private ApiResult<String> getMaterial(){
        Material material = homeService.findMaterialById();
        ApiResult showPrice = null;
        //1要显示收费的内容，0不显示收费的内容
        String rate = material.getRate().toString();
        String tempRate = rate.substring(0, rate.lastIndexOf("."));
        log.info(MessageFormat.format("素材配置开关rate是: {0}", JSON.toJSONString(tempRate)));
        if("1".equals(tempRate)){
            showPrice = ApiResult.returnData("ALL");
        }else {
            showPrice = ApiResult.returnData("NO");
        }
        return showPrice;
    }



}
