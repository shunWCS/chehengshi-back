package com.dingguan.cheHengShi.trade.repository;

import com.dingguan.cheHengShi.trade.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by zyc on 2018/12/5.
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {

    List<OrderDetail> findByOrderId(String orderId);




}