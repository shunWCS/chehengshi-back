package com.dingguan.cheHengShi.material.repository;

import com.dingguan.cheHengShi.material.entity.MembershipViedo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by zyc on 2019/4/8.
 */
public interface MembershipViedoRepository  extends JpaRepository<MembershipViedo,String> {


    @Query(value = "select count(id) from `membership_viedo` where open_id=?1 and status=?2 ", nativeQuery=true)
    Integer findCountByOpenIdAndStatus(String openId,String status);
}