package com.baizhi.service.ServiceImpl;

import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sun.security.util.Length;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDao chapterDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Chapter> selectChapters(Integer page, Integer rows,String id) {
        Integer start = (page-1)*rows;
        List<Chapter> chapters = chapterDao.selectChapters(start, rows,id);
        return chapters;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Integer selectCount(String id) {
        Integer integer = chapterDao.selectCount(id);
        return integer;
    }

    @Override
    public String addOneChapter(Chapter chapter) {
        String uuid = UUID.randomUUID().toString();
        chapter.setId(uuid);
        chapter.setUp_date(new Date());
        chapterDao.addOneChapter(chapter);
        return uuid;
    }

    @Override
    public HashMap<String, Object> uploadChapter(MultipartFile url, String id, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        //上传文件、修改数据库中的信息、返回信息
        //上传文件：文件是否存在、
            //获取文件夹绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/music");
            //创建文件夹
        File file = new File(realPath);
            //文件夹是否存在
        if(!file.exists()){file.mkdirs();}
            //获取上传文件的名字
        String filename = url.getOriginalFilename();
            //给文件加上事件戳前缀
        String newfilename = new Date().getTime()+"_"+filename;
            //创建文件
        File chapterfile = new File(realPath, newfilename);
        System.out.println("chapterfile是"+chapterfile);
        try {
            //上传文件
            url.transferTo(chapterfile);

            //修改数据库信息
            long size = url.getSize();  //获取的是字节B  B/1024=KB  KB/1024=MB
            //获取文件的大小（不精确版本）
            //String sizes = size/1024/1024+"MB";
            //获取文件的大小（精确版本）
            DecimalFormat format = new DecimalFormat("0.00");
            String stringsize = String.valueOf(size);   //将long类型转化成string类型
            Double doubleMBSize = Double.valueOf(stringsize)/1024/1024;
            String stringMBsize = format.format(doubleMBSize)+"MB";

            //获取文件时长
            AudioFileIO fileIO = new AudioFileIO();
            AudioFile audioFile = fileIO.readFile(chapterfile);
            AudioHeader audioHeader = audioFile.getAudioHeader();
            //获取时长(分钟)
            int trackLength = audioHeader.getTrackLength();
            System.out.println("时长是："+ trackLength);
            String duration = trackLength/60+"分"+trackLength%60+"秒";

            Chapter chapter = new Chapter();
            chapter.setId(id);
            chapter.setUrl(newfilename);
            chapter.setSize(stringMBsize);
            chapter.setDuration(duration);
            System.out.println("上传文件之后的是："+chapter);
            chapterDao.updateChapter(chapter);

            map.put("success","200");
            map.put("message","上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success","400");
            map.put("message","上传失败");
        }
        return map;
    }

    @Override
    public String delChapter(Chapter chapter) {
        chapterDao.delChapter(chapter);
        return chapter.getId();
    }

    @Override
    public String updateChater(Chapter chapter) {
        chapterDao.updateChapter(chapter);
        return chapter.getId();
    }
}
