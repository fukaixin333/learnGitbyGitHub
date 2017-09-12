package com.citic.server.gdjg.domain.request;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
/**
 * 查询类--案件
 * @author wangbo
 * @date 2017年5月19日 下午2:59:12
 */
@Data
public class Gdjg_Request_CaseCx implements Serializable{
	private static final long serialVersionUID = -3920290310119587180L;

	/** 案件编号 */
	private String caseno;
	
	/** 被调查人 */
	private List<Gdjg_Request_RespondentCx> respondents;
	
	/** 账户信息 */
	private List<Gdjg_Request_AccCx> accs;
}
