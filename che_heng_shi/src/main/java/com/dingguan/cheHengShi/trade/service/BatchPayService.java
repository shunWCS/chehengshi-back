package com.dingguan.cheHengShi.trade.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.trade.entity.BatchPay;
import com.dingguan.cheHengShi.user.entity.Address;

import java.io.IOException;
import java.util.List;

/**
 * Created by zyc on 2018/12/26.
 */
public interface BatchPayService {

    BatchPay findByPrimaryKey(String id);

    List<BatchPay> findList();

    BatchPay insertSelective(BatchPay batchPay)  ;

    void deleteByPrimaryKey(String id);

    BatchPay updateByPrimaryKeySelective(BatchPay batchPay) throws CustomException;

}