package com.citic.server.http.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoderAdapter;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

public class HttpResponseDecoder extends MessageDecoderAdapter {
	
	public HttpResponseDecoder() {
	}
	
	@Override
	public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
		return null;
	}
	
	@Override
	public MessageDecoderResult decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		return null;
	}
	
}
