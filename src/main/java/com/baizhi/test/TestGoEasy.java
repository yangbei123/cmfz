
package com.baizhi.test;

import com.alibaba.fastjson.JSONObject;
import io.goeasy.GoEasy;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class TestGoEasy {
    @Test
    public void testGoEasy1() {


        //获取随机数
        Random random = new Random();
        random.nextInt(10);

        //将随机数放入数组
        int[] randoms ={random.nextInt(500),random.nextInt(100),random.nextInt(900),random.nextInt(400),random.nextInt(300),random.nextInt(800)
        ,random.nextInt(500),random.nextInt(100),random.nextInt(900),random.nextInt(400),random.nextInt(300),random.nextInt(800)};
        for (int i = 0; i < randoms.length; i++) {
            System.out.println(randoms[i]);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("month",Arrays.asList("1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"));
        jsonObject.put("boys",randoms);
        jsonObject.put("girls",randoms);

        //将对象转为json格式字符串
        String content = jsonObject.toJSONString();
        //发布消息  发布地址，appkey
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-d703e907d2dc49108764102bd125c436");
        //参数: 管道(标识)名称,发布的内容
        goEasy.publish("myChannel158", content);
    }
}

