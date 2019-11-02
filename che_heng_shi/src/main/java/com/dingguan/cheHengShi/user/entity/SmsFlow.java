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
import java.util.Date;

/**
 * Created by zyc on 2018/11/28.
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "短信流水")
@Table(name = "sms_flow")
public class SmsFlow {



    @Id
    @Column(columnDefinition = "varchar(30) comment '产品表'")
    private String id;

    @NotBlank
    @ApiModelProperty(value = "* 发送短信的电话号码")
    @Column(name = "[phone]" ,columnDefinition = "varchar(30) comment '发送短信的电话号码'  ")
    private String phone;


    @ApiModelProperty(value = "  验证码")
    @Column(name = "[code]" ,columnDefinition = "varchar(30) comment '验证码'  ")
    private String code;

    @ApiModelProperty(value = "发送短信的时间")
    @Column(name = "[time]" ,columnDefinition = "TIMESTAMP  comment '发送短信的时间'  ")
    private Date time;


    @ApiModelProperty(value = "使用状态 1:未被使用  2:已经被使用了")
    @Column(name = "[status]" ,columnDefinition = "varchar(30)  comment '使用状态 1:未被使用  2:已经被使用了'  ")
    private String status;


    @NotBlank
    @ApiModelProperty(value = " *")
    @Column(name = "[open_id]" ,columnDefinition = "varchar(50)    ")
     private String openId;





}