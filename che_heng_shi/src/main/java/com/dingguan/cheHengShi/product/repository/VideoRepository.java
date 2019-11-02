package com.dingguan.cheHengShi.product.repository;

import com.dingguan.cheHengShi.product.entity.File;
import com.dingguan.cheHengShi.product.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zyc on 2018/12/20.
 */
public interface VideoRepository extends JpaRepository<Video,String> {

    @Query(value = "select max(sort) from `video` ", nativeQuery=true)
    Integer findMaxSort();


    @Query(value = "update `video` v set v.clicks=clicks + ?2 where v.id=?1 ", nativeQuery = true)
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    public void updateClicks(String id, Integer num);

    @Query(value = "update `video` v set v.favorite=?2 where v.id=?1 ", nativeQuery = true)
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    public void updateFavorite(String id, Integer favorite);

    @Query(value = "update `video` v set v.sales=sales + ?2 where v.id=?1 ", nativeQuery = true)
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    public void updateSales(String id, Integer num);

    @Query(value = "select * from `video`   where type_id=?1 order By sort", nativeQuery=true)
    List<Video> findByTypeIdOrderBySort(String typeId);

    @Query(value = "select * from `video`    order By sort", nativeQuery=true)
    List<Video> findList();

    @Query(value = "select * from `video`  where flash_video=?1 and recommend=?2  order By sort", nativeQuery=true)
    List<Video> findByFlashVideoAndRecommendOrderBySort(String flashVideo,String recommend);

    @Query(value = "select * from `video`  where flash_video=?1  order By sort", nativeQuery=true)
    List<Video> findByFlashVideoOrderBySort(String flashVideo);


    @Query(value = "select * from `video` f where id =?1", nativeQuery=true)
    Video findByPrimaryKey(String id);

    @Query(value = "delete  from `video`  where id =?1", nativeQuery=true)
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    void deleteByPrimaryKey(String id);


    @Query(value = "select * from `video`   where type_id=?1 and title like %?2%         order By sort",
            nativeQuery=true)
    List<Video> findByTypeIdAndTitleLikeOrderBySort(String tyepId, String title);


    @Query(value = "select * from `video`   where  title like %?1%         order By sort", nativeQuery=true)
    List<Video> findByTitleLikeOrderBySort(String title);
}