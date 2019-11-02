package com.dingguan.cheHengShi.message.webSocket;

import com.alibaba.fastjson.JSON;
import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.utils.DateUtil;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.TypeConversionUtils;
import com.dingguan.cheHengShi.message.entity.ChatRoom;
import com.dingguan.cheHengShi.message.entity.Msg;
import com.dingguan.cheHengShi.message.entity.SocketVo;
import com.dingguan.cheHengShi.message.service.ChatRoomService;
import com.dingguan.cheHengShi.message.service.MsgService;
import com.dingguan.cheHengShi.user.entity.User;
import com.dingguan.cheHengShi.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by zyc on 2018/12/21.
 */
@Component
@Slf4j
@ServerEndpoint(value = "/cheHengshi/msg" /*,configurator = SpringConfigurator.class*/)
public class MsgSocket {

    /**
     * 注入bean
     */
    private static MsgService msgService;

    @Autowired
    public void setMsgService(MsgService msgService) {
        MsgSocket.msgService = msgService;
    }





    private static ChatRoomService chatRoomService;

    @Autowired
    public void setChatRoomService(ChatRoomService chatRoomService) {
        MsgSocket.chatRoomService = chatRoomService;
    }


    private static UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        MsgSocket.userService = userService;
    }


    //缓存监听 key:openId  value session
    static ConcurrentHashMap<String, Session> cacheSession = new ConcurrentHashMap<>();
    //缓存监听 key:session  value openId
    static ConcurrentHashMap<Session, String> cacheSessionByOpenId = new ConcurrentHashMap<>();



    @OnOpen
    public void connect(Session session) throws Exception {}



    @OnMessage
    public void receiveMsg(String msg, Session session) throws Exception {
        log.info(DateUtil.getFormatDateTime(new Date(), DateUtil.fullFormat) + "收到用户发过来的消息" + msg);
        SocketVo socketVo = TypeConversionUtils.JsonStrinToJavaBean(msg, SocketVo.class);

        String type = socketVo.getType();
        if (StringUtils.isBlank(type)) {
            //错误的请求参数
            errorMse(session, "");
        } else if ("1".equals(type)) {
            String openId = socketVo.getOpenId();
            if (StringUtils.isNotBlank(openId)) {
                cacheSession.put(openId, session);
                cacheSessionByOpenId.put(session, openId);
                springCicada(openId, session,socketVo.getPageIndex(),socketVo.getPageSize());
            } else {
                errorMse(session, "");
            }
        } else if ("4".equals(type)) {
            enjoyTheCool(socketVo.getMsg(), cacheSessionByOpenId.get(session));
        }


    }



    @OnClose
    public void disConnect(Session session, CloseReason closeReason) {
        String openId = cacheSessionByOpenId.get(session);

        if(StringUtils.isNotBlank(openId)){
            System.out.println("--------------------------------------------------");
            System.out.println(cacheSession);
            System.out.println(openId);
            System.out.println("--------------------------------------------------");
            cacheSession.remove(openId);
        }

        cacheSessionByOpenId.remove(session);
        System.out.println((DateUtil.getFormatDateTime(new Date(), DateUtil.fullFormat) + openId + "关闭了通信"));
        System.out.println("关闭的连接的原因 = {} {}" + closeReason.getCloseCode() + closeReason.toString());
    }


    @OnError
    public void onError(Session session, Throwable error) {
        log.info("发生错误" + DateUtil.getFormatDateTime(new Date(), DateUtil.fullFormat));
        error.printStackTrace();
    }


    /**
     *
     */
    private void springCicada(String openId, Session session,Integer pageIndex,Integer pageSize) {


        List<ChatRoom> chatRoomList =chatRoomService.findByProduceOrConsumer(openId);

        Integer winterCicadaCount = 0;
        if (CollectionUtils.isNotEmpty(chatRoomList)) {
            for (int i = 0; i < chatRoomList.size(); i++) {
                if(i>=pageIndex&& i<=(pageIndex)*pageSize){
                    ChatRoom chatRoom = chatRoomList.get(i);

                    Msg lastMsg = msgService.findTopByChatRoomIdOrderByTimeDesc(chatRoom.getId());
                    if (lastMsg != null) {
                        //最后聊天消息
                        chatRoom.setLastText(lastMsg.getText());
                        chatRoom.setLastTime(lastMsg.getTime());

                        //昵称头像
                        if (chatRoom.getProduce().equals(openId)) {
                            //发起人
                            String consumer = chatRoom.getConsumer();
                            User twoElephants = userService.findNickNameAndHeadImgByOpenId(consumer);
                            chatRoom.setConsumerHearName(twoElephants.getHeadImg());
                            chatRoom.setConsumerNickName(twoElephants.getNickName());

                        } else if (chatRoom.getConsumer().equals(openId)) {
                            //参与者
                            String produce = chatRoom.getProduce();
                            User twoElephants = userService.findNickNameAndHeadImgByOpenId(produce);
                            chatRoom.setProduceHearName(twoElephants.getHeadImg());
                            chatRoom.setProduceNickName(twoElephants.getNickName());
                        }
                        //未读消息数
                        Integer winterCicada = msgService.winterCicada(chatRoom.getId());
                        chatRoom.setWinterCicada(winterCicada);
                        winterCicadaCount = winterCicadaCount + winterCicada;
                }

                }
            }
        }

        List<ChatRoom> collect = chatRoomList.stream().skip(pageIndex).limit((pageIndex) * pageSize).collect(Collectors.toList());

        SocketVo socketVo = SocketVo.builder()
                .type("3")
                .winterCicada(winterCicadaCount)
                .chatRoomList(collect)
                .totalElements(chatRoomList.size()+0l)
                .size(pageSize)
                .number(pageIndex)
                .build();
        sendToUser(session, socketVo);

    }


    /**
     *
     */
    private void enjoyTheCool(Msg msgEntity, String openId) throws CustomException {
        //一 消息存储
        String chatRoomId = msgEntity.getChatRoomId();
        if (StringUtils.isBlank(chatRoomId)) {

            List<String> produces=new ArrayList<>();
            List<String> consumers=new ArrayList<>();
            produces.add(openId);
            produces.add(msgEntity.getConsumer());
            consumers.add(openId);
            consumers.add(msgEntity.getConsumer());
            ChatRoom room = chatRoomService.findByProduceInAndConsumerIn(produces, consumers);
            if(room==null){
                ChatRoom chatRoom = ChatRoom.builder()
                        .id(Sequences.get())
                        .produce(openId)
                        .consumer(msgEntity.getConsumer())
                        .lastTime(new Date())
                        .lastText(msgEntity.getText())
                        .msgType(msgEntity.getMsgType())
                        .build();
                chatRoomService.insertSelective(chatRoom);
                msgEntity.setChatRoomId(chatRoom.getId());
            }else {
                msgEntity.setChatRoomId(room.getId());
            }



        }
        msgEntity.setId(Sequences.get());
        msgEntity.setTime(new Date());
        msgEntity.setStatus("1");
        msgEntity.setProduce(openId);
        msgService.insertSelective(msgEntity);


        /**
         * 二 在线发送消息
         */
        boolean flog = false;
        String twoElephants = null;
        if (msgEntity.getProduce().equals(openId)) {
            twoElephants = msgEntity.getConsumer();
        } else {
            twoElephants = msgEntity.getProduce();
        }
        Session session1 = cacheSession.get(twoElephants);
        if (session1 != null) {
            flog = true;
        }
        if (flog) {
            SocketVo sendUserMessage = SocketVo.builder()
                    .type("5")
                    .msg(msgEntity)
                    .build();
            sendToUser(session1, sendUserMessage);
        }
    }



    private void errorMse(Session session, String msg) {
        String s = StringUtils.isBlank(msg) ? "传的什么玩意???" : msg;
        SocketVo build = SocketVo.builder()
                .type("2")
                .message(s)
                .build();
        sendToUser(session, build);
    }



    public void sendToUser(Session session, Object msg) {
        String jsonString = JSON.toJSONString(msg);
        log.info("给指定用户推送了消息:" + jsonString);
        session.getAsyncRemote().sendText(jsonString);
    }





}