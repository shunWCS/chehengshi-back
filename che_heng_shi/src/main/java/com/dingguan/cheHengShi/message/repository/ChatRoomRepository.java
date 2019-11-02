package com.dingguan.cheHengShi.message.repository;

import com.dingguan.cheHengShi.message.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by zyc on 2018/12/21.
 */
public interface ChatRoomRepository extends JpaRepository<ChatRoom,String> {
    ChatRoom findByProduceInAndConsumerIn(List<String> produces,List<String> consumers );


    /*@Query(value = "select chatRoom   from chat_room chatRoom  where chatRoom.produce =?1 or chatRoom.consumer= ?1 "*//*, nativeQuery=true*//*)
    List<ChatRoom> findByProduceOrConsumer(String participant);*/


    List<ChatRoom> findByProduce(String produce);
    List<ChatRoom> findByConsumer(String consumer);

}