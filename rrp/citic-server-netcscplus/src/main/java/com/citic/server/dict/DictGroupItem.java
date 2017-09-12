package com.citic.server.dict;

import java.io.Serializable;

/**
 * 数组字典码表
 * 
 * @author Liu Xuanfei
 * @date 2016年9月18日 上午10:17:30
 */
public class DictGroupItem implements Serializable {
	private static final long serialVersionUID = 61669923442821153L;
	
	/** 转码表别名 */
	private String artifactId;
	
	/** 来源码值 */
	private String srcCode;
	
	/** 目标码值 */
	private String destCode;
	
	/** 是否默认值 */
	private String flag;
	
	public String getArtifactId() {
		return artifactId;
	}
	
	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}
	
	public String getSrcCode() {
		return srcCode;
	}
	
	public void setSrcCode(String srcCode) {
		this.srcCode = srcCode;
	}
	
	public String getDestCode() {
		return destCode;
	}
	
	public void setDestCode(String destCode) {
		this.destCode = destCode;
	}
	
	public String getFlag() {
		return flag;
	}
	
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append("ArtifactId: ").append(artifactId).append(", SrcCode: ").append(srcCode).append(", DestCode: ").append(destCode).append(", Flag: ").append(flag).append(".").toString();
	}
}
