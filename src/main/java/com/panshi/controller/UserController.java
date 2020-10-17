package com.panshi.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.panshi.common.Result;
import com.panshi.config.EasyPoiUtil;
import com.panshi.entity.Feedback;
import com.panshi.entity.User;
import com.panshi.log.anno.AddCache;
import com.panshi.log.anno.DelCache;
import com.panshi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping(value="user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired(required = false)
    private HttpServletResponse response;
    @AddCache
    @ResponseBody
    @RequestMapping(value="queryall")
    public Result queryall(Integer page, Integer rows,String searchString,String searchField){
        List<User> list=null;
            if(searchString==null){
                list=userService.queryall(page,rows);
            }else{
            list=userService.querytiaojian(searchString,searchField);
        }
        Integer records=userService.count();
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
    @DelCache
    @ResponseBody
    @RequestMapping(value="edit")
    public Result<?> edit(User user, String oper) {
        if ("add".equals(oper)) {
           user.setId(null);
            userService.save(user);
        }
        if ("edit".equals(oper)){
            if(user.getHeadShow()==null||"".equals(user.getHeadShow())){
                User us=userService.getById(user.getId());
                System.out.println(us);
                user.setHeadShow(us.getHeadShow());
            }
            userService.updateById(user);
        }
        if ("del".equals(oper)) {
            String sid = user.getId();
            User us=userService.getById(sid);
            Gson gson=new Gson();
            String s=gson.toJson(us);
            ValueOperations opsForValue= redisTemplate.opsForValue();
            opsForValue.set(sid,s,5, TimeUnit.MINUTES);
            userService.removeById(sid);
        }

        return Result.ok(user);
    }
    @DelCache
    @ResponseBody
    @RequestMapping(value="headUp")
    public String headup(String id, MultipartFile headShow,HttpSession session) throws IOException{
            String original=headShow.getOriginalFilename();
            String realpath=session.getServletContext().getRealPath("/unload");
            headShow.transferTo(new File(realpath+"/"+original));
            User user = new User();
            user.setId(id);
            user.setHeadShow(original);
            userService.updateById(user);
        return "ok";
    }
    @DelCache
    @ResponseBody
    @RequestMapping(value="headUpload")
    public String headupload(String id, MultipartFile headShow,HttpSession session){
        if(headShow.getOriginalFilename().equals("")||headShow.getOriginalFilename()==null){

        }else{
            try{
                String original = headShow.getOriginalFilename();
                String realpath = session.getServletContext().getRealPath("/unload");
                headShow.transferTo(new File(realpath + "/" + original));
                User user = new User();
                user.setId(id);
                user.setHeadShow(original);
                userService.updateById(user);
            }catch(Exception e){

            }
        }

        return "ok";
    }
    @ResponseBody
    @RequestMapping("/user-list")
    public Result select(String condition){
        System.out.println("11111111111122222222222333333333");
        User list=userService.queryname(condition);
        Result result= new Result();
        result.setRows(list);
        return result;
    }
    @RequestMapping("exportUtil")
    @ResponseBody
    public void export() throws Exception {
        System.out.println("++++++++++++--------------------");
        List<User> list=userService.list();
        for (User user : list) {
            user.setHeadShow("D:\\200\\panshi-yxue-module-system\\src\\main\\webapp\\unload\\"+user.getHeadShow());
        }
        EasyPoiUtil.exportExcel(list,"反馈信息","反馈",User.class,"用户.xls",response);
        System.out.println("-------------------------------------");
    }

    @ResponseBody
    @RequestMapping("queryUser")
    public HashMap<String, Object> queryUser(){

        /*http  应用层协议    短链接
        tcp/ip   网络层协议   长连接*/
        System.out.println("++++++++---------");
        HashMap<String, Object> map = new HashMap<>();
        map.put("month", Arrays.asList("1月","2月","3月","4月","5月","6月"));
        map.put("boys", Arrays.asList(56,56,34,23,23,89));
        map.put("girls", Arrays.asList(100,377,588,334,888,222));
        return map;
    }

}
