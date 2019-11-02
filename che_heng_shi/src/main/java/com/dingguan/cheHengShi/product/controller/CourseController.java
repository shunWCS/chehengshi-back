package com.dingguan.cheHengShi.product.controller;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.product.entity.Course;
import com.dingguan.cheHengShi.product.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.MessageFormat;

/**
 * @author: czh
 * @Date: 2019/9/16 21:39
 */
@RestController
@RequestMapping("/course")
@Api(description = "课程")
@Slf4j
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/{id}")
    @ApiOperation(value = "根据 id 查询 课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "课程Id", paramType = "path", dataType = "String",required = true),
            @ApiImplicitParam(name="openId",value="发布人唯一标识", paramType="query",dataType = "String", required=true)
    })
    public ApiResult<Course> findById(String openId, @PathVariable String id){
        log.info(MessageFormat.format("查看课程详情========================>：id:{1},openId:{2}",id,openId));
        Course course = courseService.findByPrimaryKey(id,openId);
        return ApiResult.returnData(course);
    }

    @ApiOperation(value = "获取 课程 列表")
    @GetMapping()
    public ApiResult<String> findList(
            @ApiParam("标题") @RequestParam(value = "title",required = false)String title,
            @ApiParam("课程类型id") @RequestParam(value = "typeId",required = false)String typeId
    ){
        return ApiResult.returnData(courseService.findList(typeId,title));
    }

    @ApiOperation(value = "新增 课程")
    @PostMapping(produces = "application/json;charset=utf-8")
    public ApiResult<String> save(@RequestBody @Valid Course course, @ModelAttribute Course model) throws CustomException{
        Course course1 = courseService.insertSelective(course);
        ApiResult result = ApiResult.returnData(course1);
        return result;
    }

    @ApiOperation(value = "删除 课程")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> deleteById(@PathVariable String id){
        courseService.deleteByPrimaryKey(id);
        return ApiResult.returnData(null);
    }

    @ApiOperation(value = "修改 课程")
    @PutMapping("")
    @ApiImplicitParam(name = "course", value = "", paramType = "body", dataType = "Course")
    public ApiResult<String> put(@RequestBody Course course, @ModelAttribute Course model) throws CustomException{
        Course course1 = courseService.updateByPrimaryKeySelective(course);
        ApiResult result = ApiResult.returnData(course1);
        return result;
    }

}
