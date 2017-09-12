package com.citic.server.gdjg.domain.request;

import java.io.Serializable;




import com.citic.server.dict.DictBean;

import lombok.Data;
/**
 * 查询类--冻结信息
 * @author wangbo
 * @date 2017年5月19日 下午2:59:12
 */
@Data
public class Gdjg_Request_FroInfoCx implements Serializable,DictBean{
	private static final long serialVersionUID = -9021128211992606796L;
	/** 协作编号 */
	private String docno = "";
	/** 案件编号 */
	private String caseno = "";
	/** 唯一标识 */
	private String uniqueid;
	/** 查询日期（分区） */
	private String qrydt = "";
	
	/** 措施序号 */
	private String frono;
	/** 冻结类型 */
	private String frotype;
	/** 冻结申请单位 */
	private String frounit;
	/** 冻结额度 */
	private String frobanlance;
	/** 冻结开始日期 */
	private String frostartdate;
	/** 冻结结束日期 */
	private String froenddate;
	
	//协助内部查看使用
	/** 冻结账号 */
	private String frozenaccount;
	/** 冻结机构类型 */
	private String frozeninstype; 
	/** 冻结状态 */
	private String frozenstatus;
	/** 冻结轮候次序 */
	private String waitingseq;
	
	
	
	@Override
	/** 转码使用 */
	public String getGroupId() {
		return "Gdjg_Request_FroInfoCx";
	}
	
}
