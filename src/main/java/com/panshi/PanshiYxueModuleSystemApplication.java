package com.panshi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan({"com.panshi.dao","com.panshi.log.dao"})
@ServletComponentScan
public class PanshiYxueModuleSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(PanshiYxueModuleSystemApplication.class, args);
    }

}
