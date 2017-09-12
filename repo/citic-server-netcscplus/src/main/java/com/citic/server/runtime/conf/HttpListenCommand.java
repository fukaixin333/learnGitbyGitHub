package com.citic.server.runtime.conf;

import java.util.List;

import lombok.Data;

/**
 * @author Liu Xuanfei
 * @date 2017年6月14日 下午8:14:01
 */
@Data
public final class HttpListenCommand implements ListenCommand {
	
	private String id;
	private String alias;
	private String name;
	private String uri;
	
	private String outerClass;
	private String innerClass;
	
	private List<Property> properties;
	
	public void clear() {
		if (properties != null) {
			properties.clear();
		}
		properties = null;
	}
	
	@Override
	public HttpListenCommand clone() {
		clear();
		try {
			return (HttpListenCommand) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
