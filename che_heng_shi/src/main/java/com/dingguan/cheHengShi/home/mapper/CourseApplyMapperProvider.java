package com.dingguan.cheHengShi.home.mapper;

import com.dingguan.cheHengShi.common.resp.BaseMapperProvider;
import com.dingguan.cheHengShi.common.utils.Util;
import com.dingguan.cheHengShi.home.dto.CourseVo;
import com.dingguan.cheHengShi.home.dto.FileSearchVo;

/**
 * @author: czh
 * @Date: 2019/9/21 8:21
 */
public class CourseApplyMapperProvider extends BaseMapperProvider {

    public String findCourseByTypeId(String typeId){
        StringBuffer sql = new StringBuffer(" ");
        sql.append("SELECT c.*,(SELECT if((c.end_time > NOW()),'报名中','报名截止') state) state,\n");
        sql.append("(SELECT if((c.end_time > NOW()),2,1) stateNo) stateNo \n");
        sql.append("from course c where type_id= ").append(jointequals(typeId)).append(" ORDER BY stateNo DESC,begin_time DESC");
        return sql.toString();
    }

    public String searchCourse(CourseVo courseVo){
        StringBuffer sql = new StringBuffer(" ");
        sql.append("SELECT c.*,(SELECT if((c.end_time > NOW()),'报名中','报名截止') state) state,\n");
        sql.append("(SELECT if((c.end_time > NOW()),2,1) stateNo) stateNo \n");
        sql.append(" from course c where 1=1");
        if(Util.isNotEmpty(courseVo.getTypeId())){
            sql.append(" and c.type_id = ").append(jointequals(courseVo.getTypeId()));
        }
        if(Util.isNotEmpty(courseVo.getTitle())){
            sql.append(" and c.title like ").append(jointlike(courseVo.getTitle()));
        }
        sql.append(" ORDER BY stateNo DESC,begin_time DESC");
        return sql.toString();
    }

    public String findCourseNews(FileSearchVo fileSearchVo){
        StringBuffer sql = new StringBuffer("SELECT (SELECT '3') type,(SELECT '课程新闻') typeName,(SELECT if(j.clicks ");
        if(Util.isNotEmpty(fileSearchVo.getClicks())){
            sql.append(" > ").append(fileSearchVo.getClicks()).append(",'HOT','') hot) hot,(SELECT if((j.time< NOW() and ADDDATE(j.time,'+2 days') > NOW()),'NEW','') news) news,'' isPrice,if(j.recommend='2','TOP','') top,j.favorite,j.id id,j.banner banner,j.clicks clicks,j.introduce introduce,j.sort sort,DATE_FORMAT(j.time,'%Y-%m-%d') time,j.title title,j.recommend recommend,'' video FROM journalism j where 1=1 ");
        }
        if(Util.isNotEmpty(fileSearchVo.getTitle())){
            sql.append("and j.title like ").append(jointlike(fileSearchVo.getTitle()));
        }
        sql.append(" and course = '2'");
        sql.append(" order by time desc ");
        return sql.toString();
    }

    public String findListAll(String type){
        StringBuffer sql = new StringBuffer("SELECT *FROM journalism WHERE 1=1 ");
        if(Util.isNotEmpty(type)){
            sql.append(" and course = ").append(jointequals(type));
        }
        return sql.toString();
    }

    public String selectVideo(String title){
        StringBuffer sql = new StringBuffer("select id,title from video where 1=1");
        if(Util.isNotEmpty(title)){
            sql.append(" and title like  concat('%',#{title},'%') ");
        }
        return sql.toString();
    }

}
