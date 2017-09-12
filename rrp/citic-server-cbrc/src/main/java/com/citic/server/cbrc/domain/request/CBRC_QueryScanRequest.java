package com.citic.server.cbrc.domain.request;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 凭证图像反馈结果报文
 * <ul>
 * <li>仅“国安”和“检察院”使用此报文。
 * <li>“检察院”使用<strong>CXJSSJ</strong>表示<em>[回执时间]</em>，且不包含<em>[操作失败原因]</em>字段。
 * </ul>
 * 
 * @author Liu Xuanfei
 * @date 2016年7月6日 上午10:51:12
 */
@Data
public class CBRC_QueryScanRequest implements Serializable {
	private static final long serialVersionUID = 2437439777995839093L;
	
	/** 请求单标识 */
	private String qqdbs;
	
	/** 査控主体类别 */
	private String ztlb;
	
	/** 申请机构代码 */
	private String sqjgdm;
	
	/** 目标机构代码 */
	private String mbjgdm;
	
	/** 查洵结束时间 */
	private String cxjssj;
	
	/** 搡作失败原因 */
	private String czsbyy;
	
	private String qqcslx;
	/** 凭证图像反馈结果信息 */
	private List<CBRC_QueryScanRequest_Record> scanRecoredList;
}
