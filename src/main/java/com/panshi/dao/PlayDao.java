package com.panshi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.panshi.entity.Play;

public interface PlayDao extends BaseMapper<Play> {
    public void deleteplay(String videoId);
}
