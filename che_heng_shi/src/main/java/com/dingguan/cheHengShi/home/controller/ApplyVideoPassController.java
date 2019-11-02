package com.dingguan.cheHengShi.home.controller;

import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.home.entity.ShareVideo;
import com.dingguan.cheHengShi.home.service.ApplyVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(description = "已经审核过的分享视频接口")
@RestController
@RequestMapping("/passVideo")
@Slf4j
public class ApplyVideoPassController {

    @Autowired
    private ApplyVideoService applyVideoService;

    @ApiOperation(value = "后台管理端查询已经审核分享视频列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="title",value="标题",required=false,paramType="query"),
            @ApiImplicitParam(name="phone",value="联系电话",required=false,paramType="query"),
            @ApiImplicitParam(name="name",value="发布人",required=false,paramType="query")
    })
    @GetMapping()
    public ApiResult<String> findList(String title,String phone,String name){
        List<ShareVideo> shareVideoList = applyVideoService.findPassList(title,phone,name);
        ApiResult result = ApiResult.returnData(shareVideoList);
        return result;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "后台管理端根据id查询分享视频")
    @ApiImplicitParam(name = "id", value = "主键Id", paramType = "path", dataType = "String")
    public ApiResult<String> findById(@PathVariable String id) {
        ShareVideo shareVideo = applyVideoService.findVideoPassById(id);
        return ApiResult.returnData(shareVideo);
    }
}
