package com.dingguan.cheHengShi.common.resp;

import com.dingguan.cheHengShi.common.constants.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Created by JackWangon[www.] 2017/7/29.
 */
@Data
@Builder
@ApiModel(value = "返回类")
public class ApiResult<T> {

    @ApiModelProperty("返回码")
    private int code = Constants.RESP_STATUS_OK;
    @ApiModelProperty("消息")
    private String message;
    @ApiModelProperty("返回对象")
    private T data;
    @ApiModelProperty("总条数")
    private Integer pageTotal;
    @ApiModelProperty("一页显示多少")
    private Integer pageSize;
    @ApiModelProperty("第几页")
    private Integer pageIndex;


    public ApiResult() {
    }
    public ApiResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResult(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public ApiResult(int code, String message, T data, Integer pageTotal, Integer pageSize, Integer pageIndex) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.pageTotal = pageTotal;
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
    }

    public static ApiResult returnData(Object o){
        ApiResult apiResult = new ApiResult();
        apiResult.setData(o);
        return apiResult;
    }
    public static ApiResult returnDataAndInt(Object o,Integer i){
        ApiResult apiResult = new ApiResult();
        apiResult.setData(o);
        apiResult.setMessage(i+"");
        return apiResult;
    }

    public static ApiResult pageToApiResult(Page page){
        List content = page.getContent();//data

        long totalElements = page.getTotalElements();//总数量量
        int size = page.getSize();//每页size数
        int number = page.getNumber();//页面数 从0开始  当前是第n+1页


        Sort sort = page.getSort();//排序字段 相关信息
        int numberOfElements = page.getNumberOfElements();//本页实际上查询到了多少条数据
        int totalPages = page.getTotalPages();//总页面数


        ApiResult apiResult = ApiResult.returnData(content);
        apiResult.setPageIndex(++number);
        apiResult.setPageTotal((int)totalElements);
        apiResult.setPageSize(size);

        return apiResult;
    }


    public static ApiResult pageToApiResult(Page page, ApiResult apiResult){
        List content = page.getContent();//data
        long totalElements = page.getTotalElements();//总数量量
        int size = page.getSize();//每页size数
        int number = page.getNumber();//页面数 从0开始  当前是第n+1页


        Sort sort = page.getSort();//排序字段 相关信息
        int numberOfElements = page.getNumberOfElements();//本页实际上查询到了多少条数据
        int totalPages = page.getTotalPages();//总页面数


        apiResult.setData(content);
        apiResult.setPageIndex(++number);
        apiResult.setPageTotal((int)totalElements);
        apiResult.setPageSize(size);

        return apiResult;
    }



}
