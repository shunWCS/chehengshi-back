package com.dingguan.cheHengShi.common.utils;

/**
 * Created by zyc on 2018/7/23.
 */
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SmsSample {



    public static void main(String[] args) {

        String testUsername = "13530960821"; //在短信宝注册的用户名
        String testPassword = "13530960821"; //在短信宝注册的密码
        String testPhone = "13006185120";//15205201314,15205201315  群发用逗号隔开 不要超过99个
        String testContent = "【车恒士】您的验证码为123456，请于30分钟内正确输入.如非本人操作,请忽略此短信"; //
        // 注意测试时，也请带上公司简称或网站签名，发送正规内容短信。千万不要发送无意义的内容：例如 测一下、您好。否则可能会收不到

        String httpUrl = "http://api.smsbao.com/sms";

        StringBuffer httpArg = new StringBuffer();
        httpArg.append("u=").append(testUsername).append("&");
        httpArg.append("p=").append(md5(testPassword)).append("&");
        httpArg.append("m=").append(testPhone).append("&");
        httpArg.append("c=").append(encodeUrlString(testContent, "UTF-8"));

        String result = request(httpUrl, httpArg.toString());
        System.out.println(result);
    }

    /**
     *
     *
     *
     *
     */
    public static String sendSMSCheck(String phone,String checkCode ){
        String testUsername = "chehengshi"; //在短信宝注册的用户名
        String testPassword = "chehengshi2019"; //在短信宝注册的密码
        String testPhone = phone;
        String contentTemplate = "【车恒士】您的验证码为%s，请于30分钟内正确输入.如非本人操作,请忽略此短信"; //
        // 注意测试时，也请带上公司简称或网站签名，发送正规内容短信。千万不要发送无意义的内容：例如 测一下、您好。否则可能会收不到
        String format = String.format(contentTemplate, checkCode);
        String httpUrl = "http://api.smsbao.com/sms";

        StringBuffer httpArg = new StringBuffer();
        httpArg.append("u=").append(testUsername).append("&");
        httpArg.append("p=").append(md5(testPassword)).append("&");
        httpArg.append("m=").append(testPhone).append("&");
        httpArg.append("c=").append(encodeUrlString(format, "UTF-8"));

        String result = request(httpUrl, httpArg.toString());
        System.out.println(result);
        return result;
    }


    /**
     * 用户下单了通知哪些
     *
     */
    public static String sendNewOrder(
            String  productPhone,
            String orderPhone,
            String color,
            String phoneMondel,
            String serviceName,
            String name,
            String city,
            String address
            ){
        if(StringUtils.isBlank(color)){
            color="";
        }
        String testUsername = "13530960821";
        String testPassword = "13530960821";
        String testPhone = productPhone;
        String contentTemplate = "【鹿小修】用户%s 联系电话:%s  为%s%s预约了%s服务,地址为%s%s,请及时与客户沟通并处理"; //
        // 注意测试时，也请带上公司简称或网站签名，发送正规内容短信。千万不要发送无意义的内容：例如 测一下、您好。否则可能会收不到
        String format = String.format(contentTemplate,name, orderPhone,color,phoneMondel,serviceName,city,address);
        System.out.println(format);
        String httpUrl = "http://api.smsbao.com/sms";

        StringBuffer httpArg = new StringBuffer();
        httpArg.append("u=").append(testUsername).append("&");
        httpArg.append("p=").append(md5(testPassword)).append("&");
        httpArg.append("m=").append(testPhone).append("&");
        httpArg.append("c=").append(encodeUrlString(format, "UTF-8"));

        String result = request(httpUrl, httpArg.toString());
        System.out.println(result);
        return result;
    }


    public static String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = reader.readLine();
            if (strRead != null) {
                sbf.append(strRead);
                while ((strRead = reader.readLine()) != null) {
                    sbf.append("\n");
                    sbf.append(strRead);
                }
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String md5(String plainText) {
        StringBuffer buf = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }

    public static String encodeUrlString(String str, String charset) {
        String strret = null;
        if (str == null)
            return str;
        try {
            strret = java.net.URLEncoder.encode(str, charset);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return strret;
    }
}