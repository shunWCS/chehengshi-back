package com.dingguan.cheHengShi.message.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.UpdateTool;
import com.dingguan.cheHengShi.job.AsynchronousJob;
import com.dingguan.cheHengShi.message.entity.ChatRoom;
import com.dingguan.cheHengShi.message.entity.Msg;
import com.dingguan.cheHengShi.message.repository.MsgRepository;
import com.dingguan.cheHengShi.user.entity.BrowseRecord;
import com.dingguan.cheHengShi.user.repository.BrowseRecordRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zyc on 2018/12/21.
 */
@Service
public class MsgServiceImpl implements MsgService {

    @Autowired
    private MsgRepository msgRepository;
    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    @Lazy
    private AsynchronousJob asynchronousJob;


    @Override
    public Msg findByPrimaryKey(String id) {
        Msg msg= msgRepository.findOne(id);
        return msg;
    }

    @Override
    public List<Msg> findByProduceOrderByTimeDesc(String produce) {
        List<Msg> msgList = msgRepository.findByProduceOrderByTimeDesc(produce);
        return msgList;
    }

    @Override
    public List<Msg> findByConsumerOrderByTimeDesc(String consumer) {
        List<Msg> msgList = msgRepository.findByConsumerOrderByTimeDesc(consumer);
        return msgList;
    }



    @Override
    public ApiResult<List<Msg>> findList(Msg msg, Pageable pageable,String consumer) {

        Example<Msg> of = Example.of(msg);
        Page<Msg> page = msgRepository.findAll(of, pageable);
        ApiResult<List<Msg>> apiResult = ApiResult.pageToApiResult(page);
        asynchronousJob.toAlreadyRead(apiResult.getData(),null,consumer);
        return apiResult;
    }

    @Override
    public List<Msg> findList(Msg msg, Sort sort,String consumer) {
        Example<Msg> of = Example.of(msg);
        List<Msg> msgList = msgRepository.findAll(of, sort);
        asynchronousJob.toAlreadyRead(msgList,null,consumer);
        return msgList;
    }


    @Override
    public Msg insertSelective(Msg msg) throws CustomException {
        if (StringUtils.isBlank(msg.getId())) {
            msg.setId(Sequences.get());
        }
        if (msg.getTime() == null) {
            msg.setTime(new Date());
        }
        if(msg.getStatus()==null){
            msg.setStatus("1");
        }

        ChatRoom build = ChatRoom.builder()
                .id(msg.getChatRoomId())
                .lastTime(msg.getTime())
                .lastText(msg.getText())
                .msgType(msg.getMsgType())
                .build();
        chatRoomService.updateByPrimaryKeySelective(build);

        //System.out.println(msg);
        return msgRepository.save(msg);
    }

    @Override
    public void deleteByPrimaryKey(String id) {
        msgRepository.delete(id);
    }

    @Override
    public Msg updateByPrimaryKeySelective(Msg msg) throws CustomException {
        Msg source = msgRepository.findOne(msg.getId());
        UpdateTool.copyNullProperties(source, msg);
        return msgRepository.save(msg);
    }

    @Override
    public Msg findTopByChatRoomIdOrderByTimeDesc(String chatRoomId) {
        return msgRepository.findTopByChatRoomIdOrderByTimeDesc(chatRoomId);
    }

    @Override
    public Integer winterCicada(String chatRoomId) {
        return msgRepository.winterCicada(chatRoomId,"2");
    }


}