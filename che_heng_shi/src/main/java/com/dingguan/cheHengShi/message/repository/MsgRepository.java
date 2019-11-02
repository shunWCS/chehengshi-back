package com.dingguan.cheHengShi.message.repository;

import com.dingguan.cheHengShi.message.entity.Msg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by zyc on 2018/12/21.
 */
public interface MsgRepository extends JpaRepository<Msg,String> {


    List<Msg> findByProduceOrderByTimeDesc(String produce);
    List<Msg> findByConsumerOrderByTimeDesc(String consumer);


    Page<Msg> findByProduce(String produce, Pageable pageable);
    Page<Msg> findByConsumer(String consumer, Pageable pageable);


    Msg findTopByChatRoomIdOrderByTimeDesc(String chatRoomId);

    @Query(value = "select count(id) from msg  where chat_room_id =?1 and `status`= ?2 ", nativeQuery=true)
    Integer winterCicada(String chatRoomId,String status);





}