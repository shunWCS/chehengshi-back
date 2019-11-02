package com.dingguan.cheHengShi.product.controller;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.product.entity.CourseType;
import com.dingguan.cheHengShi.product.entity.FileType;
import com.dingguan.cheHengShi.product.service.CourseTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author: czh
 * @Date: 2019/9/16 21:39
 */
@RestController
@RequestMapping("/courseType")
@Api(description = "课程类型")
public class CourseTypeController {

    @Autowired
    private CourseTypeService courseTypeService;

    @GetMapping("/{id}")
    @ApiOperation(value = "根据 id 查询 课程类型")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> findById(@PathVariable String id){
        return ApiResult.returnData(courseTypeService.findByPrimaryKey(id));
    }

    @ApiOperation(value = "获取 课程类型 列表")
    @GetMapping()
    public ApiResult<String> findList(){
        return ApiResult.returnData(courseTypeService.findList());
    }


    @ApiOperation(value = "新增 课程类型")
    @PostMapping(produces = "application/json;charset=utf-8")
    @ApiImplicitParam(name = "courseType", value = "", paramType = "body", dataType = "CourseType")
    public ApiResult<String> save(@RequestBody @Valid CourseType courseType, @ModelAttribute CourseType model){
        return ApiResult.returnData(courseTypeService.insertSelective(courseType));
    }

    @ApiOperation(value = "删除 课程类型")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> deleteById(@PathVariable String id){
        courseTypeService.deleteByPrimaryKey(id);
        return ApiResult.returnData(null);
    }

    @ApiOperation(value = "修改 课程类型")
    @PutMapping("")
    @ApiImplicitParam(name = "courseType", value = "", paramType = "body", dataType = "CourseType")
    public ApiResult<String> put(@RequestBody CourseType courseType, @ModelAttribute CourseType model) throws CustomException{
        return ApiResult.returnData(courseTypeService.updateByPrimaryKeySelective(courseType));
    }

}
