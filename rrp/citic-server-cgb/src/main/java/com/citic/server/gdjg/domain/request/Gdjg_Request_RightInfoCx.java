package com.citic.server.gdjg.domain.request;

import java.io.Serializable;

import lombok.Data;
/**
 * 查询类--权利信息
 * @author wangbo
 * @date 2017年5月19日 下午2:59:12
 */
@Data
public class Gdjg_Request_RightInfoCx implements Serializable {
	private static final long serialVersionUID = 2153690690832107773L;
	
	/** 权利序号 */
	private String right;
	/** 权利类型 */
	private String righttype;
	/** 权利证件类型 */
	private String rightidtype;
	/** 权利证件号码 */
	private String rightid;
	/** 权利人姓名 */
	private String rightname;
	/** 权利金额 */
	private String rightbalance;
	/** 权利人通讯地址 */
	private String rightaddr;
	/** 权利人联系方式 */
	private String righttel;
}
