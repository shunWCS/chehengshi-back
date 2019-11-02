package com.dingguan.cheHengShi.user.repository;

import com.dingguan.cheHengShi.user.entity.SmsFlow;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by zyc on 2018/11/30.
 */

public interface SmsFlowRepository extends JpaRepository<SmsFlow,String> {

    SmsFlow findByOpenIdAndCodeAndPhoneAndStatus(String openId, String code, String phone, String status);

}