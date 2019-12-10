package com.dingguan.cheHengShi.product.controller;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.product.entity.PosterType;
import com.dingguan.cheHengShi.product.service.PosterTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/posterType")
@Api( description = "后台轮播类型相关接口说明")
public class PosterTypeController {

    @Autowired
    private PosterTypeService posterTypeService;

    @GetMapping("/{id}")
    @ApiOperation(value = "根据 id 查询 轮播类型")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> findById(@PathVariable String id){
        return ApiResult.returnData(posterTypeService.findByPrimaryKey(id));
    }

    @ApiOperation(value = "获取 轮播类型 列表")
    @GetMapping()
    public ApiResult<String> findList(){
        return ApiResult.returnData(posterTypeService.findList());
    }


    @ApiOperation(value = "新增 轮播类型")
    @PostMapping(produces = "application/json;charset=utf-8")
    @ApiImplicitParam(name = "posterType", value = "", paramType = "body", dataType = "PosterType")
    public ApiResult<String> save(@RequestBody @Valid PosterType posterType, @ModelAttribute PosterType model){
        return ApiResult.returnData(posterTypeService.insertSelective(posterType));
    }

    @ApiOperation(value = "删除 轮播类型")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> deleteById(@PathVariable String id){
        posterTypeService.deleteByPrimaryKey(id);
        return ApiResult.returnData(null);
    }

    @ApiOperation(value = "修改 轮播类型")
    @PutMapping("")
    @ApiImplicitParam(name = "posterType", value = "", paramType = "body", dataType = "PosterType")
    public ApiResult<String> put(@RequestBody PosterType posterType, @ModelAttribute PosterType model) throws CustomException {
        return ApiResult.returnData(posterTypeService.updateByPrimaryKeySelective(posterType));
    }
}
