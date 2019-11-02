package com.dingguan.cheHengShi.user.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.product.entity.File;
import com.dingguan.cheHengShi.user.entity.BrowseRecord;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by zyc on 2018/12/20.
 */
public interface BrowseRecordService {

    BrowseRecord findByPrimaryKey(String id);

    List<BrowseRecord> findList();

    List<BrowseRecord> findList(String openId);

    ApiResult<List<BrowseRecord>> findList(String openId, Pageable pageable);


    BrowseRecord insertSelective(BrowseRecord browseRecord);

    void deleteByPrimaryKey(String id);

    BrowseRecord updateByPrimaryKeySelective(BrowseRecord browseRecord) throws CustomException;





}