package com.citic.server.whpsb.domain.request;

import java.io.Serializable;

import com.citic.server.whpsb.domain.Whpsb_SadxBody;

/**
 * 银行提供开户资料查询反馈-涉案对象
 * 
 * @author Liu Xuanfei
 * @date 2016年8月18日 下午4:56:34
 */
public class Whpsb_RequestKhzl_Sadx implements Serializable {
	private static final long serialVersionUID = 6307822019649110612L;
	
	private Whpsb_SadxBody sadxBody;
	private Whpsb_RequestKhzl_Detail detail;
	
	public Whpsb_SadxBody getSadxBody() {
		return sadxBody;
	}
	
	public void setSadxBody(Whpsb_SadxBody sadxBody) {
		this.sadxBody = sadxBody;
	}
	
	public Whpsb_RequestKhzl_Detail getDetail() {
		return detail;
	}
	
	public void setDetail(Whpsb_RequestKhzl_Detail detail) {
		this.detail = detail;
	}
}
