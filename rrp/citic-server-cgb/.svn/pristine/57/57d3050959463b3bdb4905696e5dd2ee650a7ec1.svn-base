package com.citic.server.jsga.domain.request;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.citic.server.dict.DictBean;

/**
 * 常规查询反馈交易流水明细数据项
 * <ul>
 * <li>“江西省公安”和“深圳分行公安厅”报文包含<em>[查询账号]</em>、<em>[证照类型代码]</em>、<em>[证照号码]</em>、<em>[客户名称]</em>、
 * <em>[交易对方通讯地址]</em>和<em>[交易对方联系电话]</em>等字段，其它监管报文不包含。
 * <li>“国安”报文包含<em>[交易手续费币种]</em>、<em>[交易手续费金额]</em>、……等8个字段，其它监管报文不包含。
 * </ul>
 * 
 * @author Liu Xuanfei
 * @date 2016年7月5日 下午8:47:03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class JSGA_QueryRequest_Transaction implements DictBean, Serializable {
	private static final long serialVersionUID = 7275007287910524196L;
	
	/** 任务流水号 */
	private String rwlsh;
	
	/** 查询反馈结果 */
	private String cxfkjg;
	
	/** 查询反馈结果原因 */
	private String cxfkjgyy;
	
	/** 查询账号 */
	private String zh;
	
	/** 查询卡号 */
	private String cxkh;
	
	/** 交易类型 */
	private String jylx;
	
	/** 借贷标志 */
	private String jdbz;
	
	/** 币种 */
	private String bz;
	
	/** 交易金额 */
	private String je;
	
	/** 交易余额 */
	private String ye;
	
	/** 交易时间 */
	private String jysj;
	
	/** 交易流水号 */
	private String jylsh;
	
	/** 交易对方名称 */
	private String jydfxm;
	
	/** 交易对方账号/交易对方账卡号 */
	private String jydfzh;
	
	/** 交易对方卡号 */
	private String jydfkh;
	
	/** 交易对方账卡号类型（0为账号、1为卡号、2为未知） */
	private String jydfzkhlx;
	
	/** 交易对方证件号码 */
	private String jydfzjhm;
	
	/** 交易对手余额 */
	private String jydsye;
	
	/** 交易对方账号开户行 */
	private String jydfzhkhh;
	
	/** 交易摘要 */
	private String jyzy;
	
	/** 交易网点名称 */
	private String jywdmc;
	
	/** 交易网点代码 */
	private String jywddm;
	
	/** 日志号 */
	private String rzh;
	
	/** 传票号 */
	private String cph;
	
	/** 凭证种类 */
	private String pzzl;
	
	/** 凭证号 */
	private String pzh;
	
	/** 现金标志 */
	private String xjbz;
	
	/** 终端号 */
	private String zdh;
	
	/** 交易是否成功 */
	private String jysfcg;
	
	/** 交易发生地 */
	private String jyfsd;
	
	/** 商户名称 */
	private String shmc;
	
	/** 商户号 */
	private String shh;
	
	/** IP地址 */
	private String ip;
	
	/** MAC地址 */
	private String mac;
	
	/** 交易柜员号 */
	private String jygyh;
	
	/** 备注 */
	private String beiz;
	
	/** 证照类型代码 */
	private String zzlx;
	
	/** 证照号码 */
	private String zzhm;
	
	/** 客户名称 */
	private String khmc;
	
	/** 交易对方通讯地址 */
	private String jydftxdz;
	
	/** 交易对方联系电话 */
	private String jydflxdh;
	
	/** 交易手续费币种 */
	private String jysxfbz;
	
	/** 交易手续费金额 */
	private String jysxfje;
	
	/** 交易手续费摘要 */
	private String jysxfzy;
	
	/** 登录用户名 */
	private String dlyhm;
	
	/** 代办人姓名 */
	private String dbrxm;
	
	/** 代办人证件类型 */
	private String dbrzjlx;
	
	/** 代办人证件号码 */
	private String dbrzjhm;
	
	/** 代办人联系电话 */
	private String dbrlxdh;
	
	/** 账户名称（动态查询反馈时需要，其实应该直接取客户名称即可） */
	private String zhmc = "";

	// ==========================================================================================
	//                     Help Field
	// ==========================================================================================
	private String qqdbs = ""; // 请求单标识
	private String qrydt = "";// 查询日期
	private String orgkey = "";
	private String tasktype = "";
	private String transseq = "";
	private String jesxfbz = "";
	private String jesxfje = "";
	private String jesxfzy = "";
	
	@Override
	public String getGroupId() {
		return "JSGA_QUERYREQUEST_TRANSACTION";
	}
}
