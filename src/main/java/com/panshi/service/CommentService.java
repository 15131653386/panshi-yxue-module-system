package com.panshi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.panshi.entity.BackComment;
import com.panshi.entity.Category;
import com.panshi.entity.Comment;
import com.panshi.entity.Feedback;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentService  extends IService<Comment> {
    public List<BackComment> queryall(Integer begin, Integer end);
    public List<BackComment> querytow(String interactId);
}
