package com.dingguan.cheHengShi.user.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by zyc on 2018/12/7.
 */

@Data
@Entity
@ApiModel(description = "管理员")
@Table(name = "admin")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin {

    @Id
    @Column(columnDefinition = "varchar(30) comment ' 管理员 表'")
    private String id;

    @NotBlank(message ="缺少 登录名 userName")
    @ApiModelProperty(value = "登录名")
    @Column(columnDefinition = "varchar(30) comment '登录名'",name = "[user_name]")
    private String userName;

    @NotBlank(message ="缺少 登录密码 password")
    @ApiModelProperty(value = "登录密码")
    @Column(columnDefinition = "varchar(30) comment '登录密码'",name = "[password]")
    private String password;






}