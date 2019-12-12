package com.dingguan.cheHengShi.home.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BannerTypeEnum implements BaseEnum {

    /**
     *
     */
    VIDEO("video", "视频"),
    FILE("file", "资料"),
    JOURNALISM("journalism", "新闻"),
    MAIL_ACCOUNT("course", "课程"),
    STORE("store", "商城");
    private String code;
    private String message;
}
