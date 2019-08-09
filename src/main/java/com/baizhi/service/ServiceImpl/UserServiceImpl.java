package com.baizhi.service.ServiceImpl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public HashMap<String, Object> selectUsers(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        //存入总条数
        Integer countUser = userDao.countUser();
        map.put("records",countUser);
        //存入总页数
        Integer pagecount = countUser%rows==0?countUser/rows:countUser/rows+1;
        map.put("total",pagecount);
        //当前页号
        map.put("page",page);
        //当前页数据
        Integer start = (page-1)*rows;
        List<User> users = userDao.selectUsers(start, rows);
        map.put("rows",users);
        return map;
    }

    @Override
    public HashMap<String, Object> updateUser(User user) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            userDao.updateUser(user);
            map.put("success",200);
            map.put("message","修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success",400);
            map.put("message","修改失败");
        }
        return map;
    }

    @Override
    public void exportUsers() {
        List<User> users = userDao.selectAllUsers();
        for (User user : users) {
            user.setPic_img("F:/cmfz/cmfz/src/main/webapp/img/"+user.getPic_img());
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户信息", "user表"), User.class, users);
        try {

            //workbook.write(new FileOutputStream(new File("F:/cmfz/cmfz/user.xlsx")));
            FileOutputStream fileOutputStream = new FileOutputStream(new File("F:/cmfz/cmfz/user.xlsx"));
            workbook.write(fileOutputStream);
            fileOutputStream.close();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public HashMap<String, Object> selectAllUserByMonth() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        List<Integer> boys = userDao.selectBoysByMonth();
        for (Integer boy : boys) {
            if(boy==null) boy=0;
            System.out.println(boy);
        }
        List<Integer> girls = userDao.selectGirlsByMonth();
        for (Integer girl : boys) {
            if(girl==null) girl=0;
            System.out.println(girl);
        }
        map.put("month", Arrays.asList("1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"));
        map.put("boys",boys);
        map.put("girls",girls);
        return map;
    }
}
