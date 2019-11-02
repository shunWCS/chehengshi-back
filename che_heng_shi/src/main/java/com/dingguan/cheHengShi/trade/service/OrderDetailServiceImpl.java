package com.dingguan.cheHengShi.trade.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.UpdateTool;
import com.dingguan.cheHengShi.trade.entity.OrderDetail;
import com.dingguan.cheHengShi.trade.repository.OrderDetailRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zyc on 2018/12/5.
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService {


    @Autowired
    private OrderDetailRepository orderDetailRepository;



    @Override
    public OrderDetail findByPrimaryKey(String id) {
        return orderDetailRepository.findOne(id);
    }

    @Override
    public List<OrderDetail> findList(OrderDetail orderDetail) {
        Example<OrderDetail> of = Example.of(orderDetail);
        return orderDetailRepository.findAll(of);
    }


    @Override
    public List<OrderDetail> findList() {
        return orderDetailRepository.findAll();
    }


    @Override
    public OrderDetail insertSelective(OrderDetail orderDetail)  {
        if(StringUtils.isBlank(orderDetail.getId())){
            orderDetail.setId(Sequences.get());
        }
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public void deleteByPrimaryKey(String id) {
        orderDetailRepository.delete(id);
    }

    @Override
    public OrderDetail updateByPrimaryKeySelective(OrderDetail orderDetail) throws CustomException {
        OrderDetail source = orderDetailRepository.findOne(orderDetail.getId());
        UpdateTool.copyNullProperties(source,orderDetail);
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public List<OrderDetail> findByOrderId(String orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }


}