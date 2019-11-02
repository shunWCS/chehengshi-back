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
 * Created by zyc on 2018/12/5.
 */

@Data
@Entity
@ApiModel(description = "地址")
@Table(name = "[address]")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {



    @Id
    @Column(name = "[id]", columnDefinition = "varchar(50)  comment '地址 表' ")
    private String id;

    @NotBlank
    @ApiModelProperty(value = " * 用户")
    @Column(name = "[open_id]", columnDefinition = "varchar(50)  comment '用户' ")
    private String openId;


    /*@NotBlank
    @ApiModelProperty(value = "* 省")
    @Column(columnDefinition = "varchar(30) comment 'spuId'",name = "[province]")
    private String province;
    @NotBlank
    @ApiModelProperty(value = "* 市/县")
    @Column(columnDefinition = "varchar(30) comment '市/县'",name = "[city]")
    private String city;

    @NotBlank
    @ApiModelProperty(value = "* 区")
    @Column(columnDefinition = "varchar(30) comment '区'",name = "[area]")
    private String area;


     */



    @NotBlank
    @ApiModelProperty(value = "* 地区")
    @Column(columnDefinition = "varchar(300) comment '地区'",name = "[region]")
    private String region;

    @NotBlank
    @ApiModelProperty(value = "* 详情地址")
    @Column(columnDefinition = "varchar(300) comment '详情地址'",name = "[crematorium]")
    private String crematorium;


    @NotBlank
    @ApiModelProperty(value = "*  姓名")
    @Column(columnDefinition = "varchar(50) comment '姓名'",name = "[name]")
    private String name;

    @NotBlank
    @ApiModelProperty(value = "*  电话")
    @Column(columnDefinition = "varchar(50) comment '电话'",name = "[phone]")
    private String phone;



    @ApiModelProperty(value = "*  是否默认地址   1:不是 2:是   ")
    @Column(columnDefinition = "varchar(5) comment '是否默认地址'",name = "[made_by_imperial_order]")
    private String madeByImperialOrder;


}