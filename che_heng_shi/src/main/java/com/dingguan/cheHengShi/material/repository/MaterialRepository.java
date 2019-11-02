package com.dingguan.cheHengShi.material.repository;

import com.dingguan.cheHengShi.material.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by zyc on 2018/12/1.
 */
public interface MaterialRepository extends JpaRepository<Material,String> {

    List<Material> findByTypeOrderBySort(String type);
    List<Material> findMaterialsByTypeOrderBySort(String type);

    @Query(value = "select max(sort) from material   where `type`=?1",nativeQuery = true)
    Integer findBySort(String type);


    Material findTopByType(String type);
    List<Material> findByTypeIn(List<String> typeList);

}