package com.dingguan.cheHengShi.user.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.UpdateTool;
import com.dingguan.cheHengShi.home.mapper.CourseApplyMapper;
import com.dingguan.cheHengShi.job.AsynchronousJob;
import com.dingguan.cheHengShi.user.entity.Address;
import com.dingguan.cheHengShi.user.repository.AddressRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by zyc on 2018/12/5.
 */
@Service
public class AddressServiceImpl implements AddressService {


    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    @Lazy
    private AsynchronousJob asynchronousJob;
    @Autowired
    private CourseApplyMapper courseApplyMapper;


    @Override
    public Address findByPrimaryKey(String id) {
        return addressRepository.findOne(id);
    }

    @Override
    public List<Address> findList(Address address) {
        //Example<Address> of = Example.of(address  );
        //List<Address> addressList = addressRepository.findAll(of);
        List<Address> addressList = courseApplyMapper.findAddress(address.getOpenId());
        return addressList;
    }


    @Override
    public Address insertSelective(Address address) throws CustomException, IOException {


        if (StringUtils.isBlank(address.getId())) {
            address.setId(Sequences.get());
        }


        if (address.getMadeByImperialOrder() == null) {
            address.setMadeByImperialOrder("1");
        }


        address = addressRepository.save(address);


        if(address.getMadeByImperialOrder().equals("2")){
            asynchronousJob.consumptionUpgrading(address.getOpenId(),address.getId());
        }
        return address;


    }

    @Override
    public void deleteByPrimaryKey(String id) {
        addressRepository.delete(id);
    }

    @Override
    public Address updateByPrimaryKeySelective(Address address) throws CustomException {
        Address source = addressRepository.findOne(address.getId());
        UpdateTool.copyNullProperties(source,address);

        if(StringUtils.isNotBlank(address.getMadeByImperialOrder())){
            if(address.getMadeByImperialOrder().equals("2")){
                asynchronousJob.consumptionUpgrading(address.getOpenId(),address.getId());
            }
        }


        return addressRepository.save(address);
    }


}