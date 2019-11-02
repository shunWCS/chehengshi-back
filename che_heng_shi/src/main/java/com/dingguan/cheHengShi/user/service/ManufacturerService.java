package com.dingguan.cheHengShi.user.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.user.entity.Admin;
import com.dingguan.cheHengShi.user.entity.Manufacturer;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Created by zyc on 2018/12/21.
 */
public interface ManufacturerService {


    Manufacturer findByPrimaryKey(String id);

    List<Manufacturer> findList(Manufacturer manufacturer, Sort sort);
    ApiResult<List<Manufacturer>> findList(Manufacturer manufacturer, PageRequest pageRequest);

    Manufacturer insertSelective(Manufacturer manufacturer) ;

    void deleteByPrimaryKey(String id);

    Manufacturer updateByPrimaryKeySelective(Manufacturer manufacturer) throws CustomException;

    Integer findMaxSort();

    //审批
    Manufacturer giveOrders(Manufacturer manufacturer)throws CustomException;


}