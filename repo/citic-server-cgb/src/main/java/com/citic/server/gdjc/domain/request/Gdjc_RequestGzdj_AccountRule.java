package com.citic.server.gdjc.domain.request;

import java.io.Serializable;

import lombok.Data;

/**
 * 银行账号规则登记-账号规则
 * 
 * @author Liu Xuanfei
 * @date 2016年8月17日 上午10:06:03
 */
@Data
public class Gdjc_RequestGzdj_AccountRule implements Serializable {
	private static final long serialVersionUID = -3726931243539505432L;
	
	/** 唯一标识 */
	private String uniqueid;
	
	/** 账号类型（如借记卡、贷记卡等） */
	private String accounttype;
	
	/** 账号长度 */
	private String accountlength;
	
	/** 账号前缀（用于识别账号、卡号归属银行的前N位数） */
	private String accountpre;
	
	/** 城市代号起始位 */
	private String startbit;
	
	/** 城市代号终止位 */
	private String endbit;
	
	/** 备注 */
	private String remark;
}
