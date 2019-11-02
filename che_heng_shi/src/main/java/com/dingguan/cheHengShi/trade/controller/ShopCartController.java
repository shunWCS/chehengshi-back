package com.dingguan.cheHengShi.trade.controller;


import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.trade.entity.ShopCart;
import com.dingguan.cheHengShi.trade.service.ShopCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zyc on 2018/11/29.
 */
@RestController
@RequestMapping("/shopCart")
@Api(description = "购物车")
public class ShopCartController {


    @Autowired
    private ShopCartService shopCartService;


    @GetMapping("/{id}")
    @ApiOperation(value = "根据 id 查询 购物车")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> findById(@PathVariable String id) {
        return ApiResult.returnData(shopCartService.findByPrimaryKey(id));
    }


    @ApiOperation(value = "获取 购物车 列表")
    @GetMapping()
    @ApiImplicitParams({@ApiImplicitParam(name = "openId", paramType = "query")})
    public ApiResult<String> findList(
            @RequestParam(value = "openId", required = false) String openId
    ) {

        List<ShopCart> shopCartList = shopCartService.findList(openId);
        return ApiResult.returnData(shopCartList);

    }

    @ApiOperation(value = "新增/取消 购物车")
    @PostMapping(produces = "application/json;charset=utf-8")
    @ApiImplicitParam(name = "shopCart", value = "", paramType = "body", dataType = "ShopCart")
    public ApiResult<String> save(@RequestBody @Valid ShopCart shopCart, @ModelAttribute ShopCart model) throws CustomException {
        return ApiResult.returnData(shopCartService.insertSelective(shopCart));

    }


    @ApiOperation(value = "删除 购物车")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "批量删除 用_拼接  如 id1_id2_id3.....", paramType = "path", dataType = "String")
    public ApiResult<String> deleteById(@PathVariable String id) {
        String[] split = id.split("_");
        Arrays.stream(split).forEach(
                param->{
                    if(StringUtils.isNotBlank(param)){
                        shopCartService.deleteByPrimaryKey(param);
                    }
                }
        );
        return ApiResult.returnData(null);
    }



    @ApiOperation(value = "修改 购物车")
    @PutMapping("")
    @ApiImplicitParam(name = "shopCart", value = "", paramType = "body", dataType = "ShopCart")
    public ApiResult<String> put(@RequestBody ShopCart shopCart, @ModelAttribute ShopCart model) throws CustomException {
        return ApiResult.returnData(shopCartService.updateByPrimaryKeySelective(shopCart));
    }


}