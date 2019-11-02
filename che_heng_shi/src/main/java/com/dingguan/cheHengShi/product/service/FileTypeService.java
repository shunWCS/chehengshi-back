package com.dingguan.cheHengShi.product.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.material.entity.Journalism;
import com.dingguan.cheHengShi.product.entity.File;
import com.dingguan.cheHengShi.product.entity.FileType;

import java.util.List;

/**
 * Created by zyc on 2018/12/20.
 */
public interface FileTypeService {


    FileType findByPrimaryKey(String id);

    List<FileType> findList();

    FileType insertSelective(FileType fileType);

    void deleteByPrimaryKey(String id);

    FileType updateByPrimaryKeySelective(FileType fileType) throws CustomException;

    Integer findMaxSort();


}