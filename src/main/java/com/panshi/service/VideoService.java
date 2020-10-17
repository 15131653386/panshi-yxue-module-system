package com.panshi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.panshi.entity.BackVideo;
import com.panshi.entity.User;
import com.panshi.entity.Video;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoService extends IService<Video> {
    public List<BackVideo> queryblj();
    public List<BackVideo> querytiaojian(String searchField);
    public List<Video> queryvideo(String video);
    List<Video> querySearchVideos(String content);
}
