package com.dingguan.cheHengShi.home.dto;

import com.dingguan.cheHengShi.common.constants.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: czh
 * @Date: 2019/9/15 0:10
 */
@Data
public class FileSearchVo extends BaseVo {

    @ApiModelProperty(value = "用户输入的条件模糊查询",required = false)
    private String title;
    @ApiModelProperty(value = "点击量超过多少才算热门让该行显示hot",required = false)
    public Integer clicks=100;
    @ApiModelProperty(value = "文档类型id、视频类型id",required = false)
    public String typeId;
}
