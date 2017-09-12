package com.citic.server.runtime;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * 包含固定长度前缀的字符串解码器。前缀为实际报文的长度（不包含前缀本身）。
 * 
 * @author Liu Xuanfei
 * @date 2016年3月11日 上午11:42:04
 */
public class PrefixedStringDecoder extends CumulativeProtocolDecoder {
	
	private final AttributeKey CONTEXT = new AttributeKey(getClass(), "context");
	
	private final Charset charset;
	private final int prefixLength;
	
	public PrefixedStringDecoder(Charset charset, int prefixLength) {
		this.charset = charset;
		this.prefixLength = prefixLength;
	}
	
	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		Context ctx = getContext(session);
		if (in.remaining() > prefixLength || ctx.isRemaining()) { // important
			int length = ctx.getLength();
			if (!ctx.isRemaining()) {
				String prefix = in.getString(prefixLength, charset.newDecoder());
				length = Integer.parseInt(prefix);
				ctx.setLength(length);
			}
			
			if (in.hasRemaining()) {
				IoBuffer innerBuffer = ctx.put(in);
				if (ctx.getMatchLength() >= length) {
					byte[] bytes = new byte[length];
					innerBuffer.flip(); // important
					innerBuffer.get(bytes);
					out.write(new String(bytes, 0, length, charset));
					ctx.reset(); //
					return true;
				}
			}
		}
		
		return false;
	}
	
	private Context getContext(IoSession session) {
		Context context = (Context) session.getAttribute(CONTEXT);
		if (context == null) {
			context = new Context();
			session.setAttribute(CONTEXT, context);
		}
		return context;
	}
	
	private class Context {
		private final IoBuffer innerBuffer;
		
		private boolean remaining = false;
		private int length = 0;
		private int matchLength = 0;
		
		public Context() {
			innerBuffer = IoBuffer.allocate(1024).setAutoExpand(true);
		}
		
		public boolean isRemaining() {
			return remaining;
		}
		
		public IoBuffer put(IoBuffer buf) {
			matchLength += buf.remaining(); //
			return innerBuffer.put(buf);
		}
		
		public void setLength(int length) {
			this.length = length;
			this.remaining = true; // 
		}
		
		public int getLength() {
			return length;
		}
		
		public int getMatchLength() {
			return matchLength;
		}
		
		public void reset() {
			this.innerBuffer.clear();
			this.remaining = false;
			this.length = 0;
			this.matchLength = 0;
		}
	}
}
