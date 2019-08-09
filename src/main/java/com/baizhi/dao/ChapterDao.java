package com.baizhi.dao;

import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterDao {
    //分页查询
    public List<Chapter> selectChapters(@Param("start")Integer start, @Param("rows")Integer rows,@Param("id")String id);
    //查询总条数
    public Integer selectCount(String id);
    //增加chapter
    public void addOneChapter(Chapter chapter);
    //修改chapter
    public void updateChapter(Chapter chapter);
    //删除chapter
    public void delChapter(Chapter chapter);
}
