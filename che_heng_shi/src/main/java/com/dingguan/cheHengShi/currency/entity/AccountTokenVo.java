package com.dingguan.cheHengShi.currency.entity;


import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * Created by zyc on 2018/11/19.
 */

@Builder
@Data
public class AccountTokenVo {



    private String accountToken;
    private Date  createTime;


    public AccountTokenVo(String accountToken, Date createTime) {
        this.accountToken = accountToken;
        this.createTime = createTime;
    }

    public AccountTokenVo() {
    }
}