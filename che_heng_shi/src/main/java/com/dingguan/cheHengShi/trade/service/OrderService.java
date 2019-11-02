package com.dingguan.cheHengShi.trade.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.trade.entity.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by zyc on 2018/12/5.
 */
public interface OrderService {


    Order findByPrimaryKey(String id);

    ApiResult<List<Order>> findList(Order order, Integer pageIndex,Integer pageSize);
     List<Order> findList(Order order, Sort sort);

    Order updateByPrimaryKeySelective(Order order) throws CustomException;

    ApiResult<Map<String, Object>> insertSelective(Order order, String ip) throws Exception;


    //重新支付
    Map payAgain(Order order, String ip) throws Exception;





    //订单支付成功回调
    Order notify(Order order) throws ParseException, CustomException;
    List<Order> notify(List<Order> orderList) throws ParseException, CustomException;



}