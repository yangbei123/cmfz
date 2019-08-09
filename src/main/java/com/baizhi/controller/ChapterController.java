package com.baizhi.controller;

import com.baizhi.entity.Chapter;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ChapterService;
import com.fasterxml.jackson.annotation.JsonFormat;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private AlbumService albumService;
    @RequestMapping("/selectChapters")
    @ResponseBody
    public Map<String,Object> selectChapters(Integer page,Integer rows,String id) throws Exception{
        HashMap<String, Object> map = new HashMap<>();
        //总条数
        Integer chaptercount = chapterService.selectCount(id);
        System.out.println("总条数是"+chaptercount );
        map.put("records",chaptercount);
        //总页数
        Integer pageCount = chaptercount%rows==0?chaptercount/rows:chaptercount/rows+1;
        map.put("total",pageCount);
        //当前页号
        map.put("page",page);
        //当前页数据
        List<Chapter> chapters = chapterService.selectChapters(page, rows,id);
        map.put("rows",chapters);
        return map;
    }
    //操作chapter
    @RequestMapping("/edit")
    @ResponseBody
    public String editChapter(Chapter chapter,String oper){
        String uid=null;
        //增加
        if(oper.equals("add")){
            //专辑集数+1
            albumService.updateCount(chapter.getAlbumId());
            uid = chapterService.addOneChapter(chapter);
        }
        //修改
        if(oper.equals("edit")){
            uid = chapterService.updateChater(chapter);
        }
        //删除
        if(oper.equals("del")){
            uid = chapterService.delChapter(chapter);
        }
        return uid;
    }
    //上传文件
    @RequestMapping("/uploadChapter")
    @ResponseBody
    public HashMap<String,Object> uploadChapter(MultipartFile url, String id, HttpServletRequest request){
        HashMap<String, Object> map = chapterService.uploadChapter(url, id, request);
        return map;
    }
    //音频下载
    @RequestMapping("/downloadChapter")
    public void downloadChapter(String fileName, HttpServletRequest request, HttpServletResponse response){
        //获取文件夹路径
        String realPath = request.getSession().getServletContext().getRealPath("/music");
        //根据路径读取文件
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(realPath, fileName));
            //设置文件响应格式     响应头                   //attachment:以附件形式打开   inline：在线打开
            response.setHeader("content-disposition","attachment;fileName="+ URLEncoder.encode(fileName,"utf-8"));
            ServletOutputStream outputStream = response.getOutputStream();
            //文件下载
            IOUtils.copy(fileInputStream,outputStream);
            //关闭资源
            fileInputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
