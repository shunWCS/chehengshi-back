package com.dingguan.cheHengShi.user.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.trade.entity.Order;
import com.dingguan.cheHengShi.user.entity.Admin;
import com.dingguan.cheHengShi.user.entity.GiveVo;
import com.dingguan.cheHengShi.user.entity.IntegralFlow;
import com.dingguan.cheHengShi.user.entity.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Created by zyc on 2018/12/22.
 */
public interface IntegralFlowService {

    IntegralFlow findByPrimaryKey(String id);

    ApiResult<List<IntegralFlow>> findList(IntegralFlow integralFlow , PageRequest pageRequest);

    List<IntegralFlow> findList(IntegralFlow integralFlow , Sort sort);

    IntegralFlow insertSelective(IntegralFlow integralFlow)  ;

    void deleteByPrimaryKey(String id);

    IntegralFlow updateByPrimaryKeySelective(IntegralFlow integralFlow) throws CustomException;

    //兑换了产品
    void exchangeSuccess(Order order, User user) throws CustomException;

    //购买了产品

    //赠送
    void give(GiveVo giveVo) throws CustomException;


}