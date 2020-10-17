package com.panshi.controller;

import com.panshi.entity.Admin;
import com.panshi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value="back")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpSession session;
    @RequestMapping(value = "loginpd")
    public String loginpd(Admin admin){
        System.out.println(admin.getUsername()+"++++++"+admin.getPassword());
        try{
            adminService.queryuser(admin.getUsername(),admin.getPassword());
            session.setAttribute("user",admin.getUsername());
            return "/back/main";
        }catch(Exception e){
            request.setAttribute("message",e.getMessage());
            System.out.println(e.getMessage());
            return "back/login";
        }
    }
}
