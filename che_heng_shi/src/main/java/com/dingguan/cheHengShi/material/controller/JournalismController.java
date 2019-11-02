package com.dingguan.cheHengShi.material.controller;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.material.entity.FlashView;
import com.dingguan.cheHengShi.material.entity.Journalism;
import com.dingguan.cheHengShi.material.service.FlashViewService;
import com.dingguan.cheHengShi.material.service.JournalismService;
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
 * Created by zyc on 2018/12/20.
 */
@RestController
@RequestMapping("/journalism")
@Api(description = "新闻")
@Slf4j
public class JournalismController {


    @Autowired
    private JournalismService journalismService;


    @GetMapping("/{id}")
    @ApiOperation(value = "根据 id 查询 新闻")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> findById(
            @ApiParam("openId") @RequestParam(value = "openId",required = false)String openId,
            @PathVariable String id) {
        log.info(MessageFormat.format("查看新闻公告详情========================>：id:{0},openId:{1}",id,openId));
        return ApiResult.returnData(journalismService.findByPrimaryKey(id,openId));
    }


    @ApiOperation(value = "获取 新闻 列表")
    @GetMapping()
    public ApiResult<String> findList(
    ) {
        return ApiResult.returnData(journalismService.findList());
    }

    @ApiOperation(value = "新增 新闻")
    @PostMapping(produces = "application/json;charset=utf-8")
    @ApiImplicitParam(name = "journalism", value = "", paramType = "body", dataType = "Journalism")
    public ApiResult<String> save(@RequestBody @Valid Journalism journalism, @ModelAttribute Journalism model) {

        return ApiResult.returnData(journalismService.insertSelective(journalism));

    }


    @ApiOperation(value = "删除 新闻")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> deleteById(@PathVariable String id) {
        journalismService.deleteByPrimaryKey(id);
        return ApiResult.returnData(null);
    }


    @ApiOperation(value = "修改 新闻")
    @PutMapping("")
    @ApiImplicitParam(name = "journalism", value = "", paramType = "body", dataType = "Journalism")
    public ApiResult<String> put(@RequestBody Journalism journalism, @ModelAttribute Journalism model) throws CustomException {
        return ApiResult.returnData(journalismService.updateByPrimaryKeySelective(journalism));
    }

}