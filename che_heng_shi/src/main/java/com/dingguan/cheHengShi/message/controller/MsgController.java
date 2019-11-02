package com.dingguan.cheHengShi.message.controller;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.message.entity.Msg;
import com.dingguan.cheHengShi.message.service.MsgService;
import com.dingguan.cheHengShi.user.entity.BrowseRecord;
import com.dingguan.cheHengShi.user.service.BrowseRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by zyc on 2018/12/21.
 */
@RestController
@RequestMapping("/msg")
@Api(description = "聊天记录")
public class MsgController {


    @Autowired
    private MsgService msgService;


    @GetMapping("/{id}")
    @ApiOperation(value = "根据 id 查询 聊天记录")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> findById(@PathVariable String id) {
        return ApiResult.returnData(msgService.findByPrimaryKey(id));
    }


    @ApiOperation(value = "获取 聊天记录 列表")
    @GetMapping()
    public ApiResult findList(
            @ApiParam("聊天室id") @RequestParam(value = "chatRoomId",required = false)String chatRoomId,
           // @ApiParam("消息 生产者") @RequestParam(value = "produce",required = false)String produce,
            @ApiParam("消息 消费者 这个参数还用于把未读改为已读") @RequestParam(value = "consumer",required = false)String consumer,
            @ApiParam("页码 从0开始") @RequestParam(value = "pageIndex",required = false)Integer pageIndex,
            @ApiParam("每页多少条") @RequestParam(value = "pageSize",required = false)Integer pageSize
    ) {
        Msg build = Msg.builder()
                .chatRoomId(chatRoomId)
                //.consumer(consumer)
                //.produce(produce)
                .build();

        Sort sort = new Sort(Sort.Direction.DESC, "time");
        if(pageIndex==null ||pageSize==null){
            List<Msg> msgList = msgService.findList(build, sort,consumer);
            return ApiResult.returnData(msgList);

        }
        Pageable pageRequest = new PageRequest(pageIndex, pageSize, sort);
        return msgService.findList(build,pageRequest,consumer);
    }

    @ApiOperation(value = "新增 聊天记录")
    @PostMapping
    @ApiImplicitParam(name = "msg", value = "", paramType = "body", dataType = "Msg")
    public ApiResult<String> save(@RequestBody @Valid Msg msg, @ModelAttribute Msg model) throws CustomException {
        return ApiResult.returnData(msgService.insertSelective(msg));
    }


    @ApiOperation(value = "删除 聊天记录")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> deleteById(@PathVariable String id) {
        msgService.deleteByPrimaryKey(id);
        return ApiResult.returnData(null);
    }


    @ApiOperation(value = "修改 聊天记录")
    @PutMapping("")
    @ApiImplicitParam(name = "msg", value = "", paramType = "body", dataType = "Msg")
    public ApiResult<String> put(@RequestBody Msg msg, @ModelAttribute Msg model) throws CustomException {
        return ApiResult.returnData(msgService.updateByPrimaryKeySelective(msg));
    }

}