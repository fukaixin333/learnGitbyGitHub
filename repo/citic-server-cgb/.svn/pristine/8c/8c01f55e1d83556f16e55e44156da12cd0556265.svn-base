package com.citic.server.gdjc.task.taskBo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.cbrc.domain.request.CBRC_QueryRequest_Transaction;
import com.citic.server.dict.DictCoder;
import com.citic.server.gdjc.domain.Br50_cxqq;
import com.citic.server.gdjc.domain.Br50_cxqq_back;
import com.citic.server.gdjc.domain.Br50_cxqq_mx;
import com.citic.server.gdjc.domain.request.Gdjc_RequestCkdj_Acc;
import com.citic.server.gdjc.domain.request.Gdjc_RequestCkdj_Bank;
import com.citic.server.gdjc.domain.request.Gdjc_RequestLsdj_Acc;
import com.citic.server.gdjc.domain.request.Gdjc_RequestLsdj_Trans;
import com.citic.server.gdjc.mapper.MM50_cxqqMapper;
import com.citic.server.gdjc.service.IDataOperateGdjc;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.runtime.Utility;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.service.task.taskBo.BaseBo;
import com.citic.server.utils.DtUtils;

public class Cxqq_CLBo extends BaseBo {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(Cxqq_CLBo.class);
	
	/** 数据获取接口 */
	private IDataOperateGdjc dataOperate;
	
	/** 码表转换接口 */
	private DictCoder dictCoder;
	
	private MM50_cxqqMapper br50_cxqqMapper;
	
	public Cxqq_CLBo(ApplicationContext ac) {
		super(ac);
		this.br50_cxqqMapper = (MM50_cxqqMapper) ac.getBean("MM50_cxqqMapper");
		this.dictCoder = (DictCoder) ac.getBean("dictCoder");
	}
	
	/**
	 * 删除查询信息
	 * 
	 * @param bdhm
	 * @throws Exception
	 */
	public void delCxqqInfo01(MC21_task_fact mc21_task_fact) throws Exception {
		br50_cxqqMapper.delBr50_cxqq_back_acct(mc21_task_fact);
	}
	
	public void delCxqqInfo02(MC21_task_fact mc21_task_fact) throws Exception {
		br50_cxqqMapper.delBr50_cxqq_back_trans(mc21_task_fact);
	}
	
	/**
	 * 处理存款查询任务 - task2
	 * 
	 * @param mc21_task_fact
	 * @throws Exception
	 */
	
