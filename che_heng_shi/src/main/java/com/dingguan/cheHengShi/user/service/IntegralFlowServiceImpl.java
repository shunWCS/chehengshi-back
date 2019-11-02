package com.dingguan.cheHengShi.user.service;

import com.dingguan.cheHengShi.common.constants.Constants;
import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.UpdateTool;
import com.dingguan.cheHengShi.trade.entity.Order;
import com.dingguan.cheHengShi.user.entity.BrowseRecord;
import com.dingguan.cheHengShi.user.entity.GiveVo;
import com.dingguan.cheHengShi.user.entity.IntegralFlow;
import com.dingguan.cheHengShi.user.entity.User;
import com.dingguan.cheHengShi.user.repository.BrowseRecordRepository;
import com.dingguan.cheHengShi.user.repository.IntegralFlowRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by zyc on 2018/12/22.
 */
@Service
public class IntegralFlowServiceImpl implements IntegralFlowService {


    @Autowired
    private IntegralFlowRepository integralFlowRepository;
    @Autowired
    private UserService userService;


    @Override
    public IntegralFlow findByPrimaryKey(String id) {
        return integralFlowRepository.findOne(id);
    }

    @Override
    public List<IntegralFlow> findList(IntegralFlow integralFlow ,Sort sort) {
        Example<IntegralFlow> of = Example.of(integralFlow);
        return integralFlowRepository.findAll(of,sort);
    }



    @Override
    public ApiResult<List<IntegralFlow>> findList(IntegralFlow integralFlow, PageRequest pageRequest) {

        Example<IntegralFlow> of = Example.of(integralFlow);
        Page<IntegralFlow> page = integralFlowRepository.findAll(of, pageRequest);
        ApiResult apiResult = ApiResult.pageToApiResult(page);
        return apiResult;
    }


    @Override
    public IntegralFlow insertSelective(IntegralFlow integralFlow) {
        if (StringUtils.isBlank(integralFlow.getId())) {
            integralFlow.setId(Sequences.get());
        }
        if (integralFlow.getTime() == null) {
            integralFlow.setTime(new Date());
        }
        return integralFlowRepository.save(integralFlow);
    }

    @Override
    public void deleteByPrimaryKey(String id) {
        integralFlowRepository.delete(id);
    }

    @Override
    public IntegralFlow updateByPrimaryKeySelective(IntegralFlow integralFlow) throws CustomException {
        IntegralFlow source = integralFlowRepository.findOne(integralFlow.getId());
        UpdateTool.copyNullProperties(source, integralFlow);
        return integralFlowRepository.save(integralFlow);
    }

    @Override
    public void exchangeSuccess(Order order, User user) throws CustomException {
        IntegralFlow t1 = IntegralFlow.builder()
                .id(Sequences.get())
                .openId(order.getOpenId())
                .time(new Date())
                .type("2")
                .title("兑换产品成功")
                .fraction(0 - order.getIntegral())
                .build();
        insertSelective(t1);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void give(GiveVo giveVo) throws CustomException {
        //发起人
        User produceUser = userService.findByPrimaryKey(giveVo.getProduce());
        if(produceUser.getIntegral()<giveVo.getFraction()){
            //用户的剩余积分不足
            throw new CustomException(Constants.USER_RESIDUAL_INTEGRAL_IS_INSUFFICIENT,"用户的剩余积分不足");
        }



        //获利者
        User consumerUser = userService.findByPrimaryKey(giveVo.getConsumer());
        if(consumerUser==null){
            throw new CustomException(Constants.USER_DOES_NOT_EXIST,"用户不存在");
        }

        //扣减积分
        int i = produceUser.getIntegral() - giveVo.getFraction();
        produceUser.setIntegral(i);
        userService.updateByPrimaryKeySelective(produceUser);

        int c = consumerUser.getIntegral() + giveVo.getFraction();
        consumerUser.setIntegral(c);
        userService.updateByPrimaryKeySelective(consumerUser);

        //生成流水
        IntegralFlow flow1 = IntegralFlow.builder()
                .id(Sequences.get())
                .fraction(0 - giveVo.getFraction())
                .openId(giveVo.getProduce())
                .time(new Date())
                .title("赠送积分("+consumerUser.getNickName()+")")
                .type("3")
                .build();
        integralFlowRepository.save(flow1);


        IntegralFlow flow2 = IntegralFlow.builder()
                .id(Sequences.get())
                .fraction(giveVo.getFraction())
                .openId(giveVo.getConsumer())
                .time(new Date())
                .title("获得赠送积分("+produceUser.getNickName()+")")
                .type("3")
                .build();
        integralFlowRepository.save(flow2);


    }


}