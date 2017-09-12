package com.citic.server.gdjg.domain;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Br57_cxqq_mx_policeman implements Serializable {
	private static final long serialVersionUID = 5775192257198582155L;
	/** 协作编号 */
	private String docno = "";
	/** 案件编号 */
	private String caseno = "";
	
	/** 办案人姓名 */
	private String exename = "";
	/** 警察证号 */
	private String policecert = "";
	/** 联系电话 */
	private String policetel = "";
	
	/** 查询日期（分区） */
	private String qrydt = "";
}
