package com.dingguan.cheHengShi.user.service;


import com.dingguan.cheHengShi.common.constants.Constants;
import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.UpdateTool;
import com.dingguan.cheHengShi.user.entity.Admin;
import com.dingguan.cheHengShi.user.repository.AdminRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zyc on 2018/12/7.
 */
@Service
public class AdminServiceImpl implements AdminService {


    @Autowired
    private AdminRepository adminRepository;





    @Override
    public Admin findByPrimaryKey(String id) {
        return adminRepository.findOne(id);
    }

    @Override
    public  List<Admin>  findList(  ) {
        return adminRepository.findAll();

    }




    @Override
    public Admin insertSelective(Admin admin) throws CustomException {
        if(StringUtils.isBlank(admin.getId())){
            admin.setId(Sequences.get());
        }

        Admin byUserName = adminRepository.findByUserName(admin.getUserName());
        if(byUserName!=null){
            throw new CustomException(Constants.RESP_STATUS_BADREQUEST,"该账户已经被注册了");
        }


        return adminRepository.save(admin);
    }

    @Override
    public void deleteByPrimaryKey(String id) {
        adminRepository.delete(id);
    }

    @Override
    public Admin updateByPrimaryKeySelective(Admin admin) throws CustomException {
        Admin source = adminRepository.findOne(admin.getId());
        UpdateTool.copyNullProperties(source,admin);

        admin=adminRepository.save(admin);
        return admin;
    }

    @Override
    public Admin login(String userName, String password) throws CustomException {
        Admin admin = adminRepository.findByUserNameAndPassword(userName, password);
        if(admin==null){
            throw new CustomException(Constants.RESP_STATUS_NOAUTH,"账户或者密码错误");
        }
        return admin;

    }


}