package com.citic.server.gf.domain.request;

import java.io.Serializable;

import lombok.Data;

/**
 * 回退请求信息
 * 
 * @author Liu Xuanfei
 * @date 2016年3月8日 下午1:44:08
 */
@Data
public class RollbackRequest_Htxx implements Serializable {
	private static final long serialVersionUID = -1595807768805165008L;
	/** 查询请求单号 */
	private String bdhm = "";
	/** 回退原因 */
	private String xchtyy = "";
	/** 回退原因备注 */
	private String xchtbz = "";
	/** 回退联系人 */
	private String xchtr = "";
	/** 回退联系人电话 */
	private String xchtdh = "";
	
	private String orgkey = "";
	
	public RollbackRequest_Htxx() {
	}
	
	public RollbackRequest_Htxx(String _bhdm, String _xchtyy, String _xchtbz) {
		this.bdhm = _bhdm;
		this.xchtyy = _xchtyy;
		this.xchtbz = _xchtbz;
	}
}
