package com.dingguan.cheHengShi.common.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by     IntelliJ IDEA
 *
 * @author :      ShaoXiangDong
 * Date         :       2018/1/19
 * Version      :       1.0
 * Company      :       众美力
 */
public class OpenIdUtils {
    public static String oauth2GetOpenid(String code,String appid,String appsecret) {

        //授权（必填）
        String grant_type = "authorization_code";
        //URL
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
        //请求参数
        String params = "appid=" + appid + "&secret=" + appsecret + "&js_code=" + code + "&grant_type=" + grant_type;
        //发送请求
        String data = HttpUtils.get(requestUrl, params);
        //解析相应内容（转换成json对象）
        JSONObject json = JSON.parseObject(data);
        //JSONObject json = JSONObject.fromObject(data);
        //用户的唯一标识（openid）
        String Openid =String.valueOf(json.get("openid"));
        //System.out.println(Openid);
        return Openid;
    }









    /**
     * 获取 小程序的 access_token
     * 2个小时过期
     *
     */
    public static String getSamllToken(String appid,String appSecret) throws
            IOException {
        HttpGet httpGet = new HttpGet(
                "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                        + appid + "&secret="
                        + appSecret );
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse res = httpClient.execute(httpGet);
        HttpEntity entity = res.getEntity();
        String result = EntityUtils.toString(entity, "UTF-8");
        net.sf.json.JSONObject jsons = net.sf.json.JSONObject.fromObject(result);
        System.out.println(jsons);
        String expires_in = jsons.getString("expires_in");

        if(Integer.parseInt(expires_in)>7000){
            //ok
            String access_token = jsons.getString("access_token");
            return access_token;
        }else{
            System.out.println("出错获取token失败！");
            return null;
        }
    }

}
