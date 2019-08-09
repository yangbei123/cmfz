package com.baizhi.controller;

import org.apache.commons.io.FilenameUtils;
import org.springframework.jdbc.support.incrementer.HsqlMaxValueIncrementer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Controller
@RequestMapping("/kindedit")
public class KindEdit {
    //上传富文本编辑器中的图片
    @RequestMapping("/upload")
    @ResponseBody
    public HashMap<String,Object> upload(MultipartFile photo, HttpServletRequest request){
        //有相对路径获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/img/kind");
        //找文件夹
        File file = new File(realPath);
        //看文件夹是否存在
        if(!file.exists()){file.mkdirs();}

        //获取文件名并且给文件名再加上时间
        String filename = photo.getOriginalFilename();
        String name = new Date().getTime()+"_"+filename;

        //获取http
        String scheme = request.getScheme();
        //获取 ip
        String serverName = request.getServerName();
        //获取端口号
        int serverPort = request.getServerPort();
        //获取项目名
        String contextPath = request.getContextPath();
        //网络路径的拼接
        String serverPath = scheme+"://"+serverName+":"+serverPort+"/"+contextPath+"/img/kind/"+name;
        HashMap<String, Object> map = new HashMap<>();
        try {
            //拼接文件 //复制
            photo.transferTo(new File(realPath,name));
            map.put("error",0);
            map.put("url",serverPath);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("error",1);
            map.put("url","上传失败");
        }
        return map;
    }



    /*
    {
	"moveup_dir_path": "",
	"current_dir_path": "",
	"current_url": "\/ke4\/php\/..\/attached\/",
	"total_count": 5,
	"file_list": [{
		"is_dir": false,
		"has_file": false,
		"filesize": 245132,
		"dir_path": "",
		"is_photo": true,
		"filetype": "jpg",
		"filename": "2009321101428.jpg",
		"datetime": "2018-06-06 00:36:39"
	}]
}
    */
  
    //本地文件图片回显
    @RequestMapping("/queryAllPhoto")
    @ResponseBody
    public HashMap<String,Object> queryAllPhoto(HttpServletRequest request){
        HashMap<String, Object> maps = new HashMap<>();
        ArrayList<Object> lists = new ArrayList<>();
        
        //获取图片文件夹的具体路径
        String realPath = request.getSession().getServletContext().getRealPath("/img/kind");
        //获取文件夹
        File file = new File(realPath);
        //获取文件夹下的所有文件的名称
        String[] names = file.list();
        for (int i = 0; i < names.length; i++) {
            //获取文件名字
            String name = names[i];

            HashMap<Object, Object> map = new HashMap<>();
            map.put("is_dir",false);//是否是文件夹
            map.put("has_file",false);//是否是文件格式
            File file1 = new File(realPath, name);
            map.put("filesize",file1.length());//文件的大小
            map.put("is_photo",true);//是否是图片
            String extension = FilenameUtils.getExtension(name);
            map.put("filetype",extension);
            map.put("filename",name);//文件的名字
            //字符串拆分，图片上传时加上的时间戳
            String[] s = name.split("_");
            String times = s[0];
            long timel = Long.parseLong(times);  //将String类型的转换成long类型的
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = dateFormat.format(timel);  //将长整形按照日期类型转化成String类型
            map.put("datetime",format);

            //封装进集合
            lists.add(map);
        }
        maps.put("current_url","http://localhost:8989/cmfz/img/kind/"); //网络路径
        maps.put("total_count",lists.size());//文件数量
        maps.put("file_list",lists);//文件集合
        return maps;
    }
}
