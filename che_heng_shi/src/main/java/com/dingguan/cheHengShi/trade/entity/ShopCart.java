package com.dingguan.cheHengShi.trade.entity;

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
import java.util.Date;

/**
 * Created by zyc on 2018/11/28.
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "购物车")
@Table(name = "shop_cart")
public class ShopCart {


    @Id
    @Column(columnDefinition = "varchar(30) comment '产品表'")
    private String id;

    @NotBlank
    @ApiModelProperty(value = "* 所属产品id")
    @Column(columnDefinition = "varchar(30) comment 'spuId'",name = "[product_id]")
    private String productId;

    @NotBlank
    @ApiModelProperty(value = "* 所属产品的sku id")
    @Column(columnDefinition = "varchar(30) comment 'skuId'",name = "[sku_id]")
    private String skuId;


    @ApiModelProperty(value = "* 最早加入购物车时间")
    @Column(name = "[time]" ,columnDefinition = "TIMESTAMP  comment '最早加入购物车时间'  ")
    private Date time;



    @NotBlank
    @ApiModelProperty(value = "* 购物车的主人")
    @Column(columnDefinition = "varchar(50) comment '购物车的主人'",name = "[open_id]")
    private String openId;

    @NotNull
    @ApiModelProperty(value = "* 数量")
    @Column( name = "[num]",columnDefinition = "int(2) comment '数量'")
    private Integer num;


    //冗余字段  产品banner 产品名 产品描述   sku 名 banner 简介 单价


    @ApiModelProperty(value = "产品图")
    @Column(columnDefinition = "varchar(500) comment '产品图'",name = "product_banner")
    private String productBanner;


    @ApiModelProperty(value = " 产品名")
    @Column( name = "[product_name]", columnDefinition = "varchar(100)  comment '产品名'")
    private String productName;


    @ApiModelProperty(value = " 产品描述")
    @Column(columnDefinition = "varchar(100)   comment '产品描述'" ,name = "[product_introduce]")
    private String productIntroduce;





    @ApiModelProperty(value = " 产品详情名")
    @Column(columnDefinition = "varchar(30) comment '产品详情名'",name = "[sku_name]")
    private String skuName;

    @ApiModelProperty(value = " sku的小图片")
    @Column(columnDefinition = "varchar(300) comment 'sku的小图片'",name = "[sku_banner]")
    private String skuBanner;

    @ApiModelProperty(value = "  描述")
    @Column( name = "[sku_introduce]",columnDefinition = "varchar(300) comment '描述'")
    private String skuIntroduce;

    @NotNull
    @ApiModelProperty(value = "* 价格  如果是积分商品这个价格就仅用作展示")
    @Column(columnDefinition = "decimal(11,2) comment 'sku的原价'",name = "[sku_price]")
    private BigDecimal skuPrice;

    @ApiModelProperty(value = "兑换所需积分  仅积分商品需要的字段")
    @Column( name = "[sku_integral]",columnDefinition = "int(11) comment '兑换所需积分'")
    private Integer skuIntegral;



    //供应商
    @NotBlank
    @ApiModelProperty(value = "* 店铺id")
    @Column(columnDefinition = "varchar(33) comment '店铺id'",name = "[manufacturer_id]")
    private String manufacturerId;

    @NotBlank
    @ApiModelProperty(value = "* 店铺名")
    @Column(columnDefinition = "varchar(33) comment '店铺名'",name = "[manufacturer_name]")
    private String manufacturerName;

    @NotBlank
    @ApiModelProperty(value = "* 店铺图片")
    @Column(columnDefinition = "varchar(33) comment '店铺图片'",name = "[manufacturer_banner]")
    private String manufacturerBanner;

    @ApiModelProperty(value = "  邮费    ")
    @Column(name = "[postage]", columnDefinition = "decimal(11,2)  comment '邮费'")
    private BigDecimal postage;

}