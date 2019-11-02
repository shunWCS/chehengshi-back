package com.dingguan.cheHengShi.home.controller;

/**
 * @author: czh
 * @Date: 2019/9/29 22:45
 */

import com.alibaba.fastjson.JSONObject;
import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.common.resp.PageInfo;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.Util;
import com.dingguan.cheHengShi.home.entity.ShareVideo;
import com.dingguan.cheHengShi.home.service.ApplyVideoService;
import com.dingguan.cheHengShi.product.entity.Video;
import com.dingguan.cheHengShi.product.service.VideoService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

@Api(description = "分享视频相关接口")
@RestController
@RequestMapping("/applyVideo")
@Slf4j
public class ApplyVideoController {

    @Autowired
    private ApplyVideoService applyVideoService;
    @Autowired
    private VideoService videoService;

    @ApiOperation(value = "小程序确定发布视频")
    @PostMapping()
    public ApiResult<String> save(ShareVideo shareVideo){
        log.info(MessageFormat.format("分享视频参数：{0}", JSONObject.toJSONString(shareVideo)));
        Integer count = applyVideoService.save(shareVideo);
        ApiResult result = null;
        if(count > 0){
            result = ApiResult.returnData("发布成功");
        }else {
            result = ApiResult.returnData("发布失败");
        }
        return result;
    }

    private Video setVideoInfo(ShareVideo shareVideo) {
        Video video = new Video();
        video.setId(Sequences.get());
        video.setTime(new Date());
        video.setClicks(0);
        video.setSales(0);
        video.setFavorite(0);
        video.setFlashVideo("1");
        video.setRecommend("1");
        video.setSort(1);
        video.setTypeId("");
        video.setPrice(new BigDecimal(0));
        if(Util.isNotEmpty(shareVideo.getBanner())){
            video.setBanner(shareVideo.getBanner());
        }
        if(Util.isNotEmpty(shareVideo.getVideo())){
            video.setVideo(shareVideo.getVideo());
        }
        if(Util.isNotEmpty(shareVideo.getIntroduce())){
            video.setIntroduce(shareVideo.getIntroduce());
        }
        if(Util.isNotEmpty(shareVideo.getTitle())){
            video.setTitle(shareVideo.getTitle());
        }
        return video;
    }

    @ApiOperation(value = "小程序的分享赚钱列表")
    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageIndex",value="第几页",required=true,paramType="query",defaultValue = "1"),
            @ApiImplicitParam(name="pageSize",value="一页多少条",required=true,paramType="query",defaultValue = "10"),
            @ApiImplicitParam(name="openId",value="发布人",required=true,paramType="query")
    })
    public ApiResult<List<ShareVideo>> findList(Integer pageIndex, Integer pageSize, String openId){
        log.info(MessageFormat.format("当前查阅人参数：{0}", JSONObject.toJSONString(openId)));
        PageHelper.startPage(pageIndex,pageSize);
        List<ShareVideo> shareVideoList = applyVideoService.findByOpenId(openId);
        PageInfo<ShareVideo> pageInfo = new PageInfo<>(shareVideoList);
        log.info(MessageFormat.format("查询结果：{0}", JSONObject.toJSONString(pageInfo)));
        ApiResult result = ApiResult.returnData(pageInfo);
        return result;
    }

    @ApiOperation(value = "后台管理端没审核分享视频列表")
    @GetMapping()
    public ApiResult<String> findList(){
        List<ShareVideo> shareVideoList = applyVideoService.findList();
        ApiResult result = ApiResult.returnData(shareVideoList);
        return result;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "后台管理端根据id分享视频")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "int")
    public ApiResult<ShareVideo> findById(@PathVariable String id){
        ShareVideo shareVideo = applyVideoService.findById(id);
        ApiResult result = ApiResult.returnData(shareVideo);
        return result;
    }

    @ApiOperation(value = "后台管理端审核视频")
    @PutMapping("")
    @ApiImplicitParam(name = "shareVideo", value = "", paramType = "body", dataType = "ShareVideo")
    public ApiResult<String> put(@RequestBody ShareVideo shareVideo, @ModelAttribute ShareVideo model) throws CustomException {
        Integer count = applyVideoService.applyVideo(shareVideo);
        ApiResult result = null;
        if(count > 0){
            result = ApiResult.returnData("审核成功");
        }else {
            result = ApiResult.returnData("审核失败");
        }
        return result;
    }

    @ApiOperation(value = "小程序删除分享视频")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "分享视频id", paramType = "path", dataType = "String",required = true)
    public ApiResult<String> deleteById(@PathVariable String id){
        Integer count = applyVideoService.deleteByPrimaryKey(id);
        return ApiResult.returnData(null);
    }


}
