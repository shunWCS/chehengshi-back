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

/**
 * Created by zyc on 2018/12/20.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product_type")
@ApiModel(description = "产品类型")
public class ProductType {

    @Id
    @Column(columnDefinition = "varchar(30) comment '产品类型 表'")
    private String id;

    @NotBlank(message = "* 产品类型名")
    @ApiModelProperty(value = "* 产品类型名")
    @Column(columnDefinition = "varchar(255) comment '产品类型名'" ,name = "[type_name]")
    private String typeName;

    @NotBlank(message = "* 产品类型 banner图")
    @ApiModelProperty(value = "* 产品类型 banner图")
    @Column(columnDefinition = "varchar(255) comment '产品类型 banner图'" ,name = "[banner]")
    private String banner;

    @NotBlank(message = "* 产品类型 简介")
    @ApiModelProperty(value = "* 产品类型 简介")
    @Column(columnDefinition = "varchar(255) comment '产品类型 简介'" ,name = "[introduce]")
    private String introduce;



    @ApiModelProperty(value = "排序字段")
    @Column(columnDefinition = "int(11)   comment '排序字段'" ,name = "[sort]")
    private Integer sort;
}