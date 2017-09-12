package com.citic.server.runtime.conf;

import java.util.List;

import lombok.Data;

/**
 * @author Liu Xuanfei
 * @date 2017年6月13日 上午10:10:13
 */
@Data
public final class TaskDef {
	
	private String id;
	private String alias;
	private String name;
	
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
	public String toString() {
		return "[id=" + id + ", alias=" + alias + ", name=" + name + ", outerClass=" + outerClass + ", innerClass=" + innerClass + "]";
	}
}
