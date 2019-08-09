package com.baizhi.service;

import com.baizhi.entity.User;

import java.util.HashMap;

public interface UserService {
    //分页查询
    public HashMap<String,Object> selectUsers(Integer page,Integer rows);
    //修改用户
    public HashMap<String,Object> updateUser(User user);
    //导出所有用户
    public void exportUsers();

    //查询所有用户的1-12月份的注册数
    public HashMap<String,Object> selectAllUserByMonth();
}
