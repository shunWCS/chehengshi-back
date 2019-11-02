package com.dingguan.cheHengShi.product.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zyc on 2018/12/21.
 */

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sku")
@ApiModel(description = "sku")
public class Sku {




    @Id
    @Column(columnDefinition = "varchar(30) comment '产品详情表'")
    private String id;

    @NotBlank
    @ApiModelProperty(value = "* 所属产品id")
    @Column(columnDefinition = "varchar(30) comment 'spuId'",name = "[product_id]")
    private String productId;



    @NotBlank
    @ApiModelProperty(value = "* 产品详情名")
    @Column(columnDefinition = "varchar(30) comment '产品详情名'",name = "[name]")
    private String name;


    @ApiModelProperty(value = " sku的小图片")
    @Column(columnDefinition = "varchar(300) comment 'sku的小图片'",name = "[banner]")
    private String banner;

    @ApiModelProperty(value = "  描述")
    @Column( name = "[introduce]",columnDefinition = "varchar(300) comment '描述'")
    private String introduce;


    @NotNull
    @ApiModelProperty(value = "* 价格  如果是积分商品这个价格就仅用作展示")
    @Column(columnDefinition = "decimal(11,2) comment 'sku的原价'" )
    private BigDecimal price;

    @ApiModelProperty(value = "兑换所需积分  仅积分商品需要的字段")
    @Column( name = "[integral]",columnDefinition = "int(11) comment '兑换所需积分'")
    private Integer integral;



    @NotNull
    @ApiModelProperty(value = "* 库存")
    @Column( name = "[stock]",columnDefinition = "int(11) comment '库存'")
    private Integer stock;

    @ApiModelProperty(value = "sku的销量")
    @Column(columnDefinition = "integer comment '销量 本销量不展示 仅做统计用'")
    private Integer sales;









}