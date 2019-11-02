package com.dingguan.cheHengShi.monitor;

import com.dingguan.cheHengShi.common.constants.Constants;
import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by zyc on 2018/4/25.
 */
@Slf4j
@Aspect
@Component
public class InsertMethodMonitor {
    @Pointcut("execution(public * com.dingguan.*.*.controller.*.save*(..))")
    private void controllerLayer() {
    }

    /**
     *
     * Monitor the method that starts with save to carry the ID
     *
     */
    @Around("controllerLayer()")
    public Object monitorInsertMethodPrimaryKeyExits(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String s = proceedingJoinPoint.getSignature().getDeclaringTypeName() + "." + proceedingJoinPoint.getSignature().getName();
        Object[] args = proceedingJoinPoint.getArgs();

        JSONObject jsonObject = JSONObject.fromObject(args[0]);
        Object o = jsonObject.get("id");
        String id=null;
        if(o!=null)
            id=o.toString();
        if (!("0".equals(id)|| org.apache.commons.lang3.StringUtils.isBlank(id))) {
            log.debug("方法" + s + "新增时携带了id");
            throw new CustomException(Constants.RESP_STATUS_BADREQUEST,"新增时不需要携带id");
        }
        Object result = proceedingJoinPoint.proceed();
        ApiResult result1 = (ApiResult) result;
        Object message = result1.getMessage();


        if(StringUtils.isNumeric(message)){
            Integer i = Integer.parseInt(message.toString());
            if(i<1){
                result1.setCode(Constants.RESP_STATUS_BADREQUEST);
                result1.setMessage("未新增数据");
            }else {
                result1.setMessage("新增成功");
            }
        }



        return result1;

    }









}