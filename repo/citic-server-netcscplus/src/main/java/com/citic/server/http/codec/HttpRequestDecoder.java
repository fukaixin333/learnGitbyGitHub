package com.citic.server.http.codec;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoderAdapter;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

import com.citic.server.http.HttpConstants;
import com.citic.server.http.api.HttpEntity;
import com.citic.server.http.api.HttpMessageContext;
import com.citic.server.http.api.HttpMethod;
import com.citic.server.http.api.HttpRequestMessage;
import com.citic.server.http.api.HttpVersion;
import com.citic.server.http.buf.ByteArrayBuffer;
import com.citic.server.http.buf.MessageBytes;
import com.citic.server.http.buf.MimeHeaders;
import com.citic.server.http.util.ContentType;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.ServerEnvironment;

public class HttpRequestDecoder extends MessageDecoderAdapter {
	
	private final AttributeKey CONTEXT = new AttributeKey(getClass(), "context");
	
	@Override
	public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
		HttpMessageContext context = getContext(session);
		
		// Validate Session
		Integer key = (Integer) session.getAttribute(HttpConstants.HTTP_KEY);
		if (key != null && key.intValue() == 1) {
			return MessageDecoderResult.NOT_OK;
		}
		
		// Reading Request-Line
		if (!parseRequestLine(context, in)) {
			return MessageDecoderResult.NEED_DATA;
		}
		
		// Prepare Request-Line
		if (!prepareRequestLine(context)) {
			return MessageDecoderResult.NOT_OK;
		}
		
		// Reading Request-Header(s)
		if (!parseRequestHeaders(context, in)) {
			return MessageDecoderResult.NEED_DATA;
		}
		
		// Check and prepare
		if (!prepareRequestHeaders(context)) {
			clearSession(session);
			return MessageDecoderResult.NOT_OK;
		}
		
