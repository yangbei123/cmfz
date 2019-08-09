package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    private String id;
    private String title;
    private String status;
    private String insert_img;
    private String content;
    private Date up_date;
    private String guruId;
}
