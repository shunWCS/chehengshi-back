package com.dingguan.cheHengShi.trade.entity;

import com.alibaba.fastjson.annotation.JSONField;
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
import java.util.Date;
import java.util.List;

/**
 * Created by zyc on 2018/12/5.
 */

@Data
@Entity
@ApiModel(description = "订单表")
@Table(name = "[order]")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {



    @Id
    @Column(columnDefinition = "varchar(30) comment '订单表'")
    private String id;



    @NotBlank(message = " openId")
    @ApiModelProperty(value = "* 下单人信息 openId")
    @Column(columnDefinition = "varchar(30) comment '下单人信息 openId'", name = "[open_id]")
    private String openId;



    @ApiModelProperty(value = "订单状态 1:未付款 2:已经付款/待发货  3:已经发货   4:已收货  5:已取消/删除订单 ")
    @Column(columnDefinition = "varchar(30) comment ' 订单状态 1:未付款 2:已经付款/待发货  3:已经发货   4:已收货  5:已取消/删除订单 ' ",
            name = "[status]")
    private String status;




    @NotNull(message = " actualPrice")
    @ApiModelProperty(value = "*  用户实际要支付的 价格   实际支付商品价格+邮费 ")
    @Column(columnDefinition = "decimal(11,2) comment '用户实际要支付的 价格 '",name = "[actual_price]")
    private BigDecimal actualPrice;







    @ApiModelProperty(value = " 下单时间")
    @Column(name = "[order_time]", columnDefinition = "TIMESTAMP  comment '下单时间'  ")
    private Date orderTime;

    @ApiModelProperty(value = " 支付时间")
    @Column(name = "[pay_time]", columnDefinition = "TIMESTAMP  comment '支付时间'  ")
    private Date payTime;

    @ApiModelProperty(value = " 买家下单时的备注")
    @Column(columnDefinition = "varchar(300) comment '买家下单时的备注'", name = "[remark]")
    private String remark;





    @ApiModelProperty(value = " 地区")
    @Column(columnDefinition = "varchar(300) comment '地区'",name = "[region]")
    private String region;

    @ApiModelProperty(value = " 详情地址")
    @Column(columnDefinition = "varchar(300) comment '详情地址'",name = "[crematorium]")
    private String crematorium;


    @ApiModelProperty(value = " 下单人信息 名字")
    @Column(columnDefinition = "varchar(30) comment '下单人信息 名字'", name = "[name]")
    private String name;


    @ApiModelProperty(value = "下单人信息 电话")
    @Column(columnDefinition = "varchar(30) comment '下单人信息 电话'", name = "[phone]")
    private String phone;


    @NotBlank(message = "orderType ")
    @ApiModelProperty(value = "* 订单类型 1:直接购买 2:视频 3:文档 4:积分兑换 5:会员套餐")
    @Column(columnDefinition = "varchar(30) comment '订单类型 1:直接购买 2:视频 3:文档 4:积分兑换 '", name = "[order_type]")
    private String orderType;







    @ApiModelProperty(value = "  所需积分   积分兑换时需要传这个参数   其余时候不用管")
    @Column(columnDefinition = "int(11) comment '所需积分'",name = "[integral]")
    private Integer integral;



    @ApiModelProperty(value = "  邮费")
    @Column(name = "[postage]", columnDefinition = "decimal(11,2)  comment '邮费'")
    private BigDecimal postage;



    @ApiModelProperty(value = " 店铺id")
    @Column(columnDefinition = "varchar(33) comment '店铺id'",name = "[manufacturer_id]")
    private String manufacturerId;


    @ApiModelProperty(value = " 店铺名")
    @Column(columnDefinition = "varchar(33) comment '店铺名'",name = "[manufacturer_name]")
    private String manufacturerName;


    @ApiModelProperty(value = " 店铺图片")
    @Column(columnDefinition = "varchar(33) comment '店铺图片'",name = "[manufacturer_banner]")
    private String manufacturerBanner;






    @JSONField(serialize = false)
    @Column(columnDefinition = "varchar(30) comment '微信端支付id'", name = "[transaction_id]")
    private String transactionId;

    @Transient
    @ApiModelProperty(value = "订单详情")
    private List<OrderDetail> orderDetailList;













}