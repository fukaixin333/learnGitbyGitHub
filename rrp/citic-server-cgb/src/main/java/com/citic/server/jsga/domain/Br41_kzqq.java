package com.citic.server.jsga.domain;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.citic.server.dict.DictBean;
import com.citic.server.jsga.domain.request.JSGA_FreezeRequest_Detail;
import com.citic.server.jsga.domain.request.JSGA_StopPaymentRequest_Detail;

@Data
@EqualsAndHashCode(callSuper = false)
public class Br41_kzqq implements DictBean, Serializable {
	private static final long serialVersionUID = -17108728150130782L;
	
	private String qqdbs = "";
	private String qqcslx = "";
	private String tasktype = "";
	private String sqjgdm = "";
	private String mbjgdm = "";
	private String ztlb = "";
	private String ajlx = "";
	private String jjcd = "";
	private String beiz = "";
	private String fssj = "";
	private String qqrxm = "";
	private String qqrzjlx = "";
	private String qqrzjhm = "";
	private String qqrdwmc = "";
	private String qqrsjh = "";
	private String xcrxm = "";
	private String xcrzjlx = "";
	private String xcrzjhm = "";
	private String status = "";
	private String orgkey = "";
	private String qrydt = "";
	private String hzsj = "";
	private String rwlsh = "";
	private String last_up_dt = "";
	private String recipient_time = "";
	
	List<JSGA_FreezeRequest_Detail> freezeList;
	
	List<JSGA_StopPaymentRequest_Detail> stoppayList;
	
	@Override
	public String getGroupId() {
		return "JSGA_BR41_KZQQ";
	}
}
