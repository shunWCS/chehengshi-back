package com.dingguan.cheHengShi.message.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.message.entity.Msg;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Created by zyc on 2018/12/21.
 */
public interface MsgService {

    Msg findByPrimaryKey(String id);

    List<Msg> findByProduceOrderByTimeDesc(String produce);

    List<Msg> findByConsumerOrderByTimeDesc(String consumer);

    ApiResult<List<Msg>> findList(Msg msg, Pageable pageable,String consumer);
    List<Msg> findList(Msg msg , Sort sort,String consumer);


    Msg insertSelective(Msg msg) throws CustomException;

    void deleteByPrimaryKey(String id);

    Msg updateByPrimaryKeySelective(Msg msg) throws CustomException;

    //查询聊天室最后一条消息
    Msg findTopByChatRoomIdOrderByTimeDesc(String chatRoomId);

    //查询聊天室未读消息数量
    Integer winterCicada(String chatRoomId);



}