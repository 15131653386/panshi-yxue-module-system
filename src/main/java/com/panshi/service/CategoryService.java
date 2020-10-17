package com.panshi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.panshi.entity.Category;
import com.panshi.dto.Categorytdto;

import java.util.List;

public interface CategoryService extends IService<Category> {
    public List<Category> queryall(Integer page1, Integer end);
    public List<Category> querytow(String qId);
}
