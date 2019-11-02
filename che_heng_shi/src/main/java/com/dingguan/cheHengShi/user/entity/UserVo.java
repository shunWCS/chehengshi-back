package com.dingguan.cheHengShi.user.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by zyc on 2018/12/27.
 */



@Data
@ApiModel(description = "注册用户封装实体")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVo {



    private String openId;





    @ApiModelProperty(value = "  用户头像 ")
    private String headImg;

    @ApiModelProperty(value = "  用户昵称")
    private String nickName;

    @ApiModelProperty(value = "  真实姓名")
    private String name;

    @ApiModelProperty(value = "  电话")
    private String phone;

    @ApiModelProperty(value = "  余额")
    private BigDecimal balance;


    @ApiModelProperty(value = "  性别 ")
     private String sex;

    @ApiModelProperty(value = "  短信验证码 ")
    private String code;




}