package com.dingguan.cheHengShi.product.controller;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.home.entity.CommonPoster;
import com.dingguan.cheHengShi.product.entity.PosterBanner;
import com.dingguan.cheHengShi.product.service.PosterBannerService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping("/posterBanner")
@Api(description = "后台轮播图管理相关接口说明")
@Slf4j
public class PosterBannerController {
    @Autowired
    private PosterBannerService posterBannerService;

    @ApiOperation(value = "查询要设置的banner图的内容列表")
    @GetMapping()
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "标题", dataType = "String",required = false,paramType="query"),
            @ApiImplicitParam(name="typeValue",value="轮播图类型",dataType = "String",required=false,paramType="query")
    })
    public ApiResult<String> findListForBanner(String title,String typeValue){
        List<CommonPoster> commonPosters = null;
        if("pullList".equals(title)){
            commonPosters = posterBannerService.findListForPullList(typeValue);
        }else {
            commonPosters = posterBannerService.findListForBanner(typeValue, title);
        }
        ApiResult result = ApiResult.returnData(commonPosters);
        return result;
    }

    @GetMapping("/{refId}")
    @ApiOperation(value = "根据 id 查询轮播图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "refId", value = "轮播图关联Id", paramType = "path", dataType = "String",required = true),
            @ApiImplicitParam(name="typeValue",value="轮播图类型",required=true,paramType="query")
    })
    public ApiResult<PosterBanner> findById(@PathVariable String refId,String typeValue){
        log.info(MessageFormat.format("查看轮播图详情========================>：id:{1}",refId));
        PosterBanner posterBanner = posterBannerService.selectPosterBannerByRefId(refId,typeValue);
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
    @DeleteMapping("/{refId}")
    @ApiImplicitParam(name = "refId", value = "refId", paramType = "path", dataType = "String")
    public ApiResult<String> deleteById(@PathVariable String refId){
        posterBannerService.deletePosterBannerByRefId(refId);
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
