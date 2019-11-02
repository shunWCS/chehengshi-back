package com.dingguan.cheHengShi.home.mapper;

import com.dingguan.cheHengShi.common.resp.BaseMapperProvider;
import com.dingguan.cheHengShi.common.utils.Util;
import com.dingguan.cheHengShi.home.dto.CommonVo;
import com.dingguan.cheHengShi.home.dto.FileSearchVo;
import com.dingguan.cheHengShi.home.dto.SearchVo;
import com.dingguan.cheHengShi.home.enumerate.QueryTypeEnum;

/**
 * @author: czh
 * @Date: 2019/9/6 19:36
 */
public class HomeMapperProvider extends BaseMapperProvider {

    public String getHomeList(CommonVo commonVo,String showPrice){
        StringBuffer sql = new StringBuffer("SELECT *from ( ");
        sql.append("SELECT (SELECT '2') type,(SELECT '文档') typeName,(SELECT if(f.clicks ");
        if(commonVo.getClicks() != null){
            sql.append(" > ").append(commonVo.getClicks()).append(",'HOT','') hot) hot,(SELECT if((f.time< NOW() and ADDDATE(f.time,'+2 days') > NOW()),'NEW','') news) news,if(f.price >0,'收费','免费') isPrice,if(f.recommend='2','TOP','') top,f.favorite,f.id id,f.banner banner,f.clicks clicks,f.introduce introduce,f.sort sort,DATE_FORMAT(f.time,'%Y-%m-%d') time,f.title title,f.recommend recommend,'' video,f.price price FROM file f where 1=1 ");
        }
        if(commonVo.getRecommend() != null){
            sql.append("and f.recommend = ").append(jointequals(commonVo.getRecommend()));
        }
        sql.append(" UNION ");
        sql.append("select (SELECT '1') type,(SELECT '视频') typeName,(SELECT if(v.clicks ");
        if(commonVo.getClicks() != null){
            sql.append(" > ").append(commonVo.getClicks()).append(",'HOT','') hot) hot,(SELECT if((v.time< NOW() and ADDDATE(v.time,'+2 days') > NOW()),'NEW','') news) news,if(v.price >0,'收费','免费') isPrice,if(v.recommend='2','TOP','') top,v.favorite,v.id id,v.banner banner,v.clicks clicks,v.introduce introduce,v.sort sort,DATE_FORMAT(v.time,'%Y-%m-%d') time,v.title title,v.recommend recommend,v.video video,v.price price FROM video v where 1=1 ");
        }
        if(commonVo.getRecommend() != null){
            sql.append("and v.recommend = ").append(jointequals(commonVo.getRecommend()));
        }
        sql.append(" UNION ");
        sql.append("SELECT (SELECT '3') type,(SELECT '新闻') typeName,(SELECT if(j.clicks ");
        if(commonVo.getClicks() != null){
            sql.append(" > ").append(commonVo.getClicks()).append(",'HOT','') hot) hot,(SELECT if((j.time< NOW() and ADDDATE(j.time,'+2 days') > NOW()),'NEW','') news) news,'免费' isPrice,if(j.recommend='2','TOP','') top,j.favorite,j.id id,j.banner banner,j.clicks clicks,j.introduce introduce,j.sort sort,DATE_FORMAT(j.time,'%Y-%m-%d') time,j.title title,j.recommend recommend,'' video,0 price FROM journalism j where 1=1 ");
        }
        if (commonVo.getRecommend() != null){
            sql.append("and j.recommend = ").append(jointequals(commonVo.getRecommend()));
            //sql.append(" and j.course = '1' ");
        }
        sql.append(" ) AS temp ");
        if("NO".equalsIgnoreCase(showPrice))
            sql.append(" where price <= 0 ");
        if("YES".equalsIgnoreCase(showPrice))
            sql.append(" where price > 0");
        if("ALL".equalsIgnoreCase(showPrice))
            sql.append(" ");
        sql.append(" order by temp.time desc");
        return sql.toString();
    }

