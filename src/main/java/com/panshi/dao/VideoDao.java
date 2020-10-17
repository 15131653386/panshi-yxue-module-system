package com.panshi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.panshi.entity.BackVideo;
import com.panshi.entity.Video;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoDao extends BaseMapper<Video> {
    public List<BackVideo> queryblj();
    public List<BackVideo> querytiaojian(@Param("searchField") String searchField);
    public List<Video> queryvideo(String video);
}
