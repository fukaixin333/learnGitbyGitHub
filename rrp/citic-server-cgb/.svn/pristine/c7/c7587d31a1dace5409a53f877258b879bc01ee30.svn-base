package com.citic.server.gdjg.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.citic.server.gdjg.domain.Br57_cxqq;
import com.citic.server.gdjg.domain.Br57_cxqq_back;
import com.citic.server.gdjg.domain.Br57_cxqq_mx;
import com.citic.server.gdjg.domain.Br57_cxqq_mx_policeman;
import com.citic.server.gdjg.domain.Br57_receipt;
import com.citic.server.gdjg.domain.Datasend_log;
import com.citic.server.gdjg.domain.request.Gdjg_RequestDhdj_City;
import com.citic.server.gdjg.domain.request.Gdjg_RequestGzdj_AccountRule;
import com.citic.server.gdjg.domain.request.Gdjg_RequestLsdj;
import com.citic.server.gdjg.domain.request.Gdjg_RequestWddj_Branch;
import com.citic.server.gdjg.domain.request.Gdjg_Request_AccCx;
import com.citic.server.gdjg.domain.request.Gdjg_Request_FinancialProductsCx;
import com.citic.server.gdjg.domain.request.Gdjg_Request_FroInfoCx;
import com.citic.server.gdjg.domain.request.Gdjg_Request_TransCx;
import com.citic.server.service.domain.MC21_task_fact;

/**
 * 广东省检察院
 * 
 * @author liuxuanfei
 * @date 2017年5月25日 下午2:50:53
 */
public interface MM57_cxqqMapper {
	/** 删除Br57_cxqq查询请求单表 */
	void delBr57_cxqq(Br57_cxqq br57_cxqq);
	
	/** 删除Br57_cxqq_mx_policeman查询请求单表 */
	void delBr57_cxqq_mx_policeman(Br57_cxqq br57_cxqq);
	
	/** 删除Br50_cxqq_mx 查询请求主体信息 */
	void delBr57_cxqq_mx(Br57_cxqq br57_cxqq);
	
	/** 删除查询请求反馈主体表 */
	void delBr57_cxqq_back(Br57_cxqq br50_cxqq);
	
	/** 插入Br57_CXQQ查询请求主表 */
	void insertBr57_cxqq(Br57_cxqq br57_cxqq);
	
	/** 插入查询请求办案人主体表 */
	void insertBr57_cxqq_mx_policeman(Br57_cxqq_mx_policeman mxpoliceman);
	
	/** 请求单主体信息 */
	void insertBr57_cxqq_mx(Br57_cxqq_mx br57_cxqq_mx);
	
	/** 插入查询请求反馈主体表 */
	void insertBr57_cxqq_back(Br57_cxqq_back br57_cxqq_back);
	
	/** 删除查询信息 */
	void delBr57_cxqq_back_acct(MC21_task_fact mc21_task_fact);
	void delBr57_cxqq_back_froInfo(MC21_task_fact mc21_task_fact);
	void delBr57_cxqq_back_trans(MC21_task_fact mc21_task_fact);
	void delBr57_cxqq_back_financePro(MC21_task_fact mc21_task_fact);
	
	/** 按条件 查询Br57_CXQQ_BACK查询请求反馈主体表 */
	Br57_cxqq_back selectBr57_cxqq_backByVo(Br57_cxqq Br57_cxqq);
	
	/** 按条件 查询Br57_CXQQ_MX查询请求主体信息 */
	Br57_cxqq_mx selectBr57_cxqq_mxByVo(Br57_cxqq Br57_cxqq);
	
	/** 批量插入存款查询的反馈信息：基本信息，冻结信息，辅助信息 */
	int batchInsertBr57_cxqq_back_acct(List<Gdjg_Request_AccCx> list);
	void batchInsertBr57_cxqq_back_froInfo(List<Gdjg_Request_FroInfoCx> froInfos);
	void updateBr57_cxqq_back(Br57_cxqq_back Br57_cxqq_back);
	
	/** 插入task3的辅助处理 */
	int getBr57_cxqq_backCount(String docno);
	void updateBr57_cxqq(Br57_cxqq br57_cxqq);
	
	
	/** 批量插入交易流水查询的反馈信息 */
	void batchInsertBr57_cxqq_back_trans(List<Gdjg_Request_TransCx> transList);
	/** 交易流水查询为空时的反馈信息 */
	void InsertBr57_cxqq_back_trans(Gdjg_Request_TransCx tranNull);
	