		// Mark position
		context.mark(in.position()); // 标记decode解析起始位置
		return MessageDecoderResult.OK;
	}
	
	@Override
	public MessageDecoderResult decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		HttpMessageContext ctx = getContext(session);
		HttpRequestMessage request = ctx.getRequestMessage();
		
		if (!ctx.capacity()) {
			ctx.setLimit(request.getContentLength());
			in.position(ctx.getMarkValue()); // 
		}
		
		if (in.hasRemaining()) {
			IoBuffer buf = ctx.put(in);
			if (ctx.position() >= ctx.limit()) {
				buf.flip(); // important
				if (parseRequestBody(request, buf)) {
					out.write(request);
					clearSession(session);
					return MessageDecoderResult.OK;
				}
				
				return MessageDecoderResult.NOT_OK;
			}
		}
		
		return MessageDecoderResult.NEED_DATA;
	}
	
	protected HttpMessageContext getContext(IoSession session) {
		HttpMessageContext context = (HttpMessageContext) session.getAttribute(CONTEXT);
		if (context == null) {
			context = new HttpMessageContext();
			session.setAttribute(CONTEXT, context);
		}
		return context;
	}
	
	protected void clearSession(IoSession session) {
		getContext(session).recycle();
		session.removeAttribute(CONTEXT);
	}
	
	// ==========================================================================================
	//                     Processing
	
	private enum RequestLineParsePosition {
		SKIPLINE, METHOD, URI, VERSION, EOL
	}
	
	protected boolean parseRequestLine(HttpMessageContext context, IoBuffer in) {
		HttpRequestMessage request = context.getRequestMessage();
		
		ByteArrayBuffer buf = new ByteArrayBuffer(64);
		RequestLineParsePosition parsePos = RequestLineParsePosition.SKIPLINE;
		
		byte chr = 0;
		boolean whitespace = false;
		int queryPos = -1;
		while (in.hasRemaining() && parsePos != RequestLineParsePosition.EOL) {
			chr = in.get();
			
			// Skip empty line(s). 
			// RFC2616: 出于健壮性考虑，服务器应该忽略 Request-Line 预期位置的任何空白行。
			if (parsePos == RequestLineParsePosition.SKIPLINE) {
				if ((chr == HttpConstants.CR) || (chr == HttpConstants.LF) || (chr == HttpConstants.SP) || (chr == HttpConstants.HT)) {
					continue;
				} else {
					parsePos = RequestLineParsePosition.METHOD;
					whitespace = false;
				}
			}
			
			// Ignore SP|HT
			if (whitespace) {
				if ((chr == HttpConstants.SP) || (chr == HttpConstants.HT)) {
					continue;
				} else {
					whitespace = false;
				}
			}
			
			// Reading Method (US-ASCII)
			if (parsePos == RequestLineParsePosition.METHOD) {
				if ((chr == HttpConstants.SP) || (chr == HttpConstants.HT)) {
					request.method().setBytes(buf.toByteArray());
					parsePos = RequestLineParsePosition.URI;
					whitespace = true;
					buf.clear();
				} else {
					buf.put(chr);
				}
			}
			
			// Reading Request-URI
			else if (parsePos == RequestLineParsePosition.URI) {
				if ((chr == HttpConstants.SP) || (chr == HttpConstants.HT) || (chr == HttpConstants.CR) || (chr == HttpConstants.LF)) {
					final byte[] uri = buf.toByteArray();
					if (queryPos == -1) {
						request.requestURI().setBytes(uri, 0, uri.length);
					} else {
						request.requestURI().setBytes(uri, 0, queryPos);
						request.queryString().setBytes(uri, queryPos, uri.length - queryPos);
					}
					if ((chr == HttpConstants.SP) || (chr == HttpConstants.HT)) {
						parsePos = RequestLineParsePosition.VERSION;
						whitespace = true;
					} else { // HTTP/0.9
						parsePos = RequestLineParsePosition.EOL;
					}
					buf.clear();
				} else {
					if (chr == HttpConstants.QUESTION && queryPos == -1) {
						queryPos = buf.length();
					} else {
						buf.put(chr);
					}
				}
			}
			
			// Reading HTTP-Version (US-ASCII)
			else if (parsePos == RequestLineParsePosition.VERSION) {
				if (chr == HttpConstants.CR) {
					// skip
				} else if (chr == HttpConstants.LF) {
					request.protocol().setBytes(buf.toByteArray());
					parsePos = RequestLineParsePosition.EOL;
					buf.clear();
				} else {
					buf.put(chr);
				}
			}
		}
		
		return (parsePos == RequestLineParsePosition.EOL);
	}
	
	protected boolean prepareRequestLine(HttpMessageContext context) {
		HttpRequestMessage request = context.getRequestMessage();
		if (!HttpMethod.valueOf(request.method())) {
			return false;
		}
		
		if (!HttpVersion.valueOf(request.protocol())) {
			return false;
		}
		
//		if (!request.requestURI().equalsVal(ServerEnvironment.getStringValue(Keys.HTTP_SERVER_LISTEN_URI))) {
//			return false;
//		}
		
		return true;
	}
	
	private enum HeaderParsePosition {
		BLK_LINE, NAME, VAL_LWS, VALUE, VAL_MTL, END
	}
	
	protected boolean parseRequestHeaders(HttpMessageContext context, IoBuffer in) {
		HttpRequestMessage request = context.getRequestMessage();
		MimeHeaders headers = request.getMimeHeaders();
		
		ByteArrayBuffer buf = new ByteArrayBuffer(128);
		HeaderParsePosition parsePos = HeaderParsePosition.BLK_LINE;
		
		byte chr = 0;
		MessageBytes valueMB = null;
		while (in.hasRemaining() && parsePos != HeaderParsePosition.END) {
			in.mark(); // mark position
			chr = in.get();
			
			// Check for blank line
			if (parsePos == HeaderParsePosition.BLK_LINE) {
				if (chr == HttpConstants.CR) { // skip
					continue;
				} else if (chr == HttpConstants.LF) {
					parsePos = HeaderParsePosition.END;
					break;
				} else {
					parsePos = HeaderParsePosition.NAME;
				}
			}
			
			// Reading the header name (US-ASCII)
			if (parsePos == HeaderParsePosition.NAME) {
				if (chr == HttpConstants.COLON) {
					valueMB = headers.addHeader(buf.toByteArray());
					parsePos = HeaderParsePosition.VAL_LWS;
					buf.clear();
				} else if (!HttpConstants.HTTP_TOKEN_CHAR[chr]) {
					// Non-token header: 如果是非法字符，忽略该报文头并跳过该行。
					skipLine(in);
					parsePos = HeaderParsePosition.BLK_LINE;
					buf.clear();
				} else {
					// Converts to lower case
					if ((chr >= HttpConstants.A) && (chr <= HttpConstants.Z)) {
						chr = (byte) (chr - HttpConstants.LC_OFFSET);
					}
					buf.put(chr);
				}
				continue;
			}
			
			// Reading the header value (which can be spanned over multiple lines)
			if (parsePos == HeaderParsePosition.VAL_LWS) {
				// Reading and skipping SP|HT
				if ((chr == HttpConstants.SP) || (chr == HttpConstants.HT)) {
					continue;
				}
				parsePos = HeaderParsePosition.VALUE;
			}
			
			if (parsePos == HeaderParsePosition.VALUE) {
				// Reading bytes until the end of the line
				if (chr == HttpConstants.CR) {
					// skip
				} else if (chr == HttpConstants.LF) {
					parsePos = HeaderParsePosition.VAL_MTL;
				} else {
					buf.put(chr);
				}
			} else if (parsePos == HeaderParsePosition.VAL_MTL) {
				// Checking the first character of the new line.
				// If the character is a linear white space (LWS), then it's a multiline header.
				if ((chr == HttpConstants.SP) || (chr == HttpConstants.HT)) {
					parsePos = HeaderParsePosition.VAL_LWS;
				} else {
					in.reset(); // reset position
					valueMB.setBytes(buf.toByteArray());
					parsePos = HeaderParsePosition.BLK_LINE;
					buf.clear();
				}
			}
		}
		
		return (parsePos == HeaderParsePosition.END);
	}
	
	protected boolean prepareRequestHeaders(HttpMessageContext context) {
		// 参考 org.apache.coyote.http11.AbstractHttp11Processor.prepareRequest()
		// 1、Apache通过MessageBytes存储（仅支持几种简单数据类型）数据，如content-length采用长整型（long）。
		// 2、对keep-alive、user-agent、host等报文头的校验。
		HttpRequestMessage request = context.getRequestMessage();
		
		// Content-Length
		// PS: Apache对Content-Length的处理比较复杂，因为历史原因，兼容了HTTP规范的低版本。咱们这里就不去管那些“已经过去的”……
		int contentLength = request.getContentLength();
		if (contentLength == -1) {
			return false;
		}
		
		// Content-Type 
		String contentType = request.getContentType();
		if (contentType == null || contentType.length() <= 0) {
			return false;
		}
		
		// boundary
		// PS: Apache的处理方式过于复杂，咱们这里就简单粗暴一些。
		//     论坛也有人提到boundary并不是Content-Type的最后一个属性，比如后面还有charset属性。
		//     但是，Apache默认也是把boundary最后最后一个属性进行处理的。既然实际报文结构与客户端的逻辑有关系，所以这里应该根据HTTP规范使用";"进行分割取值。
		//     详见 org.apache.tomcat.util.http.fileupload.FileUploadBase.getBoundary(String contentType)
		// PPS: 既然报文头里面也可能执行charset，所以boundary的值，应该采用MessageBytes。甚至其它所有报文头的key和value都应该使用MessageBytes。
		String boundary = ContentType.getBoundaryFromContentType(contentType);
		if (boundary == null || boundary.length() == 0) {
			return false;
		}
		boundary = "--" + boundary;
		request.setAttribute("$BOUNDARY", boundary.getBytes(Charset.forName("ISO-8859-1")));
		
		return true;
	}
	
	private boolean parseRequestBody(HttpRequestMessage request, IoBuffer in) {
		byte[] boundary = (byte[]) request.getAttribute("$BOUNDARY");
		return parseNextEntityPart(request, in.mark(), boundary, false);
	}
	
	/**
	 * @param request
	 * @param in
	 * @param boundary
	 * @param ignore 是否忽略<tt>CRLF</tt>
	 * @return
	 */
	private boolean parseNextEntityPart(HttpRequestMessage request, IoBuffer in, byte[] boundary, boolean ignore) {
		int endpos = in.position();
		
		byte chr;
		boolean eol = false;
		int i = 0;
		while (i < boundary.length) {
			if (in.hasRemaining()) {
				if (ignore && !eol) {
					chr = in.get();
					if (chr == HttpConstants.CR) { // skip
						continue;
					} else if (chr == HttpConstants.LF) {
						eol = true;
					} else {
						endpos = in.position();
					}
				} else { // (!ignore || eol)
					chr = in.get();
					if (chr == boundary[i]) {
						i++;
					} else {
						i = 0;
						eol = false;
						endpos = in.position();
					}
				}
			} else {
				return false;
			}
		}
		
		boolean eop = false;
		byte[] crlf = new byte[2];
		if (in.remaining() >= 2) {
			in.get(crlf);
			if (crlf[0] == HttpConstants.DASH && crlf[1] == HttpConstants.DASH) { // 结束符
				eop = true;
			}
		} else {
			return false;
		}
		
		if (eop) {
			if (in.remaining() >= 2) {
				in.get(crlf);
			} else {
				return false;
			}
		}
		
		if (crlf[0] == HttpConstants.CR && crlf[1] == HttpConstants.LF) {
			if (ignore) {
				int lastpos = in.position();
				byte[] range = new byte[endpos - in.markValue()];
				in.reset().get(range).position(lastpos);
				// ...
				parseEntityPart(request, range);
			} else {
				ignore = true;
			}
			
			if (eop) {
				return true;
			}
			
			in.mark();
		}
		
		return parseNextEntityPart(request, in, boundary, ignore);
	}
	
	private void parseEntityPart(HttpRequestMessage request, byte[] range) {
		HttpEntity part = request.addEntityPart();
		// Entity Headers
		int cursor = parseEntityHeaders(part.getHeaders(), range, 0);
		// Entity Content
		part.getContent().setBytes(range, cursor, range.length - cursor);
	}
	
	private int parseEntityHeaders(MimeHeaders headers, byte[] range, int pos) {
		// Check for blank line
		while (pos < range.length) {
			if (range[pos] == HttpConstants.CR) { // skip
				pos++;
			} else if (range[pos] == HttpConstants.LF) {
				pos++;
				return pos;
			} else {
				break;
			}
		}
		
		int start = pos;
		int realPos = pos;
		int lastSignificantChar = pos;
		MessageBytes valueMB = null;
		// Reading the header name (Header name is always US-ASCII)
		while (pos < range.length) {
			if (range[pos] == HttpConstants.COLON) {
				valueMB = headers.addHeader(range, start, pos - start);
				pos++;
				start = pos;
				realPos = pos;
				lastSignificantChar = pos;
				break;
			} else if (!HttpConstants.HTTP_TOKEN_CHAR[range[pos]]) {
				// If a non-token header is detected, skip the line and ignore the header
				pos = skipLine(range, pos);
				start = pos;
			} else {
				// Converts to lower case
				if ((range[pos] >= HttpConstants.A) && (range[pos] <= HttpConstants.Z)) {
					range[pos] = (byte) (range[pos] - HttpConstants.LC_OFFSET);
				}
				pos++;
			}
		}
		
		// Reading the header value
		boolean multipleLines = true;
		while (multipleLines && pos < range.length) {
			// Skipping spaces
			while (pos < range.length) {
				if (range[pos] == HttpConstants.SP || range[pos] == HttpConstants.HT) {
					pos++;
				} else {
					break;
				}
			}
			
			// Reading bytes until the end of the line
			boolean eol = false;
			while (!eol && pos < range.length) {
				if (range[pos] == HttpConstants.CR) {
					// skip
				} else if (range[pos] == HttpConstants.LF) {
					eol = true;
				} else if (range[pos] == HttpConstants.SP || range[pos] == HttpConstants.HT) {
					range[realPos] = range[pos];
					realPos++;
				} else {
					range[realPos] = range[pos];
					realPos++;
					lastSignificantChar = realPos;
				}
				pos++;
			}
			
			// Ignore whitespaces at the end of the line
			realPos = lastSignificantChar;
			
			// Checking the first character of the new line. 
			// If the character is a LWS, then it's a multiline header
			if (pos < range.length) {
				if ((range[pos] != HttpConstants.SP) && (range[pos] != HttpConstants.HT)) {
					multipleLines = false;
				} else {
					// Copying one extra space in the buffer (since there must
					// be at least one space inserted between the lines)
					range[realPos] = range[pos];
					realPos++;
				}
			}
		}
		
		valueMB.setBytes(range, start, lastSignificantChar - start);
		
		return parseEntityHeaders(headers, range, pos);
	}
	
	// ==========================================================================================
	//                     Help Behavior
	// ==========================================================================================
	
	private void skipLine(IoBuffer in) {
		byte chr;
		while (in.hasRemaining()) {
			chr = in.get();
			if (chr == HttpConstants.CR) {
				// skip
			} else if (chr == HttpConstants.LF) {
				break;
			}
		}
	}
	
	private int skipLine(byte[] range, int i) {
		boolean eol = false;
		for (; !eol; i++) {
			if (range[i] == HttpConstants.CR) {
				// skip
			} else if (range[i] == HttpConstants.LF) {
				eol = true;
			}
		}
		return i;
	}
}
