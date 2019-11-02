package com.dingguan.cheHengShi.product.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.UpdateTool;
import com.dingguan.cheHengShi.product.entity.FileType;
import com.dingguan.cheHengShi.product.entity.MembershipPackage;
import com.dingguan.cheHengShi.product.repository.FileTypeRepository;
import com.dingguan.cheHengShi.product.repository.MembershipPackageRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zyc on 2018/12/20.
 */
@Service
public class MembershipPackageServiceImpl implements MembershipPackageService {

    @Autowired
    private MembershipPackageRepository membershipPackageRepository;


    @Override
    public MembershipPackage findByPrimaryKey(String id) {
        return membershipPackageRepository.findOne(id);
    }

    @Override
    public List<MembershipPackage> findList() {
        Sort sort = new Sort(Sort.Direction.ASC, "price");
        return membershipPackageRepository.findAll(sort);
    }




    @Override
    public MembershipPackage insertSelective(MembershipPackage membershipPackage) {
        if(StringUtils.isBlank(membershipPackage.getId())){
            membershipPackage.setId(Sequences.get());
        }
        if(membershipPackage.getSales()==null){
            membershipPackage.setSales(0);
        }

        return  membershipPackageRepository.save(membershipPackage);


    }

    @Override
    public void deleteByPrimaryKey(String id) {
        membershipPackageRepository.delete(id);
    }

    @Override
    public MembershipPackage updateByPrimaryKeySelective(MembershipPackage membershipPackage) throws CustomException {
        MembershipPackage source = membershipPackageRepository.findOne(membershipPackage.getId());
        UpdateTool.copyNullProperties(source,membershipPackage);
        return membershipPackageRepository.save(membershipPackage);
    }


    @Override
    public void updateSales(String id, Integer num) {
        membershipPackageRepository.updateSales(id,num);
    }

}