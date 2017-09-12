package com.citic.server.whpsb.task.taskBo;

import java.util.HashMap;
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
import com.citic.server.utils.DtUtils;
import com.citic.server.whpsb.domain.Br51_cxqq;
import com.citic.server.whpsb.domain.Br51_cxqq_back;
import com.citic.server.whpsb.domain.Br51_cxqq_mx;
import com.citic.server.whpsb.domain.Whpsb_RequestJymx_Detail;
import com.citic.server.whpsb.domain.request.Whpsb_RequestCkrzl_Detail;
import com.citic.server.whpsb.domain.request.Whpsb_RequestKhzl_Detail;
import com.citic.server.whpsb.domain.request.Whpsb_RequestZhxx_Detail;
import com.citic.server.whpsb.mapper.BR51_cxqqMapper;
import com.citic.server.whpsb.service.IDataOperateWhpsb;

/**
 * 常规查询
 * 
 * @author dk
 */
public class Cxqq_CLBo extends BaseBo {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(Cxqq_CLBo.class);
	
	private BR51_cxqqMapper br51_cxqqMapper;
	
	/** 数据获取接口 */
	private IDataOperateWhpsb dataOperate;
	/** 码值转换接口 */
	private DictCoder dictCoder;
	
	public Cxqq_CLBo(ApplicationContext ac) {
		super(ac);
		br51_cxqqMapper = (BR51_cxqqMapper) ac.getBean("BR51_cxqqMapper");
		dictCoder = (DictCoder) ac.getBean("dictCoder");
	}
	
	/**
	 * 删除单位或个人账户信息
	 */
	public void delBr51_cxqq_back_acct(String bdhm) throws Exception {
		br51_cxqqMapper.delBr51_cxqq_back_acct(bdhm);
	}
	
	public void delBr51_cxqq_back_card(String bdhm) throws Exception {
		br51_cxqqMapper.delBr51_cxqq_back_card(bdhm);
	}
	
	public void delBr51_cxqq_back_party(String bdhm) throws Exception {
		br51_cxqqMapper.delBr51_cxqq_back_party(bdhm);
	}
	
	public void delBr51_cxqq_back_trans(String bdhm) throws Exception {
		br51_cxqqMapper.delBr51_cxqq_back_trans(bdhm);
	}
	
	/**
	 * 账户信息
	 */
	public String handleCxqqWH_zhxx(String bdhm, String taskType) throws Exception {
		Br51_cxqq_back br51_cxqq_back = new Br51_cxqq_back();
		String msgseq = "";
		try {
			//查询主体明细
			Br51_cxqq_mx br51_cxqq_mx = br51_cxqqMapper.selBr51_cxqq_mx(bdhm);
			msgseq = br51_cxqq_mx.getMsgseq();
			//将监管的证件类型转换为核心的证件类型
			String credentialType = br51_cxqq_mx.getZzlx();
			if (!"".equals(credentialType)) {
				HashMap etlcodeMap = (HashMap) cacheService.getCache("BB13_etl_code_mapDetail", HashMap.class);
				HashMap whzjMap = (HashMap) etlcodeMap.get("WHGA");
				if (whzjMap != null && whzjMap.get(credentialType) != null) {
					br51_cxqq_mx.setZzlx((String) whzjMap.get(credentialType));
				}
			}
			br51_cxqq_back = br51_cxqqMapper.selBr51_cxqq_back(bdhm);
			if (br51_cxqq_back != null && br51_cxqq_back.getMsgcheckresult().equals("1")) {
				//调用接口查询出返回的账户信息
				dataOperate = (IDataOperateWhpsb) this.ac.getBean(REMOTE_DATA_OPERATE_NAME_WHPSB);
				List<Whpsb_RequestZhxx_Detail> zhxxList = dataOperate.getWhpsb_RequestZhxxList(br51_cxqq_mx);
				//插入账户明细表
				//转码待定
				String qrydt = DtUtils.getNowDate();
				if (zhxxList != null) {
					for (Whpsb_RequestZhxx_Detail zhxx : zhxxList) {
						zhxx.setBdhm(bdhm);
						zhxx.setQrydt(qrydt);
						zhxx.setMsgseq(msgseq);
						//将字段进行转码 reverse:核心转监管 transcode:监管转核心 
						dictCoder.reverse(zhxx, taskType);
						
						br51_cxqqMapper.insertBr51_cxqq_back_acct(zhxx);
					}
				}
			}
		} catch (DataOperateException e) {
			logger.error("查询异常" + e.getMessage());
			br51_cxqq_back.setCljg("1");//对监管而言：0-成功  1-失败
			br51_cxqq_back.setCzsbyy(e.getDescr());
		} catch (RemoteAccessException e) {
			logger.error("异常" + e.getMessage());
			br51_cxqq_back.setCljg("1");
			br51_cxqq_back.setCzsbyy("Artery异常");
		} catch (Exception e) {
			logger.error("异常" + e.getMessage());
			br51_cxqq_back.setCljg("1");
		}
		//修改响应表
		br51_cxqq_back.setBdhm(bdhm);
		this.updateBr51_cxqq_back(br51_cxqq_back);
		
		return msgseq;
	}
	
