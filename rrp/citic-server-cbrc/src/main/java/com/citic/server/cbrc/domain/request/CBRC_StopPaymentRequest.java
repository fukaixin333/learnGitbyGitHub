package com.citic.server.cbrc.domain.request;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 紧急止付/解除止付反馈结果报文
 * <ul>
 * <li><em>[操作失败原因]</em>为“国安”报文特有字段。
 * </ul>
 * 
 * @author Liu Xuanfei
 * @date 2016年7月6日 上午10:33:55
 */
@Data
public class CBRC_StopPaymentRequest implements Serializable {
	private static final long serialVersionUID = 8103655500902072349L;
	
	/** 请求单标识 */
	private String qqdbs;
	
	/** 查控主体类别 */
	private String ztlb;
	
	/** 申请机构代码 */
	private String sqjgdm;
	
	/** 目标机构代码 */
	private String mbjgdm;
	
	/** 回执时间 */
	private String hzsj;
	
	/** 操作失败原因 */
	private String czsbyy;
	
	private List<CBRC_StopPaymentRequest_Recored> stopPaymentRecoredList;
}
