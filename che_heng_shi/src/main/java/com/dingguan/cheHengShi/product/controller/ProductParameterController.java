package com.dingguan.cheHengShi.product.controller;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.product.entity.ProductParameter;
import com.dingguan.cheHengShi.product.service.ProductParameterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by zyc on 2018/12/3.
 */
@RestController
@RequestMapping("/productParameter")
@Api(description = "产品参数")
public class ProductParameterController {



    @Autowired
    private ProductParameterService productParameterService;


    @GetMapping("/{id}")
    @ApiOperation(value = "根据 id 查询 产品参数")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> findById(@PathVariable String id) {
        return ApiResult.returnData(productParameterService.findByPrimaryKey(id));
    }


    @ApiOperation(value = "获取 产品参数 列表")
    @GetMapping()
    @ApiImplicitParam(name = "productId",value = "产品id",paramType = "query")
    public ApiResult<String> findList(@RequestParam(value = "productId",required = false)String productId ) {
        return ApiResult.returnData(productParameterService.findList(productId));
    }

    @ApiOperation(value = "新增 产品参数")
    @PostMapping(produces = "application/json;charset=utf-8")
    @ApiImplicitParam(name = "productParameter", value = "", paramType = "body", dataType = "ProductParameter")
    public ApiResult<String> save(@RequestBody @Valid ProductParameter productParameter, @ModelAttribute ProductParameter model) {


        return ApiResult.returnData(productParameterService.insertSelective(productParameter));

    }


    @ApiOperation(value = "删除 产品参数")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> deleteById(@PathVariable String id) {
        productParameterService.deleteByPrimaryKey(id);
        return ApiResult.returnData(null);
    }


    @ApiOperation(value = "修改 产品参数")
    @PutMapping("")
    @ApiImplicitParam(name = "productParameter", value = "", paramType = "body", dataType = "ProductParameter")
    public ApiResult<String> put(@RequestBody ProductParameter productParameter, @ModelAttribute ProductParameter model) throws CustomException {
        return ApiResult.returnData(productParameterService.updateByPrimaryKeySelective(productParameter));
    }


}