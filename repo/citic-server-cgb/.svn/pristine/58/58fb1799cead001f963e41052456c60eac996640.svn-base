package com.citic.server.gdjg.domain.request;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
/**
 * 查询类--被调查人
 * @author wangbo
 * @date 2017年5月19日 下午2:59:12
 */
@Data
public class Gdjg_Request_RespondentCx implements Serializable {
	private static final long serialVersionUID = -7704907641630169902L;
	
	/** 唯一标识 */
	private String uniqueid;
	
	/** 查询结束时间 */
	private String queryendtime;
	/** 查询反馈结果 */
	private String queryresult;
	/** 查询反馈结果原因 */
	private String reason;
	/** 主机查询时间 */
	private String querytime;
	
	/** 开户行 */
	private List<Gdjg_Request_BankCx> banks;
	
}
