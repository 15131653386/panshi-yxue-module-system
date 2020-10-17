package com.panshi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.panshi.entity.Category;
import com.panshi.entity.Feedback;
import com.panshi.entity.User;

import java.util.List;

public interface FeedbackService extends IService<Feedback> {
    public List<Feedback> queryall(Integer page1, Integer end);
    public List<Feedback> querytiaojian(String searchString, String searchField);
}
