package com.citic.server.dict;

import java.io.Serializable;

/**
 * 数据字典组
 * 
 * @author Liu Xuanfei
 * @date 2016年9月18日 上午10:17:25
 */
public class DictGroup implements Serializable {
	private static final long serialVersionUID = -3705891923020789897L;
	
	/** 数据字典组名 */
	private String groupId;
	
	/** 对应字段名 */
	private String fieldName;
	
	/** 对应转码表别名 */
	private String artifactId;
	
	public String getGroupId() {
		return groupId;
	}
	
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public String getArtifactId() {
		return artifactId;
	}
	
	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}
}
