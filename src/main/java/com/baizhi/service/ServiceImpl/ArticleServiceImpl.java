package com.baizhi.service.ServiceImpl;

import com.baizhi.dao.ArticleDao;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.Article;
import com.baizhi.entity.User;
import com.baizhi.service.ArticleService;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public HashMap<String, Object> selectArticles(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        //存入总条数
        Integer countUser = articleDao.countArticle();
        map.put("records",countUser);
        //存入总页数
        Integer pagecount = countUser%rows==0?countUser/rows:countUser/rows+1;
        map.put("total",pagecount);
        //当前页号
        map.put("page",page);
        //当前页数据
        Integer start = (page-1)*rows;
        List<Article> articles = articleDao.selectArticles(start, rows);
        map.put("rows",articles);
        return map;
    }

    @Override
    public void delArticle(Article article) {

        articleDao.delArticle(article.getId());
    }

    @Override
    public HashMap<String, Object> updateArticle(Article article) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            articleDao.updateArticle(article);
            map.put("success",200);
            map.put("messagge","修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success",400);
            map.put("messagge","修改失败");
        }
        return map;
    }

    @Override
    public HashMap<String, Object> addArticle(Article article) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            String uid = UUID.randomUUID().toString();
            article.setId(uid);
            article.setUp_date(new Date());
            article.setStatus("1");
            articleDao.addArticle(article);
            map.put("success",200);
            map.put("messagge","添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success",400);
            map.put("messagge","添加失败");
        }
        return map;
    }
}
