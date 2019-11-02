package com.dingguan.cheHengShi.home.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: czh
 * @Date: 2019/10/13 14:42
 */
@Data
@ApiModel("详情返回数据")
public class CommonDetail {

    private String id;


    @ApiModelProperty(value = "* 新闻banner图")
    private String banner;


    @ApiModelProperty(value = "* 新闻 标题")
    private String title;

    @ApiModelProperty(value = "* 新闻 内容")
    private String text;


    @ApiModelProperty(value = "* 新闻 简介")
    private String introduce;


    @ApiModelProperty(value = "新闻发布时间")
    private String time;

    @ApiModelProperty(value = "排序字段")
    private Integer sort;

    @ApiModelProperty(value = "点击量")
    private Integer clicks;

    @ApiModelProperty(value = "  是否推荐  1:不是  2:是    ")
    private String recommend;

    @ApiModelProperty(value = "  收藏数量    ")
    private Integer favorite;
}
