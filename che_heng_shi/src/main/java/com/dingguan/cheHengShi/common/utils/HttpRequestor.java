package com.dingguan.cheHengShi.common.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestor {

    private static String charset = "utf-8";
    private Integer connectTimeout = null;
    private Integer socketTimeout = null;
    private static String proxyHost = null;
    private static Integer proxyPort = null;


    //inviation  携带的参数
    public static String getWxQRCode(String token, String param, String filePathPrefix) throws IOException {

        URL url = new URL("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + token);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");// 提交模式
        // conn.setConnectTimeout(10000);//连接超时 单位毫秒
        // conn.setReadTimeout(2000);//读取超时 单位毫秒
        // 发送POST请求必须设置如下两行
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        // 获取URLConnection对象对应的输出流

        try (PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());) {
            // 发送请求参数
            JSONObject paramJson = new JSONObject();
            paramJson.put("scene", param);
            //todo 别人扫二维跳到那个页面后面可能需要修改
            paramJson.put("page", "pages/index/index");
            paramJson.put("width", 800);
            paramJson.put("is_hyaline", true);
            paramJson.put("auto_color", true);


            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // 开始获取数据
        File file = new File(filePathPrefix);
        if (!file.exists()) {
            file.mkdirs();
        }
        String path = filePathPrefix + param + ".png";
        try (BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
             OutputStream os = new FileOutputStream(new File(path));) {
            int len;
            byte[] arr = new byte[1024];
            while ((len = bis.read(arr)) != -1) {
                os.write(arr, 0, len);
                os.flush();
            }
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return path;






    }
}
