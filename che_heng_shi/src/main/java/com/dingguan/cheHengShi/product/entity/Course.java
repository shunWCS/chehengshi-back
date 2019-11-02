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


/**
 * @author: czh
 * @Date: 2019/9/16 19:29
 */
@Data
@Entity(name = "[course]")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "[course]")
@ApiModel(description = "课程表")
public class Course {

    @Id
    @Column(columnDefinition = "varchar(30) comment '课程 表'")
    private String id;


    @NotBlank(message = "课程banner图不能为空")
    @ApiModelProperty(value = "课程banner图")
    @Column(columnDefinition = "varchar(255) comment '课程banner图'" ,name = "[banner]")
    private String banner;

    @NotBlank(message = "课程标题不能为空")
    @ApiModelProperty(value = "课程标题")
    @Column(columnDefinition = "varchar(255) comment '课程 标题'" ,name = "[title]")
    private String title;

    @NotBlank(message = "课程简介不能为空")
    @ApiModelProperty(value = "课程简介")
    @Column(columnDefinition = "varchar(255) comment '课程 简介'" ,name = "[introduce]")
    private String introduce;


    @ApiModelProperty(value = "  课程开始时间")
    @Column(columnDefinition = "datetime   comment '课程 开始时间'" ,name = "[begin_time]")
    private String beginTime;

    @ApiModelProperty(value = "  课程 结束时间")
    @Column(columnDefinition = "datetime   comment '课程 开始时间'" ,name = "[end_time]")
    private String endTime;

    @NotNull(message = "价格不能为空")
    @ApiModelProperty(value = "价格 ")
    @Column(columnDefinition = "decimal(11,2) comment '价格'",name = "[price]" )
    private BigDecimal price;

    @ApiModelProperty(value = "点击量")
    @Column(columnDefinition = "int(11)   comment '点击量'" ,name = "[clicks]")
    private Integer clicks;

    @ApiModelProperty(value = "排序字段")
    @Column(columnDefinition = "int(11)   comment '排序字段'" ,name = "[sort]")
    private Integer sort;

    @NotBlank(message = "课程类型ID不能为空")
    @ApiModelProperty(value = "课程类型ID")
    @Column(columnDefinition = "varchar(255) comment '课程类型ID'" ,name = "[type_id]")
    private String typeId;



    @ApiModelProperty(value = "  收藏数量    ")
    @Column(columnDefinition = "int(11)   comment '收藏数量'" ,name = "[favorite]")
    private Integer favorite;

    @ApiModelProperty(value = "培训地址")
    @Column(columnDefinition = "varchar(255)  comment '培训地址'" ,name = "[address]")
    private String address;

    @ApiModelProperty(value = "联系人")
    @Column(columnDefinition = "varchar(50)  comment '联系人'" ,name = "[person]")
    private String person;

    @ApiModelProperty(value = "联系电话")
    @Column(columnDefinition = "varchar(30)  comment '联系电话'" ,name = "[phone]")
    private String phone;

    @ApiModelProperty(value = "备注信息")
    @Column(columnDefinition = "varchar(255)  comment '备注信息'" ,name = "[remark]")
    private String remark;

    @ApiModelProperty(value = "报名人数")
    @Column(columnDefinition = "int(11)  comment '报名人数'" ,name = "[apply_count]")
    private Integer applyCount;

    @ApiModelProperty(value = "报名状态")
    @Transient
    private String state;

    @ApiModelProperty(value = "根据报名状态进行排序，2报名中，1报名截止")
    @Transient
    private Integer stateNo;

    @ApiModelProperty(value = "是否已经报名，YES已报名，NO未报名")
    @Transient
    private String isApply;

    @ApiModelProperty(value = "是否已经收藏，1已收藏，0没收藏")
    @Transient
    private Integer isFavorite;

    @ApiModelProperty(value = "4表示课程")
    @Transient
    private String type = "4";
}
