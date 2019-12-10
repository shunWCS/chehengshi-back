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

    @NotBlank(message = "轮播类型ID不能为空")
    @ApiModelProperty(value = "轮播类型ID")
    @Column(columnDefinition = "varchar(30) comment '轮播类型ID'" ,name = "[type_value]")
    private String typeValue;

    @ApiModelProperty(value = "创建时间")
    @Column(columnDefinition = "datetime   comment '创建时间'" ,name = "[create_time]")
    private String createTime;

    @ApiModelProperty(value = "备注说明")
    @Column(columnDefinition = "varchar(1000)   comment '备注说明'" ,name = "[remark]")
    private String remark;

}