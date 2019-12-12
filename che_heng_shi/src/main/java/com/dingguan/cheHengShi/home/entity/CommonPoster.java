package com.dingguan.cheHengShi.home.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "轮播图设置列表")
public class CommonPoster {
    @ApiModelProperty(value = "主键ID")
    private String id;
    @ApiModelProperty(value = "关联编号")
    private String refId;
    @ApiModelProperty(value = "中文类型")
    private String typeName;
    @ApiModelProperty(value = "英文类型")
    private String typeValue;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "修改时间")
    private String editTime;
    @ApiModelProperty(value = "是否为首页轮播图 1是，0否")
    private String isFirst;
    @ApiModelProperty(value = "轮播图地址")
    private String banner;
}
