package com.panshi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.google.gson.Gson;
import com.panshi.common.Result;
import com.panshi.entity.*;
import com.panshi.service.CommentService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping(value="comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private RedisTemplate redisTemplate;
    @ResponseBody
    @RequestMapping(value="queryall")
    public Result queryall(Integer page, Integer rows, String searchString, String searchField){
        List<BackComment> list=null;
            list=commentService.queryall(page,rows);
        Integer records=commentService.count();
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
    @RequestMapping(value="querytwo")
    public Result querytwo(String qId){
        System.out.println(qId);
        List<BackComment> list=commentService.querytow(qId);
        Result result= new Result();
        result.setRows(list);
        return result;
    }
    @ResponseBody
    @RequestMapping(value="edit")
    public Result<?> edit(BackComment backComment, String oper) {
        if ("add".equals(oper)) {
            Comment comment=new Comment();
            comment.setId(null);
            comment.setContent(backComment.getContent());
            commentService.save(comment);
        }
        if ("edit".equals(oper)){
            Comment comment=new Comment();
            comment.setId(backComment.getId());
            comment.setContent(backComment.getContent());
            comment.setCreateAt(backComment.getCreateAt());
            commentService.updateById(comment);
        }
        if ("del".equals(oper)) {
            String sid = backComment.getId();
            String[] strId = sid.split(",");
            for (String s : strId) {
                Comment c=commentService.getById(s);
                Gson gson=new Gson();
                String gs=gson.toJson(c);
                ValueOperations opsForValue= redisTemplate.opsForValue();
                opsForValue.set(s,gs,5, TimeUnit.MINUTES);
                commentService.removeById(s);
            }
        }

        return Result.ok(backComment);
    }
    @ResponseBody
    @RequestMapping(value="edittwo")
    public Result<?> edittwo(Comment comment, String oper,String qId) {
        if ("add".equals(oper)) {
            comment.setId(null);
            comment.setInteractId(qId);
            commentService.save(comment);
        }
        if ("edit".equals(oper)) {
            commentService.updateById(comment);
        }
        if ("del".equals(oper)) {
            String sid = comment.getId();
            Comment c=commentService.getById(sid);
            Gson gson=new Gson();
            String gs=gson.toJson(c);
            ValueOperations opsForValue= redisTemplate.opsForValue();
            opsForValue.set(sid,gs,5, TimeUnit.MINUTES);
            commentService.removeById(sid);
        }
        return Result.ok(comment);
    }
    @RequestMapping("commentUtil")
    @ResponseBody
    public void export() throws Exception {
        System.out.println("++++++++++++--------------------");
        List<Comment> list=commentService.list();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("评论信息", "评论回复", "评论信息"),
                Comment.class, list);
        workbook.write(new FileOutputStream(new File("student.xls")));
        workbook.close();
    }
}
