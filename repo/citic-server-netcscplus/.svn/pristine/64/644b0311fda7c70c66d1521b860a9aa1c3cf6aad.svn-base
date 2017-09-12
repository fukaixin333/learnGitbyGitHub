package com.citic.server.runtime;

import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * 包含固定长度前缀的字符串编码器。前缀为实际报文的长度（不包含前缀本身）。
 * 
 * @author Liu Xuanfei
 * @date 2016年3月11日 上午11:44:20
 */
public class PrefixedStringEncoder extends ProtocolEncoderAdapter {
	
	private final Charset charset;
	private final int prefixLength;
	
	public PrefixedStringEncoder(Charset charset, int prefixLength) {
		this.charset = charset;
		this.prefixLength = prefixLength;
	}
	
	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		String document = (String) message;
		byte[] bytes = document.getBytes(charset);
		
		IoBuffer buffer = IoBuffer.allocate(bytes.length, false);
		buffer.setAutoExpand(true);
		String length = StringUtils.leftPad(String.valueOf(bytes.length), prefixLength, "0");
		buffer.putString(length, charset.newEncoder());
		buffer.put(bytes);
		
		buffer.flip(); // 
		out.write(buffer);
	}
}
