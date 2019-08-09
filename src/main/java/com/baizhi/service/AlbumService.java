package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.List;

public interface AlbumService {
    //分页查询
    public List<Album> selectAlbums(Integer page,Integer rows);  //当前页号，每页展示的数量
    //查询总条数
    public Integer albumCount();
    //修改集数
    public void updateCount(String id);
    //添加专辑
    public String addAlbum(Album album);
    //修改专辑内容
    public String updateAlbum(Album album);
}