	public String handleQueryResponse01(MC21_task_fact mc21_task_fact) throws Exception {
		String taskType = mc21_task_fact.getTasktype(); // 任务代号
		String docno = "";
		String caseno = "";
		//从task2获取docno和caseno
		if(!StringUtils.isBlank(mc21_task_fact.getTaskobj())){
			String[] str = mc21_task_fact.getTaskobj().split(";");
			docno = str[0];
			caseno = str[1];
		}
		//1.查询请求内容
		Br50_cxqq br50_cxqq = new Br50_cxqq();
		br50_cxqq.setDocno(docno);
		br50_cxqq.setCaseno(caseno);
		br50_cxqq.setUniqueid(mc21_task_fact.getBdhm());
		// 1.1.根据请求单标识、监管类别获取查询主体信息内容
		Br50_cxqq_mx br50_cxqq_mx = this.getQueryRequest_mx(br50_cxqq);
		// 1.2、获取反馈基本信息表
		Br50_cxqq_back br50_cxqq_back = this.getBr50_cxqq_back(br50_cxqq);
		docno = br50_cxqq_back.getDocno();
		//		String msgCheckResult = br50_cxqq_back.getMsgcheckresult();
		String cxfkjg = "01";
		String czsbyy = "";
		//if(msgCheckResult!=null&&msgCheckResult.equals("1")){ //判断是否是本行数据	
		try {
			//2.初始化接口
			dataOperate = (IDataOperateGdjc) this.ac.getBean(REMOTE_DATA_OPERATE_NAME_GDJC);
			
			HashMap etlcodeMap = (HashMap) cacheService.getCache("BB13_etl_code_mapDetail", HashMap.class);
			HashMap zjlxMap = (HashMap) etlcodeMap.get("GDJC");
			String cardtype = br50_cxqq_mx.getIdtype();
			if (zjlxMap != null && zjlxMap.get(cardtype) != null) { // 证件类型转成核心需要的
				cardtype = (String) zjlxMap.get(cardtype);
			}
			br50_cxqq_mx.setIdtype(cardtype);
			
			//3.获取账户信息
			Gdjc_RequestCkdj_Bank queryRequest_Bank = dataOperate.getAccountDetail(br50_cxqq_mx);
			if (queryRequest_Bank != null) {
				
				List<Gdjc_RequestCkdj_Acc> accList = queryRequest_Bank.getAccs();
				for (Gdjc_RequestCkdj_Acc acc : accList) {
					acc.setUniqueid(br50_cxqq_mx.getUniqueid());
					acc.setCaseno(br50_cxqq_mx.getCaseno());
					acc.setDocno(br50_cxqq_mx.getDocno());
					acc.setQrydt(DtUtils.getNowDate());
					//将字段进行转吗
					dictCoder.reverse(acc, taskType);
				}
				if (accList != null && accList.size() > 0) {
					br50_cxqqMapper.batchInsertBr50_cxqq_back_acct(accList);
				}
			} else {
				cxfkjg = "02";
				czsbyy = "信息不全，无法查询";
			}
			
		} catch (DataOperateException e) {
			// 3.3 错误统一采用异常处理
			logger.error("查询异常" + e.getMessage());
			cxfkjg = "02";
			czsbyy = e.getMessage();
		} catch (RemoteAccessException e) {
			e.printStackTrace();
			logger.error("Artery异常" + e.getMessage());
			cxfkjg = "02";
			czsbyy = "查询异常";
		}
		// 4、写反馈基本信息表 
		br50_cxqq_back.setStatus("1");
		br50_cxqq_back.setCxfkjg(cxfkjg);
		br50_cxqq_back.setCzsbyy(czsbyy);
		updateBr50_cxqq_back(br50_cxqq_back);
		
		return docno;
	}
	
	/**
	 * 处理交易流水查询任务 - task2
	 * 
	 * @param mc21_task_fact
	 * @throws Exception
	 */
	
	public String handleQueryResponse02(MC21_task_fact mc21_task_fact) throws Exception {
		String taskType = mc21_task_fact.getTasktype(); // 任务代号
		String docno = "";
		String caseno = "";
		//从task2获取docno和caseno
		if(!StringUtils.isBlank(mc21_task_fact.getTaskobj())){
			String[] str = mc21_task_fact.getTaskobj().split(";");
			docno = str[0];
			caseno = str[1];
		}
		//1.查询请求内容
		Br50_cxqq br50_cxqq = new Br50_cxqq();
		br50_cxqq.setDocno(docno);
		br50_cxqq.setCaseno(caseno);
		br50_cxqq.setUniqueid(mc21_task_fact.getBdhm());
		// 1.1.根据请求单标识、监管类别获取查询主体信息内容
		Br50_cxqq_mx br50_cxqq_mx = this.getQueryRequest_mx(br50_cxqq);
		// 1.2、获取反馈基本信息表
		Br50_cxqq_back br50_cxqq_back = this.getBr50_cxqq_back(br50_cxqq);
		docno = br50_cxqq_back.getDocno();
		//		String msgCheckResult = br50_cxqq_back.getMsgcheckresult();
		String cxfkjg = "01";
		String czsbyy = "";
		List<CBRC_QueryRequest_Transaction> transactionList = new ArrayList<CBRC_QueryRequest_Transaction>();
		//if(msgCheckResult!=null&&msgCheckResult.equals("1")){ //判断是否是本行数据	
		try {
			//2.初始化接口
			dataOperate = (IDataOperateGdjc) this.ac.getBean(REMOTE_DATA_OPERATE_NAME_GDJC);
			
			HashMap etlcodeMap = (HashMap) cacheService.getCache("BB13_etl_code_mapDetail", HashMap.class);
			HashMap zjlxMap = (HashMap) etlcodeMap.get("GDJC");
			String cardtype = br50_cxqq_mx.getIdtype();
			if (zjlxMap != null && zjlxMap.get(cardtype) != null) { // 证件类型转成核心需要的
				cardtype = (String) zjlxMap.get(cardtype);
			}
			//		br50_cxqq_mx.setType(cardtype); 宏程修改，估计是手误
			br50_cxqq_mx.setIdtype(cardtype);
			//3.获取账户信息
			Gdjc_RequestLsdj_Acc queryRequest_Acc = dataOperate.getAccountTransaction(br50_cxqq_mx);
			if (queryRequest_Acc != null) {
				List<Gdjc_RequestLsdj_Trans> transList = queryRequest_Acc.getTranslist();
				for (Gdjc_RequestLsdj_Trans trans : transList) {
					trans.setUniqueid(br50_cxqq_mx.getUniqueid());
					trans.setCaseno(br50_cxqq_mx.getCaseno());
					trans.setDocno(br50_cxqq_mx.getDocno());
					trans.setQrydt(DtUtils.getNowDate());
					//将字段进行转吗【核心转监管】
					dictCoder.reverse(trans, taskType);
				}
				if (transList != null && transList.size() > 0) {
					br50_cxqqMapper.batchInsertBr50_cxqq_back_trans(transList);
				}
			} else {
				cxfkjg = "02";
				czsbyy = "信息不全，无法查询";
			}
			
		} catch (DataOperateException e) {
			// 3.3 错误统一采用异常处理
			logger.error("查询异常" + e.getMessage());
			cxfkjg = "02";
			czsbyy = e.getMessage();
		} catch (RemoteAccessException e) {
			e.printStackTrace();
			logger.error("Artery异常" + e.getMessage());
			cxfkjg = "02";
			czsbyy = "查询异常";
		}
		// 4、写反馈基本信息表 
		br50_cxqq_back.setCxfkjg(cxfkjg);
		br50_cxqq_back.setCzsbyy(czsbyy);
		updateBr50_cxqq_back(br50_cxqq_back);
		
		return docno;
	}
	
