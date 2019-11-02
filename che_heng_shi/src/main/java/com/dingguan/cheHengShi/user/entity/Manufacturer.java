package com.dingguan.cheHengShi.user.entity;

import com.dingguan.cheHengShi.trade.entity.ShopCart;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by zyc on 2018/12/21.
 *
 *  todo 要能导航 怎么存?
 *
 */
@Data
@Entity
@ApiModel(description = "供应商/维修厂")
@Table(name = "[manufacturer]")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Manufacturer {


    @Id
    @Column(name = "[id]",columnDefinition = "varchar(50)  comment '厂商/维修厂 表' ")
    private String id;





    //厂商资料

    @NotBlank(message = "* 图标")
    @ApiModelProperty(value = "* 图标  ")
    @Column(name = "[banner]",columnDefinition = "varchar(500)  comment '图标' ")
    private String banner;

    @NotBlank(message = "* 联系电话")
    @ApiModelProperty(value = "* * 联系电话  ")
    @Column(name = "[phone]",columnDefinition = "varchar(100)  comment '* 联系电话' ")
    private String phone;

    @NotBlank(message = "* 公司名")
    @ApiModelProperty(value = "* * 公司名  ")
    @Column(name = "[company_name]",columnDefinition = "varchar(100)  comment ' 公司名' ")
    private String companyName;


    @NotBlank(message = "*  公司介绍文字")
    @ApiModelProperty(value = "*  公司介绍文字")
    @Column(columnDefinition = "varchar(255) comment ' 公司介绍文字'" ,name = "[introduce]")
    private String introduce;


    @ApiModelProperty(value = "  公司介绍图片")
    @Column(columnDefinition = "varchar(255) comment ' 公司介绍图片'" ,name = "[detail_img]")
    private String detailImg;


    @ApiModelProperty(value = " 营业执照")
    @Column(columnDefinition = "varchar(300) comment ' 营业执照 '",name = "[business_license]")
    private String businessLicense;



    //厂商地址信息

    @ApiModelProperty(value = "省")
    @Column(columnDefinition = "varchar(30) comment 'spuId'",name = "[province]")
    private String province;


    @ApiModelProperty(value = "市/县")
    @Column(columnDefinition = "varchar(30) comment '市/县'",name = "[city]")
    private String city;


    @ApiModelProperty(value = "区")
    @Column(columnDefinition = "varchar(30) comment '区'",name = "[area]")
    private String area;



    @ApiModelProperty(value = "   详细地址")
    @Column(columnDefinition = "varchar(60) comment '详细地址'", name = "[detailed_address]")
    private String detailedAddress;


    //厂商申请人信息
    @NotBlank(message = "* openId ")
    @ApiModelProperty(value = "* openId  ")
    @Column(name = "[open_id]",columnDefinition = "varchar(50)  comment 'openId' ")
    private String openId;

    @ApiModelProperty(value = " 身份证号码 ")
    @Column(columnDefinition = "varchar(300) comment '身份证号码 '",name = "[id_cart]")
    private String idCart;


    @ApiModelProperty(value = " 身份证 正面")
    @Column(columnDefinition = "varchar(300) comment '身份证 正面'",name = "[id_yan]")
    private String idYan;


    @ApiModelProperty(value = " 身份证 反面")
    @Column(columnDefinition = "varchar(300) comment '身份证 反面'",name = "[id_yin]")
    private String idYin;




    @NotBlank(message = "* 1:供应商 2:维修厂 ")
    @ApiModelProperty(value = "* 1:入驻商 2:维修厂 ")
    @Column(columnDefinition = "varchar(3) comment ' 1:供应商 2:维修厂 '",name = "[type]")
    private String type;

    @ApiModelProperty(value = " 1:申请中  2:通过/正常使用中 3:不通过 ")
    @Column(columnDefinition = "varchar(3) comment ' 1:申请中  2:通过/正常使用中 3:不通过 '",name = "[status]")
    private String status;


    @ApiModelProperty(value = "排序字段")
    @Column(columnDefinition = "int(11)   comment '排序字段'" ,name = "[sort]")
    private Integer sort;


    @ApiModelProperty(value = "申请/入驻时间")
    @Column(columnDefinition = "datetime   comment ' 申请/入驻时间'" ,name = "[time]")
    private Date time;

    @ApiModelProperty(value = "1:普通入驻的供应商/维修厂  2:平台的店铺")
    @Column(columnDefinition = "varchar(3)   comment ' 1:普通入驻的供应商/维修厂  2:平台的店铺 '" ,name = "[pattern]")
    private String pattern;

    @ApiModelProperty(value = "*  营业额 ")
    @Column(columnDefinition = "decimal(11,2) comment '营业额  '",name = "[turnover]")
    private BigDecimal turnover;




    @Transient
    @ApiModelProperty(hidden = true)
    private List<ShopCart> shopCartList;




}