package com.dingguan.cheHengShi.trade.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dingguan.cheHengShi.product.entity.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by zyc on 2018/12/5.
 */
@Data
@Entity
@ApiModel(description = "订单详情表")
@Table(name = "order_detail")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetail {


    @Id
    @Column(columnDefinition = "varchar(30) comment '订单详情表'")
    private String id;

    @NotBlank
    @ApiModelProperty(value = "* 产品id/视频id/文档id/会员套餐id  ")
    @Column(columnDefinition = "varchar(30) comment '产品id'",name = "[commodity_id]")
    private String commodityId;

    //@NotBlank
    @ApiModelProperty(value = "* SkuId  仅产品/积分 需要")
    @Column(columnDefinition = "varchar(30) comment 'SkuId'",name = "[sku_id]")
    private String skuId;


    @NotNull
    @ApiModelProperty(value = "* 数量 ")
    @Column(columnDefinition = "int(11) comment '数量'",name = "[num]")
    private Integer num;



    @ApiModelProperty(value = " 订单id ")
    @Column(columnDefinition = "varchar(30) comment '订单id'",name = "[order_id]")
    private String orderId;




    @ApiModelProperty(value = "  下单人信息 ",hidden = true)
    @Column(columnDefinition = "varchar(30) comment '下单人信息 openId'", name = "[open_id]")
    private String openId;

    @ApiModelProperty(value = "  订单类型 1:直接购买 2:视频 3:文档 4:积分兑换 5:会员套餐")
    @Column(columnDefinition = "varchar(30) comment '订单类型 1:直接购买 2:视频 3:文档 4:积分兑换 '", name = "[order_type]")
    private String orderType;

    @JSONField(serialize = false)
    @ApiModelProperty(value = " 有没有被使用0:未支付的 1:没有 2:已经用过了  仅视频/文档才会用到这个字段  前端不用展示出这个字段")
    @Column(columnDefinition = "varchar(30) comment ' 有没有被使用 '", name = "[have_usr]")
    private String haveUsr;

    @JSONField(serialize = false)
    @ApiModelProperty(value = " 使用结束时间 ")
    @Column(columnDefinition = "TIMESTAMP comment ' 使用结束时间 '", name = "[stop_time]")
    private Date stopTime;


    @Transient
    @ApiModelProperty(hidden = true)
    private Product product;
    @Transient
    @ApiModelProperty(hidden = true)
    private Sku sku;
    @Transient
    @ApiModelProperty(hidden = true)
    private File file;
    @Transient
    @ApiModelProperty(hidden = true)
    private Video video;
    @Transient
    @ApiModelProperty(hidden = true)
    private MembershipPackage membershipPackage;


}