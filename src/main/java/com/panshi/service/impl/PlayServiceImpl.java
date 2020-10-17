package com.panshi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panshi.dao.LikeDao;
import com.panshi.dao.PlayDao;
import com.panshi.entity.Like;
import com.panshi.entity.Play;
import com.panshi.log.anno.YxueLog;
import com.panshi.service.PlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class PlayServiceImpl extends ServiceImpl<PlayDao, Play> implements PlayService {
    @Autowired
    private PlayDao playDao;
    @Override
    @YxueLog(value="用户删除了视频同时删除了播放量数据",tableName = "yx_play")
    public void deleteplay(String videoId) {
        playDao.deleteplay(videoId);
    }

}
