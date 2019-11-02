package com.dingguan.cheHengShi.user.controller;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.common.utils.DateUtil;
import com.dingguan.cheHengShi.product.entity.MembershipPackage;
import com.dingguan.cheHengShi.product.service.MembershipPackageService;
import com.dingguan.cheHengShi.user.entity.User;
import com.dingguan.cheHengShi.user.entity.UserVo;
import com.dingguan.cheHengShi.user.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Created by zyc on 2018/12/4.
 */
@RestController
@RequestMapping("/user")
@Api(description = "用户")
public class UserController {


    @Autowired
    private UserService userService;
    @Autowired
    private MembershipPackageService membershipPackageService;


    @GetMapping("/{id}")
    @ApiOperation(value = "根据 id 查询 用户")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<User> findById(@PathVariable String id) {
        return ApiResult.returnData(userService.findByPrimaryKey(id));
    }


    @ApiOperation(value = "获取 用户 列表")
    @GetMapping()

    public ApiResult findList(
            @ApiParam("") @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
            @ApiParam("") @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @ApiParam("") @RequestParam(name = "name", required = false) String name,
            @ApiParam("是否咨询师  1:不是 2:是") @RequestParam(name = "consultant", required = false) String consultant,
            @ApiParam("咨询师是否推荐咨询师 1:不是  2:是") @RequestParam(name = "recommend", required = false) String recommend,
            @ApiParam("") @RequestParam(name = "nickName", required = false) String nickName,
            @ApiParam("") @RequestParam(name = "phone", required = false) String phone
    ) {

        User build = User.builder()
                .name(name)
                .recommend(recommend)
                .consultant(consultant)
                .nickName(nickName)
                .phone(phone)
                .build();

        Sort sort = new Sort(Sort.Direction.ASC, "registrationTime");

        if (pageIndex == null || pageSize == null) {
            List<User> userList = userService.findList(build, sort);
            return ApiResult.returnData(userList);
        }

        PageRequest pageRequest = new PageRequest(pageIndex, pageSize, sort);

        return userService.findList(build, pageRequest);
    }

    @ApiOperation(value = "新增 用户")
    @PostMapping(produces = "application/json;charset=utf-8")
    @ApiImplicitParam(name = "user", value = "", paramType = "body", dataType = "User")
    public ApiResult<String> insert(@RequestBody @Valid User user, @ModelAttribute User model) throws IOException,
            CustomException {
        return ApiResult.returnData(userService.insertSelective(user));

    }


    @ApiOperation(value = "删除 用户")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> deleteById(@PathVariable String id) {
        userService.deleteByPrimaryKey(id);
        return ApiResult.returnData(null);
    }


    @ApiOperation(value = "修改 用户")
    @PutMapping("")
    @ApiImplicitParam(name = "user", value = "", paramType = "body", dataType = "User")
    public ApiResult<String> update(@RequestBody User user, @ModelAttribute User model) throws CustomException {
        return ApiResult.returnData(userService.updateByPrimaryKeySelective(user));
    }


    @ApiOperation(value = " 用户 注册 ")
    @PostMapping("/register")
    @ApiImplicitParam(name = "userVo", value = "", paramType = "body", dataType = "UserVo")
    public ApiResult<String> insert(@RequestBody @Valid UserVo userVo, @ModelAttribute UserVo model) throws IOException,
            CustomException {
        return ApiResult.returnData(userService.register(userVo));

    }


    @ApiOperation(value = "给用户续会员")
    @GetMapping("/payMembershipPackage")
     public ApiResult<String> update1(
            @ApiParam("openId") @RequestParam(value = "openId") String openId,
            @ApiParam("套餐id") @RequestParam(value = "membershipPackageId") String membershipPackageId
    ) throws CustomException {
        User user = userService.findByPrimaryKey(openId);
        MembershipPackage membershipPackage = membershipPackageService.findByPrimaryKey(membershipPackageId);
        user = userService.payMembershipPackage(membershipPackage, user);


        return ApiResult.returnData(user);
    }

    @ApiOperation(value = "**********")
    @GetMapping("/test")
    @Deprecated
    public ApiResult<String> update2(
    ) throws CustomException {


        User build = User.builder().build();
        Sort sort = new Sort(Sort.Direction.ASC, "registrationTime");
        List<User> userList = userService.findList(build, sort);

        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            String membershipId = user.getMembershipId();
            if (membershipId != null) {

                int totleNum = 0;
                int betweenDays = (int) DateUtil.getBetweenDays(user.getMemberStartTime(), user.getMemberStopTime());
                if (betweenDays > 300) {
                    //包年
                    int i1 = betweenDays / 300;
                    totleNum = totleNum + i1 * 360;
                } else {
                    int i1 = betweenDays / 29;
                    totleNum = totleNum + i1 * 30;
                }

                user.setTotalVideoNum(totleNum);
                userService.updateByPrimaryKeySelective(user);


            }


        }


        return ApiResult.returnData(userList);
    }


}