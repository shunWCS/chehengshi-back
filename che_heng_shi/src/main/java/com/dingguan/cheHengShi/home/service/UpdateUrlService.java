package com.dingguan.cheHengShi.home.service;

import com.dingguan.cheHengShi.home.mapper.UpdateUrlMapper;
import com.dingguan.cheHengShi.product.entity.File;
import com.dingguan.cheHengShi.product.entity.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpdateUrlService {

    @Autowired
    private UpdateUrlMapper updateUrlMapper;

    private static final String url = "https://xcxossfile.oss-cn-shenzhen.aliyuncs.com/fileUpload";

    public Integer selectFileUrl(String id) {
        File file = updateUrlMapper.selectFileUrl(id);
        String banner = file.getBanner();
        int begin = banner.lastIndexOf("/");
        String substring = banner.substring(begin, banner.length());
        String tempUrl = url.concat(substring);
        file.setBanner(tempUrl);
        int count = updateUrlMapper.updateUrlFile(file);
        return count;
    }

    public Integer selectFileUrlALL() {
        List<File> files = updateUrlMapper.selectFileUrlALL();
        Integer count = 0;
        for(File file : files){
            String banner = file.getBanner();
            int begin = banner.lastIndexOf("/");
            String substring = banner.substring(begin, banner.length());
            String tempUrl = url.concat(substring);
            file.setBanner(tempUrl);
            updateUrlMapper.updateUrlFile(file);
            count ++;
        }
        return count;
    }

    public Integer selectVideoUrl(String id) {
        Video video = updateUrlMapper.selectVideoUrl(id);
        String banner = video.getBanner();
        int begin = banner.lastIndexOf("/");
        String substring = banner.substring(begin, banner.length());
        video.setBanner(url.concat(substring));
        int index = video.getVideo().lastIndexOf("/");
        String substring1 = video.getVideo().substring(index, video.getVideo().length());
        video.setVideo(url.concat(substring1));
        return updateUrlMapper.updateUrlvideo(video);
    }

    public Integer selectVideoUrlALL(String id) {
        List<Video> videos = updateUrlMapper.selectVideoUrlALL();
        Integer count = 0;
        for(Video video : videos){
            int begin = video.getBanner().lastIndexOf("/");
            String substring = video.getBanner().substring(begin, video.getBanner().length());
            video.setBanner(url.concat(substring));
            int index = video.getVideo().lastIndexOf("/");
            String substring1 = video.getVideo().substring(index, video.getVideo().length());
            video.setVideo(url.concat(substring1));
            updateUrlMapper.updateUrlvideo(video);
            count ++;
        }
        return count;
    }
}
