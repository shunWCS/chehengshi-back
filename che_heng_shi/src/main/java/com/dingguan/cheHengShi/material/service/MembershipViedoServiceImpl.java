package com.dingguan.cheHengShi.material.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.common.utils.DateUtil;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.UpdateTool;
import com.dingguan.cheHengShi.common.utils.Util;
import com.dingguan.cheHengShi.home.mapper.CourseApplyMapper;
import com.dingguan.cheHengShi.material.entity.FlashView;
import com.dingguan.cheHengShi.material.entity.Material;
import com.dingguan.cheHengShi.material.entity.MembershipViedo;
import com.dingguan.cheHengShi.material.repository.FlashViewReposition;
import com.dingguan.cheHengShi.material.repository.MembershipViedoRepository;
import com.dingguan.cheHengShi.product.entity.File;
import com.dingguan.cheHengShi.product.entity.Video;
import com.dingguan.cheHengShi.product.service.FileService;
import com.dingguan.cheHengShi.product.service.VideoService;
import com.dingguan.cheHengShi.trade.entity.OrderDetail;
import com.dingguan.cheHengShi.trade.service.OrderDetailService;
import com.dingguan.cheHengShi.user.entity.User;
import com.dingguan.cheHengShi.user.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by zyc on 2019/4/8.
 */
@Service
public class MembershipViedoServiceImpl implements MembershipViedoService {






    @Autowired
    private MembershipViedoRepository membershipViedoRepository;


    @Override
    public MembershipViedo findByPrimaryKey(String id) {
        return membershipViedoRepository.findOne(id);
    }

    @Override
    public List<MembershipViedo> findList(MembershipViedo membershipViedo) {



        Example<MembershipViedo> of = Example.of(membershipViedo);
        return membershipViedoRepository.findAll(of);
    }




    @Override
    public MembershipViedo insertSelective(MembershipViedo membershipViedo) {
        if(StringUtils.isBlank(membershipViedo.getId())){
            membershipViedo.setId(Sequences.get());
        }
        if(StringUtils.isBlank(membershipViedo.getStatus())){
            membershipViedo.setStatus("1");
        }

        return  membershipViedoRepository.save(membershipViedo);


    }

    @Override
    public void deleteByPrimaryKey(String id) {
        membershipViedoRepository.delete(id);
    }

    @Override
    public MembershipViedo updateByPrimaryKeySelective(MembershipViedo membershipViedo) throws CustomException {
        MembershipViedo source = membershipViedoRepository.findOne(membershipViedo.getId());
        UpdateTool.copyNullProperties(source,membershipViedo);

        return membershipViedoRepository.save(membershipViedo);
    }

