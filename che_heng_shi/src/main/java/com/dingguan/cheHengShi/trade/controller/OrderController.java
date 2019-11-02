package com.dingguan.cheHengShi.trade.controller;

import com.alibaba.fastjson.JSON;
import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.common.rest.BaseController;
import com.dingguan.cheHengShi.common.utils.PayUtils;
import com.dingguan.cheHengShi.common.utils.TypeConversionUtils;
import com.dingguan.cheHengShi.product.entity.*;
import com.dingguan.cheHengShi.product.service.*;
import com.dingguan.cheHengShi.trade.entity.BatchPay;
import com.dingguan.cheHengShi.trade.entity.Order;
import com.dingguan.cheHengShi.trade.entity.OrderDetail;
import com.dingguan.cheHengShi.trade.repository.OrderRepository;
import com.dingguan.cheHengShi.trade.service.BatchPayService;
import com.dingguan.cheHengShi.trade.service.OrderDetailService;
import com.dingguan.cheHengShi.trade.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zyc on 2018/12/5.
 */

@RestController
@RequestMapping("/order")
@Api(description = "订单")
@Slf4j
public class OrderController extends BaseController {


    @Autowired
    private OrderService orderService;
    @Autowired
    private SkuService skuService;
    @Autowired
    private ProductService productService;
    @Autowired
    private BatchPayService batchPayService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private FileService fileService;
    @Autowired
    private MembershipPackageService membershipPackageService;
    @Autowired
    private OrderDetailService orderDetailService;

    /**
     *
     */
    @ApiOperation(value = "新增订单", notes = "新增预约订单需要" +
            "  \t\n" +
            "  \t\n" +
            "  \t\n" +

            "")
    @PostMapping
    @ApiImplicitParam(name = "order", value = "订单", paramType = "body", dataType = "Order")
    public ApiResult postInsert(
            @RequestBody @Valid Order order
            , @ModelAttribute Order model,
            HttpServletRequest request) throws Exception {

        log.info(JSON.toJSONString(order));

        List<OrderDetail> orderDetailList = order.getOrderDetailList();
        String orderType = order.getOrderType();

        if ("1".equals(orderType)) {
            //直接购买
            orderDetailList.stream().forEach(
                    param -> {
                        Product product = productService.findByPrimaryKey(param.getCommodityId());
                        param.setProduct(product);
                        String skuId = param.getSkuId();
                        Sku sku = skuService.findByPrimaryKey(skuId);
                        param.setSku(sku);
                    }
            );
        } else if ("2".equals(orderType)) {
            //视频
            orderDetailList.stream().forEach(
                    param -> {
                        Video video = videoService.findByPrimaryKey(param.getCommodityId(), null);
                        param.setVideo(video);
                    }
            );
        } else if ("3".equals(orderType)) {
            //文档
            orderDetailList.stream().forEach(
                    param -> {
                        File file = fileService.findByPrimaryKey(param.getCommodityId(), null);
                        param.setFile(file);


                    }
            );
        } else if ("4".equals(orderType)) {
            //积分兑换
            orderDetailList.stream().forEach(
                    param -> {
                        Product product = productService.findByPrimaryKey(param.getCommodityId());
                        param.setProduct(product);


                        String skuId = param.getSkuId();
                        if (StringUtils.isNotBlank(skuId)) {
                            Sku sku = skuService.findByPrimaryKey(skuId);
                            param.setSku(sku);
                        }
                    }
            );
        } else if ("5".equals(orderType)) {
            //会员套餐
            orderDetailList.stream().forEach(
                    param -> {
                        MembershipPackage membershipPackage = membershipPackageService.findByPrimaryKey(param.getCommodityId());
                        param.setMembershipPackage(membershipPackage);


                    }
            );
        }

        //ip
        String ip = getIpFromRequest(request);
        ApiResult<Map<String, Object>> mapApiResult = orderService.insertSelective(order, ip);


        return mapApiResult;
    }


    /**
     * 支付回调
     */
    @Deprecated
    @RequestMapping(value = "/wxNotify")
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        //sb为微信返回的xml
        String notityXml = sb.toString();
        String resXml = "";
        log.error("接收到的报文：" + notityXml);
        log.info(MessageFormat.format("回调方法接收到的报文:===================================>",notityXml));