	/**
	 * 持卡人信息
	 */
	public String handleCxqqWH_Ckzl(String bdhm, String taskType) throws Exception {
		Br51_cxqq_back br51_cxqq_back = new Br51_cxqq_back();
		String msgseq = "";
		try {
			//查询主体明细
			Br51_cxqq_mx br51_cxqq_mx = br51_cxqqMapper.selBr51_cxqq_mx(bdhm);
			msgseq = br51_cxqq_mx.getMsgseq();
			br51_cxqq_back = br51_cxqqMapper.selBr51_cxqq_back(bdhm);
			if (br51_cxqq_back != null && br51_cxqq_back.getMsgcheckresult().equals("1")) {
				//调用接口查询出返回的账户信息
				dataOperate = (IDataOperateWhpsb) this.ac.getBean(REMOTE_DATA_OPERATE_NAME_WHPSB);
				Whpsb_RequestCkrzl_Detail ckr = dataOperate.getWhpsb_RequestCkzlList(br51_cxqq_mx);
				//插入账户明细表
				//转码待定
				String qrydt = DtUtils.getNowDate();
				if (ckr != null) {
					ckr.setBdhm(bdhm);
					ckr.setQrydt(qrydt);
					ckr.setMsgseq(msgseq);
					//将字段进行转码 reverse:核心转监管 transcode:监管转核心 
					dictCoder.reverse(ckr, taskType);
					
					br51_cxqqMapper.insertBr51_cxqq_back_card(ckr);
				}
			}
		} catch (DataOperateException e) {
			logger.error("查询异常" + e.getMessage());
			br51_cxqq_back.setCljg("1");//对监管而言：0-成功  1-失败
			br51_cxqq_back.setCzsbyy(e.getDescr());
		} catch (RemoteAccessException e) {
			logger.error("异常" + e.getMessage());
			br51_cxqq_back.setCljg("1");
			br51_cxqq_back.setCzsbyy("Artery异常");
		} catch (Exception e) {
			logger.error("异常" + e.getMessage());
			br51_cxqq_back.setCljg("1");
		}
		//修改响应表
		br51_cxqq_back.setBdhm(bdhm);
		this.updateBr51_cxqq_back(br51_cxqq_back);
		
		return msgseq;
	}
	
	/**
	 * 开户信息
	 */
	public String handleCxqqWH_Khzl(String bdhm, String taskType) throws Exception {
		Br51_cxqq_back br51_cxqq_back = new Br51_cxqq_back();
		String msgseq = "";
		try {
			//查询主体明细
			Br51_cxqq_mx br51_cxqq_mx = br51_cxqqMapper.selBr51_cxqq_mx(bdhm);
			msgseq = br51_cxqq_mx.getMsgseq();
			//将监管的证件类型转换为核心的证件类型
			String credentialType = br51_cxqq_mx.getZzlx();
			if (!"".equals(credentialType)) {
				HashMap etlcodeMap = (HashMap) cacheService.getCache("BB13_etl_code_mapDetail", HashMap.class);
				HashMap whzjMap = (HashMap) etlcodeMap.get("WHGA");
				if (whzjMap != null && whzjMap.get(credentialType) != null) {
					br51_cxqq_mx.setZzlx((String) whzjMap.get(credentialType));
				}
			}
			br51_cxqq_back = br51_cxqqMapper.selBr51_cxqq_back(bdhm);
			if (br51_cxqq_back != null && br51_cxqq_back.getMsgcheckresult().equals("1")) {
				//调用接口查询出返回的账户信息
				dataOperate = (IDataOperateWhpsb) this.ac.getBean(REMOTE_DATA_OPERATE_NAME_WHPSB);
				Whpsb_RequestKhzl_Detail khzl = dataOperate.getWhpsb_RequestKhzlList(br51_cxqq_mx);
				//插入账户明细表
				//转码待定
				String qrydt = DtUtils.getNowDate();
				if (khzl != null) {
					khzl.setBdhm(bdhm);
					khzl.setQrydt(qrydt);
					khzl.setMsgseq(msgseq);
					//将字段进行转码 reverse:核心转监管 transcode:监管转核心 
					dictCoder.reverse(khzl, taskType);
					
					br51_cxqqMapper.insertBr51_cxqq_back_party(khzl);
				}
			}
		} catch (DataOperateException e) {
			logger.error("查询异常" + e.getMessage());
			br51_cxqq_back.setCljg("1");//对监管而言：0-成功  1-失败
			br51_cxqq_back.setCzsbyy(e.getDescr());
		} catch (RemoteAccessException e) {
			logger.error("异常" + e.getMessage());
			br51_cxqq_back.setCljg("1");
			//			br51_cxqq_back.setCzsbyy("Artery异常");	
			br51_cxqq_back.setCzsbyy("查无数据");
		} catch (Exception e) {
			logger.error("异常" + e.getMessage());
			br51_cxqq_back.setCljg("1");
		}
		//修改响应表
		br51_cxqq_back.setBdhm(bdhm);
		this.updateBr51_cxqq_back(br51_cxqq_back);
		
		return msgseq;
	}
	
