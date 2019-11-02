package com.dingguan.cheHengShi.material.entity;

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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by zyc on 2018/12/20.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "journalism")
@ApiModel(description = "新闻")
public class Journalism {

    @Id
    @Column(columnDefinition = "varchar(30) comment '新闻 表'")
    private String id;

    @NotBlank(message = "* 新闻banner图")
    @ApiModelProperty(value = "* 新闻banner图")
    @Column(columnDefinition = "varchar(255) comment '新闻banner图'" ,name = "[banner]")
    private String banner;

    @NotBlank(message = "* 新闻 标题")
    @ApiModelProperty(value = "* 新闻 标题")
    @Column(columnDefinition = "varchar(255) comment '新闻 标题'" ,name = "[title]")
    private String title;

    @NotBlank(message = "* 新闻 内容")
    @ApiModelProperty(value = "* 新闻 内容")
    @Column(columnDefinition = "text comment '新闻 内容'" ,name = "[text]")
    private String text;


    @ApiModelProperty(value = "* 新闻 简介")
    @Column(columnDefinition = "varchar(255) comment '新闻 简介'" ,name = "[introduce]")
    private String introduce;


     @ApiModelProperty(value = "  新闻 发布时间")
    @Column(columnDefinition = "datetime   comment '新闻 发布时间'" ,name = "[time]")
    private Date time;

    @ApiModelProperty(value = "排序字段")
    @Column(columnDefinition = "int(11)   comment '排序字段'" ,name = "[sort]")
    private Integer sort;

    @ApiModelProperty(value = "点击量")
    @Column(columnDefinition = "int(11)   comment '点击量'" ,name = "[clicks]")
    private Integer clicks;

    @ApiModelProperty(value = "  是否推荐  1:不是  2:是    ")
    @Column(columnDefinition = "varchar(5) comment ' 是否推荐 1:不是  2:是'",name = "[recommend]")
    private String recommend;

    @ApiModelProperty(value = "  收藏数量    ")
    @Column(columnDefinition = "int(11)   comment '收藏数量'" ,name = "[favorite]")
    private Integer favorite;

    @ApiModelProperty(value = "  是否课程新闻 1否，2是    ")
    @Column(columnDefinition = "varchar(5)   comment '是否课程新闻 1否，2是'" ,name = "[course]")
    private String course;

    @ApiModelProperty(value = "新闻发布时间")
    @Transient
    private String beginTime;

    @ApiModelProperty(value = "是否已经收藏，1已收藏，0没收藏")
    @Transient
    private Integer isFavorite;

    @ApiModelProperty(value = "3表示新闻")
    @Transient
    private String type = "3";
}