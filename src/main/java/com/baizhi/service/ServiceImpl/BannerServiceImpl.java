package com.baizhi.service.ServiceImpl;

import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Banner> selectAllBanners(Integer page,Integer rows) {
        //计算起始下表和结束小标
        Integer start = (page-1)*rows;
        Integer end=page*rows;
        List<Banner> banners = bannerDao.selectAllBanners(start,end);
        return banners;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Integer selectCount() {
        Integer count = bannerDao.selectCounts();
        return count;
    }

    @Override
    public String addBanner(Banner banner) {
        String uuid = UUID.randomUUID().toString();
        banner.setId(uuid);
        banner.setUp_date(new Date());
        banner.setStatus("激活");
        bannerDao.addBanner(banner);
        return uuid;
    }

    @Override
    public String delBanner(Banner banner) {
        bannerDao.deleteBanner(banner.getId());
        return banner.getId();
    }

    @Override
    public String updateBanner(Banner banner) {
        bannerDao.updateBanner(banner);
        return banner.getId();
    }
}

