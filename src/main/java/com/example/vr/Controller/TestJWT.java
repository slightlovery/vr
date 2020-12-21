package com.example.vr.Controller;

import com.alibaba.fastjson.JSONObject;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;



public class TestJWT {

    public static void main(String[] args)
    {
	try
	{
		String data= "";
		// =================示例：解密xjwt (token也是一个xjwt);
		//String xjwt = "AAABbUy0XxsCAAAAAAABiDA%3D.xPwVH6y5s7tALHu1W3z4zX9Moo5j3qHhHylUxL2lVFzRKDBzQpK1YmrohX2gKKVE.zxDXPoreJXv8N1BAtMUcceupBM8nf0UcWQx5j0u6Ao0%3D";


		// =================示例：生成xjwt
		JSONObject param=new JSONObject();
		param.put("username","test");
		param.put("issuerId",KEY.issuerId.toString());
		String json=param.toString();

		data = encrty(json);
		System.out.println(data);

		String token = "AAABc%2FpwmUkBAAAAAAABkdM%3D.nFiAD0eUQbeCseuvLr5MSC00abxELyJ1dcWedTB6DJdn2mIuk9bczZsDbanyK%2B2%2BeSF5KTkwENkDS%2FV5c9bI2prhzAFwsc3wHfFbjKPujIo%3D.gUd6EfyyQ0arRdC1wbvJbJCWfpOmABNSNv4vsuSVAVg%3D";

		data = dencrty(token);
		System.out.println(data);
		
	} catch (Exception e)
	{
		e.printStackTrace();
	}
    }
    public static String dencrty(String xjwt) throws Exception {
    	//获取当前时间
    	long now = System.currentTimeMillis();
    	//创建JWT实例
        JWT jwt = new JWT(KEY.secret, KEY.aeskey,now,KEY.issuerId);
        //对数据进行url 解码
        xjwt=URLDecoder.decode(xjwt,"UTF-8"); 
        //解密数据
        String json = jwt.verifyAndDecrypt(xjwt,now);
        return json;
    }
    public static String encrty(String json) throws Exception {
	//获取当前时间
	long now=System.currentTimeMillis();
	//创建JWT实例
	JWT jwt=new JWT(KEY.secret,KEY.aeskey,now,KEY.issuerId);
	//创建payload
	ByteBuffer payload = ByteBuffer.allocate(1024).order(ByteOrder.BIG_ENDIAN);
        payload.put(json.getBytes("UTF-8")).flip();
	//创建out
        ByteBuffer out = ByteBuffer.allocate(1024);
        //加密数据
        jwt.encryptAndSign(JWT.Type.SYS,payload,out,now+10*60*1000); //设置过期时间，例:10分钟
        String xjwt = new String(out.array(),out.arrayOffset(),out.remaining());
        //对数据进行url 编码
        return URLEncoder.encode(xjwt,"UTF-8");
    }
}
