package com.citic.server.dx.domain;

/**
 * 报文-附件
 * 
 * @author Liu Xuanfei
 * @date 2016年4月12日 下午1:21:09
 */
public class Attachment {
	/** 文件名 */
	private String filename;
	
	/** 文件内容（Base64编码，不大于1MB） */
	private byte[] content;
	
	// ==========================================================================================
	//                     Help Field
	// ==========================================================================================
	/** 文件地址 */
	private String filepath = "";
	
	/** 文件ID */
	private String fileno = "";
	
	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public byte[] getContent() {
		return content;
	}
	
	public void setContent(byte[] content) {
		this.content = content;
	}
	
	public String getFilepath() {
		return filepath;
	}
	
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
	public String getFileno() {
		return fileno;
	}
	
	public void setFileno(String fileno) {
		this.fileno = fileno;
	}
}