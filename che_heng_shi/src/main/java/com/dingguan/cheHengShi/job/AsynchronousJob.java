package com.dingguan.cheHengShi.job;


import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.material.service.MaterialService;
import com.dingguan.cheHengShi.message.entity.Msg;
import com.dingguan.cheHengShi.message.service.MsgService;
import com.dingguan.cheHengShi.product.entity.Product;
import com.dingguan.cheHengShi.product.entity.Sku;
import com.dingguan.cheHengShi.product.service.ProductService;
import com.dingguan.cheHengShi.product.service.SkuService;
import com.dingguan.cheHengShi.trade.entity.Order;
import com.dingguan.cheHengShi.trade.entity.ShopCart;
import com.dingguan.cheHengShi.trade.service.ShopCartService;
import com.dingguan.cheHengShi.user.entity.Address;
import com.dingguan.cheHengShi.user.entity.IntegralFlow;
import com.dingguan.cheHengShi.user.entity.Manufacturer;
import com.dingguan.cheHengShi.user.entity.User;
import com.dingguan.cheHengShi.user.service.AddressService;
import com.dingguan.cheHengShi.user.service.IntegralFlowService;
import com.dingguan.cheHengShi.user.service.ManufacturerService;
import com.dingguan.cheHengShi.user.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by zyc on 2018/12/7.
 */
@EnableAsync
@Component
public class AsynchronousJob {

    // @Lazy
    // @Async


    @Autowired
    private MsgService msgService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private ProductService productService;
    @Autowired
    private SkuService skuService;
    @Autowired
    private ShopCartService shopCartService;
    @Autowired
    private UserService userService;
    @Autowired
    private IntegralFlowService integralFlowService;
    @Autowired
    private ManufacturerService manufacturerService;


    /**
     * 未读消息多弄成已读
     */
    @Async
    public void toAlreadyRead(List<Msg> msgList, Msg msg, String openId) {
        if (msgList == null) {
            msgList = new ArrayList<>();
        }
        if (msg != null) {
            msgList.add(msg);
        }


        if (CollectionUtils.isNotEmpty(msgList)) {
            msgList.stream().forEach(
                    param -> {
                        String status = param.getStatus();
                        if ("1".equals(status)) {
                            if(StringUtils.isNotBlank(openId)){
                                if (param.getConsumer().equals(openId)) {
                                    param.setStatus("2");
                                    try {
                                        msgService.updateByPrimaryKeySelective(param);
                                    } catch (CustomException e) {
                                            e.printStackTrace();
                                    }
                                }
                            }

                        }
                    }
            );
        }


    }


    /**
     * 把其他的默认地址都改成非默认的
     */
    @Async
    public void consumptionUpgrading(String openId, String id) throws CustomException {
        Address build = Address.builder()
                .openId(openId)
                .madeByImperialOrder("2")
                .build();

        List<Address> addressList = addressService.findList(build);
        if (CollectionUtils.isNotEmpty(addressList)) {
            for (int i = 0; i < addressList.size(); i++) {
                Address address = addressList.get(i);

                if (!address.getId().equals(id)) {
                    address.setMadeByImperialOrder("1");
                    addressService.updateByPrimaryKeySelective(address);


                }

            }

        }


    }


    /**
     * 更新购物车的product信息 或者 sku信息
     */
    @Async
    public void synchronousShopCart(Product product, Sku sku) {
        if (product == null && sku != null) {
            List<ShopCart> cartList = shopCartService.findBySkuId(sku.getId());
            if (CollectionUtils.isNotEmpty(cartList)) {
                cartList.stream().forEach(
                        param -> {
                            param.setSkuIntroduce(sku.getIntroduce());
                            param.setSkuPrice(sku.getPrice());
                            param.setSkuIntegral(sku.getIntegral());
                            param.setSkuName(sku.getName());
                            param.setSkuBanner(sku.getBanner());
                            try {
                                shopCartService.updateByPrimaryKeySelective(param);
                            } catch (CustomException e) {
        e.printStackTrace();
                            }
                        }
                );
            }
        }

        if (sku == null && product != null) {
            List<ShopCart> cartList = shopCartService.findByProductId(product.getId());
            if (CollectionUtils.isNotEmpty(cartList)) {
                cartList.stream().forEach(
                        param -> {
                            param.setProductName(product.getName());
                            param.setProductIntroduce(product.getIntroduce());
                            param.setProductBanner(product.getBanner());
                            try {
                                shopCartService.updateByPrimaryKeySelective(param);
                            } catch (CustomException e) {
e.printStackTrace();
                            }
                        }
                );
            }
        }


    }


