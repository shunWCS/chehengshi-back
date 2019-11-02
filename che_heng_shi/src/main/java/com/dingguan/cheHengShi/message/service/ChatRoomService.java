package com.dingguan.cheHengShi.message.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.message.entity.ChatRoom;
import com.dingguan.cheHengShi.message.entity.Msg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by zyc on 2018/12/21.
 */
public interface ChatRoomService {


    ChatRoom findByPrimaryKey(String id);



    ApiResult<List<ChatRoom>> findList(ChatRoom chatRoom, Pageable pageable);

    List<ChatRoom> findList(ChatRoom chatRoom);
    Page<ChatRoom> findList(ChatRoom chatRoom, PageRequest pageRequest);


    ChatRoom insertSelective(ChatRoom chatRoom);

    void deleteByPrimaryKey(String id);

    ChatRoom updateByPrimaryKeySelective(ChatRoom chatRoom) throws CustomException;

    ChatRoom findByProduceInAndConsumerIn(List<String> produces,List<String> consumers );

    List<ChatRoom> findByProduceOrConsumer(String participant);

}