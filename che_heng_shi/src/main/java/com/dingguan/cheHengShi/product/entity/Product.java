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
@Table(name = "product")
@ApiModel(description = "产品")
public class Product {



    @Id
    @Column(name = "[id]",columnDefinition = "varchar(30)  comment '产品表' ")
    private String id;


    @NotBlank(message = "banner")
    @ApiModelProperty(value = "* 产品图")
    @Column(columnDefinition = "varchar(500) comment '产品图'")
    private String banner;


    @ApiModelProperty(value = "  产品图 前端使用")
    @Column(columnDefinition = "varchar(500) comment '产品图 前端使用'",name = "[banner_t]")
    private String bannerT;

    @NotBlank(message = "产品名")
    @ApiModelProperty(value = "* 产品名")
    @Column( name = "[name]", columnDefinition = "varchar(100)  comment '产品名'")
    private String name;



    @NotBlank(message = "产品描述")
    @ApiModelProperty(value = "* 产品描述")
    @Column(columnDefinition = "varchar(100)   comment '产品描述'" ,name = "[introduce]")
    private String introduce;

    @NotBlank(message = "产品分类id")
    @ApiModelProperty(value = "*  产品分类id")
    @Column( name = "[product_type_id]",columnDefinition = "varchar(30)  comment '产品分类id'")
    private String productTypeId;

    @NotBlank(message = "产品详情图")
    @ApiModelProperty(value = "* 产品详情图")
    @Column( name = "[detail_img]",columnDefinition = "varchar(500) comment '产品详情图'")
    private String detailImg;




    @ApiModelProperty(value = "排序字段")
    @Column(columnDefinition = "int(11) comment '排序字段' ")
    private Integer sort;




    @NotBlank(message = "产品类型")
    @ApiModelProperty(value = " * 产品类型 1:普通只能直接购买的   2:积分  ")
    @Column(columnDefinition = "varchar(3) comment '产品类型 1:普通只能直接购买的   2:积分' ",name = "[type]")
    private String type;









    @ApiModelProperty(value = "销量")
    @Column(columnDefinition = "integer comment '销量'")
    private Integer sales;
    @ApiModelProperty(value = "产品库存 就是所有sku的库存 总和  ")
    @Column( name = "[stock]",columnDefinition = "int(11) comment '产品库存'")
    private Integer stock;




    @ApiModelProperty(value = "最小sku价格 /原价")
    @Column(columnDefinition = "decimal(11,2) comment 'sku的价格'" ,name = "[min_price]")
    private BigDecimal minPrice;


    @ApiModelProperty(value = "最小sku积分")
    @Column(columnDefinition = "int(11) comment '最小sku积分'" ,name = "[min_integral]")
    private Integer minIntegral;


    @ApiModelProperty(value = "  邮费  0就是免邮 ")
    @Column(name = "[postage]", columnDefinition = "decimal(11,2)  comment '邮费'")
    private BigDecimal postage;








    @ApiModelProperty(value = "点击量")
    @Column(columnDefinition = "int(11)   comment '点击量'" ,name = "[clicks]")
    private Integer clicks;






    //@NotBlank
    @ApiModelProperty(value = "* 店铺id")
    @Column(columnDefinition = "varchar(33) comment '店铺id'",name = "[manufacturer_id]")
    private String manufacturerId;

    //@NotBlank
    @ApiModelProperty(value = "* 店铺名")
    @Column(columnDefinition = "varchar(33) comment '店铺名'",name = "[manufacturer_name]")
    private String manufacturerName;

    //@NotBlank
    @ApiModelProperty(value = "* 店铺图片")
    @Column(columnDefinition = "varchar(33) comment '店铺图片'",name = "[manufacturer_banner]")
    private String manufacturerBanner;

    @ApiModelProperty(value = " 1:审核中 2:上架 3:下架 ")
    @Column(columnDefinition = "varchar(33) comment ' 1:审核中 2:上架 3:下架 '",name = "[status]")
    private String status;





    @ApiModelProperty(value = "  是否推荐  1:不是  2:是    ")
    @Column(columnDefinition = "varchar(5) comment ' 是否推荐 1:不是  2:是'",name = "[recommend]")
    private String recommend;

    @ApiModelProperty(value = "  上传时间    ")
    @Column(columnDefinition = "varchar(30) comment '上传时间'",name = "[time]")
    private String time;


    @ApiModelProperty(value = "  海报轮播 1:不是  2:是 ")
    @Column(columnDefinition = "varchar(5) comment '海报轮播 1:不是  2:是'",name = "[poster]")
    private String poster;


    @Transient
   //@ApiModelProperty(value = "产品参数",hidden = true)
    private List<Sku> skuList;


    @Transient
    //@ApiModelProperty(value = "产品参数",hidden = true)
    private List<ProductParameter> productParameterList;





}