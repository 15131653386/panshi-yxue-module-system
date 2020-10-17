package com.panshi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panshi.dao.UserDao;
import com.panshi.entity.Admin;
import com.panshi.entity.User;
import com.panshi.log.anno.YxueLog;
import com.panshi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl extends ServiceImpl<UserDao,User> implements UserService {
    @Autowired(required = false)
    private UserDao userDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> queryall(Integer page1,Integer end) {
        IPage<User> page=new Page<>(page1,end);
        IPage<User> pages=userDao.selectPage(page,null);
        return pages.getRecords();
    }

    @Override
    public User queryname(String condition) {
        QueryWrapper<User> queryWapper=new QueryWrapper<>();
        queryWapper.eq("sign",condition);
        User user=userDao.selectOne(queryWapper);
        return user;
    }

    @Override
    public List<User> querytiaojian(String searchString, String searchField) {
        QueryWrapper<User> queryWapper=new QueryWrapper<>();
        queryWapper.eq(searchField,searchString);
        User user=userDao.selectOne(queryWapper);
        List<User> list=new ArrayList<>();
        list.add(user);
        return list;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public int count(){
        QueryWrapper<User> queryWapper=new QueryWrapper<>();
        List<User> list=userDao.selectList(queryWapper);
        return  list.size();
    }
    @YxueLog(value="对用户信息进行了删除操作",tableName = "yx_user")
    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }
}
