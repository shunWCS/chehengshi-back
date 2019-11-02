package com.dingguan.cheHengShi.common.exception;

import com.alibaba.fastjson.JSONException;
import com.dingguan.cheHengShi.common.constants.Constants;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by zyc on 2018/4/9.
 * 统一的异常处理
 */

@ControllerAdvice
@ResponseBody
@Slf4j
public class ExceptionHandlerAdvice {


    @ExceptionHandler(Exception.class)
    public ApiResult handleException(Exception e){
        log.error(e.getMessage(),e);
        return new ApiResult(Constants.RESP_STATUS_INTERNAL_ERROR,"系统异常，请稍后再试");
    }
    @ExceptionHandler(CustomException.class)
    public ApiResult handleException(CustomException e){
        log.error(e.getMessage(),e);
        return new ApiResult(e.getStatusCode(),e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult handleIllegalParamException(MethodArgumentNotValidException e) {
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String message = "参数不合法";
        if (errors.size() > 0) {
            message = errors.get(0).getDefaultMessage();
        }
        ApiResult result = new ApiResult(Constants.RESP_STATUS_BADREQUEST,message);
        return result;
    }



    //  json格式错误的异常
    @ExceptionHandler(JSONException.class)
    public ApiResult handleIllegalParamException(JSONException e) {
        ApiResult result = new ApiResult(Constants.RESP_STATUS_BADREQUEST,"json格式错误 自己检查 比心");
        return result;
    }



    //  The primary key (id) to be operated on does not exist
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ApiResult handleIllegalParamException(EmptyResultDataAccessException e) {
        ApiResult result = new ApiResult(Constants.RESP_STATUS_BADREQUEST,"要操作的主键(id)不存在");
        return result;
    }

    @ExceptionHandler(JpaObjectRetrievalFailureException.class)
    public ApiResult handleIllegalParamException(JpaObjectRetrievalFailureException e) {
        ApiResult result = new ApiResult(Constants.RESP_STATUS_BADREQUEST,"外键id不存在");
        return result;
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResult handleIllegalParamException(HttpRequestMethodNotSupportedException e) {
        ApiResult result = new ApiResult(Constants.RESP_STATUS_BADREQUEST,"请求方式错误");
        return result;
    }






}
