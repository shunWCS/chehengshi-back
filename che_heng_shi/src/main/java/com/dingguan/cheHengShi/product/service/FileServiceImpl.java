package com.dingguan.cheHengShi.product.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.utils.DateUtil;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.UpdateTool;
import com.dingguan.cheHengShi.home.mapper.CourseApplyMapper;
import com.dingguan.cheHengShi.material.entity.Journalism;
import com.dingguan.cheHengShi.material.repository.JournalismRepository;
import com.dingguan.cheHengShi.product.entity.File;
import com.dingguan.cheHengShi.product.entity.Product;
import com.dingguan.cheHengShi.product.repository.FileRepository;
import com.dingguan.cheHengShi.user.entity.BrowseRecord;
import com.dingguan.cheHengShi.user.service.BrowseRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by zyc on 2018/12/20.
 */
@Service
public class FileServiceImpl implements FileService {


    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private BrowseRecordService browseRecordService;
    @Autowired
    private CourseApplyMapper courseApplyMapper;


    @Override
    public File findByPrimaryKey(String id, String openId) {
        File file = fileRepository.findByPrimaryKey(id);
        file.setBeginTime(DateUtil.getFormatDateTime(file.getTime(),DateUtil.fullFormat));
        String tempType = "2";
        Integer count2 = courseApplyMapper.findIsFavorite(id,openId,tempType);
        if(count2>0){
            file.setIsFavorite(1);
        }else {
            file.setIsFavorite(0);
        }
        if (file != null) {
            updateClicks(id, 1);

            if (StringUtils.isNotBlank(openId)) {
                BrowseRecord build = BrowseRecord.builder()
                        .id(Sequences.get())
                        .openId(openId)
                        .banner(file.getBanner())
                        .introduce(file.getIntroduce())
                        .productId(id)
                        .productType("3")
                        .time(new Date())
                        .Title(file.getTitle())
                        .price(file.getPrice())
                        .sales(file.getSales())
                        .clicks(file.getClicks())
                        .build();

                browseRecordService.insertSelective(build);
            }


        }
        return file;
    }

    @Override
    public List<File> findList(String tyepId, String title) {

        List<File> fileList = null;
        if (StringUtils.isBlank(tyepId) && StringUtils.isBlank(title)) {
            fileList = fileRepository.findList();
        } else if (StringUtils.isNotBlank(tyepId) && StringUtils.isNotBlank(title)) {
            fileList = fileRepository.findByTypeIdAndTitleLikeOrderBySort(tyepId, title);
        } else if (StringUtils.isNotBlank(tyepId) && StringUtils.isBlank(title)) {
            fileList = fileRepository.findByTypeIdOrderBySort(tyepId);
        } else {
            fileList = fileRepository.findByTitleLikeOrderBySort(title);
        }


        return fileList;
    }


    @Override
    public File insertSelective(File file) {
        if (StringUtils.isBlank(file.getId())) {
            file.setId(Sequences.get());
        }
        if (file.getSort() == null) {
            Integer sort = fileRepository.findMaxSort();
            sort = sort == null ? 1 : ++sort;
            file.setSort(sort);
        }

        if (file.getTime() == null) {
            file.setTime(new Date());
        }
        if (file.getClicks() == null) {
            file.setClicks(0);
        }
        if (file.getSales() == null) {
            file.setSales(0);
        }
        if (file.getRecommend() == null) {
            file.setRecommend("1");
        }
        if(file.getFavorite() == null){
            file.setFavorite(0);
        }

        return fileRepository.save(file);


    }

    @Override
    @Transactional
    public void deleteByPrimaryKey(String id) {
        fileRepository.deleteByPrimaryKey(id);
    }

    @Override
    public File updateByPrimaryKeySelective(File file) throws CustomException {
        File source = fileRepository.findByPrimaryKey(file.getId());
        UpdateTool.copyNullProperties(source, file);

        return fileRepository.save(file);
    }

    @Override
    public Integer findMaxSort() {
        return fileRepository.findMaxSort();
    }

    @Override
    public void updateClicks(String id, Integer num) {
        fileRepository.updateClicks(id, num);
    }

    @Override
    public void updateSales(String id, Integer num) {
        fileRepository.updateSales(id, num);
    }

    @Override
    public List<File> findByRecommendOrderBySort(String recommend) {
        return fileRepository.findByRecommendOrderBySort(recommend);
    }


}