package com.dingguan.cheHengShi.job;

import com.dingguan.cheHengShi.user.entity.User;
import com.dingguan.cheHengShi.user.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by zyc on 2018/12/25.
 */
@EnableScheduling
@Component
public class TimedTask {

    @Autowired
    private UserService userService;




    @Scheduled(cron = "0 56 0 * * ? ")
    public void redisKeep(){


        //校验过期的会员
        Integer pageIndex=0;
        Integer pageSize=10;
        Boolean flog =true;
        while (flog){
            PageRequest pageRequest = new PageRequest(pageIndex, pageSize);

            List<User> userList = userService.findUserBymembershipId(pageRequest);
            if(CollectionUtils.isEmpty(userList)){
                flog=false;
                pageIndex=0;
            }else {
                pageIndex++;
                userService.checkUser(userList);
            }
        }






    }





}