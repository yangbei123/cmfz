package com.baizhi.controller;

import com.baizhi.entity.User;
import com.baizhi.service.GoEasyUser;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private GoEasyUser goEasyUser;
    @RequestMapping("/selectUsers")
    @ResponseBody
    private HashMap<String,Object> selectUsers(Integer page,Integer rows){
        HashMap<String, Object> map = userService.selectUsers(page, rows);
        return map;
    }
    @RequestMapping("/updateStatus")
    @ResponseBody
    public HashMap<String,Object> updateStatus(User user) {
        HashMap<String, Object> map = userService.updateUser(user);
        return map;
    }
    @RequestMapping("/updateUsers")
    @ResponseBody
    public String updateUsers(User user,String oper) {
        String uid = null;
        if(oper.equals("add")){}
        if(oper.equals("edit")){
            userService.updateUser(user);
            uid=user.getId();
        }
        return uid;
    }


    @RequestMapping("/uploadimg")
    @ResponseBody
    public void uploadimg(String id, MultipartFile pic_img, HttpServletRequest request){
        if(pic_img.isEmpty()){
            User user = new User();
            user.setId(id);
            user.setPic_img(null);
            userService.updateUser(user);

        }else{



            //1.获取要上传文件夹的路径
            String realPath = request.getSession().getServletContext().getRealPath("/img");

            //获取文件夹
            File file = new File(realPath);

            //创建文件夹
            if(!file.exists()){
                file.mkdirs();
            }

            //获取上传文件的名字
            String filename = pic_img.getOriginalFilename();

            String name=new Date().getTime()+"-"+filename;

            //文件上传
            try {
                pic_img.transferTo(new File(realPath,name));
            } catch (IOException e) {
                e.printStackTrace();
            }
            User user = new User();
            user.setId(id);
            user.setPic_img(name);
            userService.updateUser(user);
        }


    }
    @RequestMapping("/exportUsers")
    public void exportUsers(){
        userService.exportUsers();
    }


    //获取数据统计的数据
    @RequestMapping("/selectUsersByMonth")
    @ResponseBody
    public HashMap<String,Object> selectUsersByMonth(){
        HashMap<String, Object> map = userService.selectAllUserByMonth();
        return map;
    }

    //触发用户统计的执行
    @RequestMapping("/changUserCount")
    @ResponseBody
    public void changUserCount(){
        System.out.println("触发进入controller层");
        goEasyUser.userChangeCount();
    }
}
