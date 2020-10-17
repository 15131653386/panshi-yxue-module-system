package com.panshi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panshi.dao.CategoryDao;
import com.panshi.dao.CommentDao;
import com.panshi.entity.BackComment;
import com.panshi.entity.Category;
import com.panshi.entity.Comment;
import com.panshi.entity.Feedback;
import com.panshi.log.anno.YxueLog;
import com.panshi.service.CategoryService;
import com.panshi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CommentServiceimpl extends ServiceImpl<CommentDao, Comment> implements CommentService {
@Autowired
private CommentDao commentDao;
    @Override
    public List<BackComment> queryall(Integer begin, Integer end) {
        Integer b = begin - 1;
        Integer e = end - 1;
        return commentDao.queryall(b, e);
    }
    @Override
    public List<BackComment> querytow(String interactId) {
        return commentDao.querytow(interactId);
    }
    @YxueLog(value="删除了一条评论",tableName = "yx_comment")
    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

}
