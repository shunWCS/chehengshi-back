package com.dingguan.cheHengShi.home.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author: czh
 * @Date: 2019/9/22 12:36
 */
@Data
@Table(name = "course_apply")
public class CourseApply {

    @Id
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "当前查阅人不能为空")
    @ApiModelProperty(value = "当前查阅人不能为空",required = true)
    @Column(name = "open_id")
    private String openId;

    @NotBlank(message = "课程id不能为空")
    @ApiModelProperty(value = "课程id不能为空",required = true)
    @Column(name = "course_id")
    private String courseId;

    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value = "姓名",required = true)
    @Column(name = "apply_name")
    private String applyName;

    @NotBlank(message = "电话号码不能为空")
    @ApiModelProperty(value = "电话",required = true)
    @Column(name = "phone")
    private String phone;

    @ApiModelProperty(value = "备注信息",required = true)
    @Column(name = "remark")
    private String remark;

    @ApiModelProperty(value = "报名申请时间",required = false)
    @Column(name = "create_time")
    private String createTime;

    @ApiModelProperty(value = "课程标题(名称)")
    @Transient
    private String courseName;
}
