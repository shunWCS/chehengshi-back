package com.dingguan.cheHengShi.home.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: czh
 * @Date: 2019/9/7 8:34
 */
@ApiModel("查询类型")
@Data
public class QueryType {
    @ApiModelProperty(value = "类型英文名")
    private String type;
    @ApiModelProperty(value = "类型中文名")
    private String typeName;
}
