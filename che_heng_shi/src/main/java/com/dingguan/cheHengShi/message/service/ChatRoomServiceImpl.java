package com.dingguan.cheHengShi.message.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.UpdateTool;
import com.dingguan.cheHengShi.job.AsynchronousJob;
import com.dingguan.cheHengShi.message.entity.ChatRoom;
import com.dingguan.cheHengShi.message.entity.Msg;
import com.dingguan.cheHengShi.message.repository.ChatRoomRepository;
import com.dingguan.cheHengShi.message.repository.MsgRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zyc on 2018/12/21.
 */
@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;


    @Override
    public ChatRoom findByPrimaryKey(String id) {
        ChatRoom msg = chatRoomRepository.findOne(id);
        return msg;
    }


    @Override
    public ApiResult<List<ChatRoom>> findList(ChatRoom chatRoom, Pageable pageable) {

        Example<ChatRoom> of = Example.of(chatRoom);
        Page<ChatRoom> page = chatRoomRepository.findAll(of, pageable);
        ApiResult<List<ChatRoom>> apiResult = ApiResult.pageToApiResult(page);
        return apiResult;
    }

    @Override
    public List<ChatRoom> findList(ChatRoom chatRoom) {
        Example<ChatRoom> of = Example.of(chatRoom);
        List<ChatRoom> chatRoomList = chatRoomRepository.findAll(of);
        return chatRoomList;

    }

    @Override
    public Page<ChatRoom> findList(ChatRoom chatRoom, PageRequest pageRequest) {
        Example<ChatRoom> of = Example.of(chatRoom);
        Page<ChatRoom> all = chatRoomRepository.findAll(of, pageRequest);
        return all;
    }


    @Override
    public ChatRoom insertSelective(ChatRoom chatRoom) {
        if (StringUtils.isBlank(chatRoom.getId())) {
            chatRoom.setId(Sequences.get());
        }


        return chatRoomRepository.save(chatRoom);
    }

    @Override
    public void deleteByPrimaryKey(String id) {
        chatRoomRepository.delete(id);
    }

    @Override
    public ChatRoom updateByPrimaryKeySelective(ChatRoom chatRoom) throws CustomException {
        System.out.println(chatRoom);
        ChatRoom source = chatRoomRepository.findOne(chatRoom.getId());
        UpdateTool.copyNullProperties(source, chatRoom);
        return chatRoomRepository.save(chatRoom);
    }

    @Override
    public ChatRoom findByProduceInAndConsumerIn(List<String> produces, List<String> consumers) {
        return chatRoomRepository.findByProduceInAndConsumerIn(produces,consumers);
    }

    @Override
    public List<ChatRoom> findByProduceOrConsumer(String participant) {
        List<ChatRoom> chatRoomList=new ArrayList<>();
        List<ChatRoom> byProduce = chatRoomRepository.findByProduce(participant);
        List<ChatRoom> byConsumer = chatRoomRepository.findByConsumer(participant);

        if(CollectionUtils.isNotEmpty(byProduce)){
            chatRoomList.addAll(byProduce);
        }
        if(CollectionUtils.isNotEmpty(byConsumer)){
            chatRoomList.addAll(byConsumer);
        }


        return chatRoomList;

        //return chatRoomRepository.findByProduceOrConsumer(participant);
    }


}