package com.citic.server.dx.domain;


/**
 * 下行报文
 * 
 * @author Liu Xuanfei
 * @date 2016年4月8日 上午10:11:09
 */
public class ResponseMessage extends Message {
	private static final long serialVersionUID = 1275092884342835232L;
	
	/** MessageFrom 报文下发机构 */
	protected String messageFrom;
	
	public String getMessageFrom() {
		return messageFrom;
	}
	
	public void setMessageFrom(String messageFrom) {
		this.messageFrom = messageFrom;
	}
	
	// ==========================================================================================
	//                     Helper Field
	// ==========================================================================================
	/** 附件存储路径（多个附件以分号（';'）隔开） */
	protected String attachpaths;
	
	/** 附件文件名（多个附件以分号（';'）隔开） */
	protected String attachnames;
	
	public String getAttachpaths() {
		return attachpaths;
	}
	
	public void setAttachpaths(String attachpaths) {
		this.attachpaths = attachpaths;
	}
	
	public String getAttachnames() {
		return attachnames;
	}
	
	public void setAttachnames(String attachnames) {
		this.attachnames = attachnames;
	}
}
