package com.panshi.controller;

import com.google.gson.Gson;
import com.panshi.common.Result;
import com.panshi.entity.*;
import com.panshi.log.Service.LogService;
import com.panshi.log.entity.Log;
import com.panshi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping(value="log")
public class LogController {
    @Autowired
    private LogService logService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private UserService userService;
    @Autowired
    private VideoService videoService;
    @ResponseBody
    @RequestMapping(value="queryall")
    public Result queryall(Integer page, Integer rows, String searchString, String searchField){
        List<Log> list=null;
            list=logService.list();
        Integer records=logService.count();
        Integer total=null;
        if(records%rows==0){
            total=records/rows;
        }else{
            total=records/rows+1;
        }
        Result result= new Result();
        result.setRows(list);
        result.setTotal(total);
        result.setPage(page);
        result.setRecords(records);
        return result;
    }
    @ResponseBody
    @RequestMapping(value="huifu")
    public Result<?> huifu(String id){
        Log log=logService.getById(id);
        System.out.println(log);
        String s=log.getDataInfo().replace("\\","");
        String s1=s.substring(1,s.length()-1);
        System.out.println(s1);
        Gson gson=new Gson();
        if("yx_user".equals(log.getTableName())){
            User user=gson.fromJson(s1,User.class);
            System.out.println(user);
            userService.save(user);
            logService.removeById(id);
        }else if("yx_category".equals(log.getTableName())){
            Category category=gson.fromJson(s1,Category.class);
            System.out.println(category);
            categoryService.save(category);
            logService.removeById(id);
        }else if("yx_video".equals(log.getTableName())){
            Video video=gson.fromJson(s1,Video.class);
            System.out.println(video);
            videoService.save(video);
            logService.removeById(id);
        }else if("yx_comment".equals(log.getTableName())){
            Comment comment=gson.fromJson(s1,Comment.class);
            System.out.println(comment);
            commentService.save(comment);
            logService.removeById(id);
        }else if("yx_feedback".equals(log.getTableName())){
            Feedback feedback=gson.fromJson(s1,Feedback.class);
            System.out.println(feedback);
            feedbackService.save(feedback);
            logService.removeById(id);
        }

        return null;
    }

}
