package com.dingguan.cheHengShi.product.controller;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.product.entity.ProductType;
import com.dingguan.cheHengShi.product.service.ProductTypeService;
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
@RequestMapping("/productType")
@Api(description = "产品类型")
public class ProductTypeController {
    @Autowired
    private ProductTypeService productTypeService;


    @GetMapping("/{id}")
    @ApiOperation(value = "根据 id 查询 产品类型")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> findById(@PathVariable String id) {
        return ApiResult.returnData(productTypeService.findByPrimaryKey(id));
    }


    @ApiOperation(value = "获取 产品类型 列表")
    @GetMapping()
    public ApiResult<String> findList() {
        return ApiResult.returnData(productTypeService.findList());
    }

    @ApiOperation(value = "新增 产品类型")
    @PostMapping(produces = "application/json;charset=utf-8")
    @ApiImplicitParam(name = "productType", value = "", paramType = "body", dataType = "ProductType")
    public ApiResult<String> save(@RequestBody @Valid ProductType productType, @ModelAttribute ProductType model) {


        return ApiResult.returnData(productTypeService.insertSelective(productType));

    }


    @ApiOperation(value = "删除 产品类型")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> deleteById(@PathVariable String id) {
        productTypeService.deleteByPrimaryKey(id);
        return ApiResult.returnData(null);
    }


    @ApiOperation(value = "修改 产品类型")
    @PutMapping("")
    @ApiImplicitParam(name = "productType", value = "", paramType = "body", dataType = "ProductType")
    public ApiResult<String> put(@RequestBody ProductType productType, @ModelAttribute ProductType model) throws CustomException {
        return ApiResult.returnData(productTypeService.updateByPrimaryKeySelective(productType));
    }


}