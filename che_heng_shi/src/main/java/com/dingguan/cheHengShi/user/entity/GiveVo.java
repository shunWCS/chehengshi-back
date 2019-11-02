package com.dingguan.cheHengShi.user.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by zyc on 2018/12/22.
 */

@Data
@ApiModel(description = "赠送积分封装类")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiveVo {


    @NotBlank(message = "* 发起人")
    @ApiModelProperty(value = "* 发起人")
    @Column(columnDefinition = "varchar(255) comment '发起人'" ,name = "[produce]")
    private String produce;

    @NotBlank(message = "* 获利者")
    @ApiModelProperty(value = "* 获利者")
    @Column(columnDefinition = "varchar(255) comment '获利者'" ,name = "[consumer]")
    private String consumer;



    @NotNull
    @ApiModelProperty(value = " * 分数")
    @Column(name = "[fraction]", columnDefinition = "int(11)  comment '分数' ")
    private Integer fraction;


}