	/** 获取 Br57_cxqq信息*/
	List<Br57_cxqq> getBr57_cxqqList(String bdhm);
	/** 获取 Br57_cxqq_mx信息*/
	List<Br57_cxqq_mx> getBr57_cxqq_mxList(Br57_cxqq br57_cxqq);
	/** 获取Br57_cxqq_back信息*/
	Br57_cxqq_back getBr57_cxqq_back(Br57_cxqq_mx br57_cxqq_mx);
	
	/** 获取Br57_cxqq_back_acct信息*/
	List<Gdjg_Request_AccCx> getBr57_cxqq_back_acctList1(Br57_cxqq cxqq);

	/** 插入回执信息*/
	void insertBr57_receipt(Br57_receipt receipt);

	/** 获取Br57_cxqq_back_trans信息*/
	List<Gdjg_Request_TransCx> getBr57_cxqq_back_transListByDocno(String docno);

	/** 获取最后发送时间（银行基础数据发送表）*/
	String getLastsendts(MC21_task_fact mc21_task_fact);

	/**获取银行规则登记、网点登记、城市网点登记表*/
	List<Gdjg_RequestGzdj_AccountRule> getBr40_acct_ruleList(MC21_task_fact mc21_task_fact);
	List<Gdjg_RequestWddj_Branch> getBr40_branch_regList(MC21_task_fact mc21_task_fact);
    List<Gdjg_RequestDhdj_City> getBr40_city_noList(MC21_task_fact mc21_task_fact);
    
    /**插入基础数据发送日志表*/
    void insertBr40_datasend_log(Datasend_log logdto);
    
    /**更新银行基础数据发送表*/
	void updateBr40_basedata_send(MC21_task_fact mc21_task_fact);

	/** 批量插入金融产品查询的信息 */
	void batchInsertBr57_cxqq_back_financePro(List<Gdjg_Request_FinancialProductsCx> productList);
	/** 批量获取金融产品查询的信息 */
	List<Gdjg_Request_FinancialProductsCx> getBr57_cxqq_back_financePro(Br57_cxqq_mx mx);

	
	/** 批量获取冻结信息*/
	List<Gdjg_Request_FroInfoCx> getBr57_cxqq_froList(Br57_cxqq_mx ck_mx);

	
	
	
	
	/**  广东省检察院动态查询  */
	/** 删除动态查询信息*/
	void delBr57_cxqq_acct_dynamic_main(Br57_cxqq_mx br57_mx);
	/** 插入动态查询任务*/
	void insertBr57_cxqq_acct_dynamic_main(Br57_cxqq_mx br57_cxqq_mx);
	/** 解除动态查询任务*/
	void updateBr57_cxqq_acct_dynamic_jc(Br57_cxqq_mx br57_cxqq_mx);
	/** 获取办案人信息*/
	List<Br57_cxqq_mx_policeman> getBr57_cxqq_mxpolicemanList(Br57_cxqq br57_cxqq);
	
	/** 关闭过期的动态查询任务*/
	public void closeBr57_cxqq_acct_dynamic_main(String currDataTime);
	/** 获取动态查询任务*/
	public List<Br57_cxqq_mx> queryBr57_cxqq_acct_dynamic_main(@Param("tasktype")String taskTypeGdjcy, @Param("datatime")String currDataTime);
	/** 获取动态查询流水信息*/
	List<String> selBr57_trans_infoList(@Param("uniqueid")String uniqueId, @Param("account")String account, @Param("tasktype")String taskType, @Param("starttime")String startTime, @Param("endtime")String lastPollingTime);
	/** 更新动态查询任务*/
	void updateBr57_cxqq_acct_dynamic_main(@Param("tasktype")String taskType, @Param("uniqueid")String uniqueId, @Param("datatime")String currDataTime, @Param("pollinglock")String pollingLock);
	/** 批量插入动态数据*/
	void batchInsertBr57_cxqq_back_dyntrans(List<Gdjg_Request_TransCx> newTransList);

	/** 获取动态数据*/
	List<Gdjg_Request_TransCx> getBr57_cxqq_back_dynamic_transList(Br57_cxqq_mx mx);


}
