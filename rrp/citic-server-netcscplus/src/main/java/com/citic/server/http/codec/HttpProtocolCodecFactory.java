package com.citic.server.http.codec;

import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;

import com.citic.server.http.api.HttpRequestMessage;

/**
 * MINA-HTTP ProtocolCodecFactory
 * 
 * @author Liu Xuanfei
 * @date 2016年7月15日 下午3:49:18
 */
public class HttpProtocolCodecFactory extends DemuxingProtocolCodecFactory {
	/**
	 * @param server 服务端/客户端
	 */
	public HttpProtocolCodecFactory(boolean server) {
		if (server) {
			this.addMessageDecoder(HttpRequestDecoder.class);
			this.addMessageEncoder(HttpResponseMessage.class, HttpResponseEncoder.class);
		} else {
			this.addMessageEncoder(HttpRequestMessage.class, HttpRequestEncoder.class);
			this.addMessageDecoder(HttpResponseDecoder.class);
		}
	}
}