    public String search(SearchVo searchVo,String showPrice){
        StringBuffer sql = new StringBuffer("SELECT *from ( ");
        sql.append("SELECT (SELECT '2') type,(SELECT '文档') typeName,(SELECT if(f.clicks ");
        if(Util.isNotEmpty(searchVo.getClicks())){
            sql.append(" > ").append(searchVo.getClicks()).append(",'HOT','') hot) hot,(SELECT if((f.time< NOW() and ADDDATE(f.time,'+2 days') > NOW()),'NEW','') news) news,if(f.price >0,'收费','免费') isPrice,if(f.recommend='2','TOP','') top,f.favorite,f.id id,f.banner banner,f.clicks clicks,f.introduce introduce,f.sort sort,DATE_FORMAT(f.time,'%Y-%m-%d') time,f.title title,f.recommend recommend,'' video,f.price price FROM file f where 1=1 ");
        }
        if(Util.isNotEmpty(searchVo.getRecommend())){
            sql.append("and f.recommend = ").append(jointequals(searchVo.getRecommend()));
        }
        if(Util.isNotEmpty(searchVo.getTitle())){
            sql.append("and f.title like ").append(jointlike(searchVo.getTitle()));
        }
        sql.append(" UNION ");
        sql.append("select (SELECT '1') type,(SELECT '视频') typeName,(SELECT if(v.clicks ");
        if(Util.isNotEmpty(searchVo.getClicks())){
            sql.append(" > ").append(searchVo.getClicks()).append(",'HOT','') hot) hot,(SELECT if((v.time< NOW() and ADDDATE(v.time,'+2 days') > NOW()),'NEW','') news) news,if(v.price >0,'收费','免费') isPrice,if(v.recommend='2','TOP','') top,v.favorite,v.id id,v.banner banner,v.clicks clicks,v.introduce introduce,v.sort sort,DATE_FORMAT(v.time,'%Y-%m-%d') time,v.title title,v.recommend recommend,v.video video,v.price price FROM video v where 1=1 ");
        }
        if(Util.isNotEmpty(searchVo.getRecommend())){
            sql.append("and v.recommend = ").append(jointequals(searchVo.getRecommend()));
        }
        if(Util.isNotEmpty(searchVo.getTitle())){
            sql.append("and v.title like ").append(jointlike(searchVo.getTitle()));
        }
        sql.append(" UNION ");
        sql.append("SELECT (SELECT '3') type,(SELECT '新闻') typeName,(SELECT if(j.clicks ");
        if(Util.isNotEmpty(searchVo.getClicks())){
            sql.append(" > ").append(searchVo.getClicks()).append(",'HOT','') hot) hot,(SELECT if((j.time< NOW() and ADDDATE(j.time,'+2 days') > NOW()),'NEW','') news) news,'免费' isPrice,if(j.recommend='2','TOP','') top,j.favorite,j.id id,j.banner banner,j.clicks clicks,j.introduce introduce,j.sort sort,DATE_FORMAT(j.time,'%Y-%m-%d') time,j.title title,j.recommend recommend,'' video,0 price FROM journalism j where 1=1 ");
        }
        if (Util.isNotEmpty(searchVo.getRecommend())){
            sql.append("and j.recommend = ").append(jointequals(searchVo.getRecommend()));
        }
        if(Util.isNotEmpty(searchVo.getTitle())){
            sql.append("and j.title like ").append(jointlike(searchVo.getTitle()));
        }
        sql.append(" ) AS temp");
        if("NO".equalsIgnoreCase(showPrice)){
            sql.append(" where price <= 0 ");
        }
        if("YES".equalsIgnoreCase(showPrice)){
            sql.append(" where price > 0 ");
        }
        if("ALL".equalsIgnoreCase(showPrice)){

        }
        sql.append(" order by temp.time desc ");
        return sql.toString();
    }

