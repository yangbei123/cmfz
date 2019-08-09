package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    @RequestMapping("/selectAlbums")
    @ResponseBody                           //当前页      //每页展示的具体数据
    public Map<String,Object> selectAlbums(Integer page,Integer rows) throws Exception{
        HashMap<String, Object> map = new HashMap<>();
        //总条数
        Integer albumCount = albumService.albumCount();
        map.put("records",albumCount);
        //总页数
        Integer pagecount = albumCount%rows==0?albumCount/rows:albumCount/rows+1;
        map.put("total",pagecount);
        //当前页数据
        List<Album> albums = albumService.selectAlbums(page, rows);
        map.put("rows",albums);
        //当前页号
        map.put("page",page);
        return map;
    }
    @RequestMapping("/edit")
    @ResponseBody
    public String editAlbum(Album album,String oper){
        String uid = null;
        if(oper.equals("add")){
            uid = albumService.addAlbum(album);
        }
        if(oper.equals("edit")){
            uid = albumService.updateAlbum(album);
        }
        if(oper.equals("del")){

        }
        return uid;
    }
}
