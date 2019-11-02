package com.dingguan.cheHengShi.home.dto;

import com.dingguan.cheHengShi.common.constants.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: czh
 * @Date: 2019/9/7 8:54
 */
@Data
public class SearchVo extends BaseVo {

    @ApiModelProperty(value = "搜索类型",required = false)
    private String type;
    @ApiModelProperty(value = "用户输入的条件模糊查询",required = false)
    private String title;

    public String recommend = "2";
    @ApiModelProperty(value = "点击量超过多少才算热门让该行显示HOT",required = false)
    public Integer clicks=100;
}
