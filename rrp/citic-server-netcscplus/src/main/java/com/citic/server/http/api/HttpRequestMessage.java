package com.citic.server.http.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.citic.server.http.buf.MessageBytes;
import com.citic.server.http.buf.MimeHeaders;

/**
 * An HTTP request
 * 
 * @author Liu Xuanfei
 * @date 2016年7月18日 下午4:10:30
 */
public class HttpRequestMessage {
	
	private MessageBytes method = new MessageBytes();
	private MessageBytes protocol = new MessageBytes();
	private MessageBytes uri = new MessageBytes();
	private MessageBytes query = new MessageBytes();
	
	private MimeHeaders headers = new MimeHeaders();
	private long contentLength = -1;
	private MessageBytes contentType = null;
	
	/**  */
	private Map<String, List<String>> parameters = new HashMap<String, List<String>>();
	
	/**  */
	private Map<String, Object> attributes = new HashMap<String, Object>();
	
	/**  */
	private List<HttpEntity> parts = new ArrayList<HttpEntity>();
	
	public MessageBytes method() {
		return method;
	}
	
	public MessageBytes requestURI() {
		return uri;
	}
	
	public MessageBytes queryString() {
		return query;
	}
	
	public MessageBytes protocol() {
		return protocol;
	}
	
	public MessageBytes contentType() {
		if (contentType == null) {
			contentType = headers.getHeader("content-type");
		}
		return contentType;
	}
	
	public String getContentType() {
		contentType();
		if (contentType == null || contentType.isNull()) {
			return null;
		}
		return contentType.toString();
	}
	
	public boolean isKeepAlive() {
		return false;
	}
	
	public String getHeader(String name) {
		return headers.getHeaderValue(name);
	}
	
	public MimeHeaders getMimeHeaders() {
		return headers;
	}
	
	public Object setAttribute(String key, Object obj) {
		return (key == null || key.length() <= 0) ? null : attributes.put(key, obj);
	}
	
	public Object getAttribute(String key) {
		return (key == null || key.length() <= 0) ? null : attributes.get(key);
	}
	
	public HttpEntity addEntityPart() {
		HttpEntity part = new HttpEntity();
		parts.add(part);
		return part;
	}
	
	public List<HttpEntity> getEntityParts() {
		return parts;
	}
	
	public int getContentLength() {
		long length = getContentLengthLong();
		if (length < Integer.MAX_VALUE) {
			return (int) length;
		}
		return -1;
	}
	
	public long getContentLengthLong() {
		if (contentLength > -1) {
			return contentLength;
		}
		MessageBytes mb = headers.getHeader("content-length");
		contentLength = (mb == null || mb.isNull()) ? -1 : mb.getLong();
		return contentLength;
	}
	
	/**
	 * Determines whether this request contains at least one parameter with the specified name
	 * 
	 * @param name The parameter name
	 * @return <tt>true</tt> if this request contains at least one parameter with the specified name
	 */
	public boolean containsParameter(String name) {
		return getParameters().containsKey(name);
	}
	
	/**
	 * Returns the value of a request parameter as a String, or null if the parameter does not
	 * exist.
	 * If the request contained multiple parameters with the same name, this method returns the
	 * first parameter
	 * encountered in the request with the specified name
	 * 
	 * @param name The parameter name
	 * @return The value
	 */
	public String getParameter(String name) {
		List<String> vals = getParameters().get(name);
		return vals.size() > 0 ? vals.get(0) : null;
	}
	
	public String[] getMultiParameter(String name) {
		List<String> vals = getParameters().get(name);
		return vals.size() > 0 ? vals.toArray(new String[0]) : null;
	}
	
	/**
	 * @return a read only {@link Map} of query parameters whose key is a {@link String} and whose
	 *         value is a {@link List} of {@link String}s.
	 */
	public Map<String, List<String>> getParameters() {
		if (parameters.size() == 0) {
			String[] params = query.toString().split("&");
			if (params.length == 1) {
				return parameters;
			}
			for (int i = 0; i < params.length; i++) {
				String[] param = params[i].split("=");
				String name = param[0];
				String value = param.length == 2 ? param[1] : "";
				if (!parameters.containsKey(name)) {
					parameters.put(name, new ArrayList<String>());
				}
				parameters.get(name).add(value);
			}
		}
		return parameters;
	}
	
	public void recycle() {
		this.method.recycle();
		this.protocol.recycle();
		this.query.recycle();
		// this.uri.recycle();
	}

	@Override
	public String toString() {
		return "HttpRequestMessage [method=" + method + ", protocol=" + protocol + ", uri=" + uri + ", query=" + query + ", headers=" + headers + ", contentLength=" + contentLength + ", contentType="
				+ contentType + ", parameters=" + parameters + ", attributes=" + attributes + ", parts=" + parts + "]";
	}
	
	
}
