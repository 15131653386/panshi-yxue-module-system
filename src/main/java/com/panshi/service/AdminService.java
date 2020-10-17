package com.panshi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.panshi.dao.AdminDao;
import com.panshi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public interface AdminService{

    public Admin queryuser(String username,String password);
}
