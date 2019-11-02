package com.dingguan.cheHengShi.trade.dao;


import com.dingguan.cheHengShi.trade.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    int deleteByPrimaryKey(String id);

    List<Order> findList(
            @Param("openId")String openId,
            @Param("transactionid")String transactionid,
            @Param("status")String status,
            @Param("orderType")String orderType,
            @Param("manufacturerId")String manufacturerId,
            @Param("pageNo")Integer pageNo,
            @Param("pageSize")Integer pageSize
    );





    Integer findCount(
            @Param("openId")String openId,
            @Param("transactionid")String transactionid,
            @Param("status")String status,
            @Param("orderType")String orderType,
            @Param("manufacturerId")String manufacturerId
    );


}