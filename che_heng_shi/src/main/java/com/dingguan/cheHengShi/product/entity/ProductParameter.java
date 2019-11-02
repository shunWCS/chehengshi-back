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
 * Created by zyc on 2018/12/3.
 */

@Data
@Entity(name = "product_parameter")
@ApiModel(description = "产品参数")
@Table(name = "product_parameter")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductParameter {


    @Id
    @Column(columnDefinition = "varchar(30) comment '产品详情表'")
    private String id;


    @NotBlank
    @ApiModelProperty(value = "* 所属产品id")
    @Column(columnDefinition = "varchar(30) comment '所属产品id'",name = "[product_id]")
    private String productId;


    @NotBlank
    @ApiModelProperty(value = "* 产品参数 key")
    @Column(columnDefinition = "varchar(30) comment '产品参数 key'",name = "[param_key]")
    private String paramKey;

    @NotBlank
    @ApiModelProperty(value = "* 产品参数 value")
    @Column(columnDefinition = "varchar(300) comment '产品参数 value'",name = "[param_value]")
    private String paramValue;


    @ApiModelProperty(value = "排序字段")
    private Integer sort;






}