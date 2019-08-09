package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.entity.User;
import com.baizhi.service.ArticleService;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

@Controller
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @RequestMapping("/selectArticles")
    @ResponseBody
    private HashMap<String,Object> selectArticles(Integer page,Integer rows){
        HashMap<String, Object> map = articleService.selectArticles(page, rows);
        return map;
    }

   @RequestMapping("/exitArticle")
   @ResponseBody
   private String exitArticle(Article article,String oper){
       System.out.println("ar"+article);
       String uid=null;
       if(oper.equals("del")){
           articleService.delArticle(article);
           uid=article.getId();
       }
       return uid;
   }

   //修改信息
    @RequestMapping("/upload")
    @ResponseBody
    public HashMap<String,Object> update(Article article,MultipartFile insert_img){
        //article.setId(ArticleId);
        System.out.println("article："+article);
        System.out.println("insert_img"+insert_img);
        HashMap<String, Object> map = articleService.updateArticle(article);
        return map;
    }
    @RequestMapping("/add")
    @ResponseBody
    public HashMap<String,Object> add(Article article){
        System.out.println("add article："+article);
        HashMap<String, Object> map = articleService.addArticle(article);
        return map;
    }
    @RequestMapping("/updatestatus")
    @ResponseBody
    public HashMap<String,Object> updatestatus(Article article){
        System.out.println("update article："+article);
        HashMap<String, Object> map = articleService.updateArticle(article);
        return map;
    }
    @RequestMapping("/del")
    @ResponseBody
    public String del(Article article){
        System.out.println("update article："+article);
        articleService.delArticle(article);
        return article.getId();
    }
}
