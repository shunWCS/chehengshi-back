package com.dingguan.cheHengShi.message.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zyc on 2018/12/21.
 */

@Data
@Entity
@ApiModel(description = "聊天室友")
@Table(name = "chat_room")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom {


    @Id
    @Column(columnDefinition = "varchar(30) comment '聊天消息 表'")
    private String id;

    @NotBlank(message = "* 最早的生产者 ")
    @ApiModelProperty(value = "* 最早的生产者")
    @Column(columnDefinition = "varchar(255) comment '最早的生产者'" ,name = "[produce]")
    private String produce;

    @NotBlank(message = "* 最早的消费者")
    @ApiModelProperty(value = "* 最早的消费者")
    @Column(columnDefinition = "varchar(255) comment '最早的消费者'" ,name = "[consumer]")
    private String consumer;


    /**
     * 用于排序
     */
  /*  @ApiModelProperty(value = "  最后聊天时间  ")
    @Column(columnDefinition = "datetime  comment '最后聊天时间'" ,name = "[time]")
    private Date time;*/

    //冗余字段
    @Transient
    @ApiModelProperty(value = "* 生产者 昵称")
    private String produceNickName;

    @Transient
    @ApiModelProperty(value = "* 生产者 头像")
    private String produceHearName;

    @Transient
    @ApiModelProperty(value = "* 消费者 昵称")
    private String consumerNickName;

    @Transient
    @ApiModelProperty(value = "* 消费者 头像")
    private String consumerHearName;





    //临时数据
    //@Transient
    @ApiModelProperty(value = "  最后聊天时的时间")
    @Column(columnDefinition = "datetime   comment '最后聊天时的时间'" ,name = "[last_time]")
    private Date lastTime;

    //@Transient
    @ApiModelProperty(value = "  最后聊天时的内容")
    @Column(columnDefinition = "text comment '最后聊天时的内容'" ,name = "[last_text]")
    private String lastText;

    @ApiModelProperty(value = " 1:文字 2:文件 ")
    @Column(columnDefinition = "varchar(5) comment '1:未读 2:已读'" ,name = "[msg_type]")
    private String msgType;

    @Transient
    @ApiModelProperty(value = " 该聊天室未读消息数量 ")
    private Integer winterCicada;





}