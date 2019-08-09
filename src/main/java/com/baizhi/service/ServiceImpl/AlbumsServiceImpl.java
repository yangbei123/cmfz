package com.baizhi.service.ServiceImpl;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AlbumsServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private ChapterDao chapterDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Album> selectAlbums(Integer page, Integer rows) {
        Integer start = (page-1)*rows;
        List<Album> albums = albumDao.selectAlbums(start, rows);
        for (Album album : albums) {
            album.setCount(chapterDao.selectCount(album.getId()));
        }

        return albums;
    }

    @Override
    public Integer albumCount() {
        Integer count = albumDao.albumsCount();
        return count;
    }

    @Override
    public void updateCount(String id) {
        albumDao.updateCount(id);
    }

    @Override
    public String addAlbum(Album album) {
        String uid = UUID.randomUUID().toString();
        album.setId(uid);
        album.setCount(0);
        album.setPub_date(new Date());
        System.out.println("添加："+album);
        albumDao.addAlbum(album);
        return uid;
    }

    @Override
    public String updateAlbum(Album album) {
        albumDao.updateAlbum(album);
        return album.getId();
    }
}
