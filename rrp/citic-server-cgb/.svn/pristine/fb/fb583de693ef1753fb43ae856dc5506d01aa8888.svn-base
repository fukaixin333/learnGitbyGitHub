package com.citic.server.jsga.domain.request;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 动态查询反馈查询结果报文
 * <ul>
 * <li>“江西省公安”和“深圳分行公安厅”报文包含<em>[银行主机数据时间]</em>字段，其它监管不包含。
 * </ul>
 * 
 * @author Liu Xuanfei
 * @date 2016年7月6日 上午9:40:08
 */
@Data
public class JSGA_ControlRecordRequest implements Serializable {
	private static final long serialVersionUID = -5786824452123813982L;
	
	/** 请求单标识 */
	private String qqdbs;
	
	/** 查控主体类别 */
	private String ztlb;
	
	/** 申请机构代码 */
	private String sqjgdm;
	
	/** 目标机构代码 */
	private String mbjgdm;
	
	/** 查询结束时间 */
	private String cxjssj;
	
	/** 银行主机数据时间 */
	private String yhzjsjsj;
	
	/** 动态查询反馈查询结果信息 */
	private List<JSGA_ControlRequest_Record> controlRecordList;
}
