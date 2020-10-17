package com.panshi.log.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panshi.log.Service.LogService;
import com.panshi.log.dao.LogDao;
import com.panshi.log.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceimpl extends ServiceImpl<LogDao, Log> implements LogService {
    @Autowired
    private LogDao logDao;
    @Override
    public void insert(Log log) {
        logDao.insert(log);
    }
}
