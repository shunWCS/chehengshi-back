package com.dingguan.cheHengShi.home.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: czh
 * @Date: 2019/9/6 6:42
 */
@Data
@ApiModel("首页列表返回类")
public class HomePage {
    @ApiModelProperty(value = "类型，1表示视频，2表示文档，3表示新闻")
    private String type;
    @ApiModelProperty(value = "类型中文")
    private String typeName;
    @ApiModelProperty(value = "热门的")
    private String hot;
    @ApiModelProperty(value = "最新的")
    private String news;
    @ApiModelProperty(value = "主键Id")
    private String id;
    @ApiModelProperty(value = "banner图")
    private String banner;
    @ApiModelProperty(value = "点击量")
    private Integer clicks;
    @ApiModelProperty(value = "内容介绍")
    private String introduce;
    @ApiModelProperty(value = "排序字段")
    private Integer sort;
    @ApiModelProperty(value = "发布时间")
    private String time;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "是否置顶 1:不是  2:是")
    private String recommend;
    @ApiModelProperty(value = "视频地址")
    private String video;
    @ApiModelProperty(value = "是否收费")
    private String isPrice;
    @ApiModelProperty(value = "是否TOP")
    private String top;
    @ApiModelProperty(value = "收藏数量")
    private Integer favorite;
    @ApiModelProperty(value = "价格")
    private BigDecimal price;
}
