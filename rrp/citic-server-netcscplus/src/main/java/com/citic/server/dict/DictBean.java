package com.citic.server.dict;

import java.io.Serializable;

/**
 * 实现此接口的 JavaBean 可以使用 {@link DictCoder} 进行码值转换操作。
 * 
 * @author Liu Xuanfei
 * @date 2016年9月17日 下午9:18:39
 */
public interface DictBean extends Serializable {
	
	/**
	 * 指定 JavaBean 码值转换时使用的数据字典组名
	 * 
	 * @return 数据字典组名
	 */
	public String getGroupId();
}
