package com.baizhi.controller;

import com.baizhi.entity.Admin;

import com.baizhi.service.AdminService;
import com.baizhi.util.ImageCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;


import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

@Controller
@RequestMapping("/admin")
@SessionAttributes({"admin"})
public class AdminController {
    @Autowired
    private AdminService adminService;


    @ResponseBody()
    @RequestMapping("/yacode")
    public String getYzCode(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception{
        //获取验证码
        String code = ImageCodeUtil.getSecurityCode();
        //存储验证码
        session.setAttribute("code",code);
        //生成验证码图片
        BufferedImage image = ImageCodeUtil.createImage(code);
        //设置相应格式：
        response.setContentType("image/png");
        ServletOutputStream stream = null;
        try{
            stream = response.getOutputStream();
            ImageIO.write(image,"png",stream);
        }catch (Exception e){
        }
        return null;
    }

    @RequestMapping("/login")
    @ResponseBody()
   public String login(Admin admin, String code, HttpSession session,HttpServletRequest request) throws Exception{
        String message = null;
        try{
            String scode = (String)session.getAttribute("code");
            System.out.println("scode"+scode);
            adminService.login(admin,code,scode);
            request.setAttribute("admin",admin);
            message = "登陆成功";
        }catch(Exception e){
            message = e.getMessage();
        }
        return message;
    }

}
