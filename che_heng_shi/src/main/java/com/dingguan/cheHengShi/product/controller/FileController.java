package com.dingguan.cheHengShi.product.controller;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.product.entity.File;
 import com.dingguan.cheHengShi.product.service.FileService;
 import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.MessageFormat;

/**
 * Created by zyc on 2018/12/20.
 */
@RestController
@RequestMapping("/file")
@Api(description = "文档")
@Slf4j
public class FileController {


    @Autowired
    private FileService fileService;


    @GetMapping("/{id}")
    @ApiOperation(value = "根据 id 查询 文档")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> findById(
            @ApiParam("openId") @RequestParam(value = "openId",required = false)String openId,
            @PathVariable String id
    ) {
        log.info(MessageFormat.format("查看文档详情========================>：id:{1},openId:{2}",id,openId));
        return ApiResult.returnData(fileService.findByPrimaryKey(id,openId));
    }


    @ApiOperation(value = "获取 文档 列表")
    @GetMapping()
    public ApiResult<String> findList(
            @ApiParam("标题") @RequestParam(value = "title",required = false)String title,
            @ApiParam("文档类型id") @RequestParam(value = "typeId",required = false)String typeId
    ) {
        return ApiResult.returnData(fileService.findList(typeId,title));
    }

    @ApiOperation(value = "新增 文档")
    @PostMapping(produces = "application/json;charset=utf-8")
    @ApiImplicitParam(name = "file", value = "", paramType = "body", dataType = "File")
    public ApiResult<String> save(@RequestBody @Valid File file, @ModelAttribute File model) {
        return ApiResult.returnData(fileService.insertSelective(file));
    }


    @ApiOperation(value = "删除 文档")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> deleteById(@PathVariable String id) {
        fileService.deleteByPrimaryKey(id);
        return ApiResult.returnData(null);
    }


    @ApiOperation(value = "修改 文档")
    @PutMapping("")
    @ApiImplicitParam(name = "file", value = "", paramType = "body", dataType = "File")
    public ApiResult<String> put(@RequestBody File file, @ModelAttribute File model) throws CustomException {
        return ApiResult.returnData(fileService.updateByPrimaryKeySelective(file));
    }



}