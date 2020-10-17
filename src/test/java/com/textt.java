package com;

import com.panshi.PanshiYxueModuleSystemApplication;
import com.panshi.config.SendSms;
import com.panshi.dao.UserDao;
import com.panshi.entity.User;
import com.panshi.entity.Video;
import com.panshi.entity.queryUser;
import com.panshi.repository.VideoRepository;
import com.panshi.service.UserService;
import com.panshi.service.VideoService;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=PanshiYxueModuleSystemApplication.class)
public class textt {
    @Autowired
    private UserService userService;
    @Autowired
    private VideoService videoService;
    @Autowired
    VideoRepository videoRepository;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;
        @Test
    public void save(){
        List<Video> list=videoService.list();
        for (Video video : list) {
            videoRepository.save(video);
        }

    }
    @Test
    public void savv(){

    }
    @Test
    public void queryVideo(){

        String content="丘";

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("yingx") //指定索引
                .withTypes("video") //自定类型
                .withQuery(QueryBuilders.queryStringQuery(content).field("title").field("intro")) //设置查询条件
                .build();

        List<Video> videoList = elasticsearchTemplate.queryForList(searchQuery, Video.class);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++");
        for (Video video : videoList) {
            System.out.println(video);
        }
    }

}
