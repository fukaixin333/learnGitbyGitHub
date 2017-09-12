package com.citic.server.whpsb.mapper;

import java.util.ArrayList;
import java.util.List;

import com.citic.server.service.domain.MC20_Task_Fact1;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.whpsb.domain.Br51_cxqq;
import com.citic.server.whpsb.domain.Br51_cxqq_back;
import com.citic.server.whpsb.domain.Br51_cxqq_back_msg;
import com.citic.server.whpsb.domain.Br51_cxqq_mx;
import com.citic.server.whpsb.domain.Whpsb_RequestJymx_Detail;
import com.citic.server.whpsb.domain.request.Whpsb_RequestCkrzl_Detail;
import com.citic.server.whpsb.domain.request.Whpsb_RequestKhzl_Detail;
import com.citic.server.whpsb.domain.request.Whpsb_RequestZhxx_Detail;



/**
 * 武汉
 * @author
 *
 */

public interface BR51_cxqqMapper {

	/** 删除Br51_CXQQ查询请求单表 */
	void delBr51_cxqq(String msgseq);

	/** 插入Br51_CXQQ查询请求主表 */
	void insertBr51_cxqq(Br51_cxqq br51_cxqq);

	/** 删除Br51_cxqq_mx 查询请求主体信息 */
	void delBr51_cxqq_mx(String msgseq);

	/** 请求单主体信息 */
	void insertBr51_cxqq_mx(Br51_cxqq_mx qqmx);

	/** 删除查询请求反馈主体表 */
	void delBr51_cxqq_back(String msgseq);

	/** 插入查询请求反馈主体表 */
	void insertBr51_cxqq_back(Br51_cxqq_back br50_cxqq_back);
	
	/** BR51_ATTACH文书证件信息 */
	void insertBr51_attach(Br51_cxqq br51_cxqq);
	
	/** 删除账户 */
	void delBr51_cxqq_back_acct(String bdhm);
	
	/** BR51_CXQQ_BACK_PARTY  开户资料 */
	void delBr51_cxqq_back_party(String bdhm);
	
	/** BR51_CXQQ_BACK_CARD 持卡人信息 */
	void delBr51_cxqq_back_card(String bdhm);
	
	/** BR51_CXQQ_BACK_TRANS 交易信息 */
	void delBr51_cxqq_back_trans(String bdhm);
	
	/** xml信息 */
	void delBr51_cxqq_back_msg(String msgseq);
	
	//插入账户
	void  insertBr51_cxqq_back_acct(Whpsb_RequestZhxx_Detail Br51_cxqq_back_acct);
	//插入持卡主体
	void  insertBr51_cxqq_back_card(Whpsb_RequestCkrzl_Detail Br51_cxqq_back_card);
	//插入开户资料
	void  insertBr51_cxqq_back_party(Whpsb_RequestKhzl_Detail Br51_cxqq_back_party);
	//插入交易
	void  insertBr51_cxqq_back_trans(Whpsb_RequestJymx_Detail Br51_cxqq_back_trans);
	
	//插入msg
	void  insertBr51_cxqq_back_msg(Br51_cxqq_back_msg br51_cxqq_back_msg);
	
	
	//查询主体明细
	Br51_cxqq_mx  selBr51_cxqq_mx(String bdhm);
	//查询主体
	Br51_cxqq  selBr51_cxqq(String msgseq);
	//查询主体反馈
	Br51_cxqq_back  selBr51_cxqq_back(String bdhm);
	//修改反馈主表
	void  updateBr51_cxqq_back(Br51_cxqq_back br51_cxqq_back);
	//修改请求表
	void  updateBr51_cxqq(Br51_cxqq br51_cxqq);
	//查询是否可以生成报文
	int  isMakeMsg(String msgseq);
	
	//查询主体反馈
	List<Br51_cxqq_mx>  selBr51_cxqq_mxList(String msgseq);
	//查询账户
    List<Whpsb_RequestZhxx_Detail>  selBr51_cxqq_back_acctList(String msgseq);
  //查询持卡主体
    List<Whpsb_RequestCkrzl_Detail>  selBr51_cxqq_back_cardList(String msgseq);
  //查询开户资料
    List<Whpsb_RequestKhzl_Detail>  selBr51_cxqq_back_partyList(String msgseq);
  //查询交易
    List<Whpsb_RequestJymx_Detail>  selBr51_cxqq_back_transList(String msgseq);
	
}
