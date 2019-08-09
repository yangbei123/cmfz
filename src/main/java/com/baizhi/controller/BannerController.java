package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;
    //查询所有轮播图
    @RequestMapping("/showBanners")
    @ResponseBody                             //当前页号
    public Map<String,Object> showBanners(Integer page, Integer rows) throws  Exception{
        //查询数据
        List<Banner> banners = bannerService.selectAllBanners(page,rows); //当前页  每页展示的数量
        //查询总条数
        Integer count = bannerService.selectCount();

        HashMap<String, Object> map = new HashMap<>();
        map.put("page",page); //当前页号
        map.put("records",count); //总条数
        //总页数
        Integer pageCount=0;
        if(count%rows==0){
            pageCount = count/rows;
        }else{
            pageCount=count/rows+1;
        }
        map.put("total",pageCount);  //总页数
        map.put("rows",banners);  //每页展示的具体数据
        return  map;
    }
    @RequestMapping("/edit")
    @ResponseBody
    public String edit(Banner banner,String oper) throws Exception{
        String uid = null;
        //添加操作
        if(oper.equals("add")){
            uid = bannerService.addBanner(banner);
        }
        //修改操作
        if(oper.equals("edit")){
            uid = bannerService.updateBanner(banner);
            if(banner.getImg_path().equals("")){
                uid=null;
            }
        }
        //删除操作
        if(oper.equals("del")){
            uid = bannerService.delBanner(banner);
        }
        System.out.println("uid是："+uid);
        return uid;
    }

    //文件上传
    @RequestMapping("/uploadBanner")
    @ResponseBody
    public void uploadBanner(MultipartFile img_path, String id, HttpServletRequest request) throws Exception{
        System.out.println("img_path"+img_path);
        //获取要上传文件的路径
        String realPath = request.getSession().getServletContext().getRealPath("/img");
        String newfilename=new Date().getTime()+""+img_path.getOriginalFilename();
        File file = new File(realPath + "/" +newfilename);
        img_path.transferTo(file);
        Banner banner = new Banner();
        banner.setId(id);
        banner.setImg_path(newfilename);
        System.out.println("修改路径之后的"+banner);
        bannerService.updateBanner(banner);
    }

    //修改状态
    @RequestMapping("/updateStatus")
    @ResponseBody
    public String updateStatus(Banner banner){

        try {
            bannerService.updateBanner(banner);
            return "修改成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "修改失败";
        }

    }
}
