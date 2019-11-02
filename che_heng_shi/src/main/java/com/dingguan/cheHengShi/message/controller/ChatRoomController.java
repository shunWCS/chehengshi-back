package com.dingguan.cheHengShi.message.controller;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.message.entity.ChatRoom;
import com.dingguan.cheHengShi.message.entity.Msg;
import com.dingguan.cheHengShi.message.service.ChatRoomService;
import com.dingguan.cheHengShi.message.service.MsgService;
import com.dingguan.cheHengShi.user.entity.User;
import com.dingguan.cheHengShi.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zyc on 2018/12/21.
 */
@RestController
@RequestMapping("/chatRoom")
@Api(description = "聊天室")
public class ChatRoomController {

    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private UserService userService;


    @GetMapping("/{id}")
    @ApiOperation(value = "根据 id 查询 聊天室")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> findById(@PathVariable String id) {
        return ApiResult.returnData(chatRoomService.findByPrimaryKey(id));
    }


    @ApiOperation(value = "获取 聊天室 列表")
    @GetMapping()
    public ApiResult findList(
            @ApiParam("消息 生产者") @RequestParam(value = "produce", required = false) String produce,
            @ApiParam("消息 消费者") @RequestParam(value = "consumer", required = false) String consumer,
            @ApiParam("页码 从0开始") @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
            @ApiParam("每页多少条") @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {
        ChatRoom build = ChatRoom.builder()
                .consumer(consumer)
                .produce(produce)
                .build();

        Sort sort = new Sort(Sort.Direction.DESC, "lastTime");
        if (pageIndex == null || pageSize == null) {
            List<ChatRoom> chatRoomList = chatRoomService.findList(build);
            return ApiResult.returnData(chatRoomList);
        }

        Pageable pageRequest = new PageRequest(pageIndex, pageSize, sort);
        return chatRoomService.findList(build, pageRequest);
    }


    @ApiOperation(value = "根据2个用户查询一个 聊天室")
    @GetMapping("/byOne")
    public ApiResult findList(
            @ApiParam("消息 生产者") @RequestParam(value = "produce") String produce,
            @ApiParam("消息 消费者") @RequestParam(value = "consumer") String consumer
    ) {
        List<String> produces = new ArrayList<>();
        List<String> consumers = new ArrayList<>();
        produces.add(produce);
        produces.add(consumer);
        consumers.add(produce);
        consumers.add(consumer);

        ChatRoom chatRoom = chatRoomService.findByProduceInAndConsumerIn(produces, consumers);
        return ApiResult.returnData(chatRoom);
    }


    @ApiOperation(value = "查询聊天列表 顺便带上对方的头像昵称那些")
    @GetMapping("/list")
    public ApiResult findList1(
            @ApiParam("聊天参与者 就是查询这个人的聊天列表") @RequestParam(value = "participant", required = false) String participant,
            @ApiParam("页码 从0开始") @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
            @ApiParam("每页多少条") @RequestParam(value = "pageSize", required = false) Integer pageSize

    ) {

        List<ChatRoom> chatRoomList = chatRoomService.findByProduceOrConsumer(participant);
        if(pageIndex!=null &&pageSize!=null){
            Integer pageNo =(pageIndex)*pageSize;
            chatRoomList=chatRoomList.stream().skip(pageNo).limit(pageSize).collect(Collectors.toList());
        }
       if(CollectionUtils.isNotEmpty(chatRoomList)){
           chatRoomList.stream().forEach(
                   chatRoom->{
                       if (chatRoom.getProduce().equals(participant)) {
                           //发起人
                           String consumer = chatRoom.getConsumer();
                           User twoElephants = userService.findNickNameAndHeadImgByOpenId(consumer);
                           chatRoom.setConsumerHearName(twoElephants.getHeadImg());
                           chatRoom.setConsumerNickName(twoElephants.getNickName());

                       } else if (chatRoom.getConsumer().equals(participant)) {
                           //参与者
                           String produce = chatRoom.getProduce();
                           User twoElephants = userService.findNickNameAndHeadImgByOpenId(produce);
                           chatRoom.setProduceHearName(twoElephants.getHeadImg());
                           chatRoom.setProduceNickName(twoElephants.getNickName());
                       }

                   }
           );

       }

         return ApiResult.returnData(chatRoomList);
    }


    @ApiOperation(value = "新增 聊天室")
    @PostMapping
    @ApiImplicitParam(name = "chatRoom", value = "", paramType = "body", dataType = "ChatRoom")
    public ApiResult<String> save(@RequestBody @Valid ChatRoom chatRoom, @ModelAttribute ChatRoom model) {
        return ApiResult.returnData(chatRoomService.insertSelective(chatRoom));
    }


    @ApiOperation(value = "删除 聊天室")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> deleteById(@PathVariable String id) {
        chatRoomService.deleteByPrimaryKey(id);
        return ApiResult.returnData(null);
    }


    @ApiOperation(value = "修改 聊天室")
    @PutMapping("")
    @ApiImplicitParam(name = "chatRoom", value = "", paramType = "body", dataType = "ChatRoom")
    public ApiResult<String> put(@RequestBody ChatRoom chatRoom, @ModelAttribute ChatRoom model) throws CustomException {
        return ApiResult.returnData(chatRoomService.updateByPrimaryKeySelective(chatRoom));
    }

}