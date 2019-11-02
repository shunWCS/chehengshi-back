package com.dingguan.cheHengShi.material.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by zyc on 2019/4/7.
 */

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "membership_viedo")
@ApiModel(description = "会员能够看的视频")
public class MembershipViedo {

    @Id
    @Column(name = "[id]",columnDefinition = "varchar(50)  comment '会员能够看的视频表' ")
    private String id;

    @ApiModelProperty(value = "  视频id")
    @Column(columnDefinition = "varchar(50) comment '视频id'",name = "[video_id]")
    private String videoId;

    @ApiModelProperty(value = "  openId")
    @Column(columnDefinition = "varchar(50) comment 'openId'",name = "[open_id]")
    private String openId;


    @ApiModelProperty(value = "  状态1:正常使用 2:软删除")
    @Column(columnDefinition = "varchar(50) comment '状态1'",name = "[status]")
    private String status;



}