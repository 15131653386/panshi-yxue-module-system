package com;

import com.panshi.PanshiYxueModuleSystemApplication;
import com.panshi.entity.Video;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=PanshiYxueModuleSystemApplication.class)
public class texttsss {
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
