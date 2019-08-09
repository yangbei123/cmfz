package com.baizhi.dao;

import com.baizhi.entity.Admin;

public interface AdminDao {
    //登陆
    public Admin selectAdminByUsername(String username);
}
