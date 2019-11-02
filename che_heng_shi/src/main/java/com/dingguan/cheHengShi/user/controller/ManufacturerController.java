package com.dingguan.cheHengShi.user.controller;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.user.entity.Address;
import com.dingguan.cheHengShi.user.entity.Manufacturer;
import com.dingguan.cheHengShi.user.service.AddressService;
import com.dingguan.cheHengShi.user.service.ManufacturerService;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Created by zyc on 2018/12/21.
 */
@RestController
@RequestMapping("/manufacturer")
@Api(description = "供应商/维修厂")
public class ManufacturerController {

    @Autowired
    private ManufacturerService manufacturerService;


    @GetMapping("/{id}")
    @ApiOperation(value = "根据 id 查询 供应商/维修厂")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> findById(@PathVariable String id) {
        return ApiResult.returnData(manufacturerService.findByPrimaryKey(id));
    }


    @ApiOperation(value = "获取 供应商/维修厂 列表")
    @GetMapping()
    @ApiImplicitParams({
    })
    public ApiResult findList(
            @ApiParam("第几页 从0开始")  @RequestParam(value = "pageIndex",required = false) Integer pageIndex,
            @ApiParam("每页多少条数据")  @RequestParam(value = "pageSize",required = false) Integer pageSize,
            @ApiParam("1:入驻商 2:维修厂 ")  @RequestParam(name = "type", required = false) String type,
            @ApiParam(" 1:申请中  2:通过/正常使用中 3:不通过 ")  @RequestParam(name = "status", required = false) String status,
            @ApiParam("openId ")   @RequestParam(name = "openId", required = false) String openId
    ) {
        Manufacturer build = Manufacturer.builder()
                .type(type)
                .status(StringUtils.isBlank(status)?null:status)
                .openId(openId)
                .build();

        Sort sort = new Sort(Sort.Direction.ASC, "sort");

        if(pageIndex==null || pageSize==null){
            List<Manufacturer> list = manufacturerService.findList(build, sort);
            return ApiResult.returnData(list);
        }else {
            PageRequest pageRequest = new PageRequest(pageIndex, pageSize, sort);
            ApiResult<List<Manufacturer>> apiResult = manufacturerService.findList(build, pageRequest);
            return apiResult;
        }

    }

    @ApiOperation(value = "新增 供应商/维修厂")
    @PostMapping
    @ApiImplicitParam(name = "manufacturer", value = "", paramType = "body", dataType = "Manufacturer")
    public ApiResult<String> save(@RequestBody @Valid Manufacturer manufacturer, @ModelAttribute Manufacturer model) throws IOException, CustomException {
        return ApiResult.returnData(manufacturerService.insertSelective(manufacturer));

    }


    @ApiOperation(value = "删除 供应商/维修厂")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> deleteById(@PathVariable String id) {
        manufacturerService.deleteByPrimaryKey(id);
        return ApiResult.returnData(null);
    }


    @ApiOperation(value = "修改 供应商/维修厂")
    @PutMapping("")
    @ApiImplicitParam(name = "manufacturer", value = "", paramType = "body", dataType = "Manufacturer")
    public ApiResult<String> put(@RequestBody Manufacturer manufacturer, @ModelAttribute Manufacturer model) throws CustomException {
        return ApiResult.returnData(manufacturerService.updateByPrimaryKeySelective(manufacturer));
    }


    @ApiOperation(value = "审批 供应商/维修厂")
    @PostMapping("/give")
    @ApiImplicitParam(name = "manufacturer", value = "", paramType = "body", dataType = "Manufacturer")
    public ApiResult<String> putByGiveOrders(@RequestBody @Valid Manufacturer manufacturer, @ModelAttribute Manufacturer model) throws IOException, CustomException {
        return ApiResult.returnData(manufacturerService.giveOrders(manufacturer));

    }



}