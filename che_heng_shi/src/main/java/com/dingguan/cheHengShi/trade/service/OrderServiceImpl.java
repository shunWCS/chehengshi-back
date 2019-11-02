package com.dingguan.cheHengShi.trade.service;


import com.alibaba.fastjson.JSON;
import com.dingguan.cheHengShi.common.constants.Constants;
import com.dingguan.cheHengShi.common.constants.Parameters;
import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.common.utils.*;
import com.dingguan.cheHengShi.job.AsynchronousJob;
import com.dingguan.cheHengShi.product.entity.*;
import com.dingguan.cheHengShi.product.service.*;
import com.dingguan.cheHengShi.trade.dao.OrderMapper;
import com.dingguan.cheHengShi.trade.entity.*;
import com.dingguan.cheHengShi.trade.repository.OrderRepository;
import com.dingguan.cheHengShi.user.entity.Manufacturer;
import com.dingguan.cheHengShi.user.entity.User;
import com.dingguan.cheHengShi.user.service.IntegralFlowService;
import com.dingguan.cheHengShi.user.service.ManufacturerService;
import com.dingguan.cheHengShi.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.*;

/**
 * Created by zyc on 2018/12/5.
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private ProductService productService;
    @Autowired
    private SkuService skuService;
    @Autowired
    private Parameters parameters;
    @Autowired
    private ShopCartService shopCartService;
    @Autowired
    private UserService userService;
    @Autowired
    private IntegralFlowService integralFlowService;
    @Autowired
    @Lazy
    private AsynchronousJob asynchronousJob;
    @Autowired
    private ManufacturerService manufacturerService;
    @Autowired
    private BatchPayService batchPayService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private FileService fileService;
    @Autowired
    private MembershipPackageService membershipPackageService;
    @Autowired
    private OrderMapper orderMapper;


    @Override
    public Order findByPrimaryKey(String id) {
        return orderRepository.findOne(id);
    }

    @Override
    public ApiResult<List<Order>> findList(Order order, Integer pageIndex, Integer pageSize) {

        String status = order.getStatus();
        if (StringUtils.isNotBlank(status)) {
            if ("-1".equals(status)) {
                status = "status in (1,2,3,4)";
            } else if ("-2".equals(status)) {
                status = "status in (2,3,4)";
            } else {
                status = "status =" + status;
            }
        }
        String orderType = order.getOrderType();
        if (StringUtils.isNotBlank(orderType)) {
            if ("-1".equals(orderType)) {
                orderType = "order_type in (1,4)";
            } else {
                orderType = "order_type =" + orderType;
            }
        }
        Integer pageNo = (pageIndex) * pageSize;
        List<Order> orderList = orderMapper.findList(order.getOpenId(), order.getTransactionId(), status, orderType, order.getManufacturerId(), pageNo, pageSize);
        Integer count = orderMapper.findCount(order.getOpenId(), order.getTransactionId(), status, orderType, order.getManufacturerId());
        ApiResult apiResult = ApiResult.returnData(orderList);
        apiResult.setPageTotal(count);
        apiResult.setPageIndex(pageIndex);
        apiResult.setPageSize(pageSize);


        return apiResult;
    }


    @Override
    public List<Order> findList(Order order, Sort sort) {

        String status = order.getStatus();
        if (StringUtils.isNotBlank(status)) {
            if ("-1".equals(status)) {
                status = "status in (1,2,3,4)";
            } else if ("-2".equals(status)) {
                status = "status in (2,3,4)";
            } else {
                status = "status =" + status;
            }
        }
        String orderType = order.getOrderType();
        if (StringUtils.isNotBlank(orderType)) {
            if ("-1".equals(orderType)) {
                orderType = "order_type in (1,4)";
            } else {
                orderType = "order_type =" + orderType;
            }
        }

        List<Order> orderList = orderMapper.findList(order.getOpenId(), order.getTransactionId(), status, orderType,
                order.getManufacturerId(), null, null);

        return orderList;

    }


    @Override
    public Order updateByPrimaryKeySelective(Order order) throws CustomException {
        Order source = orderRepository.findOne(order.getId());
        UpdateTool.copyNullProperties(source, order);
        return orderRepository.save(order);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ApiResult<Map<String, Object>> insertSelective(Order order, String ip) throws Exception {
        String openId = order.getOpenId();
        BigDecimal qianduanjiage = order.getActualPrice();
        //基础参数
        order.setId(Sequences.get());
        order.setStatus("1");
        order.setOrderTime(new Date());

        String ids = "";
        String orderType = order.getOrderType();
        if ("1".equals(orderType)) {
            //直接购买
            List<Order> orderList = new ArrayList<>();
            Map<String, List<OrderDetail>> cacheMap = new HashMap<>();
            List<OrderDetail> orderDetailList = order.getOrderDetailList();
            for (int i = 0; i < orderDetailList.size(); i++) {
                OrderDetail orderDetail = orderDetailList.get(i);

                String manufacturerId = orderDetail.getProduct().getManufacturerId();
                List<OrderDetail> orderDetails = cacheMap.get(manufacturerId);
                if (CollectionUtils.isEmpty(orderDetails)) {
                    orderDetails = new ArrayList<>();
                }
                orderDetails.add(orderDetail);
                cacheMap.put(manufacturerId, orderDetails);
            }

            BigDecimal amount = new BigDecimal(0);

            Set<String> stringSet = cacheMap.keySet();
            for (String st : stringSet) {
                List<OrderDetail> orderDetails = cacheMap.get(st);

                Order order1 = TypeConversionUtils.JavaBeanToJavaBean(order, Order.class);
                order1.setId(Sequences.get());
                order1.setOrderDetailList(orderDetails);
                order1.setManufacturerId(orderDetails.get(0).getProduct().getManufacturerId());
                order1.setManufacturerName(orderDetails.get(0).getProduct().getManufacturerName());
                order1.setManufacturerBanner(orderDetails.get(0).getProduct().getManufacturerBanner());
                orderList.add(order1);
                ids += order1.getId() + ";";

                //检查金额
                order1 = checkRMB(order1);
                amount = amount.add(order1.getActualPrice());
                saveOrderAndOrderDetail(order1);
            }
            //清理购物车
            cleanShoppingCart(order);
            //校验库存
            checkSpike(order);


            order.setId(Sequences.get() + "*");
            order.setActualPrice(amount);
            order.setOpenId(openId);


            BatchPay build = BatchPay.builder()
                    .id(order.getId())
                    .time(new Date())
                    .orderIdStr(ids)
                    .build();
            batchPayService.insertSelective(build);


        } else if ("2".equals(orderType)) {
            //视频

            order = checkRMB(order);

            //保存订单与 订单详情
            saveOrderAndOrderDetail(order);
        } else if ("3".equals(orderType)) {
            //文档
            order = checkRMB(order);
            //保存订单与 订单详情
            saveOrderAndOrderDetail(order);
        } else if ("4".equals(orderType)) {
            //积分兑换
            order = checkRMB(order);
            checkSpike(order);
            List<OrderDetail> orderDetailList = order.getOrderDetailList();
            order.setManufacturerId(orderDetailList.get(0).getProduct().getManufacturerId());
            order.setManufacturerName(orderDetailList.get(0).getProduct().getManufacturerName());
            order.setManufacturerBanner(orderDetailList.get(0).getProduct().getManufacturerBanner());


            saveOrderAndOrderDetail(order);


            User user = userService.findByPrimaryKey(order.getOpenId());
            userService.check(order, user);


            BigDecimal actualPrice = order.getActualPrice();
            if (actualPrice.compareTo(new BigDecimal(0)) == 0) {
                //无需支付
                order = notify(order);
                ApiResult apiResult = ApiResult.returnData(order);
                apiResult.setCode(Constants.EXCHANGE_SUCCESS);
                apiResult.setMessage("兑换商品成功 无需支付");

                //无需支付直接扣减积分
                //userService.exchangeSuccess(order, user);


                return apiResult;
            } else if (actualPrice.compareTo(new BigDecimal(0)) > 0) {
                //需要支付
            } else {
                throw new CustomException(Constants.RESP_STATUS_INTERNAL_ERROR, "金额计算错误");
            }
        } else if ("5".equals(orderType)) {
            //会员套餐

            //保存订单与 订单详情
            saveOrderAndOrderDetail(order);
        }

        //店铺
        String manufacturerId = order.getManufacturerId();
        if (StringUtils.isBlank(manufacturerId)) {
            Manufacturer query = Manufacturer.builder()
                    .pattern("2")
                    .build();
            Sort sort = new Sort(Sort.Direction.ASC, "sort");
            List<Manufacturer> manufacturerList = manufacturerService.findList(query, sort);
            if (CollectionUtils.isNotEmpty(manufacturerList)) {
                Manufacturer manufacturer = manufacturerList.get(0);
                order.setManufacturerId(manufacturer.getId());
                order.setManufacturerBanner(manufacturer.getBanner());
                order.setManufacturerName(manufacturer.getCompanyName());

            }
        }


        //比较价格
        if (order.getActualPrice().compareTo(qianduanjiage) != 0) {
            throw new CustomException(Constants.RESP_STATUS_BADREQUEST, "价格错误");
        }


        if (order.getActualPrice().compareTo(new BigDecimal(0)) == 0) {
            //金额为0无需支付

            order.setPayTime(new Date());
            order = notify(order);
            ApiResult apiResult = ApiResult.returnData(order);
            apiResult.setCode(Constants.NO_PAYMENT_IS_REQUIRED_FOR_A_SUM_OF_ZERO);
            apiResult.setMessage("金额为0无需支付");
            return apiResult;
        } else if (order.getActualPrice().compareTo(new BigDecimal(0)) >= 0) {
            //支付
            Map<String, Object> response = new HashMap<String, Object>(16);
            response.put("appid", parameters.appid);
            response = pay(order, ip, response);

            ApiResult apiResult = ApiResult.returnData(response);
            apiResult.setMessage("下单成功");

            String orderId = (String) response.get("orderId");
            if (orderId.indexOf("*") != -1) {
                response.put("orderId", ids);
            }
            return apiResult;
        } else {
            throw new CustomException(Constants.RESP_STATUS_INTERNAL_ERROR, "金额计算错误");
        }


    }


    /**
     * 重新支付
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Map payAgain(Order order, String ip) throws Exception {


        //库存
        checkSpike(order);


        String orderType = order.getOrderType();
        if ("1".equals(orderType)) {
            checkSpike(order);
        } else if ("2".equals(orderType)) {
            //视频
        } else if ("3".equals(orderType)) {
            //文档
        } else if ("4".equals(orderType)) {
            //积分兑换
            checkSpike(order);
            User user = userService.findByPrimaryKey(order.getOpenId());
            userService.check(order, user);
        } else if ("5".equals(orderType)) {
            //会员套餐
        }


        Map<String, Object> response = new HashMap<String, Object>(16);
        response.put("appid", parameters.appid);
        return pay(order, ip, response);
    }

    private Map pay(Order order, String ip, Map<String, Object> response) throws Exception {
        response.put("needPay", true);
        response.put("orderId", order.getId());
        String nonce_str = RandomNumberCode.getRandomStringByLength(32);
        //商品名称
        String body = "测试商品";

        String amount = String.valueOf(order.getActualPrice().doubleValue() * 100);
        String orderAmo = amount.substring(0, amount.indexOf("."));

        //组装参数，用户生成统一下单接口的签名
        Map<String, String> packageParams = new HashMap<String, String>();
        packageParams.put("appid", parameters.appid);
        packageParams.put("mch_id", parameters.mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("body", body);
        packageParams.put("out_trade_no", order.getId());
        packageParams.put("total_fee", orderAmo);
        packageParams.put("spbill_create_ip", ip);
        packageParams.put("notify_url", parameters.notify_url);
        packageParams.put("trade_type", parameters.TRADETYPE);
        packageParams.put("openid", order.getOpenId());
        /**
         * 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
         */
        String prestr = PayUtils.createLinkString(packageParams);

        //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
        String mysign = PayUtils.sign(prestr, parameters.key, "utf-8").toUpperCase();

        //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
        String xml = "<xml>" + "<appid>" + parameters.appid + "</appid>"
                + "<body><![CDATA[" + body + "]]></body>"
                + "<mch_id>" + parameters.mch_id + "</mch_id>"
                + "<nonce_str>" + nonce_str + "</nonce_str>"
                + "<notify_url>" + parameters.notify_url + "</notify_url>"
                + "<openid>" + order.getOpenId() + "</openid>"
                + "<out_trade_no>" + order.getId() + "</out_trade_no>"
                + "<spbill_create_ip>" + ip + "</spbill_create_ip>"
                + "<total_fee>" + orderAmo + "</total_fee>"
                + "<trade_type>" + parameters.TRADETYPE + "</trade_type>"
                + "<sign>" + mysign + "</sign>"
                + "</xml>";

        System.out.println("调试模式_统一下单接口 请求XML数据：" + xml);
        log.info(MessageFormat.format("调试模式_统一下单接口 请求XML数据=========================>,xml:{0}",xml));

        //调用统一下单接口，并接受返回的结果
        String result = PayUtils.httpRequest(parameters.pay_url, "POST", xml);

        System.out.println("调试模式_统一下单接口 返回XML数据：" + result);
        log.info(MessageFormat.format("调试模式_统一下单接口 返回XML数据=========================>,xml:{0}",result));

        // 将解析结果存储在HashMap中
        Map map = PayUtils.doXMLParse(result);
        log.info(MessageFormat.format("将解析结果存储在HashMap:=========================>,xml:{0}",
                JSON.toJSONString(map)));
        /**
         * 返回状态码
         */
        String return_code = (String) map.get("return_code");
        log.info(MessageFormat.format("返回状态码:=========================>,return_code:{0}",
                JSON.toJSONString(return_code)));
        /**
         * 返回给小程序端需要的参数
         */

        if ("SUCCESS".equals(return_code)) {
            /**
             * 返回的预付单信息
             */
            String prepay_id = (String) map.get("prepay_id");
            response.put("nonceStr", nonce_str);
            response.put("package", "prepay_id=" + prepay_id);
            Long timeStamp = System.currentTimeMillis() / 1000;
            /**
             * 这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
             */
            response.put("timeStamp", timeStamp + "");
            //拼接签名需要的参数
            String stringSignTemp = "appId=" + parameters.appid + "&nonceStr=" + nonce_str + "&package=prepay_id=" + prepay_id + "&signType=MD5&timeStamp=" + timeStamp;
            //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
            String paySign = PayUtils.sign(stringSignTemp, parameters.key, "utf-8").toUpperCase();

            response.put("paySign", paySign);
            response.put("payStatus", "Success");
        } else {
            response.put("payStatus", "Fail");
        }
        response.put("appid", parameters.appid);
        return response;
    }


    /**
     * 回调
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Order notify(Order order) throws ParseException, CustomException {

        User user = userService.findByPrimaryKey(order.getOpenId());

        order.setStatus("2");
        String orderType = order.getOrderType();
        List<OrderDetail> orderDetailList = order.getOrderDetailList();

        if (orderType.equals("1")) {
            //直接支付
            orderDetailList.stream().forEach(
                    param -> {
                        productService.updateStockAndSales(param.getCommodityId(), 0 - param.getNum(), param.getNum());
                        if (StringUtils.isNotBlank(param.getSkuId())) {
                            skuService.updateStockAndSales(param.getSkuId(), 0 - param.getNum(), param.getNum());
                        }
                    }
            );

        } else if ("2".equals(orderType)) {
            //视频
            orderDetailList.stream().forEach(
                    param -> {
                        videoService.updateSales(param.getCommodityId(), param.getNum());
                    }
            );
        } else if ("3".equals(orderType)) {
            //文档
            orderDetailList.stream().forEach(
                    param -> {
                        fileService.updateSales(param.getCommodityId(), param.getNum());
                    }
            );
        } else if ("4".equals(orderType)) {
            //积分
            //userService.exchangeSuccess(order,user);

            orderDetailList.stream().forEach(
                    param -> {
                        productService.updateStockAndSales(param.getCommodityId(), 0 - param.getNum(), param.getNum());
                        if (StringUtils.isNotBlank(param.getSkuId())) {
                            skuService.updateStockAndSales(param.getSkuId(), 0 - param.getNum(), param.getNum());
                        }
                    }
            );


        } else if ("5".equals(orderType)) {
            //会员套餐
            orderDetailList.stream().forEach(
                    param -> {
                        membershipPackageService.updateSales(param.getCommodityId(), param.getNum());
                        MembershipPackage membershipPackage = membershipPackageService.findByPrimaryKey(param.getCommodityId());
                        userService.payMembershipPackage(membershipPackage, user);

                    }
            );
        }


        //修改订单状态
        updateByPrimaryKeySelective(order);
        if (CollectionUtils.isNotEmpty(orderDetailList)) {
            orderDetailList.stream().forEach(
                    param -> {
                        param.setHaveUsr("1");
                        try {
                            orderDetailService.updateByPrimaryKeySelective(param);
                        } catch (CustomException e) {
                            e.printStackTrace();
                        }
                    }
            );

        }


        asynchronousJob.paySuccess(order);
        return order;

    }

    @Override
    public List<Order> notify(List<Order> orderList) throws ParseException, CustomException {
        if (CollectionUtils.isNotEmpty(orderList)) {
            for (Order param : orderList) {
                notify(param);
            }
        }
        return orderList;
    }


    /**
     * 保存订单与订单详情
     */
    private void saveOrderAndOrderDetail(Order order) {
        orderRepository.save(order);
        List<OrderDetail> orderDetailList = order.getOrderDetailList();
        for (int i = 0; i < orderDetailList.size(); i++) {
            OrderDetail orderDetail = orderDetailList.get(i);
            orderDetail.setId(Sequences.get());
            orderDetail.setOrderId(order.getId());
            orderDetail.setOpenId(order.getOpenId());
            orderDetail.setOrderType(order.getOrderType());
            orderDetail.setHaveUsr("0");
            orderDetailService.insertSelective(orderDetail);
        }
    }


    /**
     * 清理购物车
     */
    private void cleanShoppingCart(Order order) {
        List<OrderDetail> orderDetailList = order.getOrderDetailList();

        String orderType = order.getOrderType();
        if ("1".equals(orderType) || "4".equals(orderType)) {
            List<ShopCart> shopCartList = shopCartService.findList(order.getOpenId());
            for (int i = 0; i < shopCartList.size(); i++) {
                ShopCart shopCart = shopCartList.get(i);

                boolean flog = false;
                for (int d = 0; d < orderDetailList.size(); d++) {
                    OrderDetail orderDetail = orderDetailList.get(d);
                    if (orderDetail.getCommodityId().equals(shopCart.getProductId())
                            && orderDetail.getSkuId().equals(shopCart.getSkuId())) {
                        flog = true;
                    }
                }
                if (flog) {
                    shopCartService.deleteByPrimaryKey(shopCart.getId());
                }
            }
        }

    }


    /**
     * 校验库存
     */
    private void checkSpike(Order order) throws CustomException {
        String orderType = order.getOrderType();
        if ("1".equals(orderType) || "4".equals(orderType)) {
            List<OrderDetail> orderDetailList = order.getOrderDetailList();
            for (int i = 0; i < orderDetailList.size(); i++) {
                OrderDetail orderDetail = orderDetailList.get(i);
                Sku sku = orderDetail.getSku();
                if (sku.getStock() < orderDetail.getNum()) {
                    throw new CustomException(Constants.INSUFFICIENT_INVENTORY, "库存不足");
                }
            }
        }
    }


    /**
     * 校验金额
     *
     * @param order
     * @return
     * @throws CustomException
     */
    @Deprecated
    private Order checkRMB(Order order) throws CustomException {

        BigDecimal remote = order.getActualPrice();

        BigDecimal readeRMB = new BigDecimal(0);//实际支付金额
        BigDecimal postage = new BigDecimal(0);//邮费


        List<OrderDetail> orderDetailList = order.getOrderDetailList();
        BigDecimal in = new BigDecimal(0);

        //1:直接购买 2:视频 3:文档 4:积分兑换 5:会员套餐
        String orderType = order.getOrderType();
        if ("1".equals(orderType)) {

            for (int i = 0; i < orderDetailList.size(); i++) {
                OrderDetail orderDetail = orderDetailList.get(i);
                Integer num = orderDetail.getNum();
                BigDecimal bigNum = new BigDecimal(num);

                Sku sku = orderDetail.getSku();

                //实际支付金额
                BigDecimal now = sku.getPrice().multiply(bigNum);
                readeRMB = readeRMB.add(now);


                //todo 邮费计算规则  目前是累加
               /* BigDecimal localPostage = orderDetail.getProduct().getPostage().multiply(bigNum);
                postage = postage.add(localPostage);*/

            }
        } else if ("2".equals(orderType)) {

            for (int i = 0; i < orderDetailList.size(); i++) {
                OrderDetail orderDetail = orderDetailList.get(i);
                Integer num = orderDetail.getNum();
                BigDecimal bigNum = new BigDecimal(num);

                Video video = orderDetail.getVideo();

                //实际支付金额
                BigDecimal now = video.getPrice().multiply(bigNum);
                readeRMB = readeRMB.add(now);


            }

        } else if ("3".equals(orderType)) {
            for (int i = 0; i < orderDetailList.size(); i++) {
                OrderDetail orderDetail = orderDetailList.get(i);
                Integer num = orderDetail.getNum();
                BigDecimal bigNum = new BigDecimal(num);

                File file = orderDetail.getFile();

                //实际支付金额
                BigDecimal now = file.getPrice().multiply(bigNum);
                readeRMB = readeRMB.add(now);
            }


        } else if ("4".equals(orderType)) {

            for (int i = 0; i < orderDetailList.size(); i++) {
                OrderDetail orderDetail = orderDetailList.get(i);
                Integer num = orderDetail.getNum();
                BigDecimal bigNum = new BigDecimal(num);

                Sku sku = orderDetail.getSku();

                //实际支付金额

                BigDecimal now = new BigDecimal(sku.getIntegral()).multiply(bigNum);

                in = in.add(now);


              /*  //todo 邮费计算规则  目前是累加
                BigDecimal localPostage = orderDetail.getProduct().getPostage().multiply(bigNum);
                postage = postage.add(localPostage);*/

            }

            //校验积分


        } else if ("5".equals(orderType)) {

            for (int i = 0; i < orderDetailList.size(); i++) {
                OrderDetail orderDetail = orderDetailList.get(i);
                Integer num = orderDetail.getNum();
                BigDecimal bigNum = new BigDecimal(num);

                MembershipPackage membershipPackage = orderDetail.getMembershipPackage();

                //实际支付金额
                BigDecimal now = membershipPackage.getPrice().multiply(bigNum);
                readeRMB = readeRMB.add(now);
            }

        }


        //实际支付金额
        readeRMB = readeRMB.add(postage);


        order.setPostage(postage);
        order.setIntegral(Integer.parseInt(in.toString()));
        order.setActualPrice(readeRMB);
        return order;
    }


}