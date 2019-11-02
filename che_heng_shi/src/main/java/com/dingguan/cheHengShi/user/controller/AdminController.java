package com.dingguan.cheHengShi.user.controller;


import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.user.entity.Admin;
import com.dingguan.cheHengShi.user.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by zyc on 2018/12/7.
 */

@RestController
@RequestMapping("/admin")
@Api(description = "管理员")
public class AdminController {



    @Autowired
    private AdminService adminService;


    @GetMapping("/{id}")
    @ApiOperation(value = "根据 id 查询 管理员")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> findById(@PathVariable String id) {
        return ApiResult.returnData(adminService.findByPrimaryKey(id));
    }


    @ApiOperation(value = "获取 管理员 列表")
    @GetMapping()
    public ApiResult findList(
    ) {
        List<Admin> adminList = adminService.findList();
        return ApiResult.returnData(adminList);

    }

    @ApiOperation(value = "新增 管理员")
    @PostMapping(produces = "application/json;charset=utf-8")
    @ApiImplicitParam(name = "admin", value = "", paramType = "body", dataType = "Admin")
    public ApiResult<String> save(@RequestBody @Valid Admin admin, @ModelAttribute Admin model) throws CustomException {
        return ApiResult.returnData(adminService.insertSelective(admin));

    }


    @ApiOperation(value = "删除 管理员")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> deleteById(@PathVariable String id) {

        adminService.deleteByPrimaryKey(id);
        return ApiResult.returnData(null);
    }


    @ApiOperation(value = "修改 管理员")
    @PutMapping("")
    @Deprecated
    @ApiImplicitParam(name = "admin", value = "", paramType = "body", dataType = "Admin")
    public ApiResult<String> put(@RequestBody Admin admin, @ModelAttribute Admin model) throws CustomException {
        return ApiResult.returnData(adminService.updateByPrimaryKeySelective(admin));
    }

    @ApiOperation(value = "登录" ,notes = "返回401 代表账户或密码错误")
    @PostMapping("/login")
    @ApiImplicitParam(name = "admin", value = "", paramType = "body", dataType = "Admin")
    public ApiResult<String> saveLogin(@RequestBody @Valid Admin admin, @ModelAttribute
            Admin model) throws CustomException {


        ApiResult apiResult = ApiResult.returnData(adminService.login(admin.getUserName(), admin.getPassword()));
        apiResult.setMessage("登录成功");

        return apiResult;

    }

}