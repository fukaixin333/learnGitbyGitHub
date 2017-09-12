package com.citic.server.runtime.conf;

import java.util.List;

import lombok.Data;

/**
 * @author Liu Xuanfei
 * @date 2017年6月14日 下午8:17:45
 */
@Data
public final class SocketListenCommand implements ListenCommand {
	
	private String id;
	private String alias;
	private String name;
	private int port;
	
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
	public SocketListenCommand clone() {
		clear();
		try {
			return (SocketListenCommand) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
