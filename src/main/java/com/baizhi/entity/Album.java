package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Album {
    @Excel(name = "ID",height = 20,width = 30,needMerge = true)
    private String id;
    @Excel(name="标题",needMerge = true)
    private String title;
    @Excel(name="作者",needMerge = true)
    private String author;
    @Excel(name="得分",needMerge = true)
    private Double score;  //
    @Excel(name="封面",needMerge = true)
    private String cover_img;
    @Excel(name="播音员",needMerge = true)
    private String broadcast;
    @Excel(name="音频数",needMerge = true)
    private Integer count;
    @Excel(name="内容",needMerge = true)
    private String content;
    @Excel(name="日期",needMerge = true,format = "yyyy年MM月dd日",width = 20)
    private Date pub_date;
    @ExcelCollection(name="音频")
    private List<Chapter> chapters;

}
