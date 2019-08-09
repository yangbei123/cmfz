package com.baizhi.dao;

import com.baizhi.entity.Article;
import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleDao {
    //分页查询                     //起始数据（不包含）        在起始数据的基础上，再加上几条数据
    public List<Article> selectArticles(@Param("start") Integer start, @Param("rows") Integer rows);
    //总条数
    public Integer countArticle();
    //删除
    public void delArticle(String id);
    //修改文章信息
    public void updateArticle(Article article);
    //添加文章
    public void addArticle(Article article);

}
