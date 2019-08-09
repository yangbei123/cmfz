package com.baizhi.service.ServiceImpl;

import com.alibaba.druid.wall.violation.ErrorCode;
import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;

import com.baizhi.service.AdminService;
import com.baizhi.util.ImageCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Override
    public void login(Admin admin,String code,String scode) {
        //判断验证码是否正确
        if(!scode.equals(code)) throw new RuntimeException("验证码错误");
        //判断用户是否正确
        Admin admin1 = adminDao.selectAdminByUsername(admin.getUsername());
        if(admin1==null) throw new RuntimeException("该用户不存在");
        if(!admin.getPassword().equals(admin1.getPassword())) throw new RuntimeException("密码错误");
    }

    /*@Override
    public HashMap<String, Object> login(Admin admin, String code, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String scode = (String)session.getAttribute("code");
        HashMap<String, Object> map = new HashMap<>();
        if(code.equals(scode)){

        }

        return null;
    }*/
}