	/**
	 * 根据请求单标识，监管类别获取查询请求主体信息
	 * 
	 * @param mc21_task_fact
	 * @return
	 */
	private Br50_cxqq_mx getQueryRequest_mx(Br50_cxqq br50_cxqq) {
		Br50_cxqq_mx queryrequest_mx = br50_cxqqMapper.selectBr50_cxqq_mxByVo(br50_cxqq);
		if (queryrequest_mx == null) {
			queryrequest_mx = new Br50_cxqq_mx();
		}
		return queryrequest_mx;
	}
	
	/**
	 * 获取反馈基本信息表
	 * 
	 * @param cg_q_main
	 * @return
	 * @throws Exception
	 */
	private Br50_cxqq_back getBr50_cxqq_back(Br50_cxqq br50_cxqq) throws Exception {
		Br50_cxqq_back br50_cxqq_back = br50_cxqqMapper.selectBr50_cxqq_backByVo(br50_cxqq);
		if (br50_cxqq_back == null) {
			br50_cxqq_back = new Br50_cxqq_back();
		}
		return br50_cxqq_back;
	}
	
	/**
	 * 处理反馈信息表
	 * 
	 * @param br24_bas_info
	 * @throws Exception
	 */
	public void updateBr50_cxqq_back(Br50_cxqq_back br50_cxqq_back) throws Exception {
		/** 查询结束时间,默认取当前期 */
		if (br50_cxqq_back.getCxfkjg().equals("")) {
			br50_cxqq_back.setCxfkjg("01");
		}
		br50_cxqq_back.setStatus("1");
		br50_cxqqMapper.updateBr50_cxqq_back(br50_cxqq_back);
		
	}
	
	public synchronized void insertMc21TaskFact3(MC21_task_fact mc21_task_fact, String docno) throws Exception {
		
		String isemployee = mc21_task_fact.getIsemployee();
		if (isemployee.equals("0")) {
			//查询同一批次下是否有未完成的
			int isok = br50_cxqqMapper.getBr50_cxqq_backCount(docno);
			if (isok == 0) {
				//插入task3任务
				mc21_task_fact.setBdhm(docno);
				super.insertMc21TaskFact3(mc21_task_fact, "GDJC");
				//修改Br50_cxqq的状态为待生成报文
				Br50_cxqq br50_cxqq = new Br50_cxqq();
				br50_cxqq.setDocno(docno);
				br50_cxqq.setStatus("1");//0：待处理 1：待生成报文  3：反馈成功 4：反馈失败
				br50_cxqq.setLast_up_dt(Utility.currDateTime19());
				br50_cxqqMapper.updateBr50_cxqq(br50_cxqq);
			}
		}
	}
}
