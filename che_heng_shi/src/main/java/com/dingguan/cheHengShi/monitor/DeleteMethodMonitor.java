package com.dingguan.cheHengShi.monitor;

import com.dingguan.cheHengShi.common.constants.Constants;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import lombok.extern.slf4j.Slf4j;
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
public class DeleteMethodMonitor {
    @Pointcut("execution(public * com.dingguan.*.*.controller.*.deleteById*(..))")
    private void controllerLayer() {
    }

    /**
     * Monitor the existence of ID when deleteById is deleted.
     *
     *
     *
     */
    @Around("controllerLayer()")
    public Object monitorDeleteMethodPrimaryKeyExits(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String s = proceedingJoinPoint.getSignature().getDeclaringTypeName() + "." + proceedingJoinPoint.getSignature().getName();

        Object result = proceedingJoinPoint.proceed();
        ApiResult result1 = (ApiResult) result;
        Object data = result1.getData();
        if(data!=null){
            Integer i = Integer.parseInt(data.toString());
            result1.setData(null);
            if(i<1){
                result1.setCode(Constants.RESP_STATUS_BADREQUEST);
                result1.setMessage("未删除任何数据");
            }else {
                result1.setMessage("删除成功");
            }
        }else {
            result1.setMessage("操作成功");
        }
        return result1;
    }



}