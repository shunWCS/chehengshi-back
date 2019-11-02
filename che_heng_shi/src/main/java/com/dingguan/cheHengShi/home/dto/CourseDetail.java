package com.dingguan.cheHengShi.home.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: czh
 * @Date: 2019/10/13 21:09
 */
@Data
@ApiModel("课程详情返回数据")
public class CourseDetail {
    @ApiModelProperty(value = "课程Id")
    private String id;


    @ApiModelProperty(value = "课程banner图")
    private String banner;


    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程简介")
    private String introduce;


    @ApiModelProperty(value = "  课程开始时间")
    private String beginTime;

    @ApiModelProperty(value = "  课程 结束时间")
    private String endTime;

    @ApiModelProperty(value = "价格 ")
    private BigDecimal price;

    @ApiModelProperty(value = "点击量")
    private Integer clicks;

    @ApiModelProperty(value = "排序字段")
    private Integer sort;

    @ApiModelProperty(value = "课程类型ID")
    private String typeId;



    @ApiModelProperty(value = "  收藏数量    ")
    private Integer favorite;

    @ApiModelProperty(value = "培训地址")
    private String address;

    @ApiModelProperty(value = "联系人")
    private String person;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "备注信息")
    private String remark;

    @ApiModelProperty(value = "报名人数")
    private Integer applyCount;

    @ApiModelProperty(value = "报名状态")
    private String state;

    @ApiModelProperty(value = "根据报名状态进行排序，2报名中，1报名截止")
    private Integer stateNo;
}
