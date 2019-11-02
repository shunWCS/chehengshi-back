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
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zyc on 2018/12/1.
 */

@Data
@Entity
@Table(name = "material")
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Material {




    @Id
    @Column(columnDefinition = "varchar(30) comment '素材 表'")
    private String id;


    @ApiModelProperty(value = " 图片")
    @Column(columnDefinition = "varchar(2333) comment '图片'",name = "[img]")
    private String img;

    @ApiModelProperty(value = "排序字段")
    @Column(columnDefinition = "int(11) comment '排序字段' ",name = "[sort]")
    private Integer sort;



    @NotBlank(message = "*类型")
    @ApiModelProperty(value = "*类型  1:每消费一元能够获得多少积分   2:单次购买视频/文档能看多少时间  ")
    @Column(columnDefinition = "varchar(5) comment '  '",name = "[type]")
    private String type;

    @ApiModelProperty(value = "type为2时的有效期 单位小时")
    @Column(columnDefinition = "int(11) comment '有效期' ",name = "[hour]")
    private Integer hour;

    @ApiModelProperty(value = "视频")
    @Column(columnDefinition = "varchar(111) comment '视频' ",name = "[video]")
    private String video;

    @ApiModelProperty(value = "文字/文本")
    @Column(columnDefinition = "text  comment '文字/文本' ",name = "[text]")
    private String text;

    @ApiModelProperty(value = "创建时间")
    @Column(columnDefinition = "datetime  comment '创建时间' ",name = "[time]")
    private Date time;


    @ApiModelProperty(value = "type 为1时 特有字段  用户每消费一元能得到多少积分  积分向上取整数 ")
    @Column(columnDefinition = "decimal(11,2)  comment ' ' ",name = "[rate]")
    private BigDecimal rate;


}