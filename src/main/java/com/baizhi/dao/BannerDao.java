package com.baizhi.dao;

import com.baizhi.entity.Banner;

import java.util.List;

public interface BannerDao {
    //获取所有的banner图
    public List<Banner> selectAllBanners(Integer start,Integer end);
    //获取总条数
    public Integer selectCounts();
    //添加轮播图
    public void addBanner(Banner banner);
    //删除轮播图
    public void deleteBanner(String id);
    //修改轮播图
    public void updateBanner(Banner banner);
}
