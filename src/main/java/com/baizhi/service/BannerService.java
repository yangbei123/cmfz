package com.baizhi.service;

import com.baizhi.entity.Admin;
import com.baizhi.entity.Banner;

import java.util.List;

public interface BannerService {
    //查询所有的Banner
    public List<Banner> selectAllBanners(Integer page,Integer rows);
    //获取总条数
    public Integer selectCount();
    //添加banner
    public String addBanner(Banner banner);
    //删除banner
    public String delBanner(Banner banner);
    //修改banner
    public String updateBanner(Banner banner);
}
