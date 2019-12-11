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
        StringBuffer sql = new StringBuffer("SELECT v.id refId,v.title,'视频' typeName,'video' typeValue,pb.create_time createTime,\n" +
                "pb.edit_time editTime,pb.is_first isFirst FROM video v\n" +
                "LEFT JOIN poster_banner pb ON v.id = pb.ref_id WHERE 1 =1\n");
        if(Util.isNotEmpty(title)){
            sql.append(" and v.title like  concat('%',#{title},'%') ");
        }
        return sql.toString();
    }

    public String selectForFile(String title){
        StringBuffer sql = new StringBuffer("SELECT f.id refId,f.title,'资料' typeName,'file' typeValue,pb.create_time createTime,\n" +
                "pb.edit_time editTime,pb.is_first isFirst FROM file f\n" +
                "LEFT JOIN poster_banner pb ON f.id = pb.ref_id WHERE 1 =1");
        if(Util.isNotEmpty(title)){
            sql.append(" and f.title like  concat('%',#{title},'%') ");
        }
        return sql.toString();
    }

    public String selectForJournalism(String title){
        StringBuffer sql = new StringBuffer("SELECT j.id refId,j.title,'新闻' typeName,'journalism' typeValue,pb.create_time createTime,\n" +
                "pb.edit_time editTime,pb.is_first isFirst FROM journalism j\n" +
                "LEFT JOIN poster_banner pb ON j.id = pb.ref_id WHERE 1 =1");
        if(Util.isNotEmpty(title)){
            sql.append(" and j.title like  concat('%',#{title},'%') ");
        }
        return sql.toString();
    }
    public String selectForCourse(String title){
        StringBuffer sql = new StringBuffer("SELECT c.id refId,c.title,'课程' typeName,'course' typeValue,pb.create_time createTime,\n" +
                "pb.edit_time editTime,pb.is_first isFirst FROM course c\n" +
                "LEFT JOIN poster_banner pb ON c.id = pb.ref_id WHERE 1 =1");
        if(Util.isNotEmpty(title)){
            sql.append(" and c.title like  concat('%',#{title},'%') ");
        }
        return sql.toString();
    }

    public String selectForProduct(String title){
        StringBuffer sql = new StringBuffer("SELECT pt.id refId,pt.type_name title,'商城' typeName,'store' typeValue,pb.create_time createTime,\n" +
                "pb.edit_time editTime,pb.is_first isFirst FROM product_type pt\n" +
                "LEFT JOIN poster_banner pb ON pt.id = pb.ref_id WHERE 1 =1");
        if(Util.isNotEmpty(title)){
            sql.append(" and pt.type_name like  concat('%',#{title},'%') ");
        }
        return sql.toString();
    }


}
