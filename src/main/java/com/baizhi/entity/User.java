package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Excel(name="id")
    private String id;
    @Excel(name="电话")
    private String phone;
    @Excel(name="密码")
    private String password;
    @Excel(name="加密盐")
    private String salt;
    @Excel(name="头像",type=2,width = 40 , height = 20,imageType = 1)  //type=2:该字段类型为图片，
    private String pic_img;
    @Excel(name="法名")
    private String ahama;
    @Excel(name="名字")
    private String name;
    @Excel(name="性别")
    private String sex;
    @Excel(name="城市")
    private String city;
    @Excel(name="签名")
    private String sign;
    @Excel(name="状态")
    private String status;
    @Excel(name="注册日期",format = "yyyy年MM月dd日",width = 20)
    private Date reg_date;
    @ExcelIgnore
    private String guruId;

}
