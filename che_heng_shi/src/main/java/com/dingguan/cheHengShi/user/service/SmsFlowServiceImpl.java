package com.dingguan.cheHengShi.user.service;

import com.dingguan.cheHengShi.common.constants.Constants;
import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.common.utils.RandomNumberCode;
import com.dingguan.cheHengShi.common.utils.Sequences;

import com.dingguan.cheHengShi.common.utils.SmsSample;
import com.dingguan.cheHengShi.common.utils.UpdateTool;
import com.dingguan.cheHengShi.user.entity.SmsFlow;
import com.dingguan.cheHengShi.user.repository.SmsFlowRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zyc on 2018/11/30.
 */
@Service
public class SmsFlowServiceImpl implements SmsFlowService {




    @Autowired
    private SmsFlowRepository smsFlowRepository;





    @Override
    public SmsFlow findByPrimaryKey(String id) {
        return smsFlowRepository.findOne(id);
    }

    @Override
    public ApiResult<List<SmsFlow>> findList(SmsFlow smsFlow, PageRequest pageRequest) {



        Example<SmsFlow> of = Example.of(smsFlow);


        Page<SmsFlow> page = smsFlowRepository.findAll(of, pageRequest);
        ApiResult apiResult = ApiResult.pageToApiResult(page);
        return apiResult;


    }

    @Override
    public List<SmsFlow> findList(SmsFlow smsFlow, Sort sort) {
        Example<SmsFlow> of = Example.of(smsFlow);
        return smsFlowRepository.findAll(of, sort);
    }


    @Override
    public SmsFlow insertSelective(SmsFlow smsFlow) throws CustomException {
        if(StringUtils.isBlank(smsFlow.getId())){
            smsFlow.setId(Sequences.get());
        }

        String code = RandomNumberCode.randomNo(6);
        String s = SmsSample.sendSMSCheck(smsFlow.getPhone(), code);
        if(!s.equals("0")){
            //发送失败
            throw new CustomException("发送短信异常");
        }

        smsFlow.setStatus("1");
        smsFlow.setCode(code);
        smsFlow.setTime(new Date());

        return smsFlowRepository.save(smsFlow);
    }

    @Override
    public void deleteByPrimaryKey(String id) {
        smsFlowRepository.delete(id);
    }

    @Override
    public SmsFlow updateByPrimaryKeySelective(SmsFlow smsFlow) throws CustomException {
        SmsFlow source = smsFlowRepository.findOne(smsFlow.getId());
        UpdateTool.copyNullProperties(source, smsFlow);


        return smsFlowRepository.save(smsFlow);
    }

    @Override
    public void checkCode(String code, String phone,String openId) throws CustomException {
        SmsFlow s = smsFlowRepository.findByOpenIdAndCodeAndPhoneAndStatus(openId, code, phone, "1");

        if(s==null){
            throw new CustomException(Constants.SMS_PHONE_CODE_CECHK_FILE,"短信校验码错误");
        }
        long l = (new Date().getTime() - s.getTime().getTime()) / (1000 * 60);
        if(l>30){
            throw new CustomException(Constants.SMS_PHONE_CODE_CECHK_FILE,"短信校验码错误");
        }
        s.setStatus("2");
        smsFlowRepository.save(s);

    }


}