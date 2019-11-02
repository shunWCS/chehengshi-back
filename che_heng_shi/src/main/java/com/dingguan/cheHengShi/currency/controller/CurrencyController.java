package com.dingguan.cheHengShi.currency.controller;



import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.dingguan.cheHengShi.common.constants.Parameters;
import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;

import com.dingguan.cheHengShi.common.utils.OpenIdUtils;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.TypeConversionUtils;

import com.dingguan.cheHengShi.material.entity.Journalism;

import com.dingguan.cheHengShi.material.service.FlashViewService;
import com.dingguan.cheHengShi.material.service.JournalismService;
import com.dingguan.cheHengShi.material.service.MaterialService;
import com.dingguan.cheHengShi.material.service.MembershipViedoService;
import com.dingguan.cheHengShi.product.entity.*;
import com.dingguan.cheHengShi.product.entity.File;
import com.dingguan.cheHengShi.product.service.*;

import com.dingguan.cheHengShi.trade.entity.ShopCart;
import com.dingguan.cheHengShi.trade.service.OrderDetailService;
import com.dingguan.cheHengShi.trade.service.OrderService;
import com.dingguan.cheHengShi.trade.service.ShopCartService;
 import com.dingguan.cheHengShi.user.entity.Manufacturer;
import com.dingguan.cheHengShi.user.entity.User;
 import com.dingguan.cheHengShi.user.service.ManufacturerService;
import com.dingguan.cheHengShi.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.text.MessageFormat;
import java.util.*;

/**
 * Created by zyc on 2018/4/13.
 */
@Api(description = "通用接口")
@RestController
@RequestMapping(value = "")
@Slf4j
public class CurrencyController {


    @Autowired
    private FileService fileService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ManufacturerService manufacturerService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private ShopCartService shopCartService;
    @Autowired
    private SkuService skuService;
    @Autowired
    private FileTypeService fileTypeService;
    @Autowired
    private VideoTypeService videoTypeService;
    @Autowired
    private ProductTypeService productTypeService;
    @Autowired
    private MembershipPackageService membershipPackageService;
    @Autowired
    private ProductParameterService productParameterService;
    @Autowired
    private FlashViewService flashViewService;
    @Autowired
    private JournalismService journalismService;


    @Autowired
    private Parameters parameters;


    @ApiOperation(value = "获取openId")
    @PostMapping("/samll/getOpenId/{js_code}")
    public String getOpenId(@PathVariable("js_code") String code) {
        return OpenIdUtils.oauth2GetOpenid(code, parameters.appid, parameters.appsecret);
    }


    @ApiOperation(value = "获取产品详情", notes = "  " +
            "Product:产品详情 \t\n" +
            "SkuList:sku列表 \t\n" +
            "ProductParameterList:产品参数列表 \t\n" +
            " \t\n" +
            " \t\n" +
            "" +
            "")
    @GetMapping("/samll/getProduct/{id}")
    public ApiResult findProduct(
            @ApiParam("产品id") @PathVariable("id") String id,
            @ApiParam("openId 非必要字段 仅用于记录浏览记录") @RequestParam("openId") String openId

    ) {
        Map map = new HashMap();

        Product product = productService.findByPrimaryKey(id);
        map.put("Product", product);
        List<Sku> skuList = skuService.findList(id);
        map.put("SkuList", skuList);
        List<ProductParameter> productParameterList = productParameterService.findList(id);
        map.put("ProductParameterList", productParameterList);


        return ApiResult.returnData(map);
    }


