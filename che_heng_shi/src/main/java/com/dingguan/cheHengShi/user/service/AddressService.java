package com.dingguan.cheHengShi.user.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.user.entity.Address;

import java.io.IOException;
import java.util.List;

/**
 * Created by zyc on 2018/12/5.
 */
public interface AddressService {


    Address findByPrimaryKey(String id);

    List<Address> findList(Address address);

    Address insertSelective(Address address) throws CustomException, IOException;

    void deleteByPrimaryKey(String id);

    Address updateByPrimaryKeySelective(Address address) throws CustomException;


}