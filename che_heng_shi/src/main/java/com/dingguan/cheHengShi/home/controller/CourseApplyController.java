package com.dingguan.cheHengShi.home.controller;

import com.alibaba.fastjson.JSONObject;
import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.common.resp.PageInfo;
import com.dingguan.cheHengShi.home.dto.CommonDetail;
import com.dingguan.cheHengShi.home.dto.CourseVo;
import com.dingguan.cheHengShi.home.dto.FileSearchVo;
import com.dingguan.cheHengShi.home.dto.HomePage;
import com.dingguan.cheHengShi.home.entity.CourseApply;
import com.dingguan.cheHengShi.home.service.CourseApplyService;
import com.dingguan.cheHengShi.material.entity.Journalism;
import com.dingguan.cheHengShi.material.service.JournalismService;
import com.dingguan.cheHengShi.product.entity.Course;
import com.dingguan.cheHengShi.product.service.CourseService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * @author: czh
 * @Date: 2019/9/13 13:16
 */
@Api(description = "课程报名相关接口")
@RestController
@RequestMapping("/courseApply")
@Slf4j
public class CourseApplyController {

    @Autowired
    private CourseApplyService courseApplyService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private JournalismService journalismService;

    @GetMapping("/news")
    @ApiOperation(value="课程报名课程新闻列表数据(包含搜索功能)")
    public ApiResult<List<HomePage>> courseNews(FileSearchVo fileSearchVo){
        log.info(MessageFormat.format("查询课程新闻参数：{0}", JSONObject.toJSONString(fileSearchVo)));
        PageHelper.startPage(fileSearchVo.getPageIndex(),fileSearchVo.getPageSize());
        List<HomePage> courseNews = courseApplyService.findCourseNews(fileSearchVo);
        PageInfo<HomePage> pageInfo = new PageInfo<>(courseNews);
        log.info(MessageFormat.format("查询结果：{0}", JSONObject.toJSONString(pageInfo)));
        ApiResult result = ApiResult.returnData(pageInfo);
        return result;
    }

    @GetMapping("/news/detail")
    @ApiOperation(value="课程报名课程新闻查看详情")
    @ApiImplicitParam(name="id",value="课程新闻id",required=true,paramType="query")
    public ApiResult<CommonDetail> findJournalismById(String id){
        log.info(MessageFormat.format("课程新闻详情参数：{0}", JSONObject.toJSONString(id)));
        CommonDetail commonDetail = courseApplyService.findJournalismById(id);
        log.info(MessageFormat.format("查询结果：{0}", JSONObject.toJSONString(commonDetail)));
        ApiResult result = ApiResult.returnData(commonDetail);
        return result;
    }

    @GetMapping("/course")
    @ApiOperation(value="课程报名(现场课程和线上课程数据)")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageIndex",value="第几页",required=true,paramType="query",defaultValue = "1"),
            @ApiImplicitParam(name="pageSize",value="一页多少条",required=true,paramType="query",defaultValue = "10"),
            @ApiImplicitParam(name="typeId",value="课程类型id",required=true,paramType="query")
    })
    public ApiResult<List<Course>> findCourse(Integer pageIndex,Integer pageSize,String typeId){
        log.info(MessageFormat.format("查询课程参数：typeId:{0}", JSONObject.toJSONString(typeId)));
        PageHelper.startPage(pageIndex,pageSize);
        List<Course> courseList = courseApplyService.findCourseByTypeId(typeId);
        PageInfo<Course> pageInfo = new PageInfo<>(courseList);
        log.info(MessageFormat.format("查询结果：{0}", JSONObject.toJSONString(pageInfo)));
        ApiResult result = ApiResult.returnData(pageInfo);
        return result;
    }

    @PostMapping("/search")
    @ApiOperation(value="课程搜索功能")
    public ApiResult<List<Course>> searchCourse(CourseVo courseVo){
        log.info(MessageFormat.format("课程搜索参数：{0}", JSONObject.toJSONString(courseVo)));
        PageHelper.startPage(courseVo.getPageIndex(),courseVo.getPageSize());
        List<Course> courseList = courseApplyService.searchCourse(courseVo);
        PageInfo<Course> pageInfo = new PageInfo<>(courseList);
        log.info(MessageFormat.format("搜索结果：{0}", JSONObject.toJSONString(pageInfo)));
        ApiResult result = ApiResult.returnData(pageInfo);
        return result;
    }

    @PostMapping("/apply")
    @ApiOperation(value="点击确定报名")
    public ApiResult<String> apply(CourseApply courseApply){
        log.info(MessageFormat.format("课程报名参数：{0}", JSONObject.toJSONString(courseApply)));
        String str = courseApplyService.saveCourseApply(courseApply);
        log.info(MessageFormat.format("报名结果：{0}", JSONObject.toJSONString(str)));
        ApiResult result = ApiResult.returnData(str);
        return result;
    }

    @ApiOperation(value = "后台管理端报名人员列表")
    @GetMapping()
    public ApiResult<List<CourseApply>> applyPersonList(){
        List<CourseApply> courseApplyList = courseService.applyPersonList();
        ApiResult result = ApiResult.returnData(courseApplyList);
        return result;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "后台管理端根据id查询报名人员详情")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "Integer",required = true)
    public ApiResult<String> getCourseApplyById(@PathVariable Integer id){
        Map<String,Object> courseApplyInfo = courseApplyService.getCourseApplyById(id);
        ApiResult result = ApiResult.returnData(courseApplyInfo);
        return result;
    }

    @ApiOperation(value = "后台管理端删除某个报名人员")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> deleteCourseApplyById(@PathVariable String id){
        courseApplyService.deleteByPrimaryKey(id);
        return ApiResult.returnData(null);
    }

    @ApiOperation(value = "后台管理端获取课程新闻列表")
    @GetMapping("newList")
    public ApiResult<List<HomePage>> findList(FileSearchVo fileSearchVo) {
        List<HomePage> courseNews = courseApplyService.findCourseNews(fileSearchVo);
        return ApiResult.returnData(courseNews);
    }

    @GetMapping("newList/{id}")
    @ApiOperation(value = "后台管理端根据id查询新闻")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String"),
            @ApiImplicitParam(name="openId",value="当前查阅人",required=false,paramType="form")
    })
    public ApiResult<String> findById(@PathVariable String id,String openId) {
        return ApiResult.returnData(journalismService.findByPrimaryKey(id,openId));
    }

    @ApiOperation(value = "后台管理端新增课程新闻")
    @PostMapping(value = "newList",produces = "application/json;charset=utf-8")
    @ApiImplicitParam(name = "journalism", value = "", paramType = "body", dataType = "Journalism")
    public ApiResult<String> save(@RequestBody @Valid Journalism journalism, @ModelAttribute Journalism model) {
        journalism.setCourse("2");
        return ApiResult.returnData(journalismService.insertSelective(journalism));

    }
    @ApiOperation(value = "后台管理端删除课程新闻")
    @DeleteMapping("newList/{id}")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> deleteById(@PathVariable String id) {
        journalismService.deleteByPrimaryKey(id);
        return ApiResult.returnData(null);
    }


    @ApiOperation(value = "后台管理端修改课程新闻")
    @PutMapping("newList")
    @ApiImplicitParam(name = "journalism", value = "", paramType = "body", dataType = "Journalism")
    public ApiResult<String> put(@RequestBody Journalism journalism, @ModelAttribute Journalism model) throws CustomException {
        return ApiResult.returnData(journalismService.updateByPrimaryKeySelective(journalism));
    }

}
