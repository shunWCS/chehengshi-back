package com.dingguan.cheHengShi.product.repository;

import com.dingguan.cheHengShi.product.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zyc on 2018/12/20.
 */
public interface FileRepository extends JpaRepository<File,String> {


    @Query(value = "select max(sort) from `file` ", nativeQuery=true)
    Integer findMaxSort();


    @Query(value = "update `file` f set f.clicks=clicks + ?2 where f.id=?1 ", nativeQuery = true)
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    public void updateClicks(String id, Integer num);

    @Query(value = "update `file` f set f.favorite=?2 where f.id=?1 ", nativeQuery = true)
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    public void updateFavorite(String id,Integer favorite);

    @Query(value = "update `file` f set f.sales=sales + ?2 where f.id=?1 ", nativeQuery = true)
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    public void updateSales(String id, Integer num);

    @Query(value = "select * from `file` f where type_id=?1 order By sort", nativeQuery=true)
    List<File> findByTypeIdOrderBySort(String typeId);

    @Query(value = "select * from `file` f  order By sort", nativeQuery=true)
    List<File> findList();

    @Query(value = "select * from `file` f where recommend=?1 order By sort", nativeQuery=true)
    List<File> findByRecommendOrderBySort(String recommend);


    @Query(value = "select * from `file` f where type_id=?1 and title like %?2% order By sort", nativeQuery=true)
    List<File> findByTypeIdAndTitleLikeOrderBySort(String typeId,String title);


    @Query(value = "select * from `file` f where  title like %?1% order By sort", nativeQuery=true)
    List<File> findByTitleLikeOrderBySort( String title);


    @Query(value = "select * from `file` f where id =?1", nativeQuery=true)
    File findByPrimaryKey(String id);

    @Query(value = "delete  from `file`  where id =?1", nativeQuery=true)
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    void deleteByPrimaryKey(String id);


}