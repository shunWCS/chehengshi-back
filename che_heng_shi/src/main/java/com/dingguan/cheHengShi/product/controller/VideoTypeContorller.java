package com.dingguan.cheHengShi.product.controller;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;

import com.dingguan.cheHengShi.product.entity.VideoType;
import com.dingguan.cheHengShi.product.service.VideoTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by zyc on 2018/12/20.
 */
@RestController
@RequestMapping("/videoType")
@Api(description = "视频类型")
public class VideoTypeContorller {

    @Autowired
    protected VideoTypeService videoTypeService;


    @GetMapping("/{id}")
    @ApiOperation(value = "根据 id 查询 视频类型")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> findById(@PathVariable String id) {
        return ApiResult.returnData(videoTypeService.findByPrimaryKey(id));
    }


    @ApiOperation(value = "获取 视频类型 列表")
    @GetMapping()
    public ApiResult<String> findList(
    ) {
        return ApiResult.returnData(videoTypeService.findList());
    }

    @ApiOperation(value = "新增 视频类型")
    @PostMapping(produces = "application/json;charset=utf-8")
    @ApiImplicitParam(name = "videoType", value = "", paramType = "body", dataType = "VideoType")
    public ApiResult<String> save(@RequestBody @Valid VideoType videoType, @ModelAttribute VideoType model) {


        return ApiResult.returnData(videoTypeService.insertSelective(videoType));

    }


    @ApiOperation(value = "删除 视频类型")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> deleteById(@PathVariable String id) {
        videoTypeService.deleteByPrimaryKey(id);
        return ApiResult.returnData(null);
    }


    @ApiOperation(value = "修改 视频类型")
    @PutMapping("")
    @ApiImplicitParam(name = "videoType", value = "", paramType = "body", dataType = "VideoType")
    public ApiResult<String> put(@RequestBody VideoType videoType, @ModelAttribute VideoType model) throws CustomException {
        return ApiResult.returnData(videoTypeService.updateByPrimaryKeySelective(videoType));
    }


}