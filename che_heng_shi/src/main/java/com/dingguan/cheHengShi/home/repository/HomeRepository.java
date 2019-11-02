package com.dingguan.cheHengShi.home.repository;

import com.dingguan.cheHengShi.product.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * @author: czh
 * @Date: 2019/9/5 20:56
 */
public interface HomeRepository extends JpaRepository<Video, String> {

    @Query(value = "SELECT (SELECT if(f.clicks > ?1,'hot','') hot) hot,(SELECT if((f.time< NOW() and ADDDATE(f.time,'+2 days') > NOW()),'news','') news) news,(SELECT '文档') type,f.id id,f.banner banner,f.clicks clicks,f.introduce introduce,f.sort sort,f.time time,f.title title,f.recommend recommend,'' video FROM `file` f where f.recommend = ?2 " +
            "UNION SELECT (SELECT if(j.clicks > ?1,'hot','') hot) hot,(SELECT if((j.time< NOW() and ADDDATE(j.time,'+2 days') > NOW()),'news','') news) news,(SELECT '新闻') type,j.id id,j.banner banner,j.clicks clicks,j.introduce introduce,j.sort sort,j.time time,j.title title,j.recommend recommend,'' video FROM `journalism` j where j.recommend = ?2 " +
            "UNION SELECT (SELECT if(v.clicks > ?1,'hot','') hot) hot,(SELECT if((v.time< NOW() and ADDDATE(v.time,'+2 days') > NOW()),'news','') news) news,(SELECT '视频') type,v.id id,v.banner banner,v.clicks clicks,v.introduce introduce,v.sort sort,v.time time,v.title title,v.recommend recommend,v.video video FROM `video` v where v.recommend = ?2 ", nativeQuery=true)
    List<Map<String, Object>> findByAllList(Integer clicks, String recommend);

}
