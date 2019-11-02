package com.dingguan.cheHengShi.common.resp;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author: czh
 * @Date: 2019/9/7 15:27
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
    
}
