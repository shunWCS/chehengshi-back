package com.dingguan.cheHengShi.user.controller;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.common.utils.DateUtil;
import com.dingguan.cheHengShi.product.entity.File;
import com.dingguan.cheHengShi.product.service.FileService;
import com.dingguan.cheHengShi.user.entity.BrowseRecord;
import com.dingguan.cheHengShi.user.service.BrowseRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * Created by zyc on 2018/12/20.
 */
@RestController
@RequestMapping("/browseRecord")
@Api(description = "浏览记录")
public class BrowseRecordController {

    @Autowired
    private BrowseRecordService browseRecordService;


    @GetMapping("/{id}")
    @ApiOperation(value = "根据 id 查询 浏览记录")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> findById(@PathVariable String id) {
        return ApiResult.returnData(browseRecordService.findByPrimaryKey(id));
    }


    @ApiOperation(value = "获取 浏览记录 列表")
    @GetMapping()
    public ApiResult findList(
            @ApiParam("openId") @RequestParam(value = "openId", required = false) String openId,
            @ApiParam("第几页 从0开始") @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
            @ApiParam("每页多少条数据") @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {


        if (pageIndex != null && pageSize != null) {
            Sort sort = new Sort(Sort.Direction.DESC, "time");
            PageRequest pageRequest = new PageRequest(pageIndex, pageSize, sort);
            ApiResult apiResult = browseRecordService.findList(openId, pageRequest);
            List<BrowseRecord> browseRecordList = (List<BrowseRecord>)apiResult.getData();
            Map<String, List<BrowseRecord>> map = new HashMap<>();
            if (CollectionUtils.isNotEmpty(browseRecordList)) {
                for(int i=0;i<browseRecordList.size();i++){
                    BrowseRecord browseRecord = browseRecordList.get(i);
                    Date time = browseRecord.getTime();
                    String formatDateTime = DateUtil.getFormatDateTime(time, DateUtil.yyyyMMddChineseFormat);

                    List<BrowseRecord> browseRecords = map.get(formatDateTime);
                    if (CollectionUtils.isEmpty(browseRecords)) {
                        browseRecords = new ArrayList<>();
                    }
                    browseRecords.add(browseRecord);
                    map.put(formatDateTime, browseRecords);
                }

            }
            apiResult.setData(map);
            return apiResult;
        }


        List<BrowseRecord> browseRecordList = browseRecordService.findList(openId);


        Map<String, List<BrowseRecord>> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(browseRecordList)) {


            for(int i=0;i<browseRecordList.size();i++){
                BrowseRecord browseRecord = browseRecordList.get(i);
                Date time = browseRecord.getTime();
                String formatDateTime = DateUtil.getFormatDateTime(time, DateUtil.yyyyMMddChineseFormat);

                List<BrowseRecord> browseRecords = map.get(formatDateTime);
                if (CollectionUtils.isEmpty(browseRecords)) {
                    browseRecords = new ArrayList<>();
                }
                browseRecords.add(browseRecord);
                map.put(formatDateTime, browseRecords);

            }

        }




        return ApiResult.returnData(sortMapByKey(map));
    }



    public static Map<String, List<BrowseRecord>> sortMapByKey(Map<String, List<BrowseRecord>> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, List<BrowseRecord>> sortMap = new TreeMap<String, List<BrowseRecord>>(
                new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        return  ((String)o2).compareTo((String)o1);
                    }
                });

        sortMap.putAll(map);

        return sortMap;
    }










    @ApiOperation(value = "新增 浏览记录")
    @PostMapping
    @ApiImplicitParam(name = "browseRecord", value = "", paramType = "body", dataType = "BrowseRecord")
    public ApiResult<String> save(@RequestBody @Valid BrowseRecord browseRecord, @ModelAttribute BrowseRecord model) {
        return ApiResult.returnData(browseRecordService.insertSelective(browseRecord));
    }


    @ApiOperation(value = "删除 浏览记录")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", dataType = "String")
    public ApiResult<String> deleteById(@PathVariable String id) {
        browseRecordService.deleteByPrimaryKey(id);
        return ApiResult.returnData(null);
    }


    @ApiOperation(value = "修改 浏览记录")
    @PutMapping("")
    @ApiImplicitParam(name = "browseRecord", value = "", paramType = "body", dataType = "BrowseRecord")
    public ApiResult<String> put(@RequestBody BrowseRecord browseRecord, @ModelAttribute BrowseRecord model) throws CustomException {
        return ApiResult.returnData(browseRecordService.updateByPrimaryKeySelective(browseRecord));
    }

}