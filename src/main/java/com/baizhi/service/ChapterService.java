package com.baizhi.service;

import com.baizhi.entity.Chapter;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ChapterService {
    //分页查询
    public List<Chapter> selectChapters(Integer page,Integer rows,String id);
    //查询总条数
    public Integer selectCount(String id);
    //添加chapter
    public String addOneChapter(Chapter chapter);
    //上传音频
    public HashMap<String,Object> uploadChapter(MultipartFile url, String id, HttpServletRequest request);
    //删除音频
    public String delChapter(Chapter chapter);
    //修改音频
    public String updateChater(Chapter chapter);
}
