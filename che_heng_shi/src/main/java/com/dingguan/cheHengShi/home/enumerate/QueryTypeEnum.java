package com.dingguan.cheHengShi.home.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: czh
 * @Date: 2019/9/7 9:31
 */
@Getter
@AllArgsConstructor
public enum QueryTypeEnum implements BaseEnum{
    FILE("file", "文档"),
    VIDEO("video", "视频"),
    JOURNALISM("journalism", "新闻"),
    COURSE("course", "课程");
    private String code;
    private String message;
}
