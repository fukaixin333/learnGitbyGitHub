package com.citic.server.gf.domain.request;

import java.io.Serializable;

import lombok.Data;

/**
 * 在先冻结信息
 * 
 * @author Liu Xuanfei
 * @date 2016年3月8日 下午2:05:28
 */
@Data
public class ControlRequest_Djxx implements Serializable {
	private static final long serialVersionUID = -1511551063822359144L;
	
	/** 措施序号 */
	private String csxh = "";
	
	/** 在先冻结到期日期 */
	private String djjzrq = "";
	
	/** 金融产品编号 */
	private String jrcpbh = "";
	
	/** 在先冻结机关 */
	private String djjg = "";
	
	/** 在先冻结文号 */
	private String djwh = "";
	
	/** 在先冻结金额 */
	private String djje = "";
	
	/** 备注 */
	private String beiz = "";
	
	// ==========================================================================================
	//                     Help Field
	// ==========================================================================================
	
	/** 控制请求单号 */
	private String bdhm = "";
	
	/** 控制序号 */
	private String ccxh = "";
	
}
