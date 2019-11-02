package com.dingguan.cheHengShi.user.controller;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.user.entity.Address;
import com.dingguan.cheHengShi.user.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Created by zyc on 2018/12/5.
 */
@RestController
@RequestMapping("/address")
@Api(description = "地址")
public class AddressController {


    @Autowired
    private AddressService addressService;


    @GetMapping("/{id}")
    @ApiOperation(value = "根据 id 查询 地址")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> findById(@PathVariable String id) {
        return ApiResult.returnData(addressService.findByPrimaryKey(id));
    }


    @ApiOperation(value = "获取 地址 列表")
    @GetMapping()
    @ApiImplicitParams({
            @ApiImplicitParam(name = "madeByImperialOrder", value = "madeByImperialOrder"),
            @ApiImplicitParam(name = "openId", value = " ")
    })
    public ApiResult findList(
            @RequestParam(name = "madeByImperialOrder", required = false) String madeByImperialOrder,
            @RequestParam(name = "openId", required = false) String openId
    ) {
        Address build = Address.builder()
                .madeByImperialOrder(madeByImperialOrder)
                .openId(openId)
                .build();

        List<Address> addressList = addressService.findList(build);
        return ApiResult.returnData(addressList);
    }

    @ApiOperation(value = "新增 地址")
    @PostMapping(produces = "application/json;charset=utf-8")
    @ApiImplicitParam(name = "address", value = "", paramType = "body", dataType = "Address")
    public ApiResult<String> save(@RequestBody @Valid Address address, @ModelAttribute Address model) throws IOException, CustomException {
        return ApiResult.returnData(addressService.insertSelective(address));

    }


    @ApiOperation(value = "删除 地址")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> deleteById(@PathVariable String id) {
        addressService.deleteByPrimaryKey(id);
        return ApiResult.returnData(null);
    }


    @ApiOperation(value = "修改 地址")
    @PutMapping("")
    @ApiImplicitParam(name = "address", value = "", paramType = "body", dataType = "Address")
    public ApiResult<String> put(@RequestBody Address address, @ModelAttribute Address model) throws CustomException {
        return ApiResult.returnData(addressService.updateByPrimaryKeySelective(address));
    }

}