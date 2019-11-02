package com.dingguan.cheHengShi.product.controller;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.material.entity.Journalism;
import com.dingguan.cheHengShi.material.service.JournalismService;
import com.dingguan.cheHengShi.product.entity.FileType;
import com.dingguan.cheHengShi.product.service.FileTypeService;
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
@RequestMapping("/fileType")
@Api(description = "文档类型")
public class FileTypeController {


    @Autowired
    private FileTypeService fileTypeService;


    @GetMapping("/{id}")
    @ApiOperation(value = "根据 id 查询 文档类型")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> findById(@PathVariable String id) {
        return ApiResult.returnData(fileTypeService.findByPrimaryKey(id));
    }


    @ApiOperation(value = "获取 文档类型 列表")
    @GetMapping()
    public ApiResult<String> findList() {
        return ApiResult.returnData(fileTypeService.findList());
    }

    @ApiOperation(value = "新增 文档类型")
    @PostMapping(produces = "application/json;charset=utf-8")
    @ApiImplicitParam(name = "fileType", value = "", paramType = "body", dataType = "FileType")
    public ApiResult<String> save(@RequestBody @Valid FileType fileType, @ModelAttribute FileType model) {


        return ApiResult.returnData(fileTypeService.insertSelective(fileType));

    }


    @ApiOperation(value = "删除 文档类型")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> deleteById(@PathVariable String id) {
        fileTypeService.deleteByPrimaryKey(id);
        return ApiResult.returnData(null);
    }


    @ApiOperation(value = "修改 文档类型")
    @PutMapping("")
    @ApiImplicitParam(name = "fileType", value = "", paramType = "body", dataType = "FileType")
    public ApiResult<String> put(@RequestBody FileType fileType, @ModelAttribute FileType model) throws CustomException {
        return ApiResult.returnData(fileTypeService.updateByPrimaryKeySelective(fileType));
    }




}