    @ApiOperation(value = "获取小程序首页", notes = "" +
            " FlashVideoList:轮播视频列表 \t\n" +
            " UserList:N个推荐的咨询师 \t\n" +
            " VideoList:N个推荐视频 \t\n" +
            " FileList:N篇推荐文档 \t\n" +
            " ProductList:4个产品 \t\n" +
            " JournalismList:3个新闻 \t\n" +
            "")
    @GetMapping("/samll/getIndex")
    public ApiResult findIndex() {
        Map map = new HashMap();

        //轮播视频
        List<Video> flashVideoList = videoService.findByFlashVideoOrderBySort("2");
        map.put("FlashVideoList", flashVideoList);

        //推荐的咨询师
        List<User> userList = userService.findByConsultantAndRecommend("2", "2");
        map.put("UserList", userList);

        //N个视频
        List<Video> videoList = videoService.findByFlashVideoAndRecommendOrderBySort("1", "2");
        map.put("VideoList", videoList);

        //N篇文档
        List<File> fileList = fileService.findByRecommendOrderBySort("2");
        map.put("FileList", fileList);


        //4个产品
        Sort sort = new Sort(Sort.Direction.ASC, "sort");
        PageRequest pageRequestProduct = new PageRequest(0, 4, sort);
        Product queryProduct = Product.builder()
                .type("1")
                .status("2")
                .recommend("2")
                .build();
        List<Product> productList = productService.findList(queryProduct, pageRequestProduct).getData();
        map.put("ProductList", productList);

        //3个新闻
        //2019年5月9日 改为查全部
        List<Journalism> journalismList =journalismService.findList();
       // List<Journalism> journalismList = journalismService.findByRecommendOrderBySort("2");
        map.put("JournalismList", journalismList);

        return ApiResult.returnData(map);
    }


    @ApiOperation(value = "获取文档类型页面", notes = "  " +
            "FileTypeList:文档类型列表 \t\n" +
            "FileList:第一个文档类型的文档列表 \t\n" +
            " \t\n" +
            " \t\n" +
            "" +
            "")
    @GetMapping("/samll/getFilePage")
    public ApiResult findFilePage(
            //@ApiParam("文档id 可以不传") @RequestParam(value = "fileId",required = false) String fileId
    ) {
        Map map = new HashMap();

        List<FileType> fileTypeList = fileTypeService.findList();
        map.put("FileTypeList", fileTypeList);

        if (CollectionUtils.isNotEmpty(fileTypeList)) {
            FileType fileType = fileTypeList.get(0);
            List<File> fileList = fileService.findList(fileType.getId(), null);
            map.put("FileList", fileList);

        }
        return ApiResult.returnData(map);
    }

   /* @Autowired
    private VideoTypeService videoTypeService;*/

    @ApiOperation(value = "获取视频类型页面", notes = "  " +
            "FlashViewList:轮播图 \t\n" +
            "VideoTypeList:视频类型列表 \t\n" +
            "VideoList:第一个视频类型的视频列表 \t\n" +
            " \t\n" +
            " \t\n" +
            "" +
            "")
    @GetMapping("/samll/getVideoPage")
    public ApiResult findVideoPage(
            //@ApiParam("文档id 可以不传") @RequestParam(value = "fileId",required = false) String fileId
    ) {
        Map map = new HashMap();


        //轮播视频
        List<Video> flashVideoList = videoService.findByFlashVideoOrderBySort("2");
        map.put("FlashVideoList", flashVideoList);

      /*
        //轮播图
        List<FlashView> flashViewList = flashViewService.findList();
        map.put("FlashViewList", flashViewList);*/


        List<VideoType> videoTypeList = videoTypeService.findList();
        map.put("VideoTypeList", videoTypeList);

        if (CollectionUtils.isNotEmpty(videoTypeList)) {
            VideoType videoType = videoTypeList.get(0);
            List<Video> videoList = videoService.findList(videoType.getId(), null);

            map.put("VideoList", videoList);

        }

        return ApiResult.returnData(TypeConversionUtils.disableCircularReferenceDetect(map));
    }


    @Autowired
    private MaterialService materialService;
    @Autowired
    private MembershipViedoService membershipViedoService;


