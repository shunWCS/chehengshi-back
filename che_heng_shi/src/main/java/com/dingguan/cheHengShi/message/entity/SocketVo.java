package com.dingguan.cheHengShi.message.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

/**
 * Created by zyc on 2018/12/21.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SocketVo {

    @ApiModelProperty(value = "" +
            "1:初次订阅时传openId 获取聊天室/列表分页数据这里必须做分页   " +
            " 2:错误请求  3:初次订阅时返回消息列表     4发送消息 5:接受消息 ")
    private String type;


    private String openId;

    private String message;


   /* @ApiModelProperty(value = " 1:文字 2:文件 ")
     private String msgType;*/


    @ApiModelProperty(value = " 用户总未读数量 ")
    private Integer winterCicada;


    private Integer pageIndex;
    private Integer pageSize;




    private List<ChatRoom> chatRoomList;

    @ApiModelProperty(value = " 总数量量  ")
    private Long totalElements ;
    @ApiModelProperty(value = " 每页size数  ")
    private Integer size ;
    @ApiModelProperty(value = " 页面数 从0开始  当前是第n页  ")
    private Integer number ;



    private Msg msg;



}