        Map map = PayUtils.doXMLParse(notityXml);
        log.info(MessageFormat.format("报文转换为Map:===================================>",JSON.toJSONString(map)));
        String returnCode = (String) map.get("return_code");
        if ("SUCCESS".equals(returnCode)) {


            /**此处添加自己的业务逻辑代码start**/


            //本地订单号
            String orderNo = map.get("out_trade_no").toString();
            if (orderNo.indexOf("*") != -1) {
                BatchPay batchPay = batchPayService.findByPrimaryKey(orderNo);
                String orderIdStr = batchPay.getOrderIdStr();
                String[] idArr = orderIdStr.split(";");
                List<Order> orderList = new ArrayList<>();
                for (int i = 0; i < idArr.length; i++) {
                    if (StringUtils.isNotBlank(idArr[i])) {
                        Order order = orderService.findByPrimaryKey(idArr[i]);
                        if (order != null) {
                            List<OrderDetail> orderDetailList = orderDetailService.findByOrderId(order.getId());
                            order.setOrderDetailList(orderDetailList);
                        }

                        //支付时间
                        String payTime = map.get("time_end").toString();
                        DateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
                        Date wxPayTime = null;
                        if (payTime != null) {
                            wxPayTime = fmt.parse(payTime);
                        }
                        order.setPayTime(wxPayTime);

                        //微信端的订单号
                        String transactionId = map.get("transaction_id").toString();
                        order.setTransactionId(transactionId);
                        orderList.add(order);
                        //status
                    }
                }
                orderService.notify(orderList);

            } else {
                Order order = orderService.findByPrimaryKey(orderNo);
                if (order != null) {
                    List<OrderDetail> orderDetailList = orderDetailService.findByOrderId(orderNo);
                    order.setOrderDetailList(orderDetailList);
                }
                //支付时间
                String payTime = map.get("time_end").toString();
                DateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
                Date wxPayTime = null;
                if (payTime != null) {
                    wxPayTime = fmt.parse(payTime);
                }
                order.setPayTime(wxPayTime);

                //微信端的订单号
                String transactionId = map.get("transaction_id").toString();
                order.setTransactionId(transactionId);


                //status
                orderService.notify(order);
            }


            //此处添加自己的业务逻辑代码end
            //通知微信服务器已经支付成功
            resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                    + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
        } else {
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        System.out.println(resXml);
        System.out.println("微信支付回调数据结束");


        BufferedOutputStream out = new BufferedOutputStream(
                response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }


    @GetMapping("/privilege/{id}")
    @Deprecated
    @ApiOperation(value = "假装支付成功 本接口仅用于测试时 假装付款成功了//不用真的付钱", notes = "只能一个订单一个订单的支付 不能有带*的")
    public void geId(@PathVariable String id) throws ParseException, CustomException {

        //本地订单号
        String orderNo = id;

        Order order = orderService.findByPrimaryKey(orderNo);
        if (order != null) {
            List<OrderDetail> orderDetailList = orderDetailService.findByOrderId(order.getId());
            order.setOrderDetailList(orderDetailList);
        }

        //支付时间
        order.setPayTime(new Date());

        //微信端的订单号
        String transactionId = "123456789";
        order.setTransactionId(transactionId);
        orderService.notify(order);


    }


    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询")
    public ApiResult getById(@PathVariable String id) {
        Order order = orderService.findByPrimaryKey(id);
        String orderType = order.getOrderType();
        if (order != null) {
            List<OrderDetail> orderDetailList = orderDetailService.findByOrderId(order.getId());
            order.setOrderDetailList(orderDetailList);
            for (int i = 0; i < orderDetailList.size(); i++) {
                OrderDetail orderDetail = orderDetailList.get(i);
                if ("1".equals(orderType) || "4".equals(orderType)) {
                    Product product = productService.findByPrimaryKey(orderDetail.getCommodityId());
                    orderDetail.setProduct(product);
                    if (StringUtils.isNotBlank(orderDetail.getSkuId())) {
                        Sku sku = skuService.findByPrimaryKey(orderDetail.getSkuId());
                        orderDetail.setSku(sku);
                    }
                } else if ("2".equals(orderType)) {
                    Video video = videoService.findByPrimaryKey(orderDetail.getCommodityId(), null);
                    orderDetail.setVideo(video);
                } else if ("3".equals(orderType)) {
                    File file = fileService.findByPrimaryKey(orderDetail.getCommodityId(), null);
                    orderDetail.setFile(file);
                } else if ("5".equals(orderType)) {
                    MembershipPackage membershipPackage = membershipPackageService.findByPrimaryKey(orderDetail.getCommodityId());
                    orderDetail.setMembershipPackage(membershipPackage);
                }
            }


        }
        return ApiResult.returnData(order);
    }

    /**
     * 重新支付
     */
    @PostMapping(value = "/payAgain/{id}")
    @ApiOperation(value = "重新支付")
    @ApiImplicitParam(name = "id", value = "订单id", paramType = "path", dataType = "String")
    public ApiResult<String> payAgain(HttpServletRequest request, @PathVariable String id) throws Exception {
        ApiResult resp = new ApiResult();
        String ip = getIpFromRequest(request);


        Order order = (Order) getById(id).getData();

        Map map = orderService.payAgain(order, ip);
        resp.setMessage("payAgain seccess");
        resp.setData(map);
        return resp;
    }


    @Autowired
    private OrderRepository orderRepository;

    /**
     *
     */
    @ApiOperation(value = "获取订单列表")
    @GetMapping()
    public ApiResult findList(

            @ApiParam("订单类型 1:直接购买 2:视频 3:文档 4:积分兑换 5:会员套餐") @RequestParam(value = "orderType", required = false)
                    String orderType1,
            @ApiParam("下单人") @RequestParam(value = "openId", required = false) String openId,
            @ApiParam("店铺id") @RequestParam(value = "manufacturerId", required = false) String manufacturerId,
            @ApiParam(" 1:未付款 2:已经付款/待发货  3:已经发货   4:已收货  5:已取消/删除订单 ") @RequestParam(value = "status", required = false) String status,
            @ApiParam("第几页") @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
            @ApiParam("每一页有多少条数据") @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {
        Order build = Order.builder()
                .status(status)
                .manufacturerId(manufacturerId)
                .orderType(orderType1)
                .openId(openId)
                .build();

        Sort sort = new Sort(Sort.Direction.DESC, "orderTime");


        if (pageIndex == null || pageSize == null) {
            List<Order> orderList = null;

            orderList = orderService.findList(build, sort);


            if (CollectionUtils.isNotEmpty(orderList)) {
                orderList.stream().forEach(
                        param -> {
                            List<OrderDetail> orderDetailList = orderDetailService.findByOrderId(param.getId());
                            param.setOrderDetailList(orderDetailList);
                        }
                );
            }
            if (CollectionUtils.isNotEmpty(orderList)) {
                for (int o = 0; o < orderList.size(); o++) {
                    Order order = orderList.get(o);
                    String orderType = order.getOrderType();
                    List<OrderDetail> orderDetailList = order.getOrderDetailList();
                    for (int i = 0; i < orderDetailList.size(); i++) {
                        OrderDetail orderDetail = orderDetailList.get(i);
                        if ("1".equals(orderType) || "4".equals(orderType)) {
                            Product product = productService.findByPrimaryKey(orderDetail.getCommodityId());
                            orderDetail.setProduct(product);
                            if (StringUtils.isNotBlank(orderDetail.getSkuId())) {
                                Sku sku = skuService.findByPrimaryKey(orderDetail.getSkuId());
                                orderDetail.setSku(sku);
                            }
                        } else if ("2".equals(orderType)) {
                            Video video = videoService.findByPrimaryKey(orderDetail.getCommodityId(), null);
                            orderDetail.setVideo(video);
                        } else if ("3".equals(orderType)) {
                            File file = fileService.findByPrimaryKey(orderDetail.getCommodityId(), null);
                            orderDetail.setFile(file);
                        } else if ("5".equals(orderType)) {
                            MembershipPackage membershipPackage = membershipPackageService.findByPrimaryKey(orderDetail.getCommodityId());
                            orderDetail.setMembershipPackage(membershipPackage);
                        }
                    }

                }


            }

            return ApiResult.returnData(TypeConversionUtils.disableCircularReferenceDetect(orderList));
        }
        PageRequest pageRequest = new PageRequest(pageIndex, pageSize, sort);
        ApiResult apiResult = orderService.findList(build, pageIndex,pageSize);
        List<Order> orderList = (List<Order>) apiResult.getData();
        if (CollectionUtils.isNotEmpty(orderList)) {
            orderList.stream().forEach(
                    param -> {
                        List<OrderDetail> orderDetailList = orderDetailService.findByOrderId(param.getId());
                        param.setOrderDetailList(orderDetailList);
                    }
            );
        }
        apiResult.setMessage("Query Order Success");


        if (CollectionUtils.isNotEmpty(orderList)) {
            for (int o = 0; o < orderList.size(); o++) {
                Order order = orderList.get(o);
                String orderType = order.getOrderType();
                List<OrderDetail> orderDetailList = order.getOrderDetailList();
                for (int i = 0; i < orderDetailList.size(); i++) {
                    OrderDetail orderDetail = orderDetailList.get(i);
                    if ("1".equals(orderType) || "4".equals(orderType)) {
                        Product product = productService.findByPrimaryKey(orderDetail.getCommodityId());
                        orderDetail.setProduct(product);
                        if (StringUtils.isNotBlank(orderDetail.getSkuId())) {
                            Sku sku = skuService.findByPrimaryKey(orderDetail.getSkuId());
                            orderDetail.setSku(sku);
                        }
                    } else if ("2".equals(orderType)) {
                        Video video = videoService.findByPrimaryKey(orderDetail.getCommodityId(), null);
                        orderDetail.setVideo(video);
                    } else if ("3".equals(orderType)) {
                        File file = fileService.findByPrimaryKey(orderDetail.getCommodityId(), null);
                        orderDetail.setFile(file);
                    } else if ("5".equals(orderType)) {
                        MembershipPackage membershipPackage = membershipPackageService.findByPrimaryKey(orderDetail.getCommodityId());
                        orderDetail.setMembershipPackage(membershipPackage);
                    }
                }

            }
        }
        apiResult.setData(TypeConversionUtils.disableCircularReferenceDetect(orderList));
        return apiResult;
    }


    @ApiOperation(value = "修改 订单   ", notes = "")
    @PutMapping
    @ApiImplicitParam(name = "order", value = "", paramType = "body", dataType = "Order")
    public ApiResult<String> put(@RequestBody Order order
            , @ModelAttribute Order model
    ) throws CustomException {
        return ApiResult.returnData(orderService.updateByPrimaryKeySelective(order));
    }


    @ApiOperation(value = "  测试回调   ", notes = "")
    @PutMapping("test")
    public void pu2t1(
    ) throws Exception {

        //sb为微信返回的xml
        String notityXml = "<xml><appid><![CDATA[wx907c9c5582caeb09]]></appid><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[N]]></is_subscribe><mch_id><![CDATA[1521250771]]></mch_id><nonce_str><![CDATA[3y8ew1ydqkefk1te52jzyems1nqnr3tn]]></nonce_str><openid><![CDATA[oLSUg5cwE_bLWo2cJ5FcetRzMr1c]]></openid><out_trade_no><![CDATA[119350591400004*]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[CF1742B994A9A38BA3481F3B7E4485FB]]></sign><time_end><![CDATA[20190218153839]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[4200000258201902185963214595]]></transaction_id></xml>";
        String resXml = "";
        log.error("接收到的报文：" + notityXml);


        Map map = PayUtils.doXMLParse(notityXml);

        String returnCode = (String) map.get("return_code");
        if ("SUCCESS".equals(returnCode)) {


            /**此处添加自己的业务逻辑代码start**/


            //本地订单号
            String orderNo = map.get("out_trade_no").toString();
            if (orderNo.indexOf("*") != -1) {
                BatchPay batchPay = batchPayService.findByPrimaryKey(orderNo);
                String orderIdStr = batchPay.getOrderIdStr();
                String[] idArr = orderIdStr.split(";");
                List<Order> orderList = new ArrayList<>();
                for (int i = 0; i < idArr.length; i++) {
                    if (StringUtils.isNotBlank(idArr[i])) {
                        Order order = orderService.findByPrimaryKey(idArr[i]);
                        if (order != null) {
                            List<OrderDetail> orderDetailList = orderDetailService.findByOrderId(order.getId());
                            order.setOrderDetailList(orderDetailList);
                        }

                        //支付时间
                        String payTime = map.get("time_end").toString();
                        DateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
                        Date wxPayTime = null;
                        if (payTime != null) {
                            wxPayTime = fmt.parse(payTime);
                        }
                        order.setPayTime(wxPayTime);

                        //微信端的订单号
                        String transactionId = map.get("transaction_id").toString();
                        order.setTransactionId(transactionId);
                        orderList.add(order);
                        //status
                    }
                }
                orderService.notify(orderList);

            } else {
                Order order = orderService.findByPrimaryKey(orderNo);
                if (order != null) {
                    List<OrderDetail> orderDetailList = orderDetailService.findByOrderId(orderNo);
                    order.setOrderDetailList(orderDetailList);
                }
                //支付时间
                String payTime = map.get("time_end").toString();
                DateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
                Date wxPayTime = null;
                if (payTime != null) {
                    wxPayTime = fmt.parse(payTime);
                }
                order.setPayTime(wxPayTime);

                //微信端的订单号
                String transactionId = map.get("transaction_id").toString();
                order.setTransactionId(transactionId);


                //status
                orderService.notify(order);
            }
        }

    }

}