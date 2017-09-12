package com.citic.server.cbrc.domain.request;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 冻结/续冻/解除冻结反馈结果报文
 * <ul>
 * <li><em>[操作失败原因]</em>字段为“国安”报文特有字段；<em>[是否修订]</em>字段为“深圳分行公安厅”特有字段。
 * </ul>
 * 
 * @author Liu Xuanfei
 * @date 2016年7月6日 上午9:59:18
 */
@Data
public class CBRC_FreezeRequest implements Serializable {
	private static final long serialVersionUID = 8019289361986433795L;
	
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
	
	/** 是否修订 */
	private String sfxd;
	
	/** 冻结/续冻/解除冻结反馈结果信息 */
	private List<CBRC_FreezeRequest_Record> freezeRecordList;
}
