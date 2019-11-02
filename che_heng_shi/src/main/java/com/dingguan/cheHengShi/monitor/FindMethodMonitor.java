package com.dingguan.cheHengShi.monitor;

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
public class FindMethodMonitor {

    @Pointcut("execution(public * com.dingguan.*.*.controller.*.find*(..))")
    private void controllerLayer() {
    }


    @Around("controllerLayer()")
    public Object monitorInsertMethodPrimaryKeyExits(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //String s = proceedingJoinPoint.getSignature().getDeclaringTypeName() + "." + proceedingJoinPoint.getSignature().getName();
        Object result = proceedingJoinPoint.proceed();
        ApiResult result1 = (ApiResult) result;
        if(result1.getMessage()==null){
            result1.setMessage("查询成功");
        }

        return result1;

    }

}