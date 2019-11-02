package com.dingguan.cheHengShi.material.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.material.entity.FlashView;
import com.dingguan.cheHengShi.material.entity.MembershipViedo;

import java.util.List;

/**
 * Created by zyc on 2019/4/8.
 */
public interface MembershipViedoService {


    MembershipViedo findByPrimaryKey(String id);

    List<MembershipViedo> findList(MembershipViedo  membershipViedo);

    MembershipViedo insertSelective(MembershipViedo membershipViedo);

    void deleteByPrimaryKey(String id);

    MembershipViedo updateByPrimaryKeySelective(MembershipViedo membershipViedo) throws CustomException;

    Integer findCountByOpenIdAndStatus(String openId,String status);


    ApiResult getVideoDetail(String type,String id,String openId) throws CustomException;

}