    public String searchByType(SearchVo searchVo,String showPrice){
        StringBuffer sql = new StringBuffer("SELECT *from ( ");
        if(QueryTypeEnum.FILE.getCode().equals(searchVo.getType())){
            sql = new StringBuffer("SELECT (SELECT '2') type,(SELECT '文档') typeName,(SELECT if(f.clicks ");
            if(Util.isNotEmpty(searchVo.getClicks())){
                sql.append(" > ").append(searchVo.getClicks()).append(",'HOT','') hot) hot,(SELECT if((f.time< NOW() and ADDDATE(f.time,'+2 days') > NOW()),'NEW','') news) news,if(f.price >0,'收费','免费') isPrice,if(f.recommend='2','TOP','') top,f.favorite,f.id id,f.banner banner,f.clicks clicks,f.introduce introduce,f.sort sort,DATE_FORMAT(f.time,'%Y-%m-%d') time,f.title title,f.recommend recommend,'' video,f.price price FROM file f where 1=1 ");
            }
            if(Util.isNotEmpty(searchVo.getTitle())){
                sql.append("and f.title like ").append(jointlike(searchVo.getTitle()));
            }
            if(Util.isNotEmpty(searchVo.getRecommend())){
                sql.append("and f.recommend = ").append(jointequals(searchVo.getRecommend()));
            }
            if("NO".equalsIgnoreCase(showPrice)){
                sql.append(" and f.price <= 0 ");
            }
            if("YES".equalsIgnoreCase(showPrice)){
                sql.append(" and f.price > 0 ");
            }
            if("ALL".equalsIgnoreCase(showPrice)){

            }
            sql.append(" order by f.time desc");
        }
        if(QueryTypeEnum.VIDEO.getCode().equals(searchVo.getType())){
            sql.append("select (SELECT '1') type,(SELECT '视频') typeName,(SELECT if(v.clicks ");
            if(Util.isNotEmpty(searchVo.getClicks())){
                sql.append(" > ").append(searchVo.getClicks()).append(",'HOT','') hot) hot,(SELECT if((v.time< NOW() and ADDDATE(v.time,'+2 days') > NOW()),'NEW','') news) news,if(v.price >0,'收费','免费') isPrice,if(v.recommend='2','TOP','') top,v.favorite,v.id id,v.banner banner,v.clicks clicks,v.introduce introduce,v.sort sort,DATE_FORMAT(v.time,'%Y-%m-%d') time,v.title title,v.recommend recommend,v.video video FROM video v where 1=1 ");
            }
            if(Util.isNotEmpty(searchVo.getTitle())){
                sql.append("and v.title like ").append(jointlike(searchVo.getTitle()));
            }
            if(Util.isNotEmpty(searchVo.getRecommend())){
                sql.append("and v.recommend = ").append(jointequals(searchVo.getRecommend()));
            }
            if("NO".equalsIgnoreCase(showPrice)){
                sql.append(" and v.price <= 0");
            }
            if("YES".equalsIgnoreCase(showPrice)){
                sql.append(" and v.price > 0 ");
            }
            if("ALL".equalsIgnoreCase(showPrice)){

            }
            sql.append(" ) AS temp order by temp.time desc");
        }
        if(QueryTypeEnum.JOURNALISM.getCode().equals(searchVo.getType())){
            sql.append("SELECT (SELECT '3') type,(SELECT '新闻') typeName,(SELECT if(j.clicks ");
            if(Util.isNotEmpty(searchVo.getClicks())){
                sql.append(" > ").append(searchVo.getClicks()).append(",'HOT','') hot) hot,(SELECT if((j.time< NOW() and ADDDATE(j.time,'+2 days') > NOW()),'NEW','') news) news,'免费' isPrice,if(j.recommend='2','TOP','') top,j.favorite,j.id id,j.banner banner,j.clicks clicks,j.introduce introduce,j.sort sort,DATE_FORMAT(j.time,'%Y-%m-%d') time,j.title title,j.recommend recommend,'' video FROM journalism j where 1=1 ");
            }
            if(Util.isNotEmpty(searchVo.getTitle())){
                sql.append("and j.title like ").append(jointlike(searchVo.getTitle()));
            }
            if (Util.isNotEmpty(searchVo.getRecommend())){
                sql.append("and j.recommend = ").append(jointequals(searchVo.getRecommend()));
            }
            sql.append(" ) AS temp order by temp.time desc");
        }
        return sql.toString();
    }

    public String findFile(FileSearchVo fileSearchVo,String showPrice){
        StringBuffer sql = new StringBuffer("");
        sql.append("SELECT (SELECT '2') type,(SELECT '文档') typeName,(SELECT if(f.clicks ");
        if(Util.isNotEmpty(fileSearchVo.getClicks())){
            sql.append(" > ").append(fileSearchVo.getClicks()).append(",'HOT','') hot) hot,(SELECT if((f.time< NOW() and ADDDATE(f.time,'+2 days') > NOW()),'NEW','') news) news,if(f.price >0,'收费','免费') isPrice,if(f.recommend='2','TOP','') top,f.id id,f.banner banner,f.clicks clicks,f.introduce introduce,f.sort sort,DATE_FORMAT(f.time,'%Y-%m-%d') time,f.title title,f.recommend recommend,f.favorite,f.price FROM file f where 1=1 ");
        }
        if(Util.isNotEmpty(fileSearchVo.getTitle())){
            sql.append("and f.title like ").append(jointlike(fileSearchVo.getTitle()));
        }
        if (Util.isNotEmpty(fileSearchVo.getTypeId())){
            sql.append(" and f.type_id = ").append(jointequals(fileSearchVo.getTypeId()));
        }
        if("NO".equalsIgnoreCase(showPrice)){
            sql.append(" and f.price <= 0");
        }
        if("YES".equalsIgnoreCase(showPrice)){
            sql.append(" and f.price > 0 ");
        }
        if("ALL".equalsIgnoreCase(showPrice)){

        }
        sql.append(" order by time desc ");
        return sql.toString();
    }

    public String findVideo(FileSearchVo fileSearchVo,String showPrice){
        StringBuffer sql = new StringBuffer("");
        sql.append("select (SELECT '1') type,(SELECT '视频') typeName,(SELECT if(v.clicks ");
        if(Util.isNotEmpty(fileSearchVo.getClicks())){
            sql.append(" > ").append(fileSearchVo.getClicks()).append(",'HOT','') hot) hot,(SELECT if((v.time< NOW() and ADDDATE(v.time,'+2 days') > NOW()),'NEW','') news) news,if(v.price >0,'收费','免费') isPrice,if(v.recommend='2','TOP','') top,v.favorite,v.id id,v.banner banner,v.clicks clicks,v.introduce introduce,v.sort sort,DATE_FORMAT(v.time,'%Y-%m-%d') time,v.title title,v.recommend recommend,v.video video,v.price FROM video v where 1=1 ");
        }
        if(Util.isNotEmpty(fileSearchVo.getTitle())){
            sql.append("and v.title like ").append(jointlike(fileSearchVo.getTitle()));
        }
        if(Util.isNotEmpty(fileSearchVo.getTypeId())){
            sql.append(" and v.type_id = ").append(jointequals(fileSearchVo.getTypeId()));
        }
        if("NO".equalsIgnoreCase(showPrice)){
            sql.append(" and v.price <= 0");
        }
        if("YES".equalsIgnoreCase(showPrice)){
            sql.append(" and v.price > 0 ");
        }
        if("ALL".equalsIgnoreCase(showPrice)){

        }
        sql.append(" order by time desc ");
        return sql.toString();
    }

    public String findJournalism(FileSearchVo fileSearchVo){
        StringBuffer sql = new StringBuffer("");
        sql.append("SELECT (SELECT '3') type,(SELECT '新闻公告') typeName,(SELECT if(j.clicks ");
        if(Util.isNotEmpty(fileSearchVo.getClicks())){
            sql.append(" > ").append(fileSearchVo.getClicks()).append(",'HOT','') hot) hot,(SELECT if((j.time< NOW() and ADDDATE(j.time,'+2 days') > NOW()),'NEW','') news) news,'免费' isPrice,if(j.recommend='2','1','0') isTop,if(j.recommend='2','TOP','') top,j.favorite,j.id id,j.banner banner,j.clicks clicks,j.introduce introduce,j.sort sort,DATE_FORMAT(j.time,'%Y-%m-%d') time,j.title title,j.recommend recommend,'' video FROM journalism j where 1=1 ");
        }
        if(Util.isNotEmpty(fileSearchVo.getTitle())){
            sql.append("and j.title like ").append(jointlike(fileSearchVo.getTitle()));
        }
        //sql.append(" and course = '1'");
        sql.append(" order by isTop desc,time desc");
        return sql.toString();
    }

    public String getOpenId(String nickName, String phone){
        StringBuffer sql = new StringBuffer("select *from user where 1=1 ");
        if(Util.isNotEmpty(nickName)){
            sql.append(" and nick_name = ").append(jointequals(nickName));
        }
        if(Util.isNotEmpty(phone)){
            sql.append(" and phone = ").append(jointequals(phone));
        }
        return sql.toString();
    }

    public String findPassListAll(String title,String phone,String name){
        StringBuffer sql = new StringBuffer("SELECT * FROM share_video s where state = '1' ");
        if(Util.isNotEmpty(title)){
            sql.append(" and title like concat('%',#{title},'%')");
        }
        if(Util.isNotEmpty(phone)){
            sql.append(" and phone like concat('%',#{phone},'%')");
        }
        if(Util.isNotEmpty(name)){
            sql.append(" and name like concat('%',#{name},'%')");
        }
        return sql.toString();
    }
}
