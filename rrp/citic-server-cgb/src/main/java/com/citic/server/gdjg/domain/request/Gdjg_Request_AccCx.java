package com.citic.server.gdjg.domain.request;

import java.io.Serializable;
import java.util.List;

import com.citic.server.dict.DictBean;

import lombok.Data;

/**
 * 查询类--账户信息
 * @author wangbo
 * @date 2017年5月19日 下午2:59:12
 */
@Data
public class Gdjg_Request_AccCx implements Serializable,DictBean{
	private static final long serialVersionUID = -745084424669217982L;

	/** 户名 */
	private String accname;
	/** 类型 */
	private String type;
	/** 账户类型（中文描述可依据各银行规则定义：如活期账户、定期账户、理财账户等） */
	private String acctype;
	/** 账号     若存在子账号，传送数据：主账号_子账号。*/
	private String account;
	/** 卡号 */
	private String cardno;
	/** 余额（为空值时，传 0.00 ） */
	private String banlance;
	/** 可用余额 */
	private String avabanlance;
	/** 币种 */
	private String currency;
	/** 汇钞类型（1-现钞；2-现汇） */
	private String exchangetype;
	/** 开户日期（YYYY-MM-DD） */
	private String opendate;
	/** 最后交易日期（YYYY-MM-DD） */
	private String tradedate;
	/** 开户网点编号 */
	private String openbranchno;
	/** 开户网点名称 */
	private String openbranchname;
	/** 开户网点电话 */
	private String openbranchtel;
	/** 开户网点地址 */
	private String openbranchaddr;
	/** 证件类型 */
	private String idtype;
	/** 证件号码 */
	private String id;
	/** 账户人联系地址 */
	private String addr;
	/** 账户人联系电话 */
	private String tel;
	/** 账户人联系手机 */
	private String iphone;
	/** 住宅地址 */
	private String homeaddr;
	/** 住宅电话 */
	private String hometel;
	/** 代办人姓名 */
	private String agentname;
	/** 代办人证件类型 */
	private String agentidtype;
	/** 代办人证件号码 */
	private String agentid;
	/** 工作单位 */
	private String work;
	/** 单位地址 */
	private String workaddr;
	/** 单位电话 */
	private String worktel;
	/** 邮箱地址 */
	private String email;
	/** 法人代表 */
	private String legal;
	/** 法人代表证件类型 */
	private String legalidtype;
	/** 法人代表证件号码 */
	private String legalid;
	/** 工商营业执照 */
	private String buslicense;
	/** 国税纳税号 */
	private String nationaltax;
	/** 地税纳税号 */
	private String landtax;
	/** 账户状态标识（0-正常；1-已冻结；2-已销户；3-已挂失；9-其它） */
	private String statusflag;
	/** 账户状态（用中文描述当前账户所处的状态） */
	private String status;
	/** 销户日期（YYYY-MM-DD） */
	private String closedate;
	/** 销户网点编号 */
	private String closebranchno;
	/** 销户网点名称 */
	private String closebranchname;
	/** 是否支持网上冻结   0：不支持   1：支持（为空时默认为支持） */
	private String isfro;
	
	/**执行结果    0：执行成功； 1：执行失败 */
	private String exeresult;
	/** 原因 */
	private String memo;
	/** 反馈手机号码 */
	private String phone;
	/** 时间区间 */
	private String intervals;
	/** 开始日期 */
	private String startdate;
	/** 结束日期  */
	private String enddate;
	
	
	
	//冻结
	/** 冻结流水号 */
	private String froseq;
	/** 账户可用余额 */
	private String canbanlance;
	/** 冻结类型 */
	private String frotype;
	/** 冻结方式 */
	private String fromode;
	/** 冻结标志 */
	private String froflag;
	/** 应冻结金额 */
	private String frobanlance_1;
	/** 已冻结金额 */
	private String frobanlance_2;
	/** 未冻结金额 */
	private String frobanlance_3;
	/** 冻结额度 */
	private String frobanlance;
	/** 冻结开始日期 */
	private String frostartdate;
	/** 冻结结束日期 */
	private String froenddate;
	/** 在先冻结机关*/
	private String beforefro;
	/** 在先冻结金额 */
	private String beforefroban;
	/** 在先冻结到期日*/
	private String beforefrodate;
	/** 送达时间 */
	private String servicetime;
	/** 送达证 */
	private String servicedoc;
	/** 回执时间 */
	private String replytime;
	/** 回执 */
	private String replydoc;
	/** 文件格式 */
	private String filetype;
	
	//解冻
	/** 解冻流水号 */
	private String thawseq;
	/** 解冻方式 */
	private String thawmode;
	/** 解冻标志 */
	private String thawflag;
	/** 解冻金额 */
	private String thawbanlance;
	/** 解冻日期 */
	private String thawdate;


	
	/** 冻结信息 */
	private List<Gdjg_Request_FroInfoCx> froinfos;
	
	/** 权力信息 */
	private List<Gdjg_Request_RightInfoCx> rightinfos;
	
	/** 交易流水 */
	private List<Gdjg_Request_TransCx> Trans;
	
	

	// helper  fields
	/** 协作编号 */
	private String docno = "";
	/** 案件编号 */
	private String caseno = "";
	/** 唯一标识 */
	private String uniqueid;
	/** 查询日期（分区） */
	private String qrydt = "";
	/** 冻结编号（ARTERY) */
	private String fronumber = "";
	
	
	
	@Override
	/** 转码使用 */
	public String getGroupId() {
		return "Gdjg_Request_AccCx";
	}
	
	

}
