package com.dingguan.cheHengShi.user.entity;

import com.alibaba.fastjson.annotation.JSONField;
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
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zyc on 2018/12/4.
 */

@Data
@Entity
@ApiModel(description = "用户")
@Table(name = "[user]")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "[open_id]",columnDefinition = "varchar(50)  comment '用户表' ")
    private String openId;


    @ApiModelProperty(value = "  用户头像 ")
    @Column(columnDefinition = "varchar(666) comment '用户头像'",name = "[head_img]")
    private String headImg;

    @ApiModelProperty(value = "  用户昵称")
    @Column(columnDefinition = "varchar(50) comment '用户昵称'",name = "[nick_name]")
    private String nickName;

    @ApiModelProperty(value = "  真实姓名")
    @Column(columnDefinition = "varchar(50) comment '真实姓名'",name = "[name]")
    private String name;

    @ApiModelProperty(value = "  电话")
    @Column(columnDefinition = "varchar(50) comment '电话'",name = "[phone]")
    private String phone;

    @ApiModelProperty(value = "  余额")
    @Column(columnDefinition = "decimal(13,2) comment '余额'",name = "[balance]")
    private BigDecimal balance;


    @ApiModelProperty(value = "  已提现金额")
    @Column(columnDefinition = "decimal(13,2) comment '已提现金额'",name = "[paradise]")
    private BigDecimal paradise;

    @ApiModelProperty(value = "  性别 ")
    @Column(columnDefinition = "varchar(13) comment '性别'",name = "[sex]")
    private String sex;





    @ApiModelProperty(value = "  积分余额 ")
    @Column(columnDefinition = "int(11) comment '积分余额'",name = "[integral]")
    private Integer integral;

    @ApiModelProperty(value = "  积分总额 ")
    @Column(columnDefinition = "int(11) comment '积分总额'",name = "[integral_total]")
    private Integer integralTotal;


    @ApiModelProperty(value = "  注册时间 ")
    @Column(columnDefinition = "datetime comment '注册时间'",name = "[registration_time]")
    private Date registrationTime;



    @ApiModelProperty(value = "  会员id 无此参数代表是普通用户 ")
    @Column(columnDefinition = "varchar(13) comment '会员id 无此参数代表是普通用户'",name = "[membership_id]")
    private String membershipId;

    @ApiModelProperty(value = "  会员名 ")
    @Column(columnDefinition = "varchar(13) comment '会员开始时间'",name = "[role_name]")
    private String roleName;

    @ApiModelProperty(value = "  会员开始时间 ")
    @Column(columnDefinition = "datetime comment '会员开始时间'",name = "[member_start_time]")
    private Date memberStartTime;

    @ApiModelProperty(value = "  会员结束时间 ")
    @Column(columnDefinition = "datetime comment '会员结束时间'",name = "[member_stop_time]")
    private Date memberStopTime;





    @ApiModelProperty(value = "  是否咨询师  1:不是 2:是")
    @Column(columnDefinition = "varchar(5) comment ' 是否咨询师  1:不是 2:是'",name = "[consultant]")
    private String consultant;

    @ApiModelProperty(value = "  咨询师简介")
    @Column(columnDefinition = "varchar(500) comment '咨询师简介'",name = "[consultant_introduction]")
    private String consultantIntroduction;



    @ApiModelProperty(value = "  咨询师是否推荐咨询师 1:不是  2:是  非咨询师忽略这个字段 ")
    @Column(columnDefinition = "varchar(5) comment ' 咨询师是否推荐咨询师 1:不是  2:是'",name = "[recommend]")
    private String recommend;

    @ApiModelProperty(value = "  总共能够看视频的数量 ")
    @Column(columnDefinition = "int(11) comment '总共能够看视频的数量'",name = "[total_video_num]")
    private Integer totalVideoNum;





}