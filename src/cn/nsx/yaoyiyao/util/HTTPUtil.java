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
	 * ����һ����Ϣ���õ����ص���Ϣ
	 */
	public static ChatMsgEntity sendMessage(String msg)
	{
		ChatMsgEntity chatMessage = new ChatMsgEntity();
		String jsonRes = doGet(msg);//�õ����ص�����
		Gson gson = new Gson();
		Result result = null;
		try {
			result = gson.fromJson(jsonRes, Result.class);//�����ص�����ת��result����
			chatMessage.setText(result.getText());//������Ϣ
		} catch (JsonSyntaxException e) 
		{
			chatMessage.setText("��������æ�����Ժ�����");//ת��ʧ��
		}
		chatMessage.setDate(new Date());
		chatMessage.setMsgType(true);//���
		
		return chatMessage;
	}
	
	public static String doGet(String msg)//�Ñ�����Message
	{
		String result = "";
		String url = setParams(msg);//ƴ��һ��������URL
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
			java.net.URL urlNet = new java.net.URL(url);
			HttpURLConnection conn = (HttpURLConnection) urlNet
					.openConnection();//������
			conn.setReadTimeout(5*1000);
			conn.setConnectTimeout(5*1000);
			conn.setRequestMethod("GET");
			
			is = conn.getInputStream();//ͨ��connection�õ����������ص���
			int len = -1;
			byte[] buf = new byte[128];
			baos = new ByteArrayOutputStream();
			
			while((len = is.read(buf))!=-1)//�������в���
			{
				baos.write(buf,0,len);
			}
			baos.flush();//���������
			result = new String(baos.toByteArray());//ת���ַ���
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

	//ƴ��һ��������URL
	private static String setParams(String msg) {
		String url = "";
		try {
			//msg = URLEncoder.encode(msg,"UTF-8");�������message���б���
			url = URL+"?key="+API_KEY+"&info="+URLEncoder.encode(msg,"UTF-8");
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return url;
	}
}
