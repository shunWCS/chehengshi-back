package com.dingguan.cheHengShi.monitor;

import com.dingguan.cheHengShi.common.constants.Constants;
import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.common.utils.StringUtils;
import lombok.extern.apachecommons.CommonsLog;
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

@CommonsLog
@Aspect
@Component
@Slf4j
public class UpdateMethodMonitor {


    @Pointcut("execution(public * com.dingguan.*.*.controller.*.put*(..))")
    private void controllerLayer() {
    }

    /**
     * Monitoring whether each modified method starting
     * with put carries the primary key (ID)
     *
     */
    @Around("controllerLayer()")
    public Object monitorUpdateMethodPrimaryKeyExits(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String s = proceedingJoinPoint.getSignature().getDeclaringTypeName() + "." + proceedingJoinPoint.getSignature().getName();
        //System.out.println("切面的方法是" + s);
        //访问目标方法的参数：
        Object[] args = proceedingJoinPoint.getArgs();

        JSONObject jsonObject = JSONObject.fromObject(args[0]);
        Object o = jsonObject.get("id");
        String id = null;
        if (o != null) {
            id = o.toString();
        }
        if ("0".equals(id) ||org.apache.commons.lang3.StringUtils.isBlank(id)) {
            ApiResult apiResult = new ApiResult();
            apiResult.setCode(Constants.RESP_STATUS_BADREQUEST);
            apiResult.setMessage("方法" + s + "修改时缺少id");
            return apiResult;
            //throw new CustomException(Constants.RESP_STATUS_BADREQUEST,"方法" + s + "修改时缺少id");
        }
        Object result = proceedingJoinPoint.proceed();
        ApiResult result1 = (ApiResult) result;
        Object message = result1.getMessage();


        if(StringUtils.isNumeric(message)){
            Integer i = Integer.parseInt(message.toString());
            if(i<1){
                result1.setCode(Constants.RESP_STATUS_BADREQUEST);
                result1.setMessage("未修改任何数据");
            }else {
                result1.setMessage("修改成功");
            }
        }



        return result1;

    }


}