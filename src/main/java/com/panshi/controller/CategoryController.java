package com.panshi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.google.gson.Gson;
import com.panshi.common.Result;
import com.panshi.config.EasyPoiUtil;
import com.panshi.entity.Category;
import com.panshi.entity.User;
import com.panshi.log.anno.AddCache;
import com.panshi.log.anno.DelCache;
import com.panshi.service.CategoryService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Controller
@RequestMapping(value="category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired(required = false)
    private HttpServletResponse response;
    @AddCache
    @ResponseBody
    @RequestMapping(value="queryall")
    public Result queryall(Integer page, Integer rows){
        List<Category> list=categoryService.queryall(page,rows);
        Integer records=categoryService.count();
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
    @AddCache
    @ResponseBody
    @RequestMapping(value="querytwo")
    public Result querytwo(String qId){
        List<Category> list=categoryService.querytow(qId);
        Result result= new Result();
        result.setRows(list);
        return result;
    }
    @DelCache
    @ResponseBody
    @RequestMapping(value="edit")
    public Result<?> edit(Category category, String oper) {
        if ("add".equals(oper)) {
            category.setId(null);
            category.setLevel("1");
            categoryService.save(category);
        }
        if ("edit".equals(oper)){
            categoryService.updateById(category);
        }
        if ("del".equals(oper)) {
            String sid = category.getId();
                List<Category> list=categoryService.querytow(sid);
            System.out.println("+++++++++++++++++++++++++++");
            Category ca=categoryService.getById(sid);
            Gson gson=new Gson();
            String gs=gson.toJson(ca);
            ValueOperations opsForValue= redisTemplate.opsForValue();
            opsForValue.set(sid,gs,5, TimeUnit.MINUTES);
                if(list==null){
                    categoryService.removeById(sid);
                }else{
                System.out.println("他有子类，不可以删除");
            }

        }

        return Result.ok(category);
    }
    @DelCache
    @ResponseBody
    @RequestMapping(value="edittwo")
    public Result<?> edittwo(Category category, String oper,String qId) {
        if ("add".equals(oper)) {
            category.setId(null);
            category.setLevel("2");
            category.setPId(qId);
            categoryService.save(category);
        }
        if ("edit".equals(oper)) {
            categoryService.updateById(category);
        }
        if ("del".equals(oper)) {
            String sid = category.getId();
            Category ca=categoryService.getById(sid);
            Gson gson=new Gson();
            String gs=gson.toJson(ca);
            ValueOperations opsForValue= redisTemplate.opsForValue();
            opsForValue.set(sid,gs,5, TimeUnit.MINUTES);
            categoryService.removeById(sid);

        }
        return Result.ok(category);
    }
    @RequestMapping("categoryUtil")
    @ResponseBody
    public void export() throws Exception {
        System.out.println("++++++++++++--------------------");
        List<Category> list=categoryService.list();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("一级类别", "二级类别", "类别信息"),
                Category.class, list);
        workbook.write(new FileOutputStream(new File("student.xls")));
        workbook.close();
    }
}
