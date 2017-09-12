package com.citic.server.runtime;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * 包含固定长度前缀的字符串编码解码器工厂。前缀为实际报文的长度（不包含前缀本身）。
 * 
 * @see {@link PrefixedStringEncoder} 解码器
 * @see {@link PrefixedStringDecoder} 编码器
 * @author Liu Xuanfei
 * @date 2016年3月11日 上午11:45:07
 */
public class PrefixedStringCodecFactory implements ProtocolCodecFactory {
	
	private final PrefixedStringEncoder encoder;
	private final PrefixedStringDecoder decoder;
	
	public PrefixedStringCodecFactory(Charset charset, int prefixLength) {
		encoder = new PrefixedStringEncoder(charset, prefixLength);
		decoder = new PrefixedStringDecoder(charset, prefixLength);
	}
	
	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return encoder;
	}
	
	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return decoder;
	}
	
}
