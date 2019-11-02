package com.dingguan.cheHengShi.user.repository;

import com.dingguan.cheHengShi.user.entity.BrowseRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by zyc on 2018/12/20.
 */
public interface BrowseRecordRepository extends JpaRepository<BrowseRecord,String> {

    List<BrowseRecord> findByOpenIdOrderByTimeDesc(String openId, Sort sort);

    Page<BrowseRecord> findByOpenIdOrderByTimeDesc(String openId, Pageable pageable);

    BrowseRecord findTopByProductIdAndOpenIdOrderByTimeDesc(String productId,String openId);


}