package com.baizhi.test;

import com.baizhi.entity.Banner;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TestPoi {
    @Test
    public void testpois(){
        //创建excel文档
        Workbook workbook = new HSSFWorkbook();
        //创建按sheet表
        Sheet sheet = workbook.createSheet("sheet");
        //创建行
        Row row = sheet.createRow(0);
        //创建单元格
        Cell cell = row.createCell(0);
        //给单元格设置内容
        cell.setCellValue("这是第一个单元格");
        //导出表格
        try {
            workbook.write(new FileOutputStream(new File("F://1.xlsx")));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testpois2() {
        String[] titles = {"id","title","img_path","description","status","up_date"};
        Banner banner1 = new Banner("1", "banner1","1.png","轮播图1","激活",new Date());
        Banner banner2 = new Banner("2", "banner2","1.png","轮播图1","激活",new Date());
        Banner banner3 = new Banner("3", "banner3","1.png","轮播图1","激活",new Date());
        Banner banner4 = new Banner("4", "banner4","1.png","轮播图1","激活",new Date());
        List<Banner> banners = Arrays.asList(banner1, banner2, banner3, banner4);


        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet2 = workbook.createSheet("轮播图信息");
        sheet2.setColumnWidth(5,20*256);
        //构建字体
        HSSFFont font = workbook.createFont();
        font.setBold(true);//加粗
        font.setColor(Font.COLOR_RED); //颜色
        font.setFontHeightInPoints((short)10); //字号
        font.setFontName("宋体"); //字体
        font.setItalic(true);  //斜体
        font.setUnderline(FontFormatting.U_SINGLE);//下划线

        //创建样式对象1
        HSSFCellStyle cellStyle1 = workbook.createCellStyle();
        cellStyle1.setFont(font);  //将字体样式引入
        cellStyle1.setAlignment(HorizontalAlignment.CENTER); //居中


        //创建样式对象2(主要针对时间单元格)
            //创建日期单元格样式对象
        HSSFCellStyle cellStyle2 = workbook.createCellStyle();
            //创建日期格式对象
        HSSFDataFormat dataFormat = workbook.createDataFormat();
            //设置日期格式对象并且将日期格式对象添加进单元格格式对象中
        cellStyle2.setDataFormat(dataFormat.getFormat("yyyy年MM月dd日"));

        //创建标题           行合并与创建行，创建单元格，向单元格中设置值均无关
        HSSFRow row0 = sheet2.createRow(0);
        Cell cell1 = row0.createCell(0);
        CellRangeAddress rangeAddress = new CellRangeAddress(0, 0, 0, 5);
        sheet2.addMergedRegion(rangeAddress);
        cell1.setCellValue("轮播图信息");


        //创建表头
        HSSFRow row1 = sheet2.createRow(1);
        row1.setHeight((short) 300);
        for (int i = 0; i < titles.length; i++) {
            HSSFCell cell = row1.createCell(i); //单元格下标
            cell.setCellValue(titles[i]);       //单元格内容
            cell.setCellStyle(cellStyle1);       //标题行使用样式

        }

        //创建数据行、
        for (int i = 0; i < banners.size(); i++) {
            HSSFRow row = sheet2.createRow(i + 2);
            row.createCell(0).setCellValue(banners.get(i).getId());
            row.createCell(1).setCellValue(banners.get(i).getTitle());
            row.createCell(2).setCellValue(banners.get(i).getImg_path());
            row.createCell(3).setCellValue(banners.get(i).getDescription());
            row.createCell(4).setCellValue(banners.get(i).getStatus());
            HSSFCell cell = row.createCell(5);
            cell.setCellValue(banners.get(i).getUp_date());
            cell.setCellStyle(cellStyle2);
        }
        //导出表格
        try {
            workbook.write(new FileOutputStream(new File("F://1.xlsx")));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testpoiImport(){

        try {
            //获取文件
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("F://1.xlsx")));
            //获取表
            HSSFSheet sheet = workbook.getSheet("轮播图信息");
            //在从表中获取行，单元格中数据    //获取最后一行的下标  首行的下标是2，所以从第二行开始
            for (int i = 2; i <= sheet.getLastRowNum(); i++) {
                Banner banner = new Banner();
                HSSFRow row = sheet.getRow(i); //获取行
                banner.setId(row.getCell(0).getStringCellValue());  //获取第一个单元格 中的数据，因为第一个单元格中数据是String类型的。所以方法是getStringCellValue
                banner.setTitle(row.getCell(1).getStringCellValue());
                banner.setImg_path(row.getCell(2).getStringCellValue());
                banner.setDescription(row.getCell(3).getStringCellValue());
                banner.setStatus(row.getCell(4).getStringCellValue());
                banner.setUp_date(row.getCell(5).getDateCellValue());
                System.out.println(banner);
            }
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
