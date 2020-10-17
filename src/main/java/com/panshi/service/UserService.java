package com.panshi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.panshi.dao.UserDao;
import com.panshi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface UserService extends IService<User> {
    public List<User> queryall(Integer page1,Integer end);
    public User queryname(String condition);
    public List<User> querytiaojian(String searchString, String searchField);
}
