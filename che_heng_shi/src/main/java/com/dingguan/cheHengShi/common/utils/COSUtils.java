package com.dingguan.cheHengShi.common.utils;


import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;

import java.io.File;

/**
 * Created by     IntelliJ IDEA
 *
 * @author :      ShaoXiangDong
 *         Date         :       2018/1/16
 *         Version      :       1.0
 *
 *         腾讯云上传工具类
 */
public class COSUtils {


    private final static String ACCESS_KEY_ID = "AKID3TvaJ2WVG2pcPidZ0wvtrqolifbWbKdz";
    private final static String ACCESS_KEY_SECRET = "rDe5VvLk9YwQxTyTzNZZUFHHAtQnzylM";

    private final static String REGION = "ap-guangzhou";
    private final static String PREFIX = "https://dingguan-1257007615.cos.ap-guangzhou.myqcloud.com/";

    //成都的存储空间
    //private final static String REGION = "ap-chengdu";
    //private final static String PREFIX = "https://zhaoyanei-1257007615.cos.ap-chengdu.myqcloud.com/";

    public static String simpleUploadFileFromLocal(String bucketName, String path, String folder, String suffix) {
        // 设置bucket所在的区域，比如广州(gz), 天津(tj)
        Region region = new Region(REGION);
        // 初始化客户端配置
        ClientConfig clientConfig = new ClientConfig(region);
        // 初始化秘钥信息
        COSCredentials cred = new BasicCOSCredentials(ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        // 初始化cosClient
        COSClient cosClient = new COSClient(cred, clientConfig);
        File file = new File(path);
        String cosFilePath = folder + "/" + System.currentTimeMillis() + suffix;
        PutObjectRequest putObjectRequest =
                new PutObjectRequest(bucketName, cosFilePath, file);
        try {
            cosClient.putObject(putObjectRequest);
            String result = PREFIX + cosFilePath;
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            cosClient.shutdown();
        }
    }


    public static void main(String[] args) {
        String path = "C:\\Users\\pc\\Desktop\\wechatPayProcess.png";

        /*File file = new File("C:\\Users\\sxd\\Desktop\\wechatPayProcess.png");*/

        String result = COSUtils.simpleUploadFileFromLocal("zhongmeili-1255690361", path, "sudan", ".png");
        System.out.println("--------------->" + result);

    }
}
