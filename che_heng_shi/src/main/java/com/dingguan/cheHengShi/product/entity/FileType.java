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

/**
 * Created by zyc on 2018/12/20.
 */

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "file_type")
@ApiModel(description = "文档类型")
public class FileType {

    @Id
    @Column(columnDefinition = "varchar(30) comment '文档类型 表'")
    private String id;

    @NotBlank(message = "* 文档 类型名")
    @ApiModelProperty(value = "* 文档 类型名")
    @Column(columnDefinition = "varchar(255) comment '文档 类型名'" ,name = "[type_name]")
    private String typeName;

    @ApiModelProperty(value = "排序字段")
    @Column(columnDefinition = "int(11)   comment '排序字段'" ,name = "[sort]")
    private Integer sort;

    @ApiModelProperty(value = "logo 图标")
    @Column(columnDefinition = "varchar(255) comment 'logo 图标'" ,name = "[banner]")
    private String banner;

}