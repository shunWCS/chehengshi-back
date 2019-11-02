package com.dingguan.cheHengShi.product.entity;

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
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by zyc on 2018/12/20.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "membership_package")
@ApiModel(description = "会员套餐")
public class MembershipPackage {


    @Id
    @Column(columnDefinition = "varchar(30) comment '会员套餐 表'")
    private String id;


    /**
     * 套餐名
     */
    @NotBlank(message = "* 套餐名")
    @ApiModelProperty(value = "* 价格 ")
    @Column(columnDefinition = "varchar(100) comment '套餐名'",name = "[set_meal]")
    private String setMeal;

    @NotNull(message = "* 价格")
    @ApiModelProperty(value = "* 价格 ")
    @Column(columnDefinition = "decimal(11,2) comment '价格'",name = "[price]" )
    private BigDecimal price;

    @NotNull(message = "* 原价")
    @ApiModelProperty(value = "* 原价 ")
    @Column(columnDefinition = "decimal(11,2) comment '原价'",name = "[original_price]" )
    private BigDecimal originalPrice;

    @NotBlank(message = "*  简介")
    @ApiModelProperty(value = "*  简介")
    @Column(columnDefinition = "varchar(255) comment ' 简介'" ,name = "[introduce]")
    private String introduce;



    @ApiModelProperty(value = "   描述")
    @Column(columnDefinition = "varchar(255) comment ' 描述'" ,name = "[describe]")
    private String describe;

    @NotNull(message = "* 套餐有持续日期")
    @ApiModelProperty(value = "* 套餐有持续日期 ")
    @Column(columnDefinition = "decimal(11,2) comment '套餐有持续日期'",name = "[duration]" )
    private Integer duration;


    @ApiModelProperty(value = "销量")
    @Column(columnDefinition = "int comment '销量'",name = "[sales]")
    private Integer sales;



}