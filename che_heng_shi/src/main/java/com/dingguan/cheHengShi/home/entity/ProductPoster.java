package com.dingguan.cheHengShi.home.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: czh
 * @Date: 2019/9/13 11:13
 */
@Data
@ApiModel(description = "4张海报轮播")
public class ProductPoster {

    @ApiModelProperty(value = "产品id")
    private String id;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "小程序首页的海报轮播图")
    private String bannerT;

    @ApiModelProperty(value = "海报轮播 1:不是  2:是")
    private String poster;

    @ApiModelProperty(value = "上传时间")
    private String time;
}
