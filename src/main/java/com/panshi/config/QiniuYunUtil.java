package com.panshi.config;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class QiniuYunUtil {
    public String upload(String videoUrl) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region2());
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "wZKtqV84rdOJUcmAqwbT9UIAEHe6tEsuOSp-F52P";
        String secretKey = "Oah-bbDwR5_JejXQ5I2zNwAdsDUOTAAt1VmX8i5M";
        String bucket = "jrps";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "D:\\200\\panshi-yxue-module-system\\src\\main\\webapp\\unload\\videos\\"+videoUrl;
        System.out.println(localFilePath);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;

        Auth auth = Auth.create(accessKey, secretKey);
        StringMap putPolicy = new StringMap();
       // putPolicy.put("returnBody", "{\"hash\":\"Ftgm-CkWePC9fzMBTRNmPMhGBcSV\",\"key\":\"qiniu.jpg\"}");
        long expireSeconds = 3600;
        String upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);
        System.out.println(upToken);
//        String upToken = auth.uploadToken(bucket);
        DefaultPutRet putRet =null;
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            System.out.println("+++++");
            //http://qh5yvg3ne.hn-bkt.clouddn.com/FsfolBFS8UsHn5mZdksamXNcd0bS
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            System.out.println("----------");
            try {
                System.err.println(r.bodyString());
                System.out.println("-------------------------");
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return putRet.key;
    }
}
