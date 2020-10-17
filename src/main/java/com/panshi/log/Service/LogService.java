package com.panshi.log.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.panshi.log.entity.Log;

public interface LogService extends IService<Log> {
    public void insert(Log log);
}
