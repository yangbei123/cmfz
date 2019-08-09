package com.baizhi.dao;

import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    //分页查询                     //起始数据（不包含）        在起始数据的基础上，再加上几条数据
    public List<User> selectUsers(@Param("start") Integer start, @Param("rows") Integer rows);
    //总条数
    public Integer countUser();
    //修改用户
    public void updateUser(User user);
    //查询所有用户
    public List<User> selectAllUsers();
    //根据月份查询男生的注册人数
    public List<Integer> selectBoysByMonth();
    //根据月份查询男生的注册人数
    public List<Integer> selectGirlsByMonth();
}
