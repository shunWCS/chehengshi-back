package com.dingguan.cheHengShi.product.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.product.entity.File;
import com.dingguan.cheHengShi.product.entity.MembershipPackage;

import java.util.List;

/**
 * Created by zyc on 2018/12/20.
 */
public interface MembershipPackageService {

    MembershipPackage findByPrimaryKey(String id);

    List<MembershipPackage> findList();

    MembershipPackage insertSelective(MembershipPackage membershipPackage);

    void deleteByPrimaryKey(String id);

    MembershipPackage updateByPrimaryKeySelective(MembershipPackage membershipPackage) throws CustomException;


    void updateSales(String id, Integer num);


}