package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chapter {
    @Excel(name="ID")
    private String id;
    @Excel(name="标题")
    private String title;
    @Excel(name="音频路径")
    private String url;
    @Excel(name="音频大小")
    private String  size;
    @Excel(name="音频时长")
    private String duration;
    @Excel(name="音频上传日期")
    private Date up_date;
    @ExcelIgnore()
    private String albumId;
}
