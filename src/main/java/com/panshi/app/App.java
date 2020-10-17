package com.panshi.app;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.panshi.config.QiniuYunUtil;
import com.panshi.config.SendSms;
import com.panshi.config.TestFirst;
import com.panshi.dto.*;
import com.panshi.entity.*;
import com.panshi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping(value="app")
public class App {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private VideoService videoService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private PlayService playService;
    @Autowired(required = false)
    private AttentionService attentionService;
    @Autowired
    private HttpSession session;
    @ResponseBody
    @PostMapping(value="getPhoneCode")
    public Phonefh phonefs(String phone){
        if(phone==null||"".equals(phone)){
            Phonefh ph=new Phonefh(null,"验证码发送失败","104");
            return ph;
        }else{
            SendSms sms=new SendSms();
            System.out.println(phone);
            Random random = new Random();
            Integer s=random.nextInt(8999)+1000;
            String s2=s.toString();
            //String s1=sms.sendPhoneCode(phone,s2);
            ValueOperations opsForValue= redisTemplate.opsForValue();
            opsForValue.set(phone,s2,5, TimeUnit.MINUTES);
            System.out.println(phone+"11111111122222222223333333333333");
            Phonefh ph=new Phonefh(phone,"验证码发送成功","100");
            return ph;
        }
}
    @ResponseBody
    @PostMapping(value="login")
    public Map<String, Object> userdd(String phone, String phoneCode){
        Jedis jedis=new Jedis("192.168.147.128",6379);
        String ph=jedis.get(phone);
        String th=ph.substring(1,ph.length()-1);
        System.out.println(th);
        Map<String, Object> map = new HashMap<>();
        if (!th.equals(phoneCode) || "".equals(th)) {
            map.put("status", 101);
            map.put("message", "错误信息");
        }else{
            map.put("status", 100);
            map.put("message", "登录成功");
            AppUser user=new AppUser("1",phone,"小可爱");
            map.put("user",user);
            session.setAttribute("phone",phone);
        }
        return map;
    }
    @ResponseBody
    @GetMapping("queryByReleaseTime")
    public Map<String, Object> queryByReleaseTime() {
        System.out.println("+++++++++++++++++++");
        List<Video> list = videoService.list();

        //自己封装结果
        List<MainDTO> mainDTOS = new ArrayList<>();
        List<Like> like=likeService.list();
        for (Video video : list) {
            Integer l=0;
            for (Like like1 : like) {
                if(video.getId().equals(like1.getSourceId())){l++;}
            }
            Category categor=categoryService.getById(video.getCid());
            User user=userService.getById(video.getUserId());
            System.out.println(user);
            String s="http://localhost:8081/yingx/unload/"+user.getHeadShow();
            String a="http://localhost:8081/yingx/unload/"+video.getCoverUrl();
            MainDTO mainDTO = new MainDTO(
                    video.getId(),
                    video.getTitle(),
                    a,
                    video.getVideoUrl(),
                    video.getCreateAt(),
                    video.getIntro(),
                    l,
                    categor.getName(),
                    s
            );
            mainDTOS.add(mainDTO);
        }
        return result(mainDTOS);
    }
    public Map<String, Object> result(Object obj) {
        Map<String, Object> result = new HashMap<>();
        result.put("data", obj);
        result.put("message", "操作成功！~");
        result.put("status", 100);
        return result;
    }
    @ResponseBody
    @GetMapping("queryByLikeVideoName")
    public Map<String, Object> queryByLikeVideoName(String content) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Video> queryWrapper=new QueryWrapper<>();
        QueryWrapper<Video> title =queryWrapper.like("title",content);
        List<Video> list=videoService.list(queryWrapper);
        if(list.isEmpty()){
            map.put("data",list);
            map.put("message","查询失败");
            map.put("status","104");
        }else{
            List<SelectVideo> lis=new ArrayList<>();
            List<Like> like=likeService.list();
            for (Video video : list) {
                //try{
                    Integer l=0;
                    for (Like like1 : like) {
                        if(video.getId().equals(like1.getSourceId())){l++;}
                        System.out.println(like1);
                    }
                    Category categor=categoryService.getById(video.getCid());
                    User user=userService.getById(video.getUserId());
                    SelectVideo sv=new SelectVideo();
                String a="http://localhost:8081/yingx/unload/"+video.getCoverUrl();
                    sv.setId(video.getId());
                    sv.setVideoTitle(video.getTitle());
                    sv.setCover(a);
                    sv.setPath(video.getVideoUrl());
                    sv.setUploadTime(video.getCreateAt().toString());
                    sv.setDescription(video.getIntro());
                    sv.setLikeCount(l);
                    sv.setCateName(categor.getName());
                    sv.setCategoryId(video.getCid());
                    sv.setUserId(video.getUserId());
                    sv.setUserName(user.getUsername());
                    lis.add(sv);
                map.put("data",lis);
                map.put("message","查询成功");
                map.put("status","100");
                //}catch(Exception e){throw e;}
            }
        }
        return map;
    }
    @ResponseBody
    @GetMapping("queryByVideoDetail")
    public Map<String, Object> queryByVideoDetail(String videoId,String cateId,String userId){
        Map<String, Object> map = new HashMap<>();
            SelectVideoid selectVideoid = new SelectVideoid();
            Video video = videoService.getById(videoId);
            Category categor = categoryService.getById(video.getCid());
            User user = userService.getById(video.getUserId());
            List<Like> like = likeService.list();
            Integer l = 0;
            for (Like like1 : like) {
                if (video.getId().equals(like1.getSourceId())) {
                    l++;
                }
                System.out.println(like1);
            }
        QueryWrapper<Play> queryWrappera = new QueryWrapper<>();
        QueryWrapper<Play> titlew = queryWrappera.eq("video_id", video.getId());
        List<Play> attentiona = playService.list(queryWrappera);
            Play play = attentiona.get(0);
            QueryWrapper<Attention> queryWrapper = new QueryWrapper<>();
            QueryWrapper<Attention> title = queryWrapper.eq("to_user_id", userId);
            List<Attention> attention = attentionService.list(queryWrapper);
            Boolean boo = false;
            for (Attention attention1 : attention) {
                if (attention1.getFromUserId().equals(video.getUserId())) {
                    boo = true;
                }
            }
            QueryWrapper<Video> queryWrapperq = new QueryWrapper<>();
            QueryWrapper<Video> titleq = queryWrapperq.eq("cid", video.getCid());
            List<Video> list = videoService.list(queryWrapperq);

            List<SelectVideo> lis = new ArrayList<>();
            List<Like> likeq = likeService.list();
            for (Video videop : list) {
                Integer b = 0;
                for (Like like1 : likeq) {
                    if (videop.getId().equals(like1.getSourceId())) {
                        l++;
                    }
                    System.out.println(like1);
                }
                Category catego = categoryService.getById(videop.getCid());
                User userf = userService.getById(videop.getUserId());
                SelectVideo sv = new SelectVideo();
                String a = "http://localhost:8081/yingx/unload/" + videop.getCoverUrl();
                sv.setId(videop.getId());
                sv.setVideoTitle(videop.getTitle());
                sv.setCover(a);
                sv.setPath(videop.getVideoUrl());
                sv.setUploadTime(videop.getCreateAt().toString());
                sv.setDescription(videop.getIntro());
                sv.setLikeCount(l);
                sv.setCateName(catego.getName());
                sv.setCategoryId(videop.getCid());
                sv.setUserId(videop.getUserId());
                sv.setUserName(userf.getUsername());
                lis.add(sv);
            }

            selectVideoid.setId(video.getId());
            selectVideoid.setVideoTitle(video.getTitle());
            selectVideoid.setCover("http://localhost:8081/yingx/unload/" + video.getCoverUrl());
            selectVideoid.setPath(video.getVideoUrl());
            selectVideoid.setUploadTime(video.getCreateAt().toString());
            selectVideoid.setDescription(video.getIntro());
            selectVideoid.setLikeCount(l);
            selectVideoid.setCateName(categor.getName());
            selectVideoid.setCategoryId(video.getCid());
            selectVideoid.setUserId(video.getUserId());
            selectVideoid.setUserName(user.getUsername());
            selectVideoid.setUserPicImg("http://localhost:8081/yingx/unload/"+user.getHeadShow());
            selectVideoid.setPlayCount(play.getPlayNum());
            selectVideoid.setIsAttention(boo);
            selectVideoid.setVideoList(lis);
            map.put("data", selectVideoid);
            map.put("message", "查询成功");
            map.put("status", "100");
        return map;
    }
    @ResponseBody
    @GetMapping("save")
    public Map<String, Object> save(String description, MultipartFile videoFile, String videoTitle,String categoryId,String userId,HttpSession session) throws Exception{
        Map<String, Object> map = new HashMap<>();
        try {
            String original = videoFile.getOriginalFilename();
            System.out.println(original);
            String realpath = session.getServletContext().getRealPath("/unload/videos");
            videoFile.transferTo(new File(realpath + "/" + original));
            QiniuYunUtil qi = new QiniuYunUtil();
            String s = qi.upload(original);
            TestFirst testFirst = new TestFirst();
            String s5 = UUID.randomUUID().toString();
            try {
                testFirst.fetchFrame("D:\\200\\panshi-yxue-module-system\\src\\main\\webapp\\unload\\videos\\" + original, "D:\\200\\panshi-yxue-module-system\\src\\main\\webapp\\unload\\" + s5 + ".jpg");
            } catch (Exception e) {
            }
            Video video = new Video();
            String fd = UUID.randomUUID().toString();
            String sd=fd.replace("-","");
            video.setId(sd);
            System.out.println(sd);
            video.setTitle(videoTitle);
            video.setIntro(description);
            video.setCoverUrl(s5 + ".jpg");
            video.setVideoUrl("http://qhaxrdmmj.hn-bkt.clouddn.com/" + s);
            video.setCreateAt(new Date());
            video.setUserId(userId);
            video.setCid(categoryId);
            videoService.save(video);
            Play pl=new Play();
            pl.setVideoId(sd);
            pl.setPlayNum(0);
            playService.save(pl);
            map.put("data", null);
            map.put("message", "添加成功");
            map.put("status", "100");
        }catch(Exception e){
            map.put("data", null);
            map.put("message", "添加失败");
            map.put("status", "104");
            throw new RuntimeException(e);
        }
        return map;
    }
    @ResponseBody
    @GetMapping("queryAllCategory")
    public Map<String, Object> queryAllCategory(){
        Map<String, Object> map = new HashMap<>();
        try {
            QueryWrapper<Category> queryWrapperqw = new QueryWrapper<>();
            queryWrapperqw.eq("level", "1");
            List<Category> categorytdto = categoryService.list(queryWrapperqw);

            List<Categorytdto> cated=new ArrayList<>();
            for (Category category : categorytdto) {
                Categorytdto sss=new Categorytdto();
                sss.setId(category.getId());
                sss.setCateName(category.getName());
                sss.setLevels(category.getLevel());
                sss.setParentId(category.getPId());
                QueryWrapper<Category> queryWrapperqwq = new QueryWrapper<>();
                queryWrapperqwq.eq("p_id", category.getId());
                List<Category> list = categoryService.list(queryWrapperqwq);
                List<Categorytdto> cat=new ArrayList<>();
                for (Category category1 : list) {
                    Categorytdto ccc=new Categorytdto();
                    ccc.setId(category1.getId());
                    ccc.setCateName(category1.getName());
                    ccc.setLevels(category1.getLevel());
                    ccc.setParentId(category1.getPId());
                    ccc.setCategoryList(null);
                    cat.add(ccc);
                }
                sss.setCategoryList(cat);
                cated.add(sss);
            }
            map.put("data",cated);
            map.put("message", "查询成功");
            map.put("status", "100");
        }catch(Exception e){
            map.put("data", null);
            map.put("message", "添加失败");
            map.put("status", "104");
            throw new RuntimeException(e);
        }
        return map;
    }
    @ResponseBody
    @GetMapping("queryCateVideoList")
    public Map<String, Object> queryCateVideoList(String cateId) {
        Map<String, Object> map = new HashMap<>();

        QueryWrapper<Video> queryWrapper=new QueryWrapper<>();
        QueryWrapper<Video> title =queryWrapper.eq("cid",cateId);
        List<Video> list=videoService.list(queryWrapper);
        if(list.isEmpty()){
            map.put("data",list);
            map.put("message","查询失败");
            map.put("status","104");
        }else{
            List<SelectVideo> lis=new ArrayList<>();
            List<Like> like=likeService.list();
            for (Video video : list) {
                //try{
                Integer l=0;
                for (Like like1 : like) {
                    if(video.getId().equals(like1.getSourceId())){l++;}
                    System.out.println(like1);
                }
                Category categor=categoryService.getById(video.getCid());
                User user=userService.getById(video.getUserId());
                SelectVideo sv=new SelectVideo();
                String a="http://localhost:8081/yingx/unload/"+video.getCoverUrl();
                sv.setId(video.getId());
                sv.setVideoTitle(video.getTitle());
                sv.setCover(a);
                sv.setPath(video.getVideoUrl());
                sv.setUploadTime(video.getCreateAt().toString());
                sv.setDescription(video.getIntro());
                sv.setLikeCount(l);
                sv.setCateName(categor.getName());
                sv.setCategoryId(video.getCid());
                sv.setUserId(video.getUserId());
                sv.setUserName(user.getUsername());
                lis.add(sv);
                map.put("data",lis);
                map.put("message","查询成功");
                map.put("status","100");
                //}catch(Exception e){throw e;}
            }
        }
        return map;
    }
}
