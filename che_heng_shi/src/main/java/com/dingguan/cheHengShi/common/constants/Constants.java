package com.dingguan.cheHengShi.common.constants;

/**
 * Created by JackWangon[www.] 2017/7/29.
 */
public class Constants {



    /**自定义返回状态码 start**/
    public static final int RESP_STATUS_OK = 200;

    public static final int RESP_STATUS_NOAUTH = 401;

    public static final int RESP_STATUS_INTERNAL_ERROR = 500;

    public static final int RESP_STATUS_BADREQUEST = 400;




    //用户的剩余积分不足
    public static final int USER_RESIDUAL_INTEGRAL_IS_INSUFFICIENT = 101;
    //用户不存在
    public static final int USER_DOES_NOT_EXIST = 102;
    //支付失败
    public static final int PAY_FAIL = 103;
    //库存不足
    public static final int INSUFFICIENT_INVENTORY= 104;

    //兑换成功
    public static final int EXCHANGE_SUCCESS = 201;
    //金额为0无需支付
    public static final int NO_PAYMENT_IS_REQUIRED_FOR_A_SUM_OF_ZERO = 202;



    //短信校验码错误
    public static final int SMS_PHONE_CODE_CECHK_FILE=105;
    /**自定义状态码 end**/

    public static  final String showPrice = "ALL";





}
