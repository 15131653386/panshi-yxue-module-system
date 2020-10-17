package com.panshi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panshi.dao.LikeDao;
import com.panshi.dao.UserDao;
import com.panshi.entity.Like;
import com.panshi.entity.User;
import com.panshi.service.LikeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class LikeServiceImpl extends ServiceImpl<LikeDao, Like> implements LikeService {

}
