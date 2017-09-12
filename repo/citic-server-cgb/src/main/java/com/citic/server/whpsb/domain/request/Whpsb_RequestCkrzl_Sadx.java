package com.citic.server.whpsb.domain.request;

import java.io.Serializable;

import com.citic.server.whpsb.domain.Whpsb_SadxBody;

/**
 * 银行提供账卡号持有人资料查询反馈-涉案对象
 * 
 * @author Liu Xuanfei
 * @date 2016年8月19日 上午10:12:46
 */
public class Whpsb_RequestCkrzl_Sadx implements Serializable {
	private static final long serialVersionUID = -542022374087227447L;
	
	private Whpsb_SadxBody sadxBody;
	private Whpsb_RequestCkrzl_Detail detail;
	
	public Whpsb_SadxBody getSadxBody() {
		return sadxBody;
	}
	
	public void setSadxBody(Whpsb_SadxBody sadxBody) {
		this.sadxBody = sadxBody;
	}
	
	public Whpsb_RequestCkrzl_Detail getDetail() {
		return detail;
	}
	
	public void setDetail(Whpsb_RequestCkrzl_Detail detail) {
		this.detail = detail;
	}
}
