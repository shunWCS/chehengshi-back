package com.dingguan.cheHengShi.material.controller;


import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.material.entity.FlashView;
import com.dingguan.cheHengShi.material.service.FlashViewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by zyc on 2018/11/22.
 */
@RestController
@RequestMapping("/flashView")
@Api(description = "视频页的轮播图  -----------首页没有轮播图 是轮播视频....")
public class FlashViewController {


    @Autowired
    private FlashViewService flashViewService;


    @GetMapping("/{id}")
    @ApiOperation(value = "根据 id 查询 轮播图")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> findById(@PathVariable String id) {
        return ApiResult.returnData(flashViewService.findByPrimaryKey(id));
    }


    @ApiOperation(value = "获取 轮播图 列表")
    @GetMapping()
    public ApiResult<String> findList(
    ) {
        return ApiResult.returnData(flashViewService.findList());
    }

    @ApiOperation(value = "新增 轮播图")
    @PostMapping(produces = "application/json;charset=utf-8")
    @ApiImplicitParam(name = "flashView", value = "", paramType = "body", dataType = "FlashView")
    public ApiResult<String> save(@RequestBody @Valid FlashView flashView, @ModelAttribute FlashView model) {


        return ApiResult.returnData(flashViewService.insertSelective(flashView));

    }


    @ApiOperation(value = "删除 轮播图")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> deleteById(@PathVariable String id) {
        flashViewService.deleteByPrimaryKey(id);
        return ApiResult.returnData(null);
    }


    @ApiOperation(value = "修改 轮播图")
    @PutMapping("")
    @ApiImplicitParam(name = "flashView", value = "", paramType = "body", dataType = "FlashView")
    public ApiResult<String> put(@RequestBody FlashView flashView, @ModelAttribute FlashView model) throws CustomException {
        return ApiResult.returnData(flashViewService.updateByPrimaryKeySelective(flashView));
    }

}