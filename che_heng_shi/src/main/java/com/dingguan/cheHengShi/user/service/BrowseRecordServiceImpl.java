package com.dingguan.cheHengShi.user.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.common.utils.DateUtil;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.UpdateTool;
import com.dingguan.cheHengShi.product.entity.File;
import com.dingguan.cheHengShi.product.repository.FileRepository;
import com.dingguan.cheHengShi.user.entity.BrowseRecord;
import com.dingguan.cheHengShi.user.repository.BrowseRecordRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zyc on 2018/12/20.
 */
@Service
public class BrowseRecordServiceImpl implements BrowseRecordService {

    @Autowired
    private BrowseRecordRepository browseRecordRepository;


    @Override
    public BrowseRecord findByPrimaryKey(String id) {
        BrowseRecord journalism = browseRecordRepository.findOne(id);

        return journalism;
    }

    @Override
    public List<BrowseRecord> findList() {
        Sort sort = new Sort(Sort.Direction.DESC, "time");
        return browseRecordRepository.findAll(sort);
    }

    @Override
    public List<BrowseRecord> findList(String openId) {
        Sort sort = new Sort(Sort.Direction.DESC, "time");
        BrowseRecord build = BrowseRecord.builder()
                .openId(openId)
                .build();
        Example<BrowseRecord> of = Example.of(build);
        return browseRecordRepository.findAll(of,sort);

    }

    @Override
    public ApiResult<List<BrowseRecord>> findList(String openId, Pageable pageable) {
        BrowseRecord build = BrowseRecord.builder()
                .openId(openId)
                .build();
        Example<BrowseRecord> of = Example.of(build);
        Page<BrowseRecord> page = browseRecordRepository.findAll(of, pageable);

        ApiResult apiResult = ApiResult.pageToApiResult(page);
        return apiResult;
    }


    @Override
    public BrowseRecord insertSelective(BrowseRecord browseRecord) {
        if (StringUtils.isBlank(browseRecord.getId())) {
            browseRecord.setId(Sequences.get());
        }
        Date now = new Date();
        if (browseRecord.getTime() == null) {
            browseRecord.setTime(now);
        }
        BrowseRecord topByProductIdOrderByTimeDesc = browseRecordRepository.findTopByProductIdAndOpenIdOrderByTimeDesc(browseRecord.getProductId(),browseRecord.getOpenId());
        if(topByProductIdOrderByTimeDesc!=null){
            if(DateUtil.isSameDay(now,topByProductIdOrderByTimeDesc.getTime())){
                deleteByPrimaryKey(topByProductIdOrderByTimeDesc.getId());
            }

        }


        return browseRecordRepository.save(browseRecord);
    }

    @Override
    public void deleteByPrimaryKey(String id) {
        browseRecordRepository.delete(id);
    }

    @Override
    public BrowseRecord updateByPrimaryKeySelective(BrowseRecord browseRecord) throws CustomException {
        BrowseRecord source = browseRecordRepository.findOne(browseRecord.getId());
        UpdateTool.copyNullProperties(source, browseRecord);
        return browseRecordRepository.save(browseRecord);
    }


}