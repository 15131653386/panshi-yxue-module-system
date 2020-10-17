package com.panshi.controller;

import com.google.gson.Gson;
import com.panshi.common.Result;
import com.panshi.config.QiniuYunUtil;
//import com.panshi.config.TestFirst;
import com.panshi.config.TestFirst;
import com.panshi.dao.VideoDao;
import com.panshi.entity.*;
import com.panshi.repository.VideoRepository;
import com.panshi.service.CategoryService;
import com.panshi.service.LikeService;
import com.panshi.service.PlayService;
import com.panshi.service.VideoService;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping(value="quera")
public class VideoController {
    @Autowired
    private VideoService videoService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private PlayService playService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    VideoRepository videoRepository;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;
    @ResponseBody
    @RequestMapping(value="queryall")
    public Result aaa(Integer page, Integer rows,String searchField){
        List<BackVideo> list=null;
        if(searchField==null||"".equals(searchField)){
            list=videoService.queryblj();
            List<Like> like=likeService.list();
            for (BackVideo backVideo : list) {
                Integer l=0;
                for (Like like1 : like) {
                    if(backVideo.getId().equals(like1.getSourceId())){l++;}
                }
                backVideo.setLike(l);
            }
        }else {
            list=videoService.querytiaojian(searchField);
            List<Like> like=likeService.list();
            for (BackVideo backVideo : list) {
                Integer l=0;
                for (Like like1 : like) {
                    if(backVideo.getId().equals(like1.getSourceId())){l++;}
                }
                backVideo.setLike(l);
                System.out.println(backVideo);
            }
        }
        Integer records=videoService.count();
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
    public Result<?> edit(BackVideo backVideo, String oper) {
        if ("add".equals(oper)) {
            Video video=new Video(null,backVideo.getTitle(),backVideo.getIntro(),backVideo.getCoverUrl(), backVideo.getVideoUrl(),backVideo.getCreateAt(),
                    backVideo.getUser().getUsername(), backVideo.getCategory().getName(),backVideo.getGroup().getName());
            videoService.save(video);
            Play play=new Play();
            play.setId(null);
            play.setVideoId(video.getId());
            play.setPlayNum(0);
            playService.save(play);
        }
        if ("edit".equals(oper)){

        }
        if ("del".equals(oper)) {
            String sid = backVideo.getId();
            Video vi=videoService.getById(sid);
            Gson gson=new Gson();
            String gs=gson.toJson(vi);
            ValueOperations opsForValue= redisTemplate.opsForValue();
            opsForValue.set(sid,gs,5, TimeUnit.MINUTES);
                videoService.removeById(sid);
                playService.deleteplay(sid);
                videoRepository.deleteById(sid);
        }
        return Result.ok(backVideo);
    }
    @ResponseBody
    @RequestMapping(value="filevideo")
    public void headupload(String id, MultipartFile videoUrl, HttpSession session) throws Exception{
                String original = videoUrl.getOriginalFilename();
                String realpath = session.getServletContext().getRealPath("/unload/videos");
                videoUrl.transferTo(new File(realpath + "/" + original));
                Video vide=new Video();
                List<Video> video =videoService.list();
                String ii=null;
                for (Video video1 : video) {
                    String[] is=video1.getVideoUrl().split("\\\\");
                    String a=is[is.length-1];
                    if(a.equals(original)){
                        ii=video1.getId();
                        vide.setId(ii);
                    }
                }
                vide.setVideoUrl(original);
                System.out.println(vide);
                videoService.updateById(vide);
              QiniuYunUtil qi=new QiniuYunUtil();
               String s= qi.upload(original);
                vide.setVideoUrl("http://qhaxrdmmj.hn-bkt.clouddn.com/"+s);
                TestFirst testFirst=new TestFirst();
                String s5= UUID.randomUUID().toString();
        try{
            testFirst.fetchFrame("D:\\200\\panshi-yxue-module-system\\src\\main\\webapp\\unload\\videos\\"+original,"D:\\200\\panshi-yxue-module-system\\src\\main\\webapp\\unload\\"+s5+".jpg");
        }catch(Exception e){}
                vide.setCoverUrl(s5+".jpg");
                videoService.updateById(vide);
                Video vv=videoService.getById(ii);
                videoRepository.save(vv);
    }
    @ResponseBody
    @RequestMapping("querySearchVideo")
    public List<Video> querySearchVideo(String content){
        System.out.println("******************");
        List<Video> videos = videoService.querySearchVideos(content);
        return videos;
    }
}
