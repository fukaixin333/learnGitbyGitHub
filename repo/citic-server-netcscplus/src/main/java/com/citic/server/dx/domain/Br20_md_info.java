/* =============================================
*  Copyright (c) 2014-2015 by CITIC All rights reserved.
*  Created [2016-03-03] 
* =============================================
*/

package com.citic.server.dx.domain;                
                                                  
/***
* <p>Br20_party_acct_black.java</p>
* <p>Description: </p>
* @author $Author:  $
*/

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.citic.server.dict.DictBean;
                                              
@Data                                                  
@EqualsAndHashCode(callSuper = false)                                                  
public class Br20_md_info implements Serializable, DictBean {
                                         

	/**
	 * 
	 */
	private static final long serialVersionUID = -4810046695154852336L;
	
	/**名单编号*/
	private String md_code = "";
	
	/**名单类型 1.黑名单 2:灰名单*/
	private String md_type = "";
	
	/**名单种类 
	 * AccountNumber表示账号  IDType_IDNumber表示证件类型_证件号(暂时只有身份证号一种 类型)*/
	private String md_kind = "";
	
	/**证件类型 代码01*/
	private String cardtype = "";      
	
	/**名单值 
	 * 加密后的帐号或证件号码数据值为字符串 ChinaFinancialCertificationAuthority_
	 * 加上原始值后所得结果使用 SM3 算法进行哈希后得到的值*/
	private String md_value = "";
	
	/**名称*/
	private String p_name = "";
	
	/**名单来源 暂时只有公安这一种*/
	private String md_source = "";
	
	/**案件类型 暂时只有电信诈骗这一种*/
	private String case_type = "";
	
	/**名单标志 0:无效 1:有效 2:白名单*/
	private String md_flag = "";
	
	/**最后更新时间*/
	private String last_update_time = "";
	
	/**最后更新用户*/
	private String last_update_user = "";
	
	/**银行机构编码*/
	private String bank_id = "";
	/**公安联系人*/
	private String police = "";
	/**公安联系人电话*/
	private String police_phone = "";
	/**公安审核通过黑名单时间戳*/
	private String police_check_dt = "";
	/**开卡时间*/
	private String open_card_dt = "";
	/**有效期开始时间*/
	private String start_dt = "";
	/**有效期结束时间*/
	private String end_dt = "";
	/**名单说明*/
	private String remark = "";
//=================数据日志表含有的==================================	
	//文件编号
	private String file_code = "";
	//操作标志
	private String operate_flag = "";
	//发送标志 0:未发送 1:已发送 9:中间状态
	private String send_flag = "";
	//接收时间
	private String accept_time = "";
	//发送时间
	private String send_time = "";
	//下次发送时间
    private String next_time = "";
    
    //调用接口类型 0:不调用接口1:核心接口2:信用卡接口
    private String interface_type = "";
    //发送类型 0:都发送1:发送核心 2:发送信用卡
    private String send_type = "";
    //信用卡发送标志 0:为发送 1:已发送
    private String credit_send_flag = "";
    //信用卡发送时间
    private String credit_send_time = "";
    //反馈状态 Y：成功 N：正常 X：失败
    private String credit_status = "";
    //反馈原因
    private String credit_sbyy = "";
    //发送失败次数
    private int credit_failed_num = 0;
    //是否行内名单 0:否 1:是
    private String is_inner = "";
//=================文件接收日志和文件发送日志表含有的============================================  
    //文件名称
    private String file_name = "";
    //文件类型
    private String file_type = "";
    //是否成功
    private String flag = "";

//===================名单发送任务配置表含有的========================================    
    //接收系统编码
    private String syscode = "";
    //接收系统名称
    private String sysmc = "";
    //地址
    private String address = "";
    //端口号
    private String port = "";
    //发送频率单位
    private String unit = "";
    //发送频率
    private String frequency = "";
    //最后发送时间
    private String last_time = "";
    
    
  //===================sql取数限制============================================  
    private String  top_size = "100";
    
//===================码值转换有关============================================    
	/* (non-Javadoc)
	 * @see com.citic.server.dict.DictBean#getGroupId()
	 */
    //核心转码组
    private boolean coreflag = true;
    
	@Override
	public String getGroupId() {
		if(coreflag){
			return "Br20_md_info";
		}else{
			return "Br20_md_info_credit";//信用卡证件类型转码
		}
	}
    
}
