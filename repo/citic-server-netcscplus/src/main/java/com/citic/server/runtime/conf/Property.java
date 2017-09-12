package com.citic.server.runtime.conf;

import java.util.List;

import lombok.Data;

/**
 * @author Liu Xuanfei
 * @date 2017年6月13日 上午10:07:04
 */
@Data
public final class Property {
	
	private String name;
	private String value;
	private boolean encrypt = false;
	private String descr;
	
	private List<Option> options;
	
	public void clear() {
		if (options != null) {
			options.clear();
		}
		options = null;
	}
	
	public boolean hasOptions() {
		return (options != null) && (options.size() > 0);
	}
	
	@Override
	public String toString() {
		return "Property [name=" + name + ", value=" + value + ", encrypt=" + encrypt + ", descr=" + descr + ", options=" + options + "]";
	}
}
