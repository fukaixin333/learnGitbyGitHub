package com.citic.server.jsga.domain.request;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.citic.server.dict.DictBean;

/**
 * 常规查询反馈客户基本信息数据项
 * <ul>
 * <li>“检察院”报文包含<em>[账单地址]</em>字段，其它监管不包含。
 * <li>“国安”报文包含<em>[代办人联系电话]</em>、<em>[网银账户名]</em>、……等11个字段，其它监管不包含。
 * </ul>
 * 
 * @author Liu Xuanfei
 * @date 2016年7月5日 下午8:04:05
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class JSGA_QueryRequest_Customer implements DictBean, Serializable {
	private static final long serialVersionUID = -2102163488656259622L;
	
	/** 任务流水号 */
	private String rwlsh;
	
	/** 查询反馈结果 */
	private String cxfkjg;
	
	/** 查询反馈结果原因 */
	private String cxfkjgyy;
	
	/** 证照类型代码 */
	private String zzlx;
	
	/** 证照号码 */
	private String zzhm;
	
	/** 客户名称 */
	private String khmc;
	
	/** 联系电话 */
	private String lxdh;
	
	/** 联系手机 */
	private String lxsj;
	
	/** 代办人姓名 */
	private String dbrxm;
	
	/** 代办人证件类型 */
	private String dbrzjlx;
	
	/** 代办人证件号码 */
	private String dbrzjhm;
	
	/** 住宅地址 */
	private String zzdz;
	
	/** 住宅电话 */
	private String zzdh;
	
	/** 工作单位 */
	private String gzdw;
	
	/** 单位地址 */
	private String dwdz;
	
	/** 单位电话 */
	private String dwdh;
	
	/** 邮箱地址 */
	private String yxdz;
	
	/** 账单地址 */
	private String zddz;
	
	/** 法人代表 */
	private String frdb;
	
	/** 法人代表证件类型 */
	private String frdbzjlx;
	
	/** 法人代表证件号码 */
	private String frdbzjhm;
	
	/** 客户工商执照号码 */
	private String khgszzhm;
	
	/** 国税纳税号 */
	private String gsnsh;
	
	/** 地税纳税号 */
	private String dsnsh;
	
	/** 代办人联系电话 */
	private String dbrlxdh;
	
	/** 网银账户名 */
	private String wyzhm;
	
	/** 网银办理曰期 */
	private String wyblrq;
	
	/** 网银开户网点 */
	private String wykhwd;
	
	/** 网银开户网点代码 */
	private String wykhwddm;
	
	/** 网银开户网点所在 地 */
	private String wykhwdszd;
	
	/** 手机银行账户名 */
	private String sjyhzhm;
	
	/** 手机银行办理曰期 */
	private String sjyhblrq;
	
	/** 手机银行开户网点 */
	private String sjyhkhwd;
	
	/** 手机银行开户网点 代硏 */
	private String sjyhkhwddm;
	
	/** 手机银行开户网点 所在地 */
	private String sjyhkhwdszd;
	
	/** 账户基本信息 */
	private List<JSGA_QueryRequest_Account> accountList;
	
	/** 账户强制措施信息 */
	private List<JSGA_QueryRequest_Measure> measureList;
	
	/** 账户共有权/优先权信息 */
	private List<JSGA_QueryRequest_Priority> prioritiesList;
	
	/** 关联子账户信息 */
	private List<JSGA_QueryRequest_SubAccount> subAccountList;
	
	/** 交易明細信息 */
	private List<JSGA_QueryRequest_Transaction> transactionList;
	
	// ==========================================================================================
	//                     Help Field
	// ==========================================================================================
	/** 请求单标识 */
	private String qqdbs = "";
	/** 查询日期 */
	private String qrydt = "";
	private String tasktype = "";
	private String orgkey = "";
	private String party_id = "";
	private String dbrzjhmdbrzjlx = "";
	
	@Override
	public String getGroupId() {
		return "JSGA_QUERYREQUEST_CUSTOMER";
	}
}