    @Override
    public Integer findCountByOpenIdAndStatus(String openId, String status) {
        return membershipViedoRepository.findCountByOpenIdAndStatus(openId,status);
    }


    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private CourseApplyMapper courseApplyMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResult getVideoDetail(String type, String id, String openId) throws CustomException {

        User user = userService.findByPrimaryKey(openId);

        BigDecimal zero = new BigDecimal(0);
        if ("1".equals(type)) {
            //视频

            Video video = videoService.findByPrimaryKey(id, openId);
            String time = DateUtil.getFormatDateTime(video.getTime(), DateUtil.fullFormat);

            video.setBeginTime(time);
            if(zero.compareTo(video.getPrice())==0){
                ApiResult apiResult = ApiResult.returnData(video);
                apiResult.setCode(251);
                apiResult.setData(video);
                apiResult.setMessage("查询成功");
                return apiResult;
            }


        } else if ("2".equals(type)) {
            File file = fileService.findByPrimaryKey(id, openId);

            file.setBeginTime(DateUtil.getFormatDateTime(file.getTime(),DateUtil.fullFormat));
            if(zero.compareTo(file.getPrice())==0){
                ApiResult apiResult = ApiResult.returnData(file);
                apiResult.setCode(251);
                apiResult.setData(file);
                apiResult.setMessage("查询成功");
                return apiResult;
            }

        }





        /*if(user==null){
            ApiResult apiResult=new ApiResult();
            apiResult.setCode(253);
            apiResult.setMessage("无权查看 需要购买或者充会员");
            return apiResult;
        }*/
        if (user != null && StringUtils.isNotBlank(user.getMembershipId())) {
            //会员




            if ("1".equals(type)) {


                MembershipViedo build = MembershipViedo.builder()
                        .openId(openId)
                        .status("1")
                        .videoId(id)
                        .build();
                List<MembershipViedo> membershipViedoList =  findList(build);

                boolean flog=false;
                if(membershipViedoList!=null &&membershipViedoList.size()>0){
                    if(membershipViedoList.size()>1){
                        for(int i=1;i<membershipViedoList.size();i++){
                            MembershipViedo membershipViedo1 = membershipViedoList.get(i);
                            deleteByPrimaryKey(membershipViedo1.getId());
                        }
                    }

                    flog=true;
                }
                //如果没有了的话判断还有没有剩余空间
                if(flog){
                    Integer totalVideoNum = user.getTotalVideoNum();
                    Integer useVideoNum =  findCountByOpenIdAndStatus(openId, "1");
                    if(totalVideoNum.intValue()==useVideoNum.intValue()){
                        //可以看的视频已经满了
                        ApiResult apiResult = new ApiResult();
                        apiResult.setCode(254);
                        apiResult.setData(videoService.findByPrimaryKey(id, null));
                        apiResult.setMessage("能看的视频已经到上限了");
                        return apiResult;
                    }else {
                        MembershipViedo membershipViedo = MembershipViedo.builder()
                                .id(Sequences.get())
                                .openId(openId)
                                .videoId(id)
                                .status("1")
                                .build();
                        insertSelective(membershipViedo);
                    }
                }




                Video video = videoService.findByPrimaryKey(id, openId);
                ApiResult apiResult = ApiResult.returnData(video);
                apiResult.setCode(251);
                apiResult.setData(video);
                apiResult.setMessage("查询成功");
                return apiResult;
            } else if ("2".equals(type)) {
                File file = fileService.findByPrimaryKey(id, openId);
                ApiResult apiResult = ApiResult.returnData(file);
                apiResult.setCode(251);
                apiResult.setData(file);
                apiResult.setMessage("查询成功");
                return apiResult;
            }
        }


        //单次购买了
        OrderDetail build = OrderDetail.builder()
                .commodityId(id)
                .build();
        List<OrderDetail> detailList = orderDetailService.findList(build);



        boolean flog=false;
        if (CollectionUtils.isNotEmpty(detailList)) {

            Date now = new Date();
            for(int i=0;i<detailList.size();i++){
                OrderDetail orderDetail = detailList.get(i);
                String haveUsr = orderDetail.getHaveUsr();
                if(!haveUsr.equals("0")){
                    Date stopTime = orderDetail.getStopTime();
                    if(stopTime!=null){
                        if(stopTime.compareTo(now)>0){
                            flog=true;
                            break;
                        }
                    }
                }
            }
            if(!flog){
                for(int i=0;i<detailList.size();i++){
                    OrderDetail orderDetail = detailList.get(i);
                    String haveUsr = orderDetail.getHaveUsr();
                    if(!haveUsr.equals("0")){
                        if(haveUsr.equals("1")){
                            orderDetail.setHaveUsr("2");
                            Material byType = materialService.findByType("2");
                            if(byType!=null){
                                Integer hour = byType.getHour();
                                if(hour!=null){
                                    Date date = DateUtil.addMinutes(now, hour * 60);
                                    orderDetail.setStopTime(date);
                                }
                            }
                            orderDetailService.updateByPrimaryKeySelective(orderDetail);
                            flog=true;
                            break;
                        }
                    }

                }
            }






        }

        if(flog){
            //单次购买的
            if ("1".equals(type)) {
                Video video = videoService.findByPrimaryKey(id, openId);
                ApiResult apiResult = ApiResult.returnData(video);
                apiResult.setCode(252);
                apiResult.setData(video);
                apiResult.setMessage("查询成功");
                return apiResult;
            } else if ("2".equals(type)) {
                File file = fileService.findByPrimaryKey(id, openId);
                ApiResult apiResult = ApiResult.returnData(file);
                apiResult.setCode(252);
                apiResult.setData(file);
                apiResult.setMessage("查询成功");
                return apiResult;
            }
        }else {
            //无权查看
            if ("1".equals(type)) {
                Video video = videoService.findByPrimaryKey(id, openId);
                ApiResult apiResult = ApiResult.returnData(video);
                apiResult.setCode(253);
                apiResult.setData(video);
                apiResult.setMessage("无权查看 需要购买或者充会员");
                return apiResult;
            } else if ("2".equals(type)) {
                File file = fileService.findByPrimaryKey(id, openId);
                ApiResult apiResult = ApiResult.returnData(file);
                apiResult.setCode(253);
                apiResult.setData(file);
                apiResult.setMessage("无权查看 需要购买或者充会员");
                return apiResult;
            }
        }

        return null;


    }


}