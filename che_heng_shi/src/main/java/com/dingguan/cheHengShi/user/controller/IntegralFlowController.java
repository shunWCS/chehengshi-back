package com.dingguan.cheHengShi.user.controller;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.user.entity.Address;
import com.dingguan.cheHengShi.user.entity.GiveVo;
import com.dingguan.cheHengShi.user.entity.IntegralFlow;
import com.dingguan.cheHengShi.user.service.AddressService;
import com.dingguan.cheHengShi.user.service.IntegralFlowService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Created by zyc on 2018/12/22.
 */
@RestController
@RequestMapping("/integralFlow")
@Api(description = "积分流水")
public class IntegralFlowController {

    @Autowired
    private IntegralFlowService integralFlowService;


    @GetMapping("/{id}")
    @ApiOperation(value = "根据 id 查询 积分流水")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> findById(@PathVariable String id) {
        return ApiResult.returnData(integralFlowService.findByPrimaryKey(id));
    }


    @ApiOperation(value = "获取 积分流水 列表")
    @GetMapping()

    public ApiResult findList(
            @ApiParam("第几页 从0开始") @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
            @ApiParam("每页多少条数据") @RequestParam(value = "pageSize", required = false) Integer pageSize,

            @ApiParam("类型 1:交易成功 2兑换  3:赠送") @RequestParam(name = "type", required = false) String type,
            @ApiParam("") @RequestParam(name = "openId", required = false) String openId
    ) {
        IntegralFlow build = IntegralFlow.builder()
                .type(type)
                .openId(openId)
                .build();
        Sort sort = new Sort(Sort.Direction.ASC, "time");

        if (pageIndex == null || pageSize == null) {
            List<IntegralFlow> addressList = integralFlowService.findList(build, sort);
            return ApiResult.returnData(addressList);
        } else {
            PageRequest pageRequest = new PageRequest(pageIndex, pageSize, sort);
            ApiResult<List<IntegralFlow>> apiResult = integralFlowService.findList(build, pageRequest);
            return apiResult;
        }
    }

    @ApiOperation(value = "新增 积分流水")
    @PostMapping
    @ApiImplicitParam(name = "integralFlow", value = "", paramType = "body", dataType = "IntegralFlow")
    public ApiResult<String> save(@RequestBody @Valid IntegralFlow integralFlow, @ModelAttribute IntegralFlow model) throws IOException, CustomException {
        return ApiResult.returnData(integralFlowService.insertSelective(integralFlow));

    }


    @ApiOperation(value = "删除 积分流水")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> deleteById(@PathVariable String id) {
        integralFlowService.deleteByPrimaryKey(id);
        return ApiResult.returnData(null);
    }


    @ApiOperation(value = "修改 积分流水")
    @PutMapping("")
    @ApiImplicitParam(name = "integralFlow", value = "", paramType = "body", dataType = "IntegralFlow")
    public ApiResult<String> put(@RequestBody IntegralFlow integralFlow, @ModelAttribute IntegralFlow model) throws CustomException {
        return ApiResult.returnData(integralFlowService.updateByPrimaryKeySelective(integralFlow));
    }



    @ApiOperation(value = "赠送 积分 ")
    @PutMapping("/give")
    @ApiImplicitParam(name = "giveVo", value = "", paramType = "body", dataType = "GiveVo")
    public ApiResult<String> save(@RequestBody GiveVo giveVo, @ModelAttribute GiveVo model) throws CustomException {
        integralFlowService.give(giveVo);
        return ApiResult.returnData(giveVo);
    }








}