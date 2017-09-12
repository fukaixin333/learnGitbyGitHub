package com.citic.server.http.codec;

import java.nio.charset.CharsetEncoder;
import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.citic.server.http.api.HttpEntity;
import com.citic.server.runtime.StandardCharsets;

/**
 * 由于深圳公安局并非以文件的形式接收响应，故响应HTTP报文未指定分隔字符串（boundary）、实体头部信息（Entity Head）等。<br />
 * 即使如此，<code>HttpClient</code>居然也能成功通过<code>getEntity()</code>获取实体信息。
 * 
 * @author Liu Xuanfei
 * @date 2016年9月13日 下午5:06:06
 */
public class HttpResponseEncoder<T extends HttpResponseMessage> implements MessageEncoder<T> {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final CharsetEncoder ENCODER_UTF_8 = StandardCharsets.UTF_8.newEncoder();
	
	@Override
	public void encode(IoSession session, T message, ProtocolEncoderOutput out) throws Exception {
		// StatusLine
		StringBuilder sb = new StringBuilder(message.getStatus().line());
		
		// Header
		for (Map.Entry<String, String> header : message.getHeaders().entrySet()) {
			sb.append(header.getKey());
			sb.append(": ");
			sb.append(header.getValue());
			sb.append("\r\n");
		}
		
		// Content
		HttpEntity entity = message.getEnity();
		if (entity != null) {
			// String boundary = EntityUtils.generateBoundary();
			byte[] bytes = entity.getContent().bytes();
			StringBuilder contentBuilder = new StringBuilder();
			// contentBuilder.append("--");
			// contentBuilder.append(boundary);
			// contentBuilder.append("\r\n");
			
			// contentBuilder.append("Content-Disposition: form-data;"); // name=\"E007B101TaHyl7A8TKKyvDjkMyRrIQ\"; filename=\"E007B101TaHyl7A8TKKyvDjkMyRrIQ.xml\"
			// contentBuilder.append("\r\n");
			// contentBuilder.append("Content-Type: text/xml; charset=UTF-8");
			// contentBuilder.append("\r\n");
			// contentBuilder.append("Content-Transfer-Encoding: binary");
			// contentBuilder.append("\r\n\r\n");
			
			contentBuilder.append(new String(bytes, StandardCharsets.UTF_8));
			contentBuilder.append("\r\n");
			// contentBuilder.append("--");
			// contentBuilder.append(boundary);
			// contentBuilder.append("--\r\n");
			
			String content = contentBuilder.toString();
			// int length = content.getBytes(StandardCharsets.UTF_8).length;
			
			// sb.append("Content-Length: ");
			// sb.append(length);
			// sb.append("\r\n");
			
			// sb.append("Content-Type: multipart/form-data; boundary=");
			// sb.append(boundary);
			// sb.append("\r\n");
			
			sb.append("\r\n");
			sb.append(content);
		}
		sb.append("\r\n");
		
		if (logger.isDebugEnabled()) {
			logger.debug("响应回执信息 - [\r\n{}]", sb.toString());
		}
		
		// Write
		IoBuffer buf = IoBuffer.allocate(sb.length()).setAutoExpand(true);
		buf.putString(sb.toString(), ENCODER_UTF_8);
		buf.flip();
		out.write(buf);
	}
}
