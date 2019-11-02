package com.dingguan.cheHengShi.user.service;

import com.dingguan.cheHengShi.common.constants.Constants;
import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.common.utils.DateUtil;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.TypeConversionUtils;
import com.dingguan.cheHengShi.common.utils.UpdateTool;
import com.dingguan.cheHengShi.common.utils.Util;
import com.dingguan.cheHengShi.job.AsynchronousJob;

import com.dingguan.cheHengShi.material.entity.MembershipViedo;
import com.dingguan.cheHengShi.material.service.MembershipViedoService;
import com.dingguan.cheHengShi.product.entity.MembershipPackage;
import com.dingguan.cheHengShi.trade.entity.Order;
import com.dingguan.cheHengShi.user.entity.IntegralFlow;
import com.dingguan.cheHengShi.user.entity.User;
import com.dingguan.cheHengShi.user.entity.UserVo;
import com.dingguan.cheHengShi.user.repository.UserRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zyc on 2018/12/4.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IntegralFlowService integralFlowService;
    @Autowired
    private SmsFlowService smsFlowService;
    @Autowired
    private MembershipViedoService membershipViedoService;


    @Override
    public User findByPrimaryKey(String id) {
        return checkUser(userRepository.findOne(id));
    }

    @Override
    public ApiResult<List<User>> findList(User user, PageRequest pageRequest) {
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withMatcher("nickName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("phone", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<User> of = Example.of(user, matcher);
        Page<User> page = userRepository.findAll(of, pageRequest);
        ApiResult<List<User>> apiResult = ApiResult.pageToApiResult(page);

        apiResult.setData(checkUser(apiResult.getData()));

        return apiResult;
    }

    @Override
    public List<User> findList(User user, Sort sort) {
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withMatcher("nickName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("phone", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<User> of = Example.of(user, matcher);
        return checkUser(userRepository.findAll(of, sort));
    }


    @Override
    public User insertSelective(User user) throws CustomException, IOException {
        User local = findByPrimaryKey(user.getOpenId());
        /*if(local!=null){
            throw new CustomException(Constants.RESP_STATUS_BADREQUEST,"该用户已经注册过了");
        }*/

        if (user.getBalance() == null) {
            user.setBalance(new BigDecimal(0));
        }

        if (user.getRegistrationTime() == null) {
            user.setRegistrationTime(new Date());
        }
        if (user.getIntegral() == null) {
            user.setIntegral(0);
        }
        if (user.getIntegralTotal() == null) {
            user.setIntegralTotal(0);
        }
        if (user.getConsultant() == null) {
            user.setConsultant("1");
        }

        if (user.getRecommend() == null) {
            user.setRecommend("1");
        }


        User source = userRepository.findOne(user.getOpenId());
        if (source != null) {
            UpdateTool.copyNullProperties(source, user);

        }
        user = userRepository.save(user);
        return user;


    }

    @Override
    public void deleteByPrimaryKey(String id) {
        userRepository.delete(id);
    }

    @Override
    public User updateByPrimaryKeySelective(User user) throws CustomException {

        User source = userRepository.findOne(user.getOpenId());
        UpdateTool.copyNullProperties(source, user);
        return userRepository.save(user);
    }

    @Override
    public User findNickNameAndHeadImgByOpenId(String openId) {
        return checkUser(userRepository.findByOpenId(openId));
    }

    @Override
    public User payMembershipPackage(MembershipPackage membershipPackage, User user) {


        user.setMembershipId(membershipPackage.getId());
        user.setRoleName(membershipPackage.getIntroduce());
        Date memberStartTime = user.getMemberStartTime();
        if (memberStartTime == null) {
            memberStartTime = new Date();
            user.setMemberStartTime(memberStartTime);
        }
        Date memberStopTime = user.getMemberStopTime();
        if (memberStopTime == null) {
            user.setMemberStopTime(memberStartTime);
            memberStopTime = memberStartTime;
        }
        Integer duration = membershipPackage.getDuration();
        memberStopTime = DateUtil.addDays(memberStopTime, duration);
        user.setMemberStopTime(memberStopTime);

        Integer videoNum;

        if (duration > 300) {
            //包年
            videoNum=360;
        } else {
            //包月
            videoNum=30;
        }
        Integer totalVideoNum = user.getTotalVideoNum();
        if(totalVideoNum==null){
            totalVideoNum=0;
        }
        totalVideoNum=totalVideoNum+videoNum;
        user.setTotalVideoNum(totalVideoNum);
        return userRepository.save(user);

    }


    //   删除会员可看到视频的记录
    @Override
    public User checkUser(User user) {
        if (user != null) {
            Date memberStopTime = user.getMemberStopTime();
            Date now = new Date();
            if (memberStopTime != null) {
                if (memberStopTime.compareTo(now) < 0) {
                    //不再是会员了
                    user.setMemberStopTime(null);
                    user.setMemberStartTime(null);
                    user.setRoleName(null);
                    user.setMembershipId(null);
                    user.setTotalVideoNum(0);
                    userRepository.save(user);

                    MembershipViedo build = MembershipViedo.builder()
                            .openId(user.getOpenId())
                            .status("1")
                            .build();
                    List<MembershipViedo> membershipViedoList = membershipViedoService.findList(build);
                    if(CollectionUtils.isNotEmpty(membershipViedoList)){
                        membershipViedoList.stream().forEach(
                                param->{
                                    param.setStatus("2");
                                    try {
                                        membershipViedoService.updateByPrimaryKeySelective(param);
                                    } catch (CustomException e) {
                                        e.printStackTrace();
                                    }
                                }
                        );
                    }

                }
            }
        }
        if(Util.isEmpty(user.getRoleName())){
            user.setRoleName("");
        }
        return user;
    }

    @Override
    public List<User> checkUser(List<User> userList) {
        List<User> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(userList)) {
            for (int i = 0; i < userList.size(); i++) {
                User user = userList.get(i);
                user = checkUser(user);
                list.add(user);
            }
        }
        return list;
    }

    @Override
    public List<User> findUserBymembershipId(Pageable pageable) {
        Page<User> byMembershipId = userRepository.findByMembershipIdNotNull(pageable);
        List<User> content = byMembershipId.getContent();
        if (CollectionUtils.isNotEmpty(content)) {
            content = content.stream().collect(Collectors.toList());
            return content;
        } else {
            return null;

        }

    }

    @Override
    public void check(Order order, User user) throws CustomException {
        Integer integral = user.getIntegral();


        Integer orderIntegral = order.getIntegral();
        if (orderIntegral > integral) {
            throw new CustomException(Constants.USER_RESIDUAL_INTEGRAL_IS_INSUFFICIENT, "用户积分余额不足");
        }

    }

    @Override
    public void exchangeSuccess(Order order, User user) throws CustomException {
        Integer orderIntegral = order.getIntegral();

        //扣减积分
        Integer userIntegral = user.getIntegral() - orderIntegral;
        user.setIntegral(userIntegral);
        updateByPrimaryKeySelective(user);

        //生成流水  回调里面已经生成了
        integralFlowService.exchangeSuccess(order, user);

    }

    @Override
    public List<User> findByConsultantAndRecommend(String consultant, String recommend) {
        return userRepository.findByConsultantAndRecommend(consultant, recommend);
    }

    @Override
    public User register(UserVo userVo) throws CustomException, IOException {
        //校验code
        smsFlowService.checkCode(userVo.getCode(), userVo.getPhone(), userVo.getOpenId());

        User user = TypeConversionUtils.JavaBeanToJavaBean(userVo, User.class);
        return insertSelective(user);


    }

    @Override
    public void addIntegralTotal(String openId, Integer integralTotal) {
        System.out.println("给用户" + openId + "总改积分" + integralTotal);
        userRepository.updateIntegralTotal(openId, integralTotal);

      /*  User user = userRepository.findOne(openId);

        int i = user.getIntegralTotal() + integralTotal;
        user.setIntegralTotal(i);
        userRepository.save(user);*/

    }

    @Override
    public void updateIntegral(String openId, Integer integral) {
        System.out.println("给用户" + openId + "改积分" + integral);
        userRepository.updateIntegral(openId, integral);
    }


}