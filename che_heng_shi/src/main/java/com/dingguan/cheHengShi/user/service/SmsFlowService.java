package com.dingguan.cheHengShi.user.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.user.entity.SmsFlow;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Created by zyc on 2018/11/30.
 */
public interface SmsFlowService {


    SmsFlow findByPrimaryKey(String id);

    ApiResult<List<SmsFlow>> findList(SmsFlow smsFlow, PageRequest pageable);
   List<SmsFlow> findList(SmsFlow smsFlow, Sort sort);

    SmsFlow insertSelective(SmsFlow smsFlow) throws CustomException;

    void deleteByPrimaryKey(String id);

    SmsFlow updateByPrimaryKeySelective(SmsFlow smsFlow) throws CustomException;

    void checkCode(String code, String phone, String openId) throws CustomException;


}