package com.dingguan.cheHengShi.trade.repository;

import com.dingguan.cheHengShi.trade.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by zyc on 2018/12/5.
 */
public interface OrderRepository  extends JpaRepository<Order,String> {


List<Order> findByOpenIdAndStatusInAndOrderTypeIn(String openId,List<String> statusList,List<String> orderTypeList);
List<Order> findByOpenIdAndStatusInAndOrderTypeInAndManufacturerId(String openId,List<String> statusList,List<String> orderTypeList,String manufacturerId);


}