package com.dingguan.cheHengShi.trade.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.UpdateTool;
import com.dingguan.cheHengShi.trade.entity.BatchPay;
import com.dingguan.cheHengShi.trade.repository.BatchPayRepository;
import com.dingguan.cheHengShi.user.entity.Manufacturer;
import com.dingguan.cheHengShi.user.repository.ManufacturerRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zyc on 2018/12/26.
 */
@Service
public class BatchPayServiceImpl implements BatchPayService {

    @Autowired
    private BatchPayRepository batchPayRepository;





    @Override
    public BatchPay findByPrimaryKey(String id) {
        return batchPayRepository.findOne(id);
    }

    @Override
    public List<BatchPay> findList() {
        return batchPayRepository.findAll();
    }


    @Override
    public BatchPay insertSelective(BatchPay batchPay)   {
        if(StringUtils.isBlank(batchPay.getId())){
            batchPay.setId(Sequences.get());
        }

        if(batchPay.getTime()==null){
            batchPay.setTime(new Date());
        }

        return batchPayRepository.save(batchPay);
    }

    @Override
    public void deleteByPrimaryKey(String id) {
        batchPayRepository.delete(id);
    }

    @Override
    public BatchPay updateByPrimaryKeySelective(BatchPay batchPay) throws CustomException {
        BatchPay source = batchPayRepository.findOne(batchPay.getId());
        UpdateTool.copyNullProperties(source,batchPay);
        batchPay=batchPayRepository.save(batchPay);
        return batchPay;
    }

}