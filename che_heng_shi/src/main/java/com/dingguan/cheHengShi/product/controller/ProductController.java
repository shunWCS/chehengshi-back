package com.dingguan.cheHengShi.product.controller;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.product.entity.Product;
import com.dingguan.cheHengShi.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by zyc on 2018/12/3.
 */
@RestController
@RequestMapping("/product")
@Api(description = "产品")
public class ProductController {


    @Autowired
    private ProductService productService;


    @GetMapping("/{id}")
    @ApiOperation(value = "根据 id 查询 产品")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> findById(@PathVariable String id) {
        return ApiResult.returnData(productService.findByPrimaryKey(id));
    }


    @ApiOperation(value = "获取 产品 列表")
    @GetMapping()

    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex",paramType = "query"),
            @ApiImplicitParam(name = "pageSize",paramType = "query"),
            @ApiImplicitParam(name = "name",value = "产品名",paramType = "query"),
            @ApiImplicitParam(name = "status",value = " 1:审核中 2:上架 3:下架 ",paramType = "query"),
            @ApiImplicitParam(name = "manufacturerId",value = "店铺名",paramType = "query"),
            @ApiImplicitParam(name = "productTypeId",value = "产品分类id",paramType = "query"),

            @ApiImplicitParam(name = "type",value = " 产品类型 1:普通只能直接购买的  2:积分 ",paramType = "query")


    })
    public ApiResult  findList(
            @RequestParam(value = "pageIndex",required = false) Integer pageIndex,
            @RequestParam(value = "pageSize",required = false) Integer pageSize,
            @RequestParam(name = "name",required = false)String  name,
            @RequestParam(name = "status",required = false)String  status,
            @RequestParam(name = "productTypeId",required = false)String productTypeId ,
            @RequestParam(name = "manufacturerId",required = false)String manufacturerId ,
            @RequestParam(name = "type",required = false)String type
    ) {

        name= StringUtils.isBlank(name)?null:name;
        status= StringUtils.isBlank(status)?null:status;
        productTypeId= StringUtils.isBlank(productTypeId)?null:productTypeId;
        manufacturerId= StringUtils.isBlank(manufacturerId)?null:manufacturerId;
        type= StringUtils.isBlank(type)?null:type;

        Product build = Product.builder()
                .name(name)
                .status(status)
                .productTypeId(productTypeId)
                .manufacturerId(manufacturerId)
                .type(type)
                 .build();
        Sort sort = new Sort(Sort.Direction.ASC, "sort");
        if(pageIndex==null || pageSize==null){
            List<Product> productList = productService.findList(build, sort);
            return ApiResult.returnData(productList);
        }
        PageRequest pageRequest = new PageRequest(pageIndex, pageSize,sort);
        return  productService.findList(build,pageRequest) ;
    }

    @ApiOperation(value = "新增 产品")
    @PostMapping(produces = "application/json;charset=utf-8")
    @ApiImplicitParam(name = "product", value = "", paramType = "body", dataType = "Product")
    public ApiResult<String> save(@RequestBody @Valid Product product, @ModelAttribute Product model) throws CustomException {
        return ApiResult.returnData(productService.insertSelective(product));

    }


    @ApiOperation(value = "删除 产品")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> deleteById(@PathVariable String id) {
        productService.deleteByPrimaryKey(id);
        return ApiResult.returnData(null);
    }


    @ApiOperation(value = "修改 产品")
    @PutMapping("")
    @ApiImplicitParam(name = "product", value = "", paramType = "body", dataType = "Product")
    public ApiResult<String> put(@RequestBody Product product, @ModelAttribute Product model) throws CustomException {
        return ApiResult.returnData(productService.updateByPrimaryKeySelective(product));
    }

}