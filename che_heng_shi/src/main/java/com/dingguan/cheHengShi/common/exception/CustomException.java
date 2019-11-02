package com.dingguan.cheHengShi.common.exception;

import com.dingguan.cheHengShi.common.constants.Constants;

/**
 *
 *用于自定义异常
 */
public class CustomException extends Exception{

    /**
     *
     */
    private static final long serialVersionUID = -7370331410579650067L;

    private Integer errorCode;

    public CustomException(String message) {
        super(message);
        this.errorCode= Constants.RESP_STATUS_INTERNAL_ERROR;
    }

    public CustomException(Integer errorCode, String message) {
        super(message);
        this.errorCode=errorCode;
    }

    public int getStatusCode() {
        return this.errorCode;
    }


}
