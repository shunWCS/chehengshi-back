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

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "poster_type")
@ApiModel(description = "课程类型")
public class PosterType {

    @Id
    @Column(columnDefinition = "varchar(30) comment '轮播类型表'")
    private String id;

    @NotBlank(message = "轮播类型名")
    @ApiModelProperty(value = "轮播类型名")
    @Column(columnDefinition = "varchar(255) comment '轮播类型名'" ,name = "[type_name]")
    private String typeName;

    @ApiModelProperty(value = "排序字段")
    @Column(columnDefinition = "int(11)   comment '排序字段'" ,name = "[sort]")
    private Integer sort;

    @NotBlank(message = "轮播类型值")
    @ApiModelProperty(value = "轮播类型值")
    @Column(columnDefinition = "varchar(255) comment '轮播类型值'" ,name = "[type_value]")
    private String typeValue;
}
