package com.dingguan.cheHengShi.home.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @author: czh
 * @Date: 2019/9/29 22:31
 */
@Data
@Table(name = "share_video")
public class ShareVideo {

    @Id
    @Column(name = "id")
    private String id;

    @NotBlank(message = "当前查阅人不能为空")
    @ApiModelProperty(value = "当前查阅人不能为空",required = true)
    @Column(name = "open_id")
    private String openId;


    @ApiModelProperty(value = "视频banner图",required = false)
    @Column(name = "banner")
    private String banner;

    @NotBlank(message = "视频标题不能为空")
    @ApiModelProperty(value = "视频 标题",required = true)
    @Column(name = "title")
    private String title;

    @NotBlank(message = "视频连接(百度云盘地址)不能为空 ")
    @ApiModelProperty(value = "视频连接(百度云盘地址) ",required = true)
    @Column(name = "video_url")
    private String videoUrl;

    @ApiModelProperty(value = "视频地址",required = false)
    @Column(name = "video")
    private String video;

    @ApiModelProperty(value = " 正文内容",required = true)
    @Column(name = "introduce")
    private String introduce;

    @NotBlank(message = "发布人不能为空")
    @ApiModelProperty(value = " 发布人",required = true)
    @Column(name = "name")
    private String name;

    @NotBlank(message = "联系方式不能为空")
    @ApiModelProperty(value = " 联系方式",required = true)
    @Column(name = "phone")
    private String phone;

    @NotBlank(message = "备注不能为空")
    @ApiModelProperty(value = "备注",required = true)
    @Column(name = "remark")
    private String remark;


    @ApiModelProperty(value = "视频 发布时间",required = false)
    @Column(name = "time")
    private String time;

    @ApiModelProperty(value = "审核备注(在审核前这个值为空)",required = false)
    @Column(name = "apply_remark")
    private String applyRemark;

    @ApiModelProperty(value = "状态(0待审核，1已审核)",required = false)
    @Column(name = "state")
    private String state;

    @ApiModelProperty(value = "审核状态中文名",required = false)
    @Transient
    private String stateName;

}
