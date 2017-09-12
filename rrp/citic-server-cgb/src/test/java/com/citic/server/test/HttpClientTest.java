package com.citic.server.test;

import java.io.File;
import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.citic.server.runtime.StandardCharsets;
import com.citic.server.utils.IOUtils;

/**
 * @author wangbo
 * @date 2016年11月29日 上午11:26:44
 *       深圳公安本地测试发送请求数据
 */
public class HttpClientTest {
	
	//启动深圳监听服务，将测试数据包放在该目录下并运行
	public static void main(String[] args) throws ClientProtocolException, IOException {
		String path = "E:\\测试\\测试数据\\深圳\\test";
		getFile(path);
	}
	
	//适用于本地
	public static void File(String path, String name) throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost("http://127.0.0.1:9010/cgb/afp/upload");
		
		File file = new File(path);
		
		// FileEntity requestEntity = new FileEntity(file, ContentType.create("multipart/mixed", Consts.UTF_8)); // text/plain
		
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		
		// String content = "<RETURNRECEIPTS><RETURNRECEIPT QQDBS=\"String我\" SQJGDM=\"String\" MBJGDM=\"String\" JSSJ=\"20150917020103\" HZDM=\"String\" HZSM=\"String\" /></RETURNRECEIPTS>";
		// builder.addTextBody("E007B101TaHyl7A8TKKyvDjkMyRrIQ.xml", "");
		// builder.addBinaryBody("E007B101TaHyl7A8TKKyvDjkMyRrIQ", content.getBytes(Consts.UTF_8), ContentType.TEXT_XML, "E007B101TaHyl7A8TKKyvDjkMyRrIQ.xml");
		builder.addBinaryBody(name, file);
		HttpEntity requestEntity = builder.build();
		
		httppost.setEntity(requestEntity);
		CloseableHttpResponse response = httpclient.execute(httppost);
		
		StatusLine status = response.getStatusLine();
		
		System.out.println("ProtocolVersion = " + status.getProtocolVersion());
		System.out.println("StatusCode = " + status.getStatusCode());
		System.out.println("ReasonPhrase = " + status.getReasonPhrase());
		
		Header[] headers = response.getHeaders("Content-Length");
		for (int i = 0; i < headers.length; i++) {
			System.out.println(headers[i].getName() + ": " + headers[i].getValue());
		}
		
		HttpEntity entity = response.getEntity();
		System.out.println("Content-Length = " + entity.getContentLength());
		
		byte[] content = IOUtils.toByteArray(entity.getContent());
		if (content != null) {
			System.out.println("//////////////////////////////////////////////");
			System.out.println(new String(content, StandardCharsets.UTF_8));
			System.out.println("//////////////////////////////////////////////");
		}
		
		// 关闭client
		EntityUtils.consume(entity);
	}
	
	public static void getFile(String path) throws ClientProtocolException, IOException {
		File file = new File(path);
		if (file.exists()) {
			File[] files = file.listFiles();
			if (files.length == 0) {
				System.out.println("文件夹为空");
			} else {
				for (File f : files) {
					if (f.isDirectory()) {
						getFile(f.getAbsolutePath());
					} else {
						String realpath = f.getAbsolutePath();
						String name = f.getName();
						String[] str = name.split("\\.");
						name = str[0];
						System.out.println("路径：" + realpath);
						System.out.println("文件名：" + name);
						File(realpath, name);
						System.out.println();
					}
				}
			}
		}
	}
}
