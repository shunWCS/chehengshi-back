package com.dingguan.cheHengShi.product.entity;

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
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zyc on 2018/12/20.
 */
@Data
@Entity(name = "[file]")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "[file]")
@ApiModel(description = "文档")
public class File {


    @Id
    @Column(columnDefinition = "varchar(30) comment '文档 表'")
    private String id;


    @NotBlank(message = "* 文档banner图")
    @ApiModelProperty(value = "* 文档banner图")
    @Column(columnDefinition = "varchar(255) comment '文档banner图'" ,name = "[banner]")
    private String banner;

    @NotBlank(message = "* 文档 标题")
    @ApiModelProperty(value = "* 文档 标题")
    @Column(columnDefinition = "varchar(255) comment '文档 标题'" ,name = "[title]")
    private String title;

    @NotBlank(message = "* 文档 简介")
    @ApiModelProperty(value = "* 文档 简介")
    @Column(columnDefinition = "varchar(255) comment '文档 简介'" ,name = "[introduce]")
    private String introduce;

    @NotBlank(message = "* 文档 内容")
    @ApiModelProperty(value = "* 新闻 内容")
    @Column(columnDefinition = "text comment '文档 内容'" ,name = "[text]")
    private String text;

    @ApiModelProperty(value = "  文档 发布时间")
    @Column(columnDefinition = "datetime   comment '文档 发布时间'" ,name = "[time]")
    private Date time;

    @NotNull(message = "* 价格")
    @ApiModelProperty(value = "* 价格 ")
    @Column(columnDefinition = "decimal(11,2) comment '价格'" )
    private BigDecimal price;

    @ApiModelProperty(value = "点击量")
    @Column(columnDefinition = "int(11)   comment '点击量'" ,name = "[clicks]")
    private Integer clicks;

    @ApiModelProperty(value = "排序字段")
    @Column(columnDefinition = "int(11)   comment '排序字段'" ,name = "[sort]")
    private Integer sort;

    @ApiModelProperty(value = "销量")
    @Column(columnDefinition = "int comment '销量'",name = "[sales]")
    private Integer sales;


    @NotBlank(message = "* 文档 类型 id")
    @ApiModelProperty(value = "* 文档 类型 id")
    @Column(columnDefinition = "varchar(255) comment '文档 类型 id'" ,name = "[type_id]")
    private String typeId;

    @ApiModelProperty(value = "  是否推荐  1:不是  2:是    ")
    @Column(columnDefinition = "varchar(5) comment ' 是否推荐 1:不是  2:是'",name = "[recommend]")
    private String recommend;

    @ApiModelProperty(value = "  收藏数量    ")
    @Column(columnDefinition = "int(11)   comment '收藏数量'" ,name = "[favorite]")
    private Integer favorite;

    @ApiModelProperty(value = "文档发布时间")
    @Transient
    private String beginTime;

    @ApiModelProperty(value = "是否已经收藏，1已收藏，0没收藏")
    @Transient
    private Integer isFavorite;

    @ApiModelProperty(value = "2表示文档")
    @Transient
    private String type = "2";

}