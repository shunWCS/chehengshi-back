package com.dingguan.cheHengShi.home.controller;

import com.dingguan.cheHengShi.common.utils.Util;
import com.dingguan.cheHengShi.home.service.UpdateUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "更新文件地址路径")
@RestController
@RequestMapping("")
@Slf4j
public class UpdateURLController {

    @Autowired
    private UpdateUrlService updateUrlService;

    @GetMapping("/update/file")
    @ApiOperation(value = "更新file表的文件路径")
    @ApiImplicitParam(name="id",value="主键id",required=false,paramType="query")
    public String updateURLforFile(String id){
        Integer count = null;
        if(Util.isNotEmpty(id)){
            count = updateUrlService.selectFileUrl(id);
        }else {
            count = updateUrlService.selectFileUrlALL();
        }
        return "一共更新" + count + "个";
    }

    @GetMapping("/update/video")
    @ApiOperation(value = "更新video表的文件路径")
    @ApiImplicitParam(name="id",value="主键id",required=false,paramType="query")
    public String updateUrlForvideo(String id){
        Integer count = null;
        if(Util.isNotEmpty(id)){
            count = updateUrlService.selectVideoUrl(id);
        }else {
            count = updateUrlService.selectVideoUrlALL(id);
        }
        return "一共更新" + count + "个";
    }
}
