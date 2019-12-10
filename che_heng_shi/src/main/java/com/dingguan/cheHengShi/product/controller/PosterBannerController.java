package com.dingguan.cheHengShi.product.controller;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.product.entity.PosterBanner;
import com.dingguan.cheHengShi.product.service.PosterBannerService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.MessageFormat;

@RestController
@RequestMapping("/posterBanner")
@Api(description = "后台轮播图管理相关接口说明")
@Slf4j
public class PosterBannerController {
    @Autowired
    private PosterBannerService posterBannerService;

    @ApiOperation(value = "查询要设置的banner图的内容列表")
    @GetMapping("/list")
    public ApiResult<String> findListForBanner(
            @ApiParam("标题") @RequestParam(value = "title",required = false)String title,
            @ApiParam("轮播图类型") @RequestParam(value = "typeValue",required = false)String typeValue
    ){
        return ApiResult.returnData(posterBannerService.findListForBanner(typeValue,title));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据 id 查询轮播图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "轮播图Id", paramType = "path", dataType = "String",required = true)
    })
    public ApiResult<PosterBanner> findById(@PathVariable String id){
        log.info(MessageFormat.format("查看轮播图详情========================>：id:{1}",id));
        PosterBanner posterBanner = posterBannerService.findByPrimaryKey(id);
        return ApiResult.returnData(posterBanner);
    }


    @ApiOperation(value = "新增 轮播图")
    @PostMapping(produces = "application/json;charset=utf-8")
    public ApiResult<String> save(@RequestBody @Valid PosterBanner posterBanner, @ModelAttribute PosterBanner model) throws CustomException {
        PosterBanner posterBanner1 = posterBannerService.insertSelective(posterBanner);
        ApiResult result = ApiResult.returnData(posterBanner1);
        return result;
    }

    @ApiOperation(value = "删除 轮播图")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> deleteById(@PathVariable String id){
        posterBannerService.deleteByPrimaryKey(id);
        return ApiResult.returnData(null);
    }

    @ApiOperation(value = "修改 轮播图")
    @PutMapping("")
    @ApiImplicitParam(name = "posterBanner", value = "", paramType = "body", dataType = "PosterBanner")
    public ApiResult<String> put(@RequestBody PosterBanner posterBanner, @ModelAttribute PosterBanner model) throws CustomException{
        PosterBanner posterBanner1 = posterBannerService.updateByPrimaryKeySelective(posterBanner);
        ApiResult result = ApiResult.returnData(posterBanner1);
        return result;
    }

}
