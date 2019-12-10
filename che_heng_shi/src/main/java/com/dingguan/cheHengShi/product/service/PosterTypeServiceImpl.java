package com.dingguan.cheHengShi.product.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.UpdateTool;
import com.dingguan.cheHengShi.product.entity.PosterType;
import com.dingguan.cheHengShi.product.repository.PosterTypeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PosterTypeServiceImpl implements PosterTypeService {

    @Autowired
    private PosterTypeRepository posterTypeRepository;

    @Override
    public PosterType findByPrimaryKey(String id) {
        PosterType posterType = posterTypeRepository.findOne(id);
        return posterType;
    }

    @Override
    public List<PosterType> findList() {
        Sort sort =new Sort(Sort.Direction.ASC,"sort");
        List<PosterType> posterTypes = posterTypeRepository.findAll(sort);
        return posterTypes;
    }

    @Override
    public PosterType insertSelective(PosterType posterType) {
        if(StringUtils.isBlank(posterType.getId())){
            posterType.setId(Sequences.get());
        }
        if(posterType.getSort()==null){
            Integer sort = posterTypeRepository.findMaxSort();
            sort=sort==null?1:++sort;
            posterType.setSort(sort);
        }
        return posterTypeRepository.save(posterType);
    }

    @Override
    public void deleteByPrimaryKey(String id) {
        posterTypeRepository.delete(id);
    }

    @Override
    public PosterType updateByPrimaryKeySelective(PosterType posterType) throws CustomException {
        PosterType source = posterTypeRepository.findOne(posterType.getId());
        UpdateTool.copyNullProperties(source,posterType);
        return posterTypeRepository.save(posterType);
    }
}
