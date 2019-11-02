package com.dingguan.cheHengShi.product.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.UpdateTool;
import com.dingguan.cheHengShi.material.entity.Journalism;
import com.dingguan.cheHengShi.material.repository.JournalismRepository;


import com.dingguan.cheHengShi.product.entity.File;
import com.dingguan.cheHengShi.product.entity.FileType;
import com.dingguan.cheHengShi.product.repository.FileTypeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zyc on 2018/12/20.
 */
@Service
public class FileTypeServiceImpl implements FileTypeService {

    @Autowired
    private FileTypeRepository fileTypeRepository;


    @Override
    public FileType findByPrimaryKey(String id) {
        FileType fileType = fileTypeRepository.findOne(id);

        return fileType;
    }

    @Override
    public List<FileType> findList() {
        Sort sort =new Sort(Sort.Direction.ASC,"sort");
        return fileTypeRepository.findAll(sort);
    }




    @Override
    public FileType insertSelective(FileType fileType) {
        if(StringUtils.isBlank(fileType.getId())){
            fileType.setId(Sequences.get());
        }
        if(fileType.getSort()==null){
            Integer sort = fileTypeRepository.findMaxSort();
            sort=sort==null?1:++sort;
            fileType.setSort(sort);
        }



        return  fileTypeRepository.save(fileType);


    }

    @Override
    public void deleteByPrimaryKey(String id) {
        fileTypeRepository.delete(id);
    }

    @Override
    public FileType updateByPrimaryKeySelective(FileType fileType) throws CustomException {
        FileType source = fileTypeRepository.findOne(fileType.getId());
        UpdateTool.copyNullProperties(source,fileType);
        return fileTypeRepository.save(fileType);
    }

    @Override
    public Integer findMaxSort() {
        return fileTypeRepository.findMaxSort();
    }




}