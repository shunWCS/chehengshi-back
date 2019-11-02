package com.dingguan.cheHengShi.user.service;

import com.dingguan.cheHengShi.common.constants.Constants;
import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.UpdateTool;
import com.dingguan.cheHengShi.user.entity.Admin;
import com.dingguan.cheHengShi.user.entity.Manufacturer;
import com.dingguan.cheHengShi.user.repository.AdminRepository;
import com.dingguan.cheHengShi.user.repository.ManufacturerRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by zyc on 2018/12/21.
 */
@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    @Autowired
    private ManufacturerRepository manufacturerRepository;





    @Override
    public Manufacturer findByPrimaryKey(String id) {
        return manufacturerRepository.findOne(id);
    }

    @Override
    public List<Manufacturer> findList(Manufacturer manufacturer, Sort sort) {
        Example<Manufacturer> of = Example.of(manufacturer);
        return manufacturerRepository.findAll(of,sort);
    }

    @Override
    public ApiResult<List<Manufacturer>> findList(Manufacturer manufacturer, PageRequest pageRequest) {
        Example<Manufacturer> of = Example.of(manufacturer);
        Page<Manufacturer> page = manufacturerRepository.findAll(of, pageRequest);
        return ApiResult.pageToApiResult(page);
    }


    @Override
    public Manufacturer insertSelective(Manufacturer manufacturer)   {
        if(StringUtils.isBlank(manufacturer.getId())){
            manufacturer.setId(Sequences.get());
        }
        if(StringUtils.isBlank(manufacturer.getStatus())){
            manufacturer.setStatus("1");
        }
       if(manufacturer.getTime()==null){
            manufacturer.setTime(new Date());
       }
       if(StringUtils.isBlank(manufacturer.getPattern())){
           manufacturer.setPattern("1");
       }
       if(manufacturer.getTurnover()==null){
           manufacturer.setTurnover(new BigDecimal(0));
       }


        return manufacturerRepository.save(manufacturer);
    }

    @Override
    public void deleteByPrimaryKey(String id) {
        manufacturerRepository.delete(id);
    }

    @Override
    public Manufacturer updateByPrimaryKeySelective(Manufacturer manufacturer) throws CustomException {
        Manufacturer source = manufacturerRepository.findOne(manufacturer.getId());
        UpdateTool.copyNullProperties(source,manufacturer);
        manufacturer=manufacturerRepository.save(manufacturer);
        return manufacturer;
    }

    @Override
    public Integer findMaxSort() {
        return manufacturerRepository.findMaxSort();
    }

    @Override
    public Manufacturer giveOrders(Manufacturer manufacturer) throws CustomException {
        Manufacturer old = manufacturerRepository.findOne(manufacturer.getId());
        if(!"1".equals(old.getStatus())){
            throw new CustomException(Constants.RESP_STATUS_BADREQUEST,"不在审批状态中");
        }
        UpdateTool.copyNullProperties(old,manufacturer);
        if("2".equals(manufacturer.getStatus())){
            //通过
        }else if("3".equals(manufacturer.getStatus())){
            //不通过
        }
        return manufacturerRepository.save(manufacturer);


    }


}