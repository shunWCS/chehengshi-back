package com.dingguan.cheHengShi.user.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;

import com.dingguan.cheHengShi.product.entity.MembershipPackage;
import com.dingguan.cheHengShi.trade.entity.Order;
import com.dingguan.cheHengShi.user.entity.User;
import com.dingguan.cheHengShi.user.entity.UserVo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zyc on 2018/12/4.
 */
public interface UserService {


    User findByPrimaryKey(String id);

    ApiResult<List<User>> findList(User user, PageRequest pageRequest);

    List<User> findList(User user, Sort sort);

    User insertSelective(User user) throws CustomException, IOException;

    void deleteByPrimaryKey(String id);

    User updateByPrimaryKeySelective(User user) throws CustomException;

    User findNickNameAndHeadImgByOpenId(String openId);

    //购买/续了会员套餐
    User payMembershipPackage(MembershipPackage membershipPackage,User user);

    User checkUser(User user);

    List<User> checkUser(List<User> userList);

    List<User> findUserBymembershipId( Pageable pageable);


    void check(Order order, User user) throws CustomException;

    void exchangeSuccess(Order order, User user) throws CustomException;

    List<User> findByConsultantAndRecommend(String consultant,String recommend);;

    User register(UserVo userVo) throws CustomException, IOException;

    //新增总积分
    void addIntegralTotal(String openId ,Integer integralTotal);

    //控制积分
    public void updateIntegral(String openId, Integer integral);
}