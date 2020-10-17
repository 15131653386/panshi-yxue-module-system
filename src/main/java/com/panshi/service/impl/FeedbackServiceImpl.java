package com.panshi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panshi.dao.CategoryDao;
import com.panshi.dao.FeedbackDao;
import com.panshi.entity.Category;
import com.panshi.entity.Feedback;
import com.panshi.entity.User;
import com.panshi.log.anno.YxueLog;
import com.panshi.service.CategoryService;
import com.panshi.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackDao, Feedback> implements FeedbackService {
    @Autowired
    private FeedbackDao feedbackDao;
    @Override
    public List<Feedback> queryall(Integer page1, Integer end) {
        IPage<Feedback> page=new Page<>(page1,end);
        IPage<Feedback> pages=feedbackDao.selectPage(page,null);
        return pages.getRecords();
    }
    @Override
    public List<Feedback> querytiaojian(String searchString, String searchField) {
        QueryWrapper<Feedback> queryWapper=new QueryWrapper<>();
        queryWapper.eq(searchField,searchString);
        Feedback feedback=feedbackDao.selectOne(queryWapper);
        List<Feedback> list=new ArrayList<>();
        list.add(feedback);
        return list;
    }

    @YxueLog(value="删除了一条反馈信息",tableName = "yx_feedback")
    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }
}
