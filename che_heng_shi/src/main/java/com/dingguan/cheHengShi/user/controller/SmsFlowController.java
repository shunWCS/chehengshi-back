package com.dingguan.cheHengShi.user.controller;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.user.entity.SmsFlow;
import com.dingguan.cheHengShi.user.service.SmsFlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by zyc on 2018/11/30.
 */
@RestController
@RequestMapping("/smsFlow")
@Api(description = "短信")
public class SmsFlowController {


    @Autowired
    private SmsFlowService smsFlowService;


    @GetMapping("/{id}")
    @ApiOperation(value = "根据 id 查询 短信")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> findById(@PathVariable String id) {
        return ApiResult.returnData(smsFlowService.findByPrimaryKey(id));
    }


    @ApiOperation(value = "获取 短信 列表")
    @GetMapping()
    @ApiImplicitParams({
             @ApiImplicitParam(name = "phone",paramType = "query"),
             @ApiImplicitParam(name = "openId",paramType = "query"),
             @ApiImplicitParam(name = "pageIndex",paramType = "query"),
            @ApiImplicitParam(name = "pageSize",paramType = "query"),
            @ApiImplicitParam(name = "status",paramType = "query")
    })
    public ApiResult findList(
             @RequestParam(value = "phone",required = false) String phone,
             @RequestParam(value = "pageIndex",required = false) Integer pageIndex,
            @RequestParam(value = "pageSize",required = false) Integer pageSize,
            @RequestParam(value = "openId",required = false) String openId,
             @RequestParam(value = "status",required = false) String status
    ) {


        SmsFlow build = SmsFlow.builder()
                .phone(phone)
                .status(status)
                .openId(openId)
                .build();

        Sort sort = new Sort(Sort.Direction.DESC, "time");

        if(pageIndex==null||pageSize==null){
            List<SmsFlow> list = smsFlowService.findList(build, sort);
            return ApiResult.returnData(list);
        }
        PageRequest pageRequest = new PageRequest(pageIndex, pageSize, sort);
        ApiResult<List<SmsFlow>> apiResult = smsFlowService.findList(build, pageRequest);
        return apiResult;

    }

    @ApiOperation(value = "新增 短信")
    @PostMapping(produces = "application/json;charset=utf-8")
    @ApiImplicitParam(name = "smsFlow", value = "", paramType = "body", dataType = "SmsFlow")
    public ApiResult<String> save(@RequestBody @Valid SmsFlow smsFlow, @ModelAttribute SmsFlow model) throws CustomException {
        return ApiResult.returnData(smsFlowService.insertSelective(smsFlow));

    }


    @ApiOperation(value = "删除 短信")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> deleteById(@PathVariable String id) {

        smsFlowService.deleteByPrimaryKey(id);
        return ApiResult.returnData(null);
    }


    @ApiOperation(value = "修改 短信")
    @PutMapping("")
    @Deprecated
    @ApiImplicitParam(name = "smsFlow", value = "", paramType = "body", dataType = "SmsFlow")
    public ApiResult<String> put(@RequestBody SmsFlow smsFlow, @ModelAttribute SmsFlow model) throws CustomException {
        return ApiResult.returnData(smsFlowService.updateByPrimaryKeySelective(smsFlow));
    }




}