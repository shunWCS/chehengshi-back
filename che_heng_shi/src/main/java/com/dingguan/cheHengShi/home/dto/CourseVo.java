package com.dingguan.cheHengShi.home.dto;

import com.dingguan.cheHengShi.common.constants.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: czh
 * @Date: 2019/9/22 22:31
 */
@Data
public class CourseVo extends BaseVo {
    @ApiModelProperty(value = "输入的名称关键字模糊查询",required = false)
    private String title;
    @ApiModelProperty(value = "课程类型",required = false)
    private String typeId;
}
