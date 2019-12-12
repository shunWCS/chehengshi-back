package com.dingguan.cheHengShi.product.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Data
@Entity(name = "[course]")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "[poster_banner]")
@ApiModel(description = "轮播图表")
public class PosterBanner {

    @Id
    @Column(columnDefinition = "varchar(30) comment '轮播图表主键'")
    private String id;


    @NotBlank(message = "轮播banner图不能为空")
    @ApiModelProperty(value = "轮播banner图")
    @Column(columnDefinition = "varchar(255) comment '轮播banner图'" ,name = "[banner]")
    private String banner;


    @ApiModelProperty(value = "标题")
    @Column(columnDefinition = "varchar(255) comment '标题'" ,name = "[title]")
    private String title;

    @ApiModelProperty(value = "关联ID")
    @Column(columnDefinition = "varchar(30) comment '关联ID'" ,name = "[ref_id]")
    private String refId;

    @NotBlank(message = "轮播类型不能为空")
    @ApiModelProperty(value = "轮播类型英文")
    @Column(columnDefinition = "varchar(30) comment '轮播类型'" ,name = "[type_value]")
    private String typeValue;

    @ApiModelProperty(value = "创建时间")
    @Column(columnDefinition = "datetime   comment '创建时间'" ,name = "[create_time]")
    private String createTime;

    @ApiModelProperty(value = "备注说明")
    @Column(columnDefinition = "varchar(1000)   comment '备注说明'" ,name = "[remark]")
    private String remark;

    @ApiModelProperty(value = "修改时间")
    @Column(columnDefinition = "datetime   comment '修改时间'" ,name = "[edit_time]")
    private String editTime;

    @ApiModelProperty(value = "是否为首页轮播图1是，0否")
    @Column(columnDefinition = "varchar(2)   comment '是否为首页轮播图1是，0否'" ,name = "[is_first]")
    private String isFirst;

    @ApiModelProperty(value = "轮播类型中文")
    @Transient
    private String typeName;
}
