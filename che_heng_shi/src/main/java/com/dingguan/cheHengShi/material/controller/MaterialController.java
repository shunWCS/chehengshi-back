package com.dingguan.cheHengShi.material.controller;


import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.material.entity.Material;
import com.dingguan.cheHengShi.material.service.MaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by zyc on 2018/12/1.
 */
@RestController
@RequestMapping("/material")
@Api(description = "素材")
public class MaterialController {



    @Autowired
    private MaterialService flashViewService;


    @GetMapping("/{id}")
    @ApiOperation(value = "根据 id 查询 素材")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> findById(@PathVariable String id) {
        return ApiResult.returnData(flashViewService.findByPrimaryKey(id));
    }


    @ApiOperation(value = "获取 素材 列表")
    @GetMapping()
    @ApiImplicitParam(name = "type",
            value = " 类型  1:每消费一元能够获得多少积分 "
        ,paramType = "query")
    public ApiResult<String> findList(
            @RequestParam(value = "type",required = false) String type
    ) {
        return ApiResult.returnData(flashViewService.findList(type));
    }

    @ApiOperation(value = "新增 素材")
    @PostMapping(produces = "application/json;charset=utf-8")
    @ApiImplicitParam(name = "material", value = "", paramType = "body", dataType = "Material")
    public ApiResult<String> save(@RequestBody @Valid Material material, @ModelAttribute Material model) {


        return ApiResult.returnData(flashViewService.insertSelective(material));

    }


    @ApiOperation(value = "删除 素材")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> deleteById(@PathVariable String id) {
        flashViewService.deleteByPrimaryKey(id);
        return ApiResult.returnData(null);
    }


    @ApiOperation(value = "修改 素材")
    @PutMapping("")
    @ApiImplicitParam(name = "material", value = "", paramType = "body", dataType = "Material")
    public ApiResult<String> put(@RequestBody Material material, @ModelAttribute Material model) throws CustomException {
        return ApiResult.returnData(flashViewService.updateByPrimaryKeySelective(material));
    }



}