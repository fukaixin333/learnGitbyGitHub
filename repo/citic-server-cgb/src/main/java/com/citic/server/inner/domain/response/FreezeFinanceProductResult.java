package com.citic.server.inner.domain.response;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.citic.server.inner.domain.ResponseMessage;

@Data
@EqualsAndHashCode(callSuper = false)
public class FreezeFinanceProductResult extends FinanceResponse{

	private static final long serialVersionUID = 3767194474392894198L;
	
	private String serialNo; // 系统流水号
	private String account; // 客户标识
	private String clientName;// 客户名称
	private String tACode; // TA代码
	private String prdCode; // 产品代码
	private String prdName; // 产品名称
	private String frozenCause; //冻结原因
	private String lawNo; //法律文书号
	private String orgName; // 执行机构名称
	private String endDate; // 冻结截止日期
	private String status; // 交易状态
	private String tAName; // TA名称
	private String statusName; // 交易状态名称
	private String cashFlag; // 钞汇标志
	private String clientNo; // 客户编号
	private String realFrozenVol; // 实际冻结份额
	
	
}
