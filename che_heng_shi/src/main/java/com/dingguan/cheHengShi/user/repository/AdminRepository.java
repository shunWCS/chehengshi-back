package com.dingguan.cheHengShi.user.repository;

import com.dingguan.cheHengShi.user.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by zyc on 2018/12/7.
 */
public interface AdminRepository extends JpaRepository<Admin,String> {


    Admin findByUserNameAndPassword(String userName, String password);


    Admin findByUserName(String userName);


}