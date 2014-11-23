package cn.nsx.yaoyiyao.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Date;

import org.apache.http.HttpConnection;

import cn.nsx.yaoyiyao.ChatMsgEntity;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class HTTPUtil {

	private static final String URL="http://www.tuling123.com/openapi/api";
	private static final String API_KEY ="210e758da114adfd5ef98061d13e51fa";
	
	/*
	 * 发送一个消息，得到返回的消息
	 */
	public static ChatMsgEntity sendMessage(String msg)
	{
		ChatMsgEntity chatMessage = new ChatMsgEntity();
		String jsonRes = doGet(msg);//拿到返回的数据
		Gson gson = new Gson();
		Result result = null;
		try {
			result = gson.fromJson(jsonRes, Result.class);//将返回的数据转成result对象
			chatMessage.setText(result.getText());//聊天消息
		} catch (JsonSyntaxException e) 
		{
			chatMessage.setText("服务器繁忙，请稍后再试");//转化失败
		}
		chatMessage.setDate(new Date());
		chatMessage.setMsgType(true);//左边
		
		return chatMessage;
	}
	
	public static String doGet(String msg)//用戶传入Message
	{
		String result = "";
		String url = setParams(msg);//拼接一个完整的URL
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
			java.net.URL urlNet = new java.net.URL(url);
			HttpURLConnection conn = (HttpURLConnection) urlNet
					.openConnection();//打开链接
			conn.setReadTimeout(5*1000);
			conn.setConnectTimeout(5*1000);
			conn.setRequestMethod("GET");
			
			is = conn.getInputStream();//通过connection拿到服务器返回的流
			int len = -1;
			byte[] buf = new byte[128];
			baos = new ByteArrayOutputStream();
			
			while((len = is.read(buf))!=-1)//对流进行操作
			{
				baos.write(buf,0,len);
			}
			baos.flush();//清楚缓冲区
			result = new String(baos.toByteArray());//转成字符串
		} 
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				if (baos!=null) {
					baos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (is!=null) {
					is.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	//拼接一个完整的URL
	private static String setParams(String msg) {
		String url = "";
		try {
			//msg = URLEncoder.encode(msg,"UTF-8");對傳入的message进行编码
			url = URL+"?key="+API_KEY+"&info="+URLEncoder.encode(msg,"UTF-8");
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return url;
	}
}
