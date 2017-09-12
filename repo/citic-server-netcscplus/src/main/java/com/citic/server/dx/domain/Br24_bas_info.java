/**
 * ========================================================
 * Copyright (c) 2014-2016 by CITIC All rights reserved.
 * Created Date : 2016年4月17日
 * Description: 电信诈骗反馈基本信息表
 * =========================================================
 */
package com.citic.server.dx.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Br24_bas_info extends Br24_bas_dto {
	private static final long serialVersionUID = -7704490142558749299L;
	
	/** 默认值01 */
	private String mode;
	/** 接收机构ID（对应下行报文中的MessageFrom值） */
	private String toorg;
	/** 交易类型编码100302 */
	private String txCode;
	/** 业务申请编号（参见附录I） */
	private String applicationID;
	/** 查询结果(参见勘误修订后4位业务应答码) */
	private String result = "0000";
	/** 经办人姓名 */
	private String operatorName;
	/** 经办人电话 */
	private String operatorPhoneNumber;
	/** 反馈机构名称 */
	private String feedbackOrgName;
	/** 查询反馈说明 */
	private String feedbackRemark = "";
	/** 反馈时间 */
	private String status;
	/** 状态 */
	private String feedback_dt;
	private String orgkey="";
	private String modeid="";
	private String last_up_dt="";
	private String msgcheckresult="";
	
	public String getModeid() {
		return modeid;
	}

	public void setModeid(String modeid) {
		this.modeid = modeid;
	}
	
	public String getApplicationid() {
		return applicationID;
	}
	
	public String getOperatorname() {
		return operatorName;
	}
	
	public String getOperatorphonenumber() {
		return operatorPhoneNumber;
	}
	
	public String getFeedbackorgname() {
		return feedbackOrgName;
	}
	
	public String getFeedbackremark() {
		return feedbackRemark;
	}
	
	public String getTxcode() {
		return txCode;
	}
	
}
