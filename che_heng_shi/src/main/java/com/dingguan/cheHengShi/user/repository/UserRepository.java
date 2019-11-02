package com.dingguan.cheHengShi.user.repository;

import com.dingguan.cheHengShi.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zyc on 2018/12/4.
 */
public interface UserRepository extends JpaRepository<User,String>{


    @Query(value = "select * from  `user` u where  u.open_id=?1  FOR UPDATE  ",nativeQuery = true)
    User findByOpenId(String openId);



    @Query(value = "select u.open_id ,u.nick_name ,u.head_img from  `user` u where  u.open_id=?1    ",nativeQuery = true)
    User findNickNameAndHeadImgByOpenId(String openId);


    Page<User> findByMembershipIdNotNull( Pageable pageable);


    List<User> findByConsultantAndRecommend(String consultant,String recommend);


    @Query(value = "update `user`  u set u.integral_total=integral_total + ?2 ,u.integral=integral + ?2 " +
            "where u.open_id=?1 ", nativeQuery = true)
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    public void updateIntegralTotal(String openId, Integer integralTotal);

    @Query(value = "update `user`  u set u.integral=integral + ?2 where u.open_id=?1 ", nativeQuery = true)
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    public void updateIntegral(String openId, Integer integral);






}