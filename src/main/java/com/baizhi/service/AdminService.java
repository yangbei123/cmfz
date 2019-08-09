package com.baizhi.service;

import com.baizhi.entity.Admin;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface AdminService {
    public void login(Admin admin,String code,String scode);
    //public HashMap<String,Object> login(Admin admin, String code, HttpServletRequest request);
}
