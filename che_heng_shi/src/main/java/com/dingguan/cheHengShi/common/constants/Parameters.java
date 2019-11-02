package com.dingguan.cheHengShi.common.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by zyc on 2018/4/27.
 *
 * 这里面的值只能通过注入对象然后获取的
 *
 */

@Component
public class Parameters {


    @Value("#{T(java.lang.String).valueOf('${wechatPay.appid}')}")
    public  String appid;
    @Value("#{T(java.lang.String).valueOf('${wechatPay.mch_id}')}")
    public  String mch_id;
    @Value("#{T(java.lang.String).valueOf('${wechatPay.key}')}")
    public String key;
    @Value("#{T(java.lang.String).valueOf('${wechatPay.notify_url}')}")
    public String notify_url;
    @Value("#{T(java.lang.String).valueOf('${wechatPay.SIGNTYPE}')}")
    public String SIGNTYPE;
    @Value("#{T(java.lang.String).valueOf('${wechatPay.TRADETYPE}')}")
    public String TRADETYPE;
    @Value("#{T(java.lang.String).valueOf('${wechatPay.pay_url}')}")
    public String pay_url;
    @Value("#{T(java.lang.String).valueOf('${wechatPay.appsecret}')}")
    public String appsecret;
    @Value("#{T(java.lang.String).valueOf('${wechatPay.filePathPrefix}')}")
    public String filePathPrefix;
    @Value("#{T(java.lang.String).valueOf('${wechatPay.refund_url}')}")
    public String refund_url;


}