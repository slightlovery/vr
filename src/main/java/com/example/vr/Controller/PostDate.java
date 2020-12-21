package com.example.vr.Controller;


import cn.hutool.core.map.MapUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSON;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Controller
public class PostDate {


    @RequestMapping("/greetingTest")
    @ResponseBody
    public String greetingTest() {
        try {
            Map<Object, Object> param = MapUtil.of(new String[][]{
                    {"username", "guest"},
                    {"projectTitle", "智慧工厂虚拟实训项目"},
                    {"childProjectTitle", "传热综合实验3D虚拟仿真软件"},
                    {"status", "1"},
                    {"score", "100"},
                    {"startDate", "1608272302000"},
                    {"endDate", "1608275980000"},
                    {"timeUsed", "60"},
                    {"issuerId", "100400"},
                    {"attachmentId", "12"}
            });
            Map<String,String> map = new HashMap<>();
            map.put("username", "test");
            map.put("projectTitle", "传热3D&VR虚拟仿真综合实验");
            map.put("childProjectTitle","走马灯");
            map.put("status", "1");
            map.put("score", "100");
            map.put("startDate", "1607654747000");
            map.put("endDate", "1607658347000");
            map.put("timeUsed", "60");
            map.put("issuerId", "100400");
            String json = param.toString();
            //将map数据转为json格式，encrty加密传入的参数是json类型的
            String s = JSONObject.toJSONString(map);
            String data = encrty(s);
            String url = "http://202.205.145.156:8017/project/log/upload?xjwt=" + data;
            String result2 = HttpRequest.post(url)
                    .timeout(20000)//超时，毫秒
                    .execute().body();
            return result2 ;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private final static String encrty(String json) throws Exception {
        //获取当前时间
        long now = System.currentTimeMillis();
        //创建JWT实例
        JWT jwt = new JWT(TestKey.secret, TestKey.aeskey, now, TestKey.issuerId);
        //创建payload
        ByteBuffer payload = ByteBuffer.allocate(1024).order(ByteOrder.BIG_ENDIAN);
        payload.put(json.getBytes("UTF-8")).flip();
        //创建out
        ByteBuffer out = ByteBuffer.allocate(1024);
        //加密数据
        jwt.encryptAndSign(JWT.Type.SYS, payload, out, now + 10 * 60 * 1000); //设置过期时间，例:10分钟
        String xjwt = new String(out.array(), out.arrayOffset(), out.remaining());
        //对数据进行url 编码
        return URLEncoder.encode(xjwt, "UTF-8");
    }

    public String TestEncrty(String json) throws Exception {
        //获取当前时间
        long now = System.currentTimeMillis();
        //创建JWT实例
        JWT jwt = new JWT(TestKey.secret, TestKey.aeskey, now, TestKey.issuerId);
        //创建payload
        ByteBuffer payload = ByteBuffer.allocate(1024).order(ByteOrder.BIG_ENDIAN);
        payload.put(json.getBytes("UTF-8")).flip();
        //创建out
        ByteBuffer out = ByteBuffer.allocate(1024);
        //加密数据
        jwt.encryptAndSign(JWT.Type.SYS, payload, out, now + 10 * 60 * 1000); //设置过期时间，例:10分钟
        String xjwt = new String(out.array(), out.arrayOffset(), out.remaining());
        //对数据进行url 编码
        return URLEncoder.encode(xjwt, "UTF-8");
    }

    public static String TestDencrty(String xjwt) throws Exception {
        //获取当前时间
        long now = System.currentTimeMillis();
        //创建JWT实例
        JWT jwt = new JWT(TestKey.secret, TestKey.aeskey, now, TestKey.issuerId);
        //对数据进行url 解码
        xjwt = URLDecoder.decode(xjwt, "UTF-8");
        //解密数据
        String json = jwt.verifyAndDecrypt(xjwt, now);
        return json;
    }


}
