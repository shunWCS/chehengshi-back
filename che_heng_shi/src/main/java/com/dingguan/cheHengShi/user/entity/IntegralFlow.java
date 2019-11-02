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
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by zyc on 2018/12/22.
 */
@Data
@Entity
@ApiModel(description = "积分流水")
@Table(name = "[integral_flow]")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IntegralFlow {

    @Id
    @Column(name = "[id]", columnDefinition = "varchar(50)  comment '积分流水 表' ")
    private String id;

    @NotBlank
    @ApiModelProperty(value = " * 用户")
    @Column(name = "[open_id]", columnDefinition = "varchar(50)  comment '用户' ")
    private String openId;



    @ApiModelProperty(value = " 时间")
    @Column(name = "[time]", columnDefinition = "datetime  comment '时间' ")
    private Date time;

    @NotBlank
    @ApiModelProperty(value = " * 类型 1:交易成功 2兑换  3:赠送 ")
    @Column(name = "[type]", columnDefinition = "varchar(5)  comment '类型 1:交易成功 2兑换  3:赠送 ' ")
    private String type;

    @NotBlank
    @ApiModelProperty(value = " * 标题")
    @Column(name = "[title]", columnDefinition = "varchar(500)  comment '标题' ")
    private String title;

    @NotNull
    @ApiModelProperty(value = " * 分数")
    @Column(name = "[fraction]", columnDefinition = "int(11)  comment '分数' ")
    private Integer fraction;




    @ApiModelProperty(value = " 所属订单id  ")
    @Column(name = "[order_id]", columnDefinition = "varchar(50)  comment '所属订单id' ")
    private String orderId;

}