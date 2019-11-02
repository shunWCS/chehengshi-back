package com.dingguan.cheHengShi.user.service;



import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.user.entity.Admin;

import java.util.List;

/**
 * Created by zyc on 2018/12/7.
 */
public interface AdminService {

    Admin findByPrimaryKey(String id);

     List<Admin>  findList();

    Admin insertSelective(Admin admin) throws CustomException;

    void deleteByPrimaryKey(String id);

    Admin updateByPrimaryKeySelective(Admin admin) throws CustomException;

    Admin login(String userName, String password) throws CustomException;

}