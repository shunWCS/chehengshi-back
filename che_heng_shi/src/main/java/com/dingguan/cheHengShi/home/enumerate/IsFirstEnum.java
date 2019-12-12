package com.dingguan.cheHengShi.home.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IsFirstEnum implements BaseEnum {
    YES("1", "是"),
    NO("0", "否");
    private String code;
    private String message;
}