    //todo 免费的也是251
    @ApiOperation(value = "获取视频/文档详情", notes = "  " +
            "视频/文档 价格是0   250:  \t\n" +
            "会员查阅的话返回的状态是 251:  \t\n" +
            "用户要是购买了视频或者文档查看的是: 252 \t\n" +
            "无权查看 需要购买或者充会员 :253 \t\n" +
            "无权查看 会员能够看的视频数已经到上限了 :254 \t\n" +
            " \t\n" +
            "" +
            "")
    @GetMapping("/samll/getVideoDetail/{type}/{id}/{openId}")
    public ApiResult findVideoDetail(
            @ApiParam("type 1:视频 2:文档") @PathVariable(value = "type") String type,
            @ApiParam("id 视频/文档的id") @PathVariable(value = "id") String id,
            @ApiParam("openId 查阅人") @PathVariable(value = "openId") String openId
    ) throws CustomException {
        log.info(MessageFormat.format("查看详情========================>：类型type:{0},id:{1},openId:{2}"
                ,type,id,openId));
        return membershipViedoService.getVideoDetail(type, id, openId);

    }


    @ApiOperation(value = "获取购物车页面  按淘宝的一样  多商家的购物车   每个店铺下的商品都是分开的  key是商铺id  商铺名/banner 可以从第一个元素里面拿 ", notes = "  " +
            " \t\n" +
            "" +
            "")
    @GetMapping("/samll/ShopCartPage/{openId}")
    public ApiResult findShopCartPage(
            @ApiParam("openId ") @PathVariable(value = "openId") String openId
    ) {


        List<ShopCart> shopCartList = shopCartService.findList(openId);
        Map<String, List<ShopCart>> map = new HashMap<>();


        if (CollectionUtils.isNotEmpty(shopCartList)) {
            for (int i = 0; i < shopCartList.size(); i++) {
                ShopCart shopCart = shopCartList.get(i);
                List<ShopCart> shopCarts = map.get(shopCart.getManufacturerId());
                if (CollectionUtils.isEmpty(shopCarts)) {
                    shopCarts = new ArrayList<>();
                }
                shopCarts.add(shopCart);
                map.put(shopCart.getManufacturerId(), shopCarts);
            }
        }


        List<Manufacturer> manufacturerList = new ArrayList<>();
        if (map != null && map.size() > 0) {
            Set<String> strings = map.keySet();
            for (String key : strings) {
                List<ShopCart> shopCarts = map.get(key);
                if (CollectionUtils.isNotEmpty(shopCarts)) {
                    ShopCart shopCart = shopCarts.get(0);
                    Manufacturer build = Manufacturer.builder()
                            .id(shopCart.getManufacturerId())
                            .banner(shopCart.getManufacturerBanner())
                            .companyName(shopCart.getManufacturerName())
                            .build();
                    build.setShopCartList(shopCarts);
                    manufacturerList.add(build);
                }
            }
        }


        // return ApiResult.returnData(TypeConversionUtils.disableCircularReferenceDetect(rm));
        return ApiResult.returnData(manufacturerList);

    }







    @GetMapping("/test")
    public ApiResult test(
     ) throws IOException, CustomException {
        List<Video> videoList = videoService.findList(null, null);

        for(int i=0;i<videoList.size();i++){
            Video video = videoList.get(i);
            String video1 = video.getVideo();
            video1=tos(video1);
            video.setVideo(video1);

            String banner = video.getBanner();
            banner=tos(banner);
            video.setBanner(banner);
            videoService.updateByPrimaryKeySelective(video);
        }



        // return ApiResult.returnData(TypeConversionUtils.disableCircularReferenceDetect(rm));
        return ApiResult.returnData(videoList);

    }




    static String url = "https://dingguan-1257007615.cos.ap-guangzhou.myqcloud.com/Vehicle/1553653226713.jpg";
    static String dir = "F:\\cacheImg\\";
    static String fileName;
    static String project = "che-heng-shi";



    private String tos(String yuanurl) throws IOException {


        System.out.println("原地址是:" + yuanurl);
        if (StringUtils.isBlank(yuanurl)) {
            return yuanurl;
        }

        if(yuanurl.indexOf(";")!=-1){
            String[] split = yuanurl.split(";");
            if(split.length>0){

                for(int i=0;i<split.length;i++){
                    String tos = tos1(split[i]);
                    yuanurl=yuanurl.replace(split[i],tos);
                }
                return yuanurl;

            }else {
                return yuanurl;
            }
        }else {
            return tos1(yuanurl);
        }



    }



