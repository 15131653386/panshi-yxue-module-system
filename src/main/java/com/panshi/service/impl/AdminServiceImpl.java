package com.panshi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panshi.dao.AdminDao;
import com.panshi.entity.Admin;
import com.panshi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Admin queryuser(String username,String password) {
        Admin admin=null;
        QueryWrapper<Admin> queryWapper=new QueryWrapper<>();
        queryWapper.eq("username",username);
        try{
            admin=adminDao.selectOne(queryWapper);
            if(admin==null){throw new RuntimeException("用户名或密码错误");}
            if(!password.equals(admin.getPassword())){throw new RuntimeException("用户名或密码错误");}
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return admin;
    }
}
