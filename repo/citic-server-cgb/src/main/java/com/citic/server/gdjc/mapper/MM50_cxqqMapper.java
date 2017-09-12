package com.citic.server.gdjc.mapper;

import java.util.List;

import com.citic.server.gdjc.domain.Br50_cxqq;
import com.citic.server.gdjc.domain.Br50_cxqq_back;
import com.citic.server.gdjc.domain.Br50_cxqq_mx;
import com.citic.server.gdjc.domain.Br52_receipt;
import com.citic.server.gdjc.domain.Datasend_log;
import com.citic.server.gdjc.domain.request.Gdjc_RequestCkdj_Acc;
import com.citic.server.gdjc.domain.request.Gdjc_RequestDhdj_City;
import com.citic.server.gdjc.domain.request.Gdjc_RequestGzdj_AccountRule;
import com.citic.server.gdjc.domain.request.Gdjc_RequestLsdj_Trans;
import com.citic.server.gdjc.domain.request.Gdjc_RequestWddj_Branch;
import com.citic.server.service.domain.MC21_task_fact;



/**
 * 广东纪检
 * @author
 *
 */

public interface MM50_cxqqMapper {

	/** 删除Br50_CXQQ查询请求单表 */
	void delBr50_cxqq(Br50_cxqq br50_cxqq);

	/** 插入Br50_CXQQ查询请求主表 */
	void insertBr50_cxqq(Br50_cxqq br50_cxqq);


	/** 批量插入Br50_CXQQ查询请求主表 */
	int batchInsertBr50_cxqq(List<Br50_cxqq> list);

	/** 删除Br50_cxqq_mx 查询请求主体信息 */
	void delBr50_cxqq_mx(Br50_cxqq br50_cxqq);

	/** 请求单主体信息 */
	void insertBr50_cxqq_mx(Br50_cxqq_mx qqmx);
	
	/** 批量请求单主体信息 */
	void batchInsertBr50_cxqq_mx(List<Br50_cxqq_mx> list);

	/** 删除查询请求反馈主体表 */
	void delBr50_cxqq_back(Br50_cxqq br50_cxqq);

	/** 插入查询请求反馈主体表 */
	void insertBr50_cxqq_back(Br50_cxqq_back br50_cxqq_back);

	/** 按条件 查询Br50_CXQQ_BACK查询请求反馈主体表 */
	Br50_cxqq_back selectBr50_cxqq_backByVo(Br50_cxqq Br50_cxqq);

	/** 按条件 查询Br50_CXQQ_MX查询请求主体信息 */
	Br50_cxqq_mx selectBr50_cxqq_mxByVo(Br50_cxqq Br50_cxqq);
	
	List<Br50_cxqq_mx> getBr50_cxqq_mxList(Br50_cxqq Br50_cxqq);
	
	List<Br50_cxqq> getBr50_cxqqList(String  docno);

	void delBr50_cxqq_back_acct(MC21_task_fact mc21_task_fact);
	void delBr50_cxqq_back_trans(MC21_task_fact mc21_task_fact);
	
	void insertBr50_cxqq_back_acct(Gdjc_RequestCkdj_Acc acc);
	int batchInsertBr50_cxqq_back_acct(List<Gdjc_RequestCkdj_Acc> list);

	void insertBr50_cxqq_back_trans(Gdjc_RequestLsdj_Trans trans);
	int batchInsertBr50_cxqq_back_trans(List<Gdjc_RequestLsdj_Trans> list);
	
	List<Gdjc_RequestCkdj_Acc> getBr50_cxqq_back_acctList(Br50_cxqq_mx br50_cxqq_mx);
	List<Gdjc_RequestCkdj_Acc> getBr50_cxqq_back_acctList1(Br50_cxqq br50_cxqq);
	List<Gdjc_RequestCkdj_Acc> getBr50_cxqq_back_acctMap(Br50_cxqq br50_cxqq);
	List<Gdjc_RequestLsdj_Trans> getBr50_cxqq_back_transList(Br50_cxqq_mx br50_cxqq_mx);

	List<Gdjc_RequestLsdj_Trans> getBr50_cxqq_back_transListByDocno(String  docno);
	
	
	void updateBr50_cxqq_back(Br50_cxqq_back Br50_cxqq_back);
	
	Integer getBr50_cxqq_backCount(String docno);
	
	List<Gdjc_RequestDhdj_City> getBr40_city_noList(MC21_task_fact mc21_task_fact);
	List<Gdjc_RequestGzdj_AccountRule> getBr40_acct_ruleList(MC21_task_fact mc21_task_fact);
	List<Gdjc_RequestWddj_Branch> getBr40_branch_regList(MC21_task_fact mc21_task_fact);
	
	String getLastsendts(MC21_task_fact mc21_task_fact);
	
	void updateBr40_basedata_send(MC21_task_fact mc21_task_fact);
	
	void insertBr40_datasend_log(Datasend_log log);
	
	void insertBr52_receipt(Br52_receipt receipt);

	//修改请求表
	void  updateBr50_cxqq(Br50_cxqq br50_cxqq);
}
