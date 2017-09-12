/* =============================================
*  Copyright (c) 2014-2015 by CITIC All rights reserved.
*  Created [2016-08-22] 
* =============================================
*/

        
                                                  
/**
* <p>Br51_cxqq_back.java</p>
* <p>Description: </p>
* @author $Author:  $
*/

package  com.citic.server.whpsb.domain;       

import java.io.Serializable;

import com.citic.server.dict.DictBean;

import lombok.Data; 
import lombok.EqualsAndHashCode; 
                                              
@Data                                                  
@EqualsAndHashCode(callSuper = false)                                                  
public class Whpsb_RequestJymx_Detail implements Serializable,DictBean {

	private static final long serialVersionUID = 2393944489951395441L;

	/** 案号 */
	private String ah = "";
	
	/** 核心源账号 */
	private String ctac = "";
	                                              
	/** 借贷标记 */
	private String debit_credit = "";
	                                              
	/** 交易日期年月日 */
	private String transdata = "";
	                                              
	/** 对方科目名称 */
	private String oppkm = "";
	                                              
	/** 交易金额 */
	private String amt = "";
	                                              
	/** IP地址 */
	private String ip = "";
	                                              
	/** 对方账号 */
	private String matchaccou = "";
	                                              
	/** 对方户名 */
	private String matchaccna = "";
	                                              
	/** 对方行名 */
	private String matchbankname = "";
	                                              
	/** 交易备注 */
	private String transremark = "";
	                                              
	/** 账（卡）号 */
	private String zh = "";
	                                              
	/** 传票号 */
	private String voucher_no = "";
	                                              
	/** 币种 */
	private String currency = "";
	                                              
	/** 交易网点 */
	private String organname = "";
	                                              
	/** 请求单号(案号_序号） */
	private String bdhm = "";
	                                              
	/** 交易种类 */
	private String transtype = "";
	                                              
	/** 账户余额 */
	private String banlance = "";
	                                              
	/** 交易流水号 */
	private String transnum = "";
	                                              
	/** 资金往来序号 */
	private String transseq = "";
	                                              
	/** MAC地址 */
	private String mac = "";
	                                              
	/** 小时分秒 */
	private String transtime = "";
	                                              
	/** 查询日期（分区） */
	private String qrydt = "";        
	
	private String msgseq="";

	@Override
	public String getGroupId() {
		//参数配置见BB13_DICTGROUP和BB13_DICTGROUPITEM
		return "Whpsb_RequestJymx_Detail";
	}
                                              
}