    private  static   String tos1(String yuanurl) throws IOException {
        System.out.println("原地址是:" + yuanurl);
        if (StringUtils.isBlank(yuanurl)) {
            return yuanurl;
        }

        if (yuanurl.indexOf("1257007615.cos.ap-") != -1) {
            int i1 = yuanurl.lastIndexOf("/") + 1;
            fileName = yuanurl.substring(i1, yuanurl.length());
            java.io.File file = downloadHttpUrl(yuanurl, dir, fileName);
            if (null != file && file.length() > 0) {
                FileInputStream fileInputStream = new FileInputStream(file);
                MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                        ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
                ApiResult apiResult = upload(project, multipartFile);
                String imgUrl = (String) apiResult.getData();
                System.out.println("图片的新地址是:" + imgUrl);
                return imgUrl;

            }
        }
        return yuanurl;

    }


    /**
     * 下载文件---返回下载后的文件存储路径
     *
     * @param url      文件地址
     * @param dir      存储目录
     * @param fileName 存储文件名
     * @return
     */
    public static java.io.File downloadHttpUrl(String url, String dir, String fileName) {

        try {
            URL httpurl = new URL(url);
            java.io.File dirfile = new java.io.File(dir);
            if (!dirfile.exists()) {
                dirfile.mkdirs();
            }
            java.io.File file = new java.io.File(dir + fileName);
            if (file == null || file.length() < 10) {
                //文件不存在才需要下载
                FileUtils.copyURLToFile(httpurl, file);
            }
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 云存储参数
    public static final String OSS_ENDPOINT = "oss-cn-shenzhen.aliyuncs.com";
    public static final String OSS_ACCESSKEYID = "LTAIyuQMfu9FLKm3";
    public static final String OSS_ACCESSKEYSECRET = "UNF9oM4pWXKH2bM2ktDMwb7JLpUabJ";
    public static final String OSS_BUCKETNAME = "dingguan";


    static OSSClient client = new OSSClient(OSS_ENDPOINT, OSS_ACCESSKEYID, OSS_ACCESSKEYSECRET);


    public static ApiResult upload(String project, MultipartFile file) throws IOException {
        String originalName = file.getOriginalFilename();
        String fileName = project + "/" + Sequences.get() + originalName.substring(originalName.lastIndexOf("."));
        String url = "http://" + OSS_BUCKETNAME + "." + OSS_ENDPOINT + "/" + fileName;


        client.putObject(OSS_BUCKETNAME, fileName, new ByteArrayInputStream(file.getBytes()));
        return ApiResult.returnData(url);
    }

    @Value("${oss.endpoint}")
    private String endpoint;
    @Value("${oss.accessKeyId}")
    private String accessKeyId;
    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${oss.bucketName}")
    private String bucketName;
    @Value("${oss.fileUploadUrl}")
    private String fileUploadUrl;

    private String filedir = "fileUpload/";
    @Autowired
    private OSSClient ossClient;

    @Bean("ossClient")
    public OSSClient getOssClient() {
        @SuppressWarnings("deprecation")
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        return ossClient;
    }

    @RequestMapping(value = "/upload/Vehicle",method = RequestMethod.POST)
    public ApiResult saveUpload(@RequestParam("file") MultipartFile file) throws IOException {
        String originalName = file.getOriginalFilename();
        log.info(MessageFormat.format("上传文件的名称：{0}", JSONObject.toJSONString(originalName)));
        String filename = Sequences.get() + originalName.substring(originalName.lastIndexOf("."));
        String url = fileUploadUrl.concat(filename);
        ossClient.putObject(bucketName, filedir.concat(filename), new ByteArrayInputStream(file.getBytes()));
        log.info(MessageFormat.format("上传成功返回的地址：{0}",JSONObject.toJSONString(url)));
        return ApiResult.returnData(url);
    }



}