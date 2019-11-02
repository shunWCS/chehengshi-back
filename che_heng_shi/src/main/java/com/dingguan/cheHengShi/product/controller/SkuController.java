package com.dingguan.cheHengShi.product.controller;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.product.entity.Sku;
import com.dingguan.cheHengShi.product.service.SkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by zyc on 2018/12/3.
 */
@RestController
@RequestMapping("/sku")
@Api(description = "sku")
public class SkuController {


    @Autowired
    private SkuService skuService;


    @GetMapping("/{id}")
    @ApiOperation(value = "根据 id 查询 sku")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> findById(@PathVariable String id) {
        return ApiResult.returnData(skuService.findByPrimaryKey(id));
    }


    @ApiOperation(value = "获取 sku 列表")
    @GetMapping()
    public ApiResult<String> findList(
            @ApiParam("productId") @RequestParam(value = "productId",required = false)String productId
    ) {
        return ApiResult.returnData(skuService.findList(productId));
    }

    @ApiOperation(value = "新增 sku")
    @PostMapping(produces = "application/json;charset=utf-8")
    @ApiImplicitParam(name = "sku", value = "", paramType = "body", dataType = "Sku")
    public ApiResult  save(@RequestBody @Valid Sku sku, @ModelAttribute Sku model) throws CustomException {


        return ApiResult.returnData(skuService.insertSelective(sku));

    }


    @ApiOperation(value = "删除 sku")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> deleteById(@PathVariable String id) throws CustomException {
        skuService.deleteByPrimaryKey(id);
        return ApiResult.returnData(null);
    }


    @ApiOperation(value = "修改 sku")
    @PutMapping("")
    @ApiImplicitParam(name = "sku", value = "", paramType = "body", dataType = "Sku")
    public ApiResult<String> put(@RequestBody Sku sku, @ModelAttribute Sku model) throws CustomException {
        return ApiResult.returnData(skuService.updateByPrimaryKeySelective(sku));
    }





}