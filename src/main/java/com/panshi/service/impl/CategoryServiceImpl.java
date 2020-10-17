package com.panshi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panshi.dao.CategoryDao;
import com.panshi.entity.Category;
import com.panshi.dto.Categorytdto;
import com.panshi.log.anno.YxueLog;
import com.panshi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {

    @Autowired(required = false)
    private CategoryDao categoryDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Category> queryall(Integer page1, Integer end) {
        QueryWrapper<Category> queryWapper=new QueryWrapper<>();
       queryWapper.eq("level","1");
        IPage<Category> page=new Page<>(page1,end);
        IPage<Category> pages=categoryDao.selectPage(page,queryWapper);
        return pages.getRecords();
    }

    @Override
    public List<Category> querytow(String qId) {
        QueryWrapper<Category> queryWapper=new QueryWrapper<>();
        queryWapper.eq("p_id",qId);
        List<Category> list=categoryDao.selectList(queryWapper);
        return list;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    public int count(){
        QueryWrapper<Category> queryWapper=new QueryWrapper<>();
        queryWapper.eq("level","1");
        List<Category> list=categoryDao.selectList(queryWapper);
        return  list.size();
    }
    @YxueLog(value="删除了一条类别信息",tableName = "yx_category")
    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }
}
