package com.panshi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panshi.dao.AttentionDao;
import com.panshi.dao.CategoryDao;
import com.panshi.entity.Attention;
import com.panshi.entity.Category;
import com.panshi.service.AttentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttentionServiceImpl extends ServiceImpl<AttentionDao, Attention> implements AttentionService {
}
