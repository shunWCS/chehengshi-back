package com.dingguan.cheHengShi.home.dto;

import com.dingguan.cheHengShi.common.constants.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: czh
 * @Date: 2019/9/6 20:03
 */
@Data
public class CommonVo extends BaseVo {

    public String recommend = "2";
    @ApiModelProperty(value = "点击量超过多少才算热门让该行显示hot",required = false)
    public Integer clicks = 100;
}