	/**
	 * 交易信息
	 */
	public String handleCxqqWH_Jymx(String bdhm, String taskType) throws Exception {
		Br51_cxqq_back br51_cxqq_back = new Br51_cxqq_back();
		String msgseq = "";
		try {
			//查询主体明细
			Br51_cxqq_mx br51_cxqq_mx = br51_cxqqMapper.selBr51_cxqq_mx(bdhm);
			msgseq = br51_cxqq_mx.getMsgseq();
			br51_cxqq_back = br51_cxqqMapper.selBr51_cxqq_back(bdhm);
			if (br51_cxqq_back != null && br51_cxqq_back.getMsgcheckresult().equals("1")) {
				//调用接口查询出返回的账户信息
				dataOperate = (IDataOperateWhpsb) this.ac.getBean(REMOTE_DATA_OPERATE_NAME_WHPSB);
				List<Whpsb_RequestJymx_Detail> transList = dataOperate.getWhpsb_RequestJymxList(br51_cxqq_mx);
				//插入账户明细表
				//转码待定
				String qrydt = DtUtils.getNowDate();
				String ah = br51_cxqq_mx.getAh();
				if (transList != null) {
					for (Whpsb_RequestJymx_Detail trans : transList) {
						trans.setBdhm(bdhm);
						trans.setQrydt(qrydt);
						trans.setAh(ah);
						trans.setMsgseq(msgseq);
						//将字段进行转码 reverse:核心转监管 transcode:监管转核心 
						dictCoder.reverse(trans, taskType);
						br51_cxqqMapper.insertBr51_cxqq_back_trans(trans);
					}
				}
			}
		} catch (DataOperateException e) {
			logger.error("查询异常" + e.getMessage());
			br51_cxqq_back.setCljg("1");//对监管而言：0-成功  1-失败
			br51_cxqq_back.setCzsbyy(e.getDescr());
		} catch (RemoteAccessException e) {
			logger.error("异常" + e.getMessage());
			br51_cxqq_back.setCljg("1");
			br51_cxqq_back.setCzsbyy("Artery异常");
		} catch (Exception e) {
			logger.error("异常" + e.getMessage());
			br51_cxqq_back.setCljg("1");
		}
		//修改响应表
		br51_cxqq_back.setBdhm(bdhm);
		this.updateBr51_cxqq_back(br51_cxqq_back);
		
		return msgseq;
	}
	
	public void updateBr51_cxqq_back(Br51_cxqq_back br51_cxqq_back) {
		br51_cxqq_back.setStatus("1");
		br51_cxqq_back.setLast_up_dt(Utility.currDateTime19());
		br51_cxqqMapper.updateBr51_cxqq_back(br51_cxqq_back);
	}
	
	/**
	 * task3任务插入
	 * 保证同一批次文件处理完成后才进入下一阶段
	 */
	public synchronized void insertMc21TaskFact3(MC21_task_fact mc21_task_fact, String msgseq) throws Exception {
		
		String isemployee = mc21_task_fact.getIsemployee();
		if (isemployee.equals("0")) {
			//查询同一批次下是否有未完成的
			int count = br51_cxqqMapper.isMakeMsg(msgseq);
			if (count == 0) {
				//插入task3任务
				mc21_task_fact.setBdhm(msgseq);
				super.insertMc21TaskFact3(mc21_task_fact, "whpsb");
				//修改Br51_cxqq的状态为待生成报文
				Br51_cxqq br51_cxqq = new Br51_cxqq();
				br51_cxqq.setMsgseq(msgseq);
				br51_cxqq.setStatus("1");//0：待处理 1：待生成报文 2：待打包反馈 3：反馈成功 4：反馈失败
				br51_cxqq.setLast_up_dt(Utility.currDateTime19());
				br51_cxqqMapper.updateBr51_cxqq(br51_cxqq);
			}
		}
		
	}
	
}
