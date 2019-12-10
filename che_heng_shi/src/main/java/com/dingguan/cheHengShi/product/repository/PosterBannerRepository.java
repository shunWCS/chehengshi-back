package com.dingguan.cheHengShi.product.repository;

import com.dingguan.cheHengShi.product.entity.PosterBanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PosterBannerRepository extends JpaRepository<PosterBanner,String> {

    @Query(value = "select * from `poster_banner`", nativeQuery=true)
    List<PosterBanner> findListAll();

    @Query(value = "select * from `poster_banner` f where type_id=?1 and title like %?2% ", nativeQuery=true)
    List<PosterBanner> findByTypeIdAndTitleLike(String typeId, String title);

    @Query(value = "select * from `poster_banner` f where type_id=?1 ", nativeQuery=true)
    List<PosterBanner> findByTypeId(String typeId);

    @Query(value = "select * from `course` f where  title like %?1% ", nativeQuery=true)
    List<PosterBanner> findByTitleLike(String title);
}
