package com.dingguan.cheHengShi.trade.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.trade.entity.OrderDetail;

import java.util.List;

/**
 * Created by zyc on 2018/12/5.
 */
public interface OrderDetailService {


    OrderDetail findByPrimaryKey(String id);

    List<OrderDetail> findList(OrderDetail orderDetail);
    List<OrderDetail> findList();

    OrderDetail insertSelective(OrderDetail orderDetail)  ;

    void deleteByPrimaryKey(String id);

    OrderDetail updateByPrimaryKeySelective(OrderDetail orderDetail) throws CustomException;

    List<OrderDetail> findByOrderId(String orderId);

}