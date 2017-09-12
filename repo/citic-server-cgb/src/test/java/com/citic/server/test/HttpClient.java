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

public class HttpClient {
	public static void main(String[] args) throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost("http://" + ((args == null || args.length == 0) ? "127.0.0.1" : args[0]) + ":9010/cgb/afp/upload");
		
		String filename = "E007B101E6wrnrZESfCaqDm46Dc56w";
		
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.addBinaryBody(filename, new File("E:\\testdata\\" + filename + ".zip"));
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
}
