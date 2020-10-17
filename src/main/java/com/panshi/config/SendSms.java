package com.panshi.config;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

public class SendSms {
    public String sendPhoneCode(String phone,String code) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                "LTAI4G9QG1wajwZZGoEBWZ11", "r8GobNIludt8tbqB9pTmMBFuPmTt0B");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        //设置签名
        request.putQueryParameter("SignName", "坚如磐石app");
        //设置模板
        request.putQueryParameter("TemplateCode","SMS_203670727");
        request.putQueryParameter("TemplateParam","{\"code\":\""+code+"\"}");
        String responseData = null;
         try {
            CommonResponse response = client.getCommonResponse(request);
            //获取响应结果状态
             responseData = response.getData();
             System.out.println(response.getData());
        } catch (ServerException e) {
             e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
         return responseData;
    }
}
