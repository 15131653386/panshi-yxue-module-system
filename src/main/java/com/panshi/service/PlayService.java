package com.panshi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.panshi.entity.Play;
import com.panshi.entity.Video;

public interface PlayService extends IService<Play> {
    public void deleteplay(String videoId);
}
