package com.citic.server.cbrc.domain.request;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 回执文书反馈报文
 * <ul>
 * <li>仅“江西省公安”和“深圳分行公安厅”使用此报文。
 * </ul>
 * 
 * @author Liu Xuanfei
 * @date 2016年7月6日 下午2:25:31
 */
@Data
public class CBRC_FeedbackRequest implements Serializable {
	private static final long serialVersionUID = 9159396957548384001L;
	
	/** 请求单标识 */
	private String qqdbs;
	
	/** 申请机构代码 */
	private String sqjgdm;
	
	/** 目标机构代码 */
	private String mbjgdm;
	
	/** 回执时间 */
	private String hzsj;
	
	/** 是否修订 */
	private String sfxd;
	
	/** 回执文书反馈信息 */
	private List<CBRC_FeedbackRequest_Record> feedbackRecordList;
}
