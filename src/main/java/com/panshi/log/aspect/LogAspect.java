package com.panshi.log.aspect;

import com.panshi.log.Service.LogService;
import com.panshi.log.anno.YxueLog;
import com.panshi.log.entity.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
@Aspect
public class LogAspect {
    @Autowired
    private LogService logService;
    @Autowired
    private HttpSession session;

    @Around("@annotation(com.panshi.log.anno.YxueLog)")
    public Object aroundLog(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("=====执行日志记录操作====");
        /**
         * 获取操作的用户名
         */
        Object username = session.getAttribute("username");
        // 获取操作时间
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String format = sdf.format(currentDate);
        // 操作的表名和操作的业务类型，通过对方法上的注解属性值解析得到
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object user1=session.getAttribute("user");
        String user=user1.toString();
        System.out.println(user);
        // 获取方法对象
        Method method = methodSignature.getMethod();
        // 获取方法上的注解然后解析
        YxueLog anno = method.getAnnotation(YxueLog.class);
        String tableName = anno.tableName();
        String operationMethod = anno.value();
        System.out.println("tableName:"+tableName);
        System.out.println("operationMethod:"+operationMethod);
        System.out.println(currentDate);
        // 获取方法完整签名
        System.out.println(methodSignature.toString());

        System.out.println("=====执行日志记录操作====");

        // 执行原始方法,并传参数
        Object[] args = joinPoint.getArgs();// 获取传入的参数
        Object returnValue = joinPoint.proceed(args);
        System.out.println(returnValue);
        Log log=new Log();
        log.setId(null);
        log.setUsername(user);
        log.setOperationAt(currentDate);
        log.setTableName(tableName);
        log.setOperationMethod(operationMethod);
        log.setMethodName(methodSignature.toString());
        log.setDataId(args[0].toString());
        Jedis jedis=new Jedis("192.168.147.130",6379);
        String ph=jedis.get(args[0].toString());
        log.setDataInfo(ph);
        System.out.println(log);
        logService.insert(log);
        return returnValue;
    }
}

















