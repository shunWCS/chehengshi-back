package com.dingguan.cheHengShi.user.repository;

import com.dingguan.cheHengShi.user.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by zyc on 2018/12/5.
 */
public interface AddressRepository extends JpaRepository<Address,String> {
}