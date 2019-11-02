package com.dingguan.cheHengShi.home.service;

import com.dingguan.cheHengShi.common.utils.DateUtil;
import com.dingguan.cheHengShi.common.utils.Util;
import com.dingguan.cheHengShi.home.dto.CommonVo;
import com.dingguan.cheHengShi.home.dto.FileSearchVo;
import com.dingguan.cheHengShi.home.dto.HomePage;
import com.dingguan.cheHengShi.home.dto.QueryType;
import com.dingguan.cheHengShi.home.dto.SearchVo;
import com.dingguan.cheHengShi.home.entity.Favorite;
import com.dingguan.cheHengShi.home.entity.ProductPoster;
import com.dingguan.cheHengShi.home.mapper.FavoriteMapper;
import com.dingguan.cheHengShi.home.mapper.HomeMapper;
import com.dingguan.cheHengShi.home.repository.HomeRepository;
import com.dingguan.cheHengShi.material.entity.Journalism;
import com.dingguan.cheHengShi.material.entity.Material;
import com.dingguan.cheHengShi.material.repository.JournalismRepository;
import com.dingguan.cheHengShi.product.entity.Course;
import com.dingguan.cheHengShi.product.entity.File;
import com.dingguan.cheHengShi.product.entity.Video;
import com.dingguan.cheHengShi.product.repository.CourseRepository;
import com.dingguan.cheHengShi.product.repository.FileRepository;
import com.dingguan.cheHengShi.product.repository.VideoRepository;
import com.dingguan.cheHengShi.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: czh
 * @Date: 2019/9/5 19:50
 */
@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private HomeRepository homeRepository;
    @Autowired
    private HomeMapper homeMapper;
    @Autowired
    private FavoriteMapper favoriteMapper;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private JournalismRepository journalismRepository;
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<Map<String, Object>> findByAllList(Integer clicks, String recommend) {
        return homeRepository.findByAllList(clicks, recommend);
    }

    @Override
    public List<HomePage> getHomeList(CommonVo commonVo,String showPrice) {
        if(Util.isEmpty(commonVo.getClicks())){
            throw new RuntimeException("您必须设置clicks这个参数!");
        }
        List<HomePage> homeList = homeMapper.getHomeList(commonVo,showPrice);
        return homeList;
    }

    @Override
    public List<User> findByConsultantAndRecommend(String consultant, String recommend) {
        List<User> userList = homeMapper.findByConsultantAndRecommend(consultant,recommend);
        return userList;
    }

    @Override
    public List<QueryType> findQueryType(String valueCode) {
        List<QueryType> queryType = homeMapper.findQueryType(valueCode);
        return queryType;
    }

    @Override
    public List<HomePage> search(SearchVo searchVo,String showPrice) {
        List<HomePage> dataInfo = null;
        if(Util.isEmpty(searchVo.getClicks())){
            throw new RuntimeException("您必须设置clicks这个参数!");
        }
        if(Util.isNotEmpty(searchVo.getType())){
            dataInfo = homeMapper.searchByType(searchVo,showPrice);
        }else {
            dataInfo = homeMapper.search(searchVo,showPrice);
        }
        return dataInfo;
    }

    @Override
    public String saveFavorite(Favorite favorite) {
        if(Util.isEmpty(favorite.getType())){
            throw new RuntimeException("类型不能为空");
        }
        if(Util.isEmpty(favorite.getId())){
            throw new RuntimeException("id不能为空");
        }
        if(Util.isEmpty(favorite.getOpenId())){
            throw new RuntimeException("openId不能为空");
        }
        if(Util.isEmpty(favorite.getTitle())){
            throw new RuntimeException("标题不能为空");
        }
        if(Util.isEmpty(favorite.getIntroduce())){
            favorite.setIntroduce("");
        }
        if(Util.isEmpty(favorite.getPrice())){
            favorite.setPrice(new BigDecimal(0));
        }
        favorite.setTime(DateUtil.getFormatDateTime(new Date(),DateUtil.yyyyMMddFormat));
        int selective = favoriteMapper.insertSelective(favorite);
        System.out.println( favorite.getType().trim());
        if("2".equals(favorite.getType().trim())){
            File file = fileRepository.findByPrimaryKey(favorite.getId());
            file.setFavorite(file.getFavorite() + 1);
            fileRepository.updateFavorite(file.getId(),file.getFavorite());
        }
        if("1".equals(favorite.getType().trim())){
            Video video = videoRepository.findByPrimaryKey(favorite.getId());
            video.setFavorite(video.getFavorite() + 1);
            videoRepository.updateFavorite(video.getId(),video.getFavorite());
        }
        if("3".equals(favorite.getType().trim())){
            Journalism journalism = journalismRepository.findOne(favorite.getId());
            journalism.setFavorite(journalism.getFavorite() + 1);
            journalismRepository.updateFavorite(journalism.getId(),journalism.getFavorite());
        }
        if("4".equals(favorite.getType().trim())){
            Course course = courseRepository.findByPrimaryKey(favorite.getId());
            course.setFavorite(course.getFavorite() + 1);
            courseRepository.updateFavorite(course.getId(),course.getFavorite());
        }
        if(selective>0){
            return "收藏成功!";
        }else {
            return "收藏失败!";
        }
    }

    @Override
    public String cancelFavorite(String type, String id,String openId) {
        Favorite favorite = favoriteMapper.selectByTypeId(type,id,openId);
        Integer delete = 0;
        if(Util.isNotEmpty(favorite)){
            delete = favoriteMapper.deleteByMyParam(type,id,openId);
            if("2".equals(type.trim())){
                File file = fileRepository.findByPrimaryKey(id);
                if(Util.isNotEmpty(file) && file.getFavorite() > 0){
                    file.setFavorite(file.getFavorite() - 1);
                    fileRepository.updateFavorite(file.getId(),file.getFavorite());
                }
            }
            if("1".equals(type.trim())){
                Video video = videoRepository.findByPrimaryKey(id);
                if(Util.isNotEmpty(video) && video.getFavorite() > 0){
                    video.setFavorite(video.getFavorite() - 1);
                    videoRepository.updateFavorite(video.getId(),video.getFavorite());
                }
            }
            if("3".equals(type.trim())){
                Journalism journalism = journalismRepository.findOne(favorite.getId());
                if(Util.isNotEmpty(journalism) && journalism.getFavorite() > 0){
                    journalism.setFavorite(journalism.getFavorite() - 1);
                    journalismRepository.updateFavorite(journalism.getId(),journalism.getFavorite());
                }
            }
            if("4".equals(type.trim())){
                Course course = courseRepository.findByPrimaryKey(favorite.getId());
                if(Util.isNotEmpty(course) && course.getFavorite()>0){
                    course.setFavorite(course.getFavorite()-1);
                    courseRepository.updateFavorite(course.getId(),course.getFavorite());
                }
            }
        }
        if(delete >= 0){
            return "取消成功!";
        }else {
            return "取消失败!";
        }

    }

    @Override
    public List<Favorite> myFavoriteList(String openId) {
        List<Favorite> favoriteList = favoriteMapper.myFavoriteList(openId);
        return favoriteList;
    }

    @Override
    public List<ProductPoster> getPoster() {
        List<ProductPoster> posterList = homeMapper.getPoster();
        return posterList;
    }

    @Override
    public List<HomePage> findFile(FileSearchVo fileSearchVo,String showPrice) {
        List<HomePage> fileList = homeMapper.findFile(fileSearchVo,showPrice);
        return fileList;
    }

    @Override
    public List<HomePage> findVideo(FileSearchVo fileSearchVo,String showPrice) {
        List<HomePage> videoList = homeMapper.findVideo(fileSearchVo,showPrice);
        return videoList;
    }

    @Override
    public List<HomePage> findJournalism(FileSearchVo fileSearchVo) {
        List<HomePage> journalismList = homeMapper.findJournalism(fileSearchVo);
        return journalismList;
    }

    @Override
    public User getOpenId(String nickName, String phone) {
        List<User> users = homeMapper.getOpenId(nickName,phone);
        if(Util.isEmpty(users) && users.size() == 0){
            throw new RuntimeException("您还没有注册，请先注册,谢谢！");
        }
        return users.get(0);
    }

    @Override
    public Material findMaterialById() {
        Material material = homeMapper.findMaterialById();
        return material;
    }
}
