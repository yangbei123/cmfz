package com.baizhi.dao;

import com.baizhi.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumDao {
    //分页查询所有的数据
    public List<Album> selectAlbums(@Param("start") Integer start, @Param("rows") Integer rows);
    //总条数
    public Integer albumsCount();
    //修改
    public void updateCount(String id);

    //增加专辑
    public void addAlbum(Album album);
    //修改专辑
    public void updateAlbum(Album album);
    //删除
    public void delAlbum(Album album);

}
