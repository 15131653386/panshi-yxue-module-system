package com.panshi.controller;

import com.google.gson.Gson;
import com.panshi.common.Result;
import com.panshi.config.EasyPoiUtil;
import com.panshi.entity.Feedback;
import com.panshi.entity.User;
import com.panshi.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping(value="feedback")
public class FeedbackController {
@Autowired
    private FeedbackService feedbackService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired(required = false)
    private HttpServletResponse response;
@ResponseBody
    @RequestMapping(value="queryall")
    public Result queryall(Integer page, Integer rows, String searchString, String searchField){
        List<Feedback> list=null;
        if(searchString==null){
            list=feedbackService.queryall(page,rows);
        }else{
            list=feedbackService.querytiaojian(searchString,searchField);
        }
        Integer records=feedbackService.count();
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
    @RequestMapping(value="edit")
    public Result<?> edit(Feedback feedback, String oper) {
        if ("add".equals(oper)) {
            feedback.setId(null);
            feedbackService.save(feedback);
        }
        if ("edit".equals(oper)){
            feedbackService.updateById(feedback);
        }
        if ("del".equals(oper)) {
            String sid = feedback.getId();
            String[] strId = sid.split(",");
            for (String s : strId) {
                Feedback f=feedbackService.getById(s);
                Gson gson=new Gson();
                String gs=gson.toJson(f);
                ValueOperations opsForValue= redisTemplate.opsForValue();
                opsForValue.set(s,gs,5, TimeUnit.MINUTES);
                feedbackService.removeById(s);
            }
        }
        return Result.ok(feedback);
    }
    @RequestMapping("exportUtil")
    @ResponseBody
    public void export() throws Exception {
        System.out.println("++++++++++++--------------------");
        List<Feedback> list=feedbackService.list();
        EasyPoiUtil.exportExcel(list,"反馈信息","反馈",Feedback.class,"fankui.xls",response);
        System.out.println("-------------------------------------");
    }
    @RequestMapping("import")
    @ResponseBody
    public void importUtil(MultipartFile multipartFile) throws Exception {
        System.out.println("====导入报表===");
//        InputStream inputStream = multipartFile.getInputStream();
        List<Feedback> emps = EasyPoiUtil.importExcel(multipartFile, 1, 1, Feedback.class);
        for (Feedback emp : emps) {
            emp.setId(null);
            feedbackService.save(emp);
        }

    }

}
