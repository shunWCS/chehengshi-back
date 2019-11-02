package com.dingguan.cheHengShi.currency.service;



import com.dingguan.cheHengShi.common.utils.OpenIdUtils;
import com.dingguan.cheHengShi.currency.entity.AccountTokenVo;

import java.io.IOException;
import java.util.Date;

/**
 * Created by zyc on 2018/11/19.
 */

public class AccountTokenService {






    protected static AccountTokenVo accountTokenVo;


    public static AccountTokenVo getAccountTokenVo(String appid ,String appSecret) throws IOException {
        if(accountTokenVo==null){
            String samllToken = OpenIdUtils.getSamllToken(appid, appSecret);
            accountTokenVo =AccountTokenVo.builder()
                    .accountToken(samllToken)
                    .createTime(new Date())
                    .build();
         }else {

            long timeDifference= new Date().getTime() - accountTokenVo.getCreateTime().getTime();
            //2个小时过期
            int i = 1000 * 60 * 60 * 2 - 10000;
            if(timeDifference>i){
                //过期了
                String samllToken = OpenIdUtils.getSamllToken(appid, appSecret);
                accountTokenVo =AccountTokenVo.builder()
                        .accountToken(samllToken)
                        .createTime(new Date())
                        .build();
             }
        }
        return accountTokenVo;
    }




}