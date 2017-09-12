package com.citic.server.service.domain;

import lombok.Data;

import com.citic.server.dict.DictBean;
import com.citic.server.runtime.ServerEnvironment;

/**
 * 基本DictBean
 * <p>
 * 用于使用DictCoder转码单一字段，即没有特定的JavaBean。<br />
 * 最常见的转码数据包括：证件类型（<code>certificateType</code>）、币种（<code>currency</code>）等。
 * <p>
 * 关键点：使用监管代号（<code>TaskType</code>）选择不同的<code>GroupId</code>。
 * 
 * @author Liu Xuanfei
 * @date 2016年10月21日 下午5:41:20
 */
@Data
public class BaseDictBean implements DictBean {
	
	/** 任务类型 - 用于选择不同的<code>GroupId</code> */
	private String taskType;
	
	/** 证件类型 */
	private String certificateType;
	
	/** 币种 */
	private String currency;
	
	/** 机构编码 */
	private String organ_code;
	
	@Override
	public String getGroupId() {
		return "BASE_DICTBEAN";
	}
}
