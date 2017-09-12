package com.citic.server.gdjg.domain.response;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.citic.server.gdjg.domain.Gdjg_Response;

/**
 * 查询类--案件信息
 * 
 * @author wangbo
 * @date 2017年5月23日 下午9:06:43
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Gdjg_Response_CaseCx extends Gdjg_Response implements Serializable {
	private static final long serialVersionUID = 3966270416677294267L;
	
	
	/** 案件编号 */
	private String caseno;
	/** 案件类型 */
	private String casetype;
	/** 案件名称 */
	private String casename;
	/** 侦办单位名称 */
	
	private String exeunit;
	/** 申请机构代码 */
	private String applyorg;
	/** 目标机构代码 */
	private String targetorg;
	/** 备注 */
	private String remark;
	/** 发送时间 */
	private String sendtime;
	
	/** 办案人 */
	private List<Gdjg_Response_PolicemanCx> policemans;
	
	/** 账户 */
	private List<Gdjg_Response_AccCx> accs;
	
	/** 被调查人 */
	private List<Gdjg_Response_RespondentCx> respondents;
	
	
}
