package com.citic.server.jsga.domain.response;

import java.io.Serializable;

import lombok.Data;

/**
 * 常规查询主体数据项
 * <ul>
 * <li>“四川省公安厅”报文中的<em>[查询类型]</em>字段名为<strong>CXLX</strong>；
 * <li>“江西省公安”和“深圳分行公安厅”报文中的<em>[查询类型]</em>字段名为<strong>CXFS</strong>；
 * <li>其它监管报文中无<em>[查询类型]</em>字段。
 * </ul>
 * 
 * @author Liu Xuanfei
 * @date 2016年7月5日 下午4:43:32
 */
@Data
public class JSGA_QueryResponse_Main implements Serializable {
	private static final long serialVersionUID = 7767150441512885L;
	
	/** 任务流水号 */
	private String rwlsh;
	
	/** 证照类型代码 */
	private String zzlx;
	
	/** 证照号码 */
	private String zzhm;
	
	/** 主体名称 */
	private String ztmc;
	
	/** 查询账卡号 */
	private String cxzh;
	
	/** 查询内容 */
	private String cxnr;
	
	/** 明细时段类型 */
	private String mxsdlx;
	
	/** 明细起始时间 */
	private String mxqssj;
	
	/** 明细截至时间 */
	private String mxjzsj;
	
	/** 查询方式 */
	private String cxfs;

	@Override
	public String toString() {
		return "JSGA_QueryResponse_Main [rwlsh=" + rwlsh + ", zzlx=" + zzlx + ", zzhm=" + zzhm + ", ztmc=" + ztmc + ", cxzh=" + cxzh + ", cxnr=" + cxnr + ", mxsdlx=" + mxsdlx + ", mxqssj=" + mxqssj
				+ ", mxjzsj=" + mxjzsj + ", cxfs=" + cxfs + "]";
	}
}
