package com.baizhi.test;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestEasyPoi {
    @Test  //导出
    public void testExport(){
        ArrayList<Chapter> chapters = new ArrayList<Chapter>();
        chapters.add(new Chapter("1","chaptertitle1","url1","size1","duration1",new Date(),"1"));
        chapters.add(new Chapter("2","chaptertitle1","url1","size1","duration1",new Date(),"1"));
        chapters.add(new Chapter("3","chaptertitle1","url1","size1","duration1",new Date(),"1"));
        chapters.add(new Chapter("4","chaptertitle1","url1","size1","duration1",new Date(),"1"));
        List<Album> albums = new ArrayList<Album>();
        albums.add(new Album("1","title1","author1",5.0,"cover1","broadcost1",5,"content1",new Date(),chapters));
        albums.add(new Album("2","title1","author1",5.0,"cover1","broadcost1",5,"content1",new Date(),chapters));
        albums.add(new Album("3","title1","author1",5.0,"cover1","broadcost1",5,"content1",new Date(),chapters));
        albums.add(new Album("4","title1","author1",5.0,"cover1","broadcost1",5,"content1",new Date(),chapters));
        albums.add(new Album("5","title1","author1",5.0,"cover1","broadcost1",5,"content1",new Date(),chapters));
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("专辑信息表", "专辑信息sheet"), Album.class, albums);
        try {
            workbook.write(new FileOutputStream(new File("F://1.xlsx")));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void testEasyPoiImport(){
       //创建导入对象
        ImportParams params = new ImportParams();
        params.setTitleRows(1);  //表格标题行数，默认0
        params.setHeadRows(2);   //表头行数，默认1
        //获取导入数据
        List<Album> albums = null;
        try {
            albums = ExcelImportUtil.importExcel(new FileInputStream(new File("F://1.xlsx")), Album.class, params);
        } catch (Exception e) {

        }
        for (Album a : albums) {
            System.out.println(a);
        }

    }
}
