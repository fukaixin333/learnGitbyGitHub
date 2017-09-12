package com.citic.server.gdjc.outer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.citic.server.runtime.GdjcKeys;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.utils.DES3;

@Component
public class HttpHelp {

	private static final Logger logger = LoggerFactory.getLogger(HttpHelp.class);
	//连接主机超时(单位毫秒)
	private final int connect_timeout = 30 * 1000;
	//从主机读取数据超时(单位毫秒)
	private final int read_timeout = 30 * 1000;
	
	/**
	 * 获取查询的响应报文
	 * 
	 * @param xml_content xml报文
	 * @return
	 */
	public String getxmlResponseByContent(String xml_content) throws Exception {
		
		String xmlResponse = null;
		// 1.生成查询的xml并加密
		byte[] xmldatas = xml_content.getBytes();// 生成xml的byte字节流;
		// Security.addProvider(new com.sun.crypto.provider.SunJCE());//添加新安全算法,如果用JCE就要把它添加进去
		final byte[] keyBytes = ServerEnvironment.getStringValue(GdjcKeys.HTTP_SECRET_KEY).getBytes(); // 24 字节的密钥
		byte[] xmldatas_3des = DES3.encryptMode(keyBytes, xmldatas);// 使用3DES对报文进行加密;
		// 2.根据接口访问方式，调用接口
		HttpURLConnection httpurlconn = null;
		try {
			httpurlconn = this.getHttpURLConnection(ServerEnvironment.getStringValue(GdjcKeys.HTTP_SERVER_URL));// 建立服务连接信息
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		// 3.发送信息并等待响应
		GZIPOutputStream zos = null;
		GZIPInputStream zis = null;
		ByteArrayOutputStream baos = null;
		byte[] data = null;
		try {
			// 输出流压缩处理[发送到服务器]
			zos = new GZIPOutputStream(httpurlconn.getOutputStream());
			zos.write(xmldatas_3des, 0, xmldatas_3des.length);
			zos.finish();
			logger.info("响应消息：" + httpurlconn.getResponseCode() + "," + httpurlconn.getResponseMessage());
			// 等待读取服务器的反馈并进行解压处理
			zis = new GZIPInputStream(httpurlconn.getInputStream());
			// 获取zip 输入流的内容，每次按1M字节读取（每次读取字节大小可按需调整）
			baos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int len = 0;
			while ((len = zis.read(buf)) != -1) {
				baos.write(buf, 0, len);
			}
			data = baos.toByteArray();
			//4. 将服务端反馈的XML字符串解密并转化为String
			xmlResponse = new String(DES3.decryptMode(keyBytes, data));
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		} finally {
			try {
				if (zos != null) {
					zos.close();
				}
				if (zis != null) {
					zis.close();
				}
				if (baos != null) {
					baos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return xmlResponse;
	}
	
	/**
	 * 获取http连接
	 * 
	 * @param url_interface
	 *        要调用的url
	 * @return
	 * @throws Exception
	 */
	private HttpURLConnection getHttpURLConnection(String url_interface) throws Exception {
		URL url = new URL(url_interface);//
		HttpURLConnection httpurlconn = (HttpURLConnection) url.openConnection();
		httpurlconn.setConnectTimeout(connect_timeout);//连接主机超时
		httpurlconn.setReadTimeout(read_timeout); //从主机读取数据超时
		httpurlconn.setDoOutput(true);//设置是否向httpUrlConnection输出，默认情况下是false
		httpurlconn.setDoInput(true);//设置是否从httpUrlConnection读入，默认情况下是true
		httpurlconn.setUseCaches(false);
		// 设定传送的内容类型是可序列化的java对象，避免可能的java.io.EOFException异常
		httpurlconn.setRequestProperty("Content-type", "application/x-java-serialized-object");
		httpurlconn.setRequestMethod("POST");// 设定请求的方式为POST
		httpurlconn.connect();
		
		return httpurlconn;
	}
	
}
