package com.dingguan.cheHengShi.user.entity;

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
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zyc on 2018/12/20.
 */
@Data
@Entity
@ApiModel(description = "浏览记录")
@Table(name = "browse_record")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrowseRecord {

    @Id
    @Column(name = "[id]", columnDefinition = "varchar(50)  comment '浏览记录表' ")
    private String id;

    @NotBlank(message = "*  id ")
    @ApiModelProperty(value = "*  浏览人 ")
    @Column(name = "[open_id]", columnDefinition = "varchar(50)  comment '浏览人' ")
    private String openId;

    /**
     * 1:产品 2:视频 3:文档 4:积分产品
     */
    @NotBlank(message = "* product_type ")
    @ApiModelProperty(value = "*  1:产品 2:视频 3:文档 4:积分产品 ")
    @Column(name = "[product_type]", columnDefinition = "varchar(50)  comment '1:产品 2:视频 3:文档 4:积分产品' ")
    private String productType;

    @ApiModelProperty(value = "*  1:产品 2:视频 3:文档 4:积分产品 ")
    @Column(name = "[time]", columnDefinition = "datetime  comment '1:产品 2:视频 3:文档 4:积分产品' ")
    private Date time;


    //冗余

    @ApiModelProperty(value = "*  产品id")
    @Column(columnDefinition = "varchar(255) comment ' id'", name = "[product_id]")
    private String productId;

    @ApiModelProperty(value = "* banner图")
    @Column(columnDefinition = "varchar(255) comment 'banner图'", name = "[banner]")
    private String banner;

    @ApiModelProperty(value = "* 标题")
    @Column(columnDefinition = "varchar(255) comment '标题'", name = "[title]")
    private String Title;


    @ApiModelProperty(value = "*  简介")
    @Column(columnDefinition = "varchar(255) comment ' 简介'", name = "[introduce]")
    private String introduce;


    //冗余_实时字段啊
    @ApiModelProperty(value = "* 价格 ")
    @Column(columnDefinition = "decimal(11,2) comment '价格'")
    private BigDecimal price;

    @ApiModelProperty(value = "点击量")
    @Column(columnDefinition = "int(11)   comment '点击量'", name = "[clicks]")
    private Integer clicks;


    @ApiModelProperty(value = "销量")
    @Column(columnDefinition = "int comment '销量'", name = "[sales]")
    private Integer sales;


}