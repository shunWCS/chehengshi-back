package com.dingguan.cheHengShi.product.controller;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
 import com.dingguan.cheHengShi.product.entity.MembershipPackage;
 import com.dingguan.cheHengShi.product.service.MembershipPackageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by zyc on 2018/12/20.
 */
@RestController
@RequestMapping("/membershipPackage")
@Api(description = "会员套餐")
public class MembershipPackageController {

    @Autowired
    private MembershipPackageService membershipPackageService;


    @GetMapping("/{id}")
    @ApiOperation(value = "根据 id 查询 会员套餐")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> findById(@PathVariable String id) {
        return ApiResult.returnData(membershipPackageService.findByPrimaryKey(id));
    }


    @ApiOperation(value = "获取 会员套餐 列表")
    @GetMapping()
    public ApiResult<String> findList() {
        return ApiResult.returnData(membershipPackageService.findList());
    }

    @ApiOperation(value = "新增 会员套餐")
    @PostMapping(produces = "application/json;charset=utf-8")
    @ApiImplicitParam(name = "membershipPackage", value = "", paramType = "body", dataType = "MembershipPackage")
    public ApiResult<String> save(@RequestBody @Valid MembershipPackage membershipPackage, @ModelAttribute MembershipPackage model) {
        return ApiResult.returnData(membershipPackageService.insertSelective(membershipPackage));
    }


    @ApiOperation(value = "删除 会员套餐")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> deleteById(@PathVariable String id) {
        membershipPackageService.deleteByPrimaryKey(id);
        return ApiResult.returnData(null);
    }


    @ApiOperation(value = "修改 会员套餐")
    @PutMapping("")
    @ApiImplicitParam(name = "membershipPackage", value = "", paramType = "body", dataType = "MembershipPackage")
    public ApiResult<String> put(@RequestBody MembershipPackage membershipPackage, @ModelAttribute MembershipPackage model) throws CustomException {
        return ApiResult.returnData(membershipPackageService.updateByPrimaryKeySelective(membershipPackage));
    }


}