package com.citic.server.shpsb.task.taskBo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.dict.DictCoder;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.runtime.Utility;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.service.task.taskBo.BaseBo;
import com.citic.server.shpsb.domain.Br54_cxqq;
import com.citic.server.shpsb.domain.Br54_cxqq_back;
import com.citic.server.shpsb.domain.ShpsbSadx;
import com.citic.server.shpsb.domain.request.ShpsbRequestCzrzMx;
import com.citic.server.shpsb.domain.request.ShpsbRequestDdzhMx;
import com.citic.server.shpsb.domain.request.ShpsbRequestJyglhMx;
import com.citic.server.shpsb.domain.request.ShpsbRequestJylsMx;
import com.citic.server.shpsb.domain.request.ShpsbRequestKhzlMx;
import com.citic.server.shpsb.domain.request.ShpsbRequestZhcyrMx;
import com.citic.server.shpsb.domain.request.ShpsbRequestZhxxMx;
import com.citic.server.shpsb.mapper.BR54_cxqqMapper;
import com.citic.server.shpsb.service.IDataOperateShpsb;
import com.citic.server.utils.DtUtils;

/**
 * 常规查询
 * 
 * @author dk
 */
public class Cxqq_CLBo extends BaseBo {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(Cxqq_CLBo.class);
	
	private BR54_cxqqMapper br54_cxqqMapper;
	
	/** 数据获取接口 */
	private IDataOperateShpsb dataOperate;
	
	/** 码值转换接口 */
	private DictCoder dictCoder;
	
	public Cxqq_CLBo(ApplicationContext ac) {
		super(ac);
		br54_cxqqMapper = (BR54_cxqqMapper) ac.getBean("BR54_cxqqMapper");
		dictCoder = (DictCoder) ac.getBean("dictCoder");
		dataOperate = (IDataOperateShpsb) ac.getBean(REMOTE_DATA_OPERATE_NAME_SHPSB);
	}
	
	/**
	 * 删除单位或个人账户信息
	 */
	public void delBr54_cxqq_back_acct(String bdhm) throws Exception {
		br54_cxqqMapper.delBr54_cxqq_back_acct(bdhm);
	}
	
	public void delBr54_cxqq_back_card(String bdhm) throws Exception {
		br54_cxqqMapper.delBr54_cxqq_back_card(bdhm);
	}
	
	public void delBr54_cxqq_back_party(String bdhm) throws Exception {
		br54_cxqqMapper.delBr54_cxqq_back_party(bdhm);
	}
	
	public void delBr54_cxqq_back_trans(String bdhm) throws Exception {
		br54_cxqqMapper.delBr54_cxqq_back_trans(bdhm);
	}
	
	public void delBr54_cxqq_back_czrz(String bdhm) throws Exception {
		br54_cxqqMapper.delBr54_cxqq_back_czrz(bdhm);
	}
	
	public void delBr54_cxqq_back_jyglh(String bdhm) throws Exception {
		br54_cxqqMapper.delBr54_cxqq_back_jyglh(bdhm);
	}
	
	public void delBr54_cxqq_back_ddzh(String bdhm) throws Exception {
		br54_cxqqMapper.delBr54_cxqq_back_ddzh(bdhm);
	}
	
	/**
	 * 账户信息
	 */
	public String handleCxqqSH_zhxx(String bdhm, String taskType) throws Exception {
		Br54_cxqq_back br54_cxqq_back = new Br54_cxqq_back();
		String msgseq = "";
		try {
			// 查询主体明细
			ShpsbSadx br54_cxqq_mx = br54_cxqqMapper.selBr54_cxqq_mx(bdhm);
			msgseq = br54_cxqq_mx.getMsgseq();
			// 转码
			dictCoder.transcode(br54_cxqq_mx, taskType);
			
			br54_cxqq_back = br54_cxqqMapper.selBr54_cxqq_back(bdhm);
			if (br54_cxqq_back != null && br54_cxqq_back.getMsgcheckresult().equals("1")) {
				//调用接口查询出返回的账户信息
				List<ShpsbRequestZhxxMx> zhxxList = dataOperate.getAccountInformation(br54_cxqq_mx);
				//转码待定
				String qrydt = DtUtils.getNowDate();
				if (zhxxList != null) {
					for (ShpsbRequestZhxxMx zhxx : zhxxList) {
						zhxx.setBdhm(bdhm);
						zhxx.setQrydt(qrydt);
						zhxx.setMsgseq(msgseq);
						dictCoder.reverse(zhxx, taskType); // 转码
						//插入账户明细表
						br54_cxqqMapper.insertBr54_cxqq_back_acct(zhxx);
					}
				}
			}
		} catch (DataOperateException e) {
			br54_cxqq_back.setCljg("1");//对监管而言：0-成功  1-失败
			br54_cxqq_back.setCzsbyy(e.getDescr());
			logger.warn("数据处理异常：{}", e.getMessage());
		} catch (RemoteAccessException e) {
			br54_cxqq_back.setCljg("1");
			br54_cxqq_back.setCzsbyy("应用程序异常");
			logger.error("应用程序异常：{}", e.getMessage(), e);
		}
		
		//修改响应表
		br54_cxqq_back.setBdhm(bdhm);
		this.updateBr54_cxqq_back(br54_cxqq_back);
		
		return msgseq;
	}
	
	/**
	 * 持卡人信息
	 */
	public String handleCxqqSH_Zhcyr(String bdhm, String taskType) throws Exception {
		Br54_cxqq_back br54_cxqq_back = new Br54_cxqq_back();
		String msgseq = "";
		try {
			//查询主体明细
			ShpsbSadx br54_cxqq_mx = br54_cxqqMapper.selBr54_cxqq_mx(bdhm);
			msgseq = br54_cxqq_mx.getMsgseq();
			br54_cxqq_back = br54_cxqqMapper.selBr54_cxqq_back(bdhm);
			if (br54_cxqq_back != null && br54_cxqq_back.getMsgcheckresult().equals("1")) {
				//调用接口查询出返回的账户信息
				ShpsbRequestZhcyrMx ckr = dataOperate.getAccountPossessor(br54_cxqq_mx);
				//插入账户明细表
				//转码待定
				String qrydt = DtUtils.getNowDate();
				if (ckr != null) {
					ckr.setBdhm(bdhm);
					ckr.setQrydt(qrydt);
					ckr.setMsgseq(msgseq);
					
					dictCoder.reverse(ckr, taskType); // 转码
					br54_cxqqMapper.insertBr54_cxqq_back_card(ckr);
				}
			}
		} catch (DataOperateException e) {
			br54_cxqq_back.setCljg("1"); // 对监管而言：0-成功  1-失败
			br54_cxqq_back.setCzsbyy(e.getDescr());
			logger.warn("数据处理异常：{}", e.getMessage());
		} catch (RemoteAccessException e) {
			br54_cxqq_back.setCljg("1");
			br54_cxqq_back.setCzsbyy("应用程序异常");
			logger.error("应用程序异常：{}", e.getMessage(), e);
		}
		
		//修改响应表
		br54_cxqq_back.setBdhm(bdhm);
		this.updateBr54_cxqq_back(br54_cxqq_back);
		
		return msgseq;
	}
	
	/**
	 * 开户信息
	 */
	public String handleCxqqSH_Khzl(String bdhm, String taskType) throws Exception {
		Br54_cxqq_back br54_cxqq_back = new Br54_cxqq_back();
		String msgseq = "";
		try {
			// 查询主体明细
			ShpsbSadx br54_cxqq_mx = br54_cxqqMapper.selBr54_cxqq_mx(bdhm);
			dictCoder.transcode(br54_cxqq_mx, taskType); // 转码
			
			msgseq = br54_cxqq_mx.getMsgseq();
			br54_cxqq_back = br54_cxqqMapper.selBr54_cxqq_back(bdhm);
			if (br54_cxqq_back != null && br54_cxqq_back.getMsgcheckresult().equals("1")) {
				// 调用接口查询出返回的账户信息
				ShpsbRequestKhzlMx khzl = dataOperate.getAccountOpenInformation(br54_cxqq_mx);
				// 插入账户明细表
				String qrydt = DtUtils.getNowDate();
				if (khzl != null) {
					khzl.setBdhm(bdhm);
					khzl.setQrydt(qrydt);
					khzl.setMsgseq(msgseq);
					
					dictCoder.reverse(khzl, taskType); // 逆向转码
					br54_cxqqMapper.insertBr54_cxqq_back_party(khzl);
				}
			}
		} catch (DataOperateException e) {
			br54_cxqq_back.setCljg("1"); // 对监管而言：0-成功  1-失败
			br54_cxqq_back.setCzsbyy(e.getDescr());
			logger.warn("数据处理异常：{}", e.getMessage());
		} catch (RemoteAccessException e) {
			br54_cxqq_back.setCljg("1");
			br54_cxqq_back.setCzsbyy("查无数据");
			logger.error("应用程序异常：{}", e.getMessage(), e);
		}
		
		//修改响应表
		br54_cxqq_back.setBdhm(bdhm);
		this.updateBr54_cxqq_back(br54_cxqq_back);
		
		return msgseq;
	}
	
	/**
	 * 交易信息
	 */
	public String handleCxqqSH_Jymx(String bdhm, String taskType) throws Exception {
		Br54_cxqq_back br54_cxqq_back = new Br54_cxqq_back();
		String msgseq = "";
		try {
			//查询主体明细
			ShpsbSadx br54_cxqq_mx = br54_cxqqMapper.selBr54_cxqq_mx(bdhm);
			msgseq = br54_cxqq_mx.getMsgseq();
			br54_cxqq_back = br54_cxqqMapper.selBr54_cxqq_back(bdhm);
			if (br54_cxqq_back != null && br54_cxqq_back.getMsgcheckresult().equals("1")) {
				//调用接口查询出返回的账户信息
				List<ShpsbRequestJylsMx> transList = dataOperate.getAccountTransaction(br54_cxqq_mx);
				//插入账户明细表
				//转码待定
				String qrydt = DtUtils.getNowDate();
				String ah = br54_cxqq_mx.getAh();
				if (transList != null) {
					for (ShpsbRequestJylsMx trans : transList) {
						trans.setAh(ah);
						trans.setBdhm(bdhm);
						trans.setQrydt(qrydt);
						trans.setMsgseq(msgseq);
						
						dictCoder.reverse(trans, taskType); // 逆向转码
						br54_cxqqMapper.insertBr54_cxqq_back_trans(trans);
					}
				}
			}
		} catch (DataOperateException e) {
			br54_cxqq_back.setCljg("1"); // 对监管而言：0-成功  1-失败
			br54_cxqq_back.setCzsbyy(e.getDescr());
			logger.warn("数据处理异常：{}", e.getMessage());
		} catch (RemoteAccessException e) {
			br54_cxqq_back.setCljg("1");
			br54_cxqq_back.setCzsbyy("应用程序异常");
			logger.error("应用程序异常：{}", e.getMessage(), e);
		}
		//修改响应表
		br54_cxqq_back.setBdhm(bdhm);
		this.updateBr54_cxqq_back(br54_cxqq_back);
		
		return msgseq;
	}
	
	/**
	 * 操作日志
	 */
	public String handleCxqqSH_czrz(String bdhm, String taskType) throws Exception {
		Br54_cxqq_back br54_cxqq_back = new Br54_cxqq_back();
		String msgseq = "";
		try {
			//查询主体明细
			ShpsbSadx br54_cxqq_mx = br54_cxqqMapper.selBr54_cxqq_mx(bdhm);
			msgseq = br54_cxqq_mx.getMsgseq();
			br54_cxqq_back = br54_cxqqMapper.selBr54_cxqq_back(bdhm);
			if (br54_cxqq_back != null && br54_cxqq_back.getMsgcheckresult().equals("1")) {
				List<ShpsbRequestCzrzMx> czrzList = dataOperate.getOperationLog(br54_cxqq_mx);
				//转码待定
				String qrydt = DtUtils.getNowDate();
				
				if (czrzList != null) {
					for (ShpsbRequestCzrzMx czrz : czrzList) {
						czrz.setBdhm(bdhm);
						czrz.setQrydt(qrydt);
						czrz.setMsgseq(msgseq);
						br54_cxqqMapper.insertBr54_cxqq_back_czrz(czrz);
					}
				}
			}
		} catch (DataOperateException e) {
			br54_cxqq_back.setCljg("1");//对监管而言：0-成功  1-失败
			br54_cxqq_back.setCzsbyy(e.getDescr());
			logger.warn("数据处理异常：{}", e.getMessage());
		} catch (RemoteAccessException e) {
			br54_cxqq_back.setCljg("1");
			br54_cxqq_back.setCzsbyy("应用程序异常");
			logger.error("应用程序异常：{}", e.getMessage(), e);
		}
		
		//修改响应表
		br54_cxqq_back.setBdhm(bdhm);
		this.updateBr54_cxqq_back(br54_cxqq_back);
		
		return msgseq;
	}
	
	/**
	 * 交易关联号
	 */
	public String handleCxqqSH_jyglh(String bdhm, String taskType) throws Exception {
		Br54_cxqq_back br54_cxqq_back = new Br54_cxqq_back();
		String msgseq = "";
		try {
			//查询主体明细
			ShpsbSadx br54_cxqq_mx = br54_cxqqMapper.selBr54_cxqq_mx(bdhm);
			msgseq = br54_cxqq_mx.getMsgseq();
			br54_cxqq_back = br54_cxqqMapper.selBr54_cxqq_back(bdhm);
			if (br54_cxqq_back != null && br54_cxqq_back.getMsgcheckresult().equals("1")) {
				List<ShpsbRequestJyglhMx> jyglnList = dataOperate.getAffiliatedTransactionNo(br54_cxqq_mx);
				//转码待定
				String qrydt = DtUtils.getNowDate();
				
				if (jyglnList != null) {
					for (ShpsbRequestJyglhMx jygln : jyglnList) {
						jygln.setBdhm(bdhm);
						jygln.setQrydt(qrydt);
						jygln.setMsgseq(msgseq);
						br54_cxqqMapper.insertBr54_cxqq_back_jyglh(jygln);
					}
				}
			}
		} catch (DataOperateException e) {
			br54_cxqq_back.setCljg("1");//对监管而言：0-成功  1-失败
			br54_cxqq_back.setCzsbyy(e.getDescr());
			logger.warn("数据处理异常：{}", e.getMessage());
		} catch (RemoteAccessException e) {
			br54_cxqq_back.setCljg("1");
			br54_cxqq_back.setCzsbyy("应用程序异常");
			logger.error("应用程序异常：{}", e.getMessage(), e);
		}
		
		//修改响应表
		br54_cxqq_back.setBdhm(bdhm);
		this.updateBr54_cxqq_back(br54_cxqq_back);
		
		return msgseq;
	}
	
	/**
	 * 对端账号
	 */
	public String handleCxqqSH_ddzh(String bdhm, String taskType) throws Exception {
		Br54_cxqq_back br54_cxqq_back = new Br54_cxqq_back();
		String msgseq = "";
		try {
			//查询主体明细
			ShpsbSadx br54_cxqq_mx = br54_cxqqMapper.selBr54_cxqq_mx(bdhm);
			msgseq = br54_cxqq_mx.getMsgseq();
			br54_cxqq_back = br54_cxqqMapper.selBr54_cxqq_back(bdhm);
			if (br54_cxqq_back != null && br54_cxqq_back.getMsgcheckresult().equals("1")) {
				List<ShpsbRequestDdzhMx> ddzhList = dataOperate.getReciprocalAccount(br54_cxqq_mx);
				//转码待定
				String qrydt = DtUtils.getNowDate();
				
				if (ddzhList != null) {
					for (ShpsbRequestDdzhMx ddzh : ddzhList) {
						ddzh.setBdhm(bdhm);
						ddzh.setQrydt(qrydt);
						ddzh.setMsgseq(msgseq);
						br54_cxqqMapper.insertBr54_cxqq_back_ddzh(ddzh);
					}
				}
			}
		} catch (DataOperateException e) {
			br54_cxqq_back.setCljg("1");//对监管而言：0-成功  1-失败
			br54_cxqq_back.setCzsbyy(e.getDescr());
			logger.warn("数据处理异常：{}", e.getMessage());
		} catch (RemoteAccessException e) {
			br54_cxqq_back.setCljg("1");
			br54_cxqq_back.setCzsbyy("应用程序异常");
			logger.error("应用程序异常：{}", e.getMessage(), e);
		}
		//修改响应表
		br54_cxqq_back.setBdhm(bdhm);
		this.updateBr54_cxqq_back(br54_cxqq_back);
		
		return msgseq;
	}
	
	public void updateBr54_cxqq_back(Br54_cxqq_back br54_cxqq_back) {
		br54_cxqq_back.setStatus("1");
		br54_cxqq_back.setLast_up_dt(Utility.currDateTime19());
		br54_cxqqMapper.updateBr54_cxqq_back(br54_cxqq_back);
	}
	
	/**
	 * task3任务插入
	 * 保证同一批次文件处理完成后才进入下一阶段
	 */
	public synchronized void insertMc21TaskFact3(MC21_task_fact mc21_task_fact, String msgseq) throws Exception {
		
		String isemployee = mc21_task_fact.getIsemployee();
		if (isemployee.equals("0")) {
			//查询同一批次下是否有未完成的
			int count = br54_cxqqMapper.isMakeMsg(msgseq);
			if (count == 0) {
				//插入task3任务
				mc21_task_fact.setBdhm(msgseq);
				super.insertMc21TaskFact3(mc21_task_fact, "shpsb");
				//修改Br54_cxqq的状态为待生成报文
				Br54_cxqq br54_cxqq = new Br54_cxqq();
				br54_cxqq.setMsgseq(msgseq);
				br54_cxqq.setStatus("1");//0：待处理 1：待生成报文 2：待打包反馈 3：反馈成功 4：反馈失败
				br54_cxqq.setLast_up_dt(Utility.currDateTime19());
				br54_cxqqMapper.updateBr54_cxqq(br54_cxqq);
			}
		}
	}
}
