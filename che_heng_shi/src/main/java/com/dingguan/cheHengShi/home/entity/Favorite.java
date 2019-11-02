package com.dingguan.cheHengShi.home.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Table(name = "favorite")
public class Favorite {

    @Id
    @Column(name = "favorite_id")
    private Integer favoriteId;

    /**
     * 当前查阅人
     */
    @NotBlank(message = "当前查阅人不能为空")
    @ApiModelProperty(value = "当前查阅人不能为空",required = true)
    @Column(name = "open_id")
    private String openId;

    /**
     * 收藏的类型(file,journalism,video)
     */
    @NotBlank(message = "收藏的类型(1表示视频,2表示文档,3表示新闻,4表示课程)不能为空")
    @ApiModelProperty(value = "收藏的类型(1表示视频,2表示文档,3表示新闻,4表示课程)不能为空",required = true)
    @Column(name = "type")
    private String type;

    /**
     * 文档id，新闻id，视频id
     */
    @NotBlank(message = "文档id，新闻id，视频id，课程id,不能为空")
    @ApiModelProperty(value = "文档id，新闻id，视频id,课程id,不能为空",required = true)
    @Column(name = "id")
    private String id;

    /**
     * 收藏标题
     */
    @ApiModelProperty(value = "收藏标题",required = true)
    @NotBlank(message = "收藏标题不能为空")
    @Column(name = "title")
    private String title;

    /**
     * 具体介绍
     */
    @ApiModelProperty(value = "具体介绍",required = false)
    @Column(name = "introduce")
    private String introduce;

    @ApiModelProperty(value = "banner图",required = true)
    @Column(name = "banner")
    private String banner;

    @ApiModelProperty(value = "价格",required = true)
    @Column(name = "price")
    private BigDecimal price;

    @ApiModelProperty(value = "状态",required = false)
    @Column(name = "status")
    private String status;

    @ApiModelProperty(value = "收藏时间",required = false)
    @Column(name = "time")
    private String time;

}