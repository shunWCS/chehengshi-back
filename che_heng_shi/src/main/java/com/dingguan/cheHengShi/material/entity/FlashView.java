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

/**
 * Created by zyc on 2018/11/22.
 */

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "flash_view")
@ApiModel(description = "轮播图")
public class FlashView {
    @Id
    @Column(columnDefinition = "varchar(30) comment '轮播图 表'")
    private String id;

    @NotBlank()
    @ApiModelProperty(value = "*轮播图图片")
    @Column(columnDefinition = "varchar(255) comment '轮播图的图片'",name = "[banner]")
    private String banner;

    @ApiModelProperty(value = "排序字段")
    @Column(columnDefinition = "int(11) comment '排序字段' ",name = "[sort]")
    private Integer sort;





}