package com.dingguan.cheHengShi.product.controller;

import com.aliyun.oss.OSSClient;
import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.product.entity.Video;
import com.dingguan.cheHengShi.product.service.VideoService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.*;
import java.net.URL;
import java.text.MessageFormat;
import java.util.List;

import org.springframework.mock.web.MockMultipartFile;
/**
 * Created by zyc on 2018/12/20.
 */

@RestController
@RequestMapping("/video")
@Api(description = "视频")
@Slf4j
public class VideoController {



    @Autowired
    private VideoService videoService;


    @GetMapping("/{id}")
    @ApiOperation(value = "根据 id 查询 视频")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> findById(
            @ApiParam("openId") @RequestParam(value = "openId",required = false)String openId,
            @PathVariable String id) {
        log.info(MessageFormat.format("查看视频详情========================>：id:{1},openId:{2}",id,openId));
        return ApiResult.returnData(videoService.findByPrimaryKey(id,openId));
    }


    @ApiOperation(value = "获取 视频 列表")
    @GetMapping()
    public ApiResult<String> findList(
            @ApiParam("标题") @RequestParam(value = "title",required = false)String title,
            @ApiParam("视频类型id") @RequestParam(value = "typeId",required = false)String typeId
    ) {
        return ApiResult.returnData(videoService.findList(typeId,title));
    }

    @ApiOperation(value = "新增 视频")
    @PostMapping
    @ApiImplicitParam(name = "video", value = "", paramType = "body", dataType = "Video")
    public ApiResult<String> save(@RequestBody @Valid Video video, @ModelAttribute Video model) {


        return ApiResult.returnData(videoService.insertSelective(video));

    }


    @ApiOperation(value = "删除 视频")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> deleteById(@PathVariable String id) {
        videoService.deleteByPrimaryKey(id);
        return ApiResult.returnData(null);
    }


    @ApiOperation(value = "修改 视频")
    @PutMapping("")
    @ApiImplicitParam(name = "video", value = "", paramType = "body", dataType = "Video")
    public ApiResult<String> put(@RequestBody Video video, @ModelAttribute Video model) throws CustomException {
        return ApiResult.returnData(videoService.updateByPrimaryKeySelective(video));
    }



















}