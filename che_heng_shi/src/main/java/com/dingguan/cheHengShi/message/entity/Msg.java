package com.dingguan.cheHengShi.message.entity;

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
 * Created by zyc on 2018/12/20.
 */

@Data
@Entity
@ApiModel(description = "聊天消息")
@Table(name = "msg")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Msg {

    @Id
    @Column(columnDefinition = "varchar(30) comment '聊天消息 表'")
    private String id;

    @NotBlank(message = "* 生产者")
    @ApiModelProperty(value = "* 生产者")
    @Column(columnDefinition = "varchar(255) comment '生产者'" ,name = "[produce]")
    private String produce;

    @NotBlank(message = "* 消费者")
    @ApiModelProperty(value = "* 消费者")
    @Column(columnDefinition = "varchar(255) comment '消费者'" ,name = "[consumer]")
    private String consumer;

    @NotBlank(message = "* 聊天文字")
    @ApiModelProperty(value = "* 聊天文字")
    @Column(columnDefinition = "varchar(2555) comment '聊天文字'" ,name = "[text]")
    private String text;

    @ApiModelProperty(value = "  发送时间 ")
    @Column(columnDefinition = "datetime   comment '文档 发送时间'" ,name = "[time]")
    private Date time;

    @ApiModelProperty(value = " 1:未读 2:已读 ")
    @Column(columnDefinition = "varchar(5) comment '1:未读 2:已读'" ,name = "[status]")
    private String status;


    @ApiModelProperty(value = " 聊天室id ")
    @Column(columnDefinition = "varchar(30) comment '聊天室id'" ,name = "[chat_room_id]")
    private String chatRoomId;


    @ApiModelProperty(value = " 1:文字 2:文件 ")
    @Column(columnDefinition = "varchar(5) comment '1:未读 2:已读'" ,name = "[msg_type]")
    private String msgType;


    //冗余字段
    /*@NotBlank(message = "* 生产者 昵称")
    @ApiModelProperty(value = "* 生产者 昵称")
    @Column(columnDefinition = "varchar(255) comment '生产者 昵称'" ,name = "[produce_nick_name]")
    private String produceNickName;

    @NotBlank(message = "* 生产者 头像")
    @ApiModelProperty(value = "* 生产者 头像")
    @Column(columnDefinition = "varchar(255) comment '生产者 头像'" ,name = "[produce_hear_name]")
    private String produceHearName;

    @NotBlank(message = "* 消费者 昵称")
    @ApiModelProperty(value = "* 消费者 昵称")
    @Column(columnDefinition = "varchar(255) comment '消费者 昵称'" ,name = "[consumer_nick_name]")
    private String consumerNickName;

    @NotBlank(message = "* 消费者 头像")
    @ApiModelProperty(value = "* 消费者 头像")
    @Column(columnDefinition = "varchar(255) comment '消费者 头像'" ,name = "[consumer_hear_name]")
    private String consumerHearName;*/

}