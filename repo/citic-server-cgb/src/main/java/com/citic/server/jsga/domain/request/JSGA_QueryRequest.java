package com.citic.server.jsga.domain.request;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 常规查询请求反馈报文
 * <ul>
 * <li>“国安”报文包含<em>[操作失败原因]</em>字段，其它监管报文不包含。
 * <li>“江西省公安”和“深圳分行公安厅”报文包含<em>[银行主机数据时间]</em>和<em>[是否修订]</em>等字段，其它监管报文不包含。
 * </ul>
 * 
 * @author Liu Xuanfei
 * @date 2016年7月5日 下午7:59:16
 */
@Data
public class JSGA_QueryRequest implements Serializable {
	private static final long serialVersionUID = -2102163488656259622L;
	
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
	
	/** 操作失败原因 */
	private String czsbyy;
	
	/** 银行主机数据时间 */
	private String yhzjsjsj;
	
	/** 是否修订 */
	private String sfxd;
	
	/** 回执时间 */
	private String hzsj;
	
	/** 客户基本信息 */
	private List<JSGA_QueryRequest_Customer> customerList;
}