    /**
     * 更新产品的 最新价格和库存
     */
    @Async
    public void synchronousProduct(Product product, String productId) throws CustomException {
        if (product == null) {
            product = productService.findByPrimaryKey(productId);
        }
        if(product==null){
            return;
        }

        if (StringUtils.isNotBlank(product.getId())){
            List<Sku> skuList = skuService.findList(product.getId());
            boolean flog = false;
            if (CollectionUtils.isNotEmpty(skuList)) {
                //最小价格
                BigDecimal price = new BigDecimal(0);
                for (int i = 0; i < skuList.size(); i++) {
                    Sku sku = skuList.get(i);
                    if (i == 0) {
                        price = sku.getPrice();
                    } else {
                        if (price.compareTo(sku.getPrice()) > 0) {
                            price = sku.getPrice();
                        }
                    }
                }
                if (product.getMinPrice().compareTo(price) != 0) {
                    product.setMinPrice(price);
                    flog = true;
                }


                //库存
                Integer stock = 0;
                for (int i = 0; i < skuList.size(); i++) {
                    Sku sku = skuList.get(i);
                    stock = stock + sku.getStock();
                }
                if (!(product.getStock() + "").equals(stock)) {
                    product.setStock(stock);
                    flog = true;
                }

                //最小积分
                if("2".equals(product.getType())){
                    Integer minIntegral = 0;
                    for (int i = 0; i < skuList.size(); i++) {
                        Sku sku = skuList.get(i);
                        if(i==0){
                            minIntegral=sku.getIntegral();
                        }else {
                            if(minIntegral>sku.getIntegral()){
                                minIntegral=sku.getIntegral();
                            }
                        }
                    }

                    Integer minIntegral1 = product.getMinIntegral();
                    if(minIntegral1==null){
                        minIntegral1=0;
                    }
                    if(minIntegral.intValue() != minIntegral1.intValue()){
                        product.setMinIntegral(minIntegral);
                        flog = true;
                    }

                }





                if (flog) {
                    productService.updateByPrimaryKeySelective(product);
                }

        }

        }


        //


    }

    @Autowired
    private MaterialService materialService;


    //public static BigDecimal bigDecimal = new BigDecimal(1);

    /**
     * 生成积分流水
     * 店铺总成交额
     *
     * @param order
     */
    @Async
    public void paySuccess(Order order) throws CustomException {

        //每消费一元 能够获得的积分
        BigDecimal bigDecimal = materialService.findByType("1").getRate();

        BigDecimal actualPrice = order.getActualPrice();
        //BigDecimal amountIntegra = new BigDecimal(order.getIntegral());
        User user = userService.findByPrimaryKey(order.getOpenId());
        /*if (amountIntegra.compareTo(new BigDecimal(0)) > 0) {
            long l = amountIntegra.setScale(0, BigDecimal.ROUND_UP).longValue();

            Integer integral = user.getIntegral();
            user.setIntegral((integral + Integer.parseInt(l+"")));
            Integer integralTotal = user.getIntegralTotal();
            user.setIntegralTotal((integralTotal + Integer.parseInt(l+"")));
            userService.updateByPrimaryKeySelective(user);
        }*/

        String orderType = order.getOrderType();
        if (orderType.equals("1")) {
             IntegralFlow integralFlow = IntegralFlow.builder()
                    .id(Sequences.get())
                    .openId(order.getOpenId())
                    .time(new Date())
                    .type("1")
                    .title("购买商品")
                    .orderId(order.getId())
                    .fraction(actualPrice.multiply(bigDecimal).intValue())
                    .build();
            integralFlowService.insertSelective(integralFlow);
            userService.addIntegralTotal(order.getOpenId(),integralFlow.getFraction());
        } else if ("2".equals(orderType)) {

            IntegralFlow integralFlow = IntegralFlow.builder()
                    .id(Sequences.get())
                    .openId(order.getOpenId())
                    .time(new Date())
                    .type("1")
                    .title("购买视频")
                    .fraction(actualPrice.multiply(bigDecimal).intValue())
                    .orderId(order.getId())
                    .build();
            integralFlowService.insertSelective(integralFlow);
            userService.addIntegralTotal(order.getOpenId(),integralFlow.getFraction());

        } else if ("3".equals(orderType)) {
            IntegralFlow integralFlow = IntegralFlow.builder()
                    .id(Sequences.get())
                    .openId(order.getOpenId())
                    .time(new Date())
                    .type("1")
                    .title("购买文档")
                    .orderId(order.getId())
                    .fraction(actualPrice.multiply(bigDecimal).intValue())
                    .build();
            integralFlowService.insertSelective(integralFlow);
            userService.addIntegralTotal(order.getOpenId(),integralFlow.getFraction());


        } else if ("4".equals(orderType)) {
            IntegralFlow integralFlow = IntegralFlow.builder()
                    .id(Sequences.get())
                    .openId(order.getOpenId())
                    .time(new Date())
                    .type("2")
                    .title("兑换商品")
                    .orderId(order.getId())
                    .fraction(0-order.getIntegral())
                    .build();
            integralFlowService.insertSelective(integralFlow);

            userService.updateIntegral(order.getOpenId(),integralFlow.getFraction());

        } else if ("5".equals(orderType)) {
            IntegralFlow integralFlow = IntegralFlow.builder()
                    .id(Sequences.get())
                    .openId(order.getOpenId())
                    .time(new Date())
                    .type("1")
                    .title("购买会员套餐")
                    .orderId(order.getId())
                    .fraction(actualPrice.multiply(bigDecimal).intValue())
                    .build();
            integralFlowService.insertSelective(integralFlow);
            userService.addIntegralTotal(order.getOpenId(),integralFlow.getFraction());

        }

        //店铺营业额
        String manufacturerId = order.getManufacturerId();
        if (StringUtils.isNotBlank(manufacturerId)) {
            Manufacturer manufacturer = manufacturerService.findByPrimaryKey(manufacturerId);
            BigDecimal add = manufacturer.getTurnover().add(order.getActualPrice());
            manufacturer.setTurnover(add);
            manufacturerService.updateByPrimaryKeySelective(manufacturer);
        }


    }



}