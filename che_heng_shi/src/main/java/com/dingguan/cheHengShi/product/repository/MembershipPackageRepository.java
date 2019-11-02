package com.dingguan.cheHengShi.product.repository;

import com.dingguan.cheHengShi.product.entity.MembershipPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zyc on 2018/12/20.
 */
public interface MembershipPackageRepository extends JpaRepository<MembershipPackage,String> {


    @Query(value = "update membership_package m set m.sales=sales + ?2 where m.id=?1 ", nativeQuery = true)
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    public void updateSales(String id, Integer num);



}