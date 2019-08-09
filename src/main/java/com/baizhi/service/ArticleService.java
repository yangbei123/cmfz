package com.baizhi.service;

import com.baizhi.entity.Article;
import com.baizhi.entity.User;

import java.util.HashMap;

public interface ArticleService {
    //分页查询
    public HashMap<String,Object> selectArticles(Integer page, Integer rows);
    //删除
    public void delArticle(Article article);
    //修改
    public HashMap<String,Object> updateArticle(Article article);
    //添加
    public HashMap<String,Object> addArticle(Article article);
}
