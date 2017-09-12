package com.citic.server.gdjg.task.taskBo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.dict.DictCoder;
import com.citic.server.gdjg.GdjgConstants;
import com.citic.server.gdjg.domain.Br57_kzqq;
import com.citic.server.gdjg.domain.Br57_kzqq_back;
import com.citic.server.gdjg.domain.Br57_kzqq_input;
import com.citic.server.gdjg.domain.Br57_kzqq_mx;
import com.citic.server.gdjg.domain.Br57_kzqq_mx_policeman;
import com.citic.server.gdjg.domain.request.Gdjg_Request_AccCx;
import com.citic.server.gdjg.domain.request.Gdjg_Request_BankCx;
import com.citic.server.gdjg.mapper.MM57_kzqqMapper;
import com.citic.server.gdjg.service.IDataOperateGdjg;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.runtime.Utility;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.service.task.taskBo.BaseBo;
import com.citic.server.utils.DtUtils;

/**
 * 广东省检察院
 * 
 * @author liuYing
 * @date 2017年5月25日 下午9:56:46
 */
public class Kzqq_CLBo extends BaseBo implements GdjgConstants {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(Kzqq_CLBo.class);
	
	/** 数据获取接口 */
	private IDataOperateGdjg dataOperate;
	
	/** 码表转换接口 */
	private DictCoder dictCoder;
	
	private MM57_kzqqMapper br57_kzqqMapper;
	
	public Kzqq_CLBo(ApplicationContext ac) {
		super(ac);
		this.br57_kzqqMapper = (MM57_kzqqMapper) ac.getBean("MM57_kzqqMapper");
		this.dictCoder = (DictCoder) ac.getBean("dictCoder");
	}
	
	/**
	 * 删除控制信息
	 * 
	 * @param bdhm
	 * @throws Exception
	 */
	public void delKzqqInfo05(MC21_task_fact mc21_task_fact) {
		//br57_kzqqMapper.delBr57_kzqq_back_freezeresult(mc21_task_fact);
	}
	
	public void delKzqqInfo06(MC21_task_fact mc21_task_fact) {
		//br57_kzqqMapper.delBr57_kzqq_back_unfreezeresult(mc21_task_fact);
	}
	
	public void delKzqqInfo07(MC21_task_fact mc21_task_fact) {
		//br57_kzqqMapper.delBr57_kzqq_back_stoppayment(mc21_task_fact);
	}
	
	/**
	 * 处理银行存款冻结控制任务
	 * 
	 * @param mc21_task_fact
	 * @return
	 * @author liuxuanfei
	 * @date 2017年6月17日 下午2:03:43
	 */
	public String handleQueryResponse05(MC21_task_fact mc21_task_fact) {
		String taskType = mc21_task_fact.getTasktype(); // 任务代号
		String docno = "";
		String caseno = "";
		//从task2获取docno和caseno
		if (!StringUtils.isBlank(mc21_task_fact.getTaskobj())) {
			String[] str = mc21_task_fact.getTaskobj().split(";");
			docno = str[0];
			caseno = str[1];
		}
		//1.查询请求内容
		Br57_kzqq br57_kzqq = new Br57_kzqq();
		br57_kzqq.setDocno(docno);
		br57_kzqq.setCaseno(caseno);
		br57_kzqq.setUniqueid(mc21_task_fact.getBdhm());
		// 1.1.根据请求单标识、监管类别获取查询主体信息内容
		Br57_kzqq_mx br57_kzqq_mx = this.getQueryRequest_mx(br57_kzqq);
		//1.2  获取办案人信息
		List<Br57_kzqq_mx_policeman> mx_policeman = this.getQueryRequest_mx_policeman(br57_kzqq);
		
		//1.3 获取输入项信息
		List<Br57_kzqq_input> br57_kzqq_inputList = this.getBr57_kzqq_inputList(br57_kzqq_mx);
		String cxfkjg = "01";
		String czsbyy = "冻结执行成功";
		for (Br57_kzqq_input br57_kzqq_input : br57_kzqq_inputList) {
			// 1.4、获取反馈基本信息表
			Br57_kzqq_back br57_kzqq_back = this.getBr57_kzqq_back(br57_kzqq_input);
			
			logger.info("进行冻结/续冻任务去重：");
			int num = br57_kzqqMapper.selectNum_FreezeTask(br57_kzqq_input);
			List<String>  status = new ArrayList<String>();
			status = br57_kzqqMapper.selectStatus_PreFreezeTask(br57_kzqq_input);
			if ((num > 1) && (status.contains("01"))) {
				cxfkjg = "02";
				czsbyy = "该冻结请求任务为已经成功执行过的重复任务，无法重复执行";
				//写反馈基本信息表 
				br57_kzqq_back.setKzfkjg(cxfkjg);
				br57_kzqq_back.setCzsbyy(czsbyy);
				updateBr57_kzqq_back(br57_kzqq_back);
			} else {
				//预处理br57_kzqq_input,获取核心对应流水号
				br57_kzqq_input = this.pretreatFreezeBr57_kzqq_input(br57_kzqq_input); //为续冻的冻结流水号进行的预处理，置换为artery的流水号
				
				//2.初始化接口
				dataOperate = (IDataOperateGdjg) this.ac.getBean(REMOTE_DATA_OPERATE_NAME_GDJG);
				dictCoder.transcode(br57_kzqq_input, null);
				
				logger.info("执行存款冻结申请：");
				try {
					Gdjg_Request_BankCx queryControl_Freeze = dataOperate.getFreezeResult(br57_kzqq_input, br57_kzqq_mx, mx_policeman);
					if (queryControl_Freeze != null) {
						List<Gdjg_Request_AccCx> accList = queryControl_Freeze.getAccs();
						if (accList != null && accList.size() > 0) {
							for (Gdjg_Request_AccCx account : accList) {
								account.setUniqueid(br57_kzqq_mx.getUniqueid());
								account.setCaseno(br57_kzqq_mx.getCaseno());
								account.setDocno(br57_kzqq_mx.getDocno());
								account.setQrydt(DtUtils.getNowDate());
								//将字段进行转码
								dictCoder.reverse(account, taskType);
								fromodeTrans(account); //此处为了修正转码产生的坑	，由于多个类共用了一个dto导致的，不必深究；						
								br57_kzqqMapper.updateBr57_kzqq_inputByFreeze(account); // 将冻结编号（Artery）插入Br57_kzqq_input 
							}
						}
						
						if (accList != null && accList.size() > 0) {
							br57_kzqqMapper.batchInsertBr57_kzqq_back_FreezeResult(accList);
						} else {
							cxfkjg = "02";
							czsbyy = "账号错误或信息不全，无法执行冻结";
						}
					}
					
				} catch (DataOperateException e) {
					// 3.错误统一采用异常处理
					logger.error("数据处理异常," + e.getMessage());
					cxfkjg = "02";
					czsbyy = e.getMessage();
					
				} catch (RemoteAccessException e) {
					e.printStackTrace();
					logger.error("Artery异常," + e.getMessage());
					cxfkjg = "02";
					czsbyy = "应用程序异常";
				}
				// 4、写反馈基本信息表 
				br57_kzqq_back.setKzfkjg(cxfkjg);
				br57_kzqq_back.setCzsbyy(czsbyy);
				updateBr57_kzqq_back(br57_kzqq_back);
			}
		}
		return docno;
	}
	
	/**
	 * 处理银行存款解冻控制任务
	 * 
	 * @param mc21_task_fact
	 * @return
	 * @author liuxuanfei
	 * @date 2017年6月17日 下午2:03:43
	 */
	public String handleQueryResponse06(MC21_task_fact mc21_task_fact) {
		String taskType = mc21_task_fact.getTasktype(); // 任务代号
		String docno = "";
		String caseno = "";
		//从task2获取docno和caseno
		if (!StringUtils.isBlank(mc21_task_fact.getTaskobj())) {
			String[] str = mc21_task_fact.getTaskobj().split(";");
			docno = str[0];
			caseno = str[1];
		}
		//1.查询请求内容
		Br57_kzqq br57_kzqq = new Br57_kzqq();
		br57_kzqq.setDocno(docno);
		br57_kzqq.setCaseno(caseno);
		br57_kzqq.setUniqueid(mc21_task_fact.getBdhm());
		// 1.1.根据请求单标识、监管类别获取查询主体信息内容
		Br57_kzqq_mx br57_kzqq_mx = this.getQueryRequest_mx(br57_kzqq);
		//1.2  获取办案人信息
		List<Br57_kzqq_mx_policeman> mx_policeman = this.getQueryRequest_mx_policeman(br57_kzqq);
		
		//1.3 获取输入项信息
		List<Br57_kzqq_input> br57_kzqq_inputList = this.getBr57_kzqq_inputList(br57_kzqq_mx);
		String cxfkjg = "01";
		String czsbyy = "解冻成功";
		for (Br57_kzqq_input br57_kzqq_input : br57_kzqq_inputList) {
			// 1.4、获取反馈基本信息表
			Br57_kzqq_back br57_kzqq_back = this.getBr57_kzqq_back(br57_kzqq_input);
			
			logger.info("进行解冻任务去重：");
			//getFilterRequest();
			int num = br57_kzqqMapper.selectNum_UnFreezeTask(br57_kzqq_input);
			List<String> status = new ArrayList<String>();
			status = br57_kzqqMapper.selectStatus_PreUnfreezeTask(br57_kzqq_input);
			if ((num > 1) && (status.contains("01"))) {
				cxfkjg = "02";
				czsbyy = "该解冻请求任务为重复任务，无法执行";
				//写反馈基本信息表 
				br57_kzqq_back.setKzfkjg(cxfkjg);
				br57_kzqq_back.setCzsbyy(czsbyy);
				updateBr57_kzqq_back(br57_kzqq_back);
			} else {
				br57_kzqq_input = this.pretreatUnfreezeBr57_kzqq_input(br57_kzqq_input);//为解冻的冻结流水号进行的预处理，置换为artery的流水号
				//2.初始化接口
				dataOperate = (IDataOperateGdjg) this.ac.getBean(REMOTE_DATA_OPERATE_NAME_GDJG);
				dictCoder.transcode(br57_kzqq_input, null);
				
				logger.info("执行存款解冻申请：");
				try {
					Gdjg_Request_BankCx queryControl_Unfreeze = dataOperate.getUnfreezeResult(br57_kzqq_input, br57_kzqq_mx, mx_policeman);
					
					if (queryControl_Unfreeze != null) {
						List<Gdjg_Request_AccCx> accList = queryControl_Unfreeze.getAccs();
						if (accList != null && accList.size() > 0) {
							for (Gdjg_Request_AccCx account : accList) {
								account.setUniqueid(br57_kzqq_mx.getUniqueid());
								account.setCaseno(br57_kzqq_mx.getCaseno());
								account.setDocno(br57_kzqq_mx.getDocno());
								account.setQrydt(DtUtils.getNowDate());
								//将字段进行转码
								dictCoder.reverse(account, taskType);
								// 将解冻编号（Artery）插入Br57_kzqq_input 
								br57_kzqqMapper.updateBr57_kzqq_inputByUnfreeze(account);
							}
						}
						
						if (accList != null && accList.size() > 0) {
							br57_kzqqMapper.batchInsertBr57_kzqq_back_UnfreezeResult(accList);
						} else {
							cxfkjg = "02";
							czsbyy = "账号错误或信息不全，无法执行解冻";
						}
					}
				} catch (DataOperateException e) {
					// 3. 错误统一采用异常处理
					logger.error("数据处理异常," + e.getMessage());
					cxfkjg = "02";
					czsbyy = e.getMessage();
				} catch (RemoteAccessException e) {
					e.printStackTrace();
					logger.error("Artery异常," + e.getMessage());
					cxfkjg = "02";
					czsbyy = "应用程序异常";
				}
				
				// 4、写反馈基本信息表 
				br57_kzqq_back.setKzfkjg(cxfkjg);
				br57_kzqq_back.setCzsbyy(czsbyy);
				updateBr57_kzqq_back(br57_kzqq_back);
			}
		}
		return docno;
	}
	
	/**
	 * 处理银行紧急止付控制任务
	 * 
	 * @param mc21_task_fact
	 * @return
	 * @author liuxuanfei
	 * @date 2017年6月17日 下午2:03:43
	 */
	public String handleQueryResponse07(MC21_task_fact mc21_task_fact) {
		String taskType = mc21_task_fact.getTasktype(); // 任务代号
		String docno = "";
		String caseno = "";
		//从task2获取docno和caseno
		if (!StringUtils.isBlank(mc21_task_fact.getTaskobj())) {
			String[] str = mc21_task_fact.getTaskobj().split(";");
			docno = str[0];
			caseno = str[1];
		}
		//1.查询请求内容
		Br57_kzqq br57_kzqq = new Br57_kzqq();
		br57_kzqq.setDocno(docno);
		br57_kzqq.setCaseno(caseno);
		br57_kzqq.setUniqueid(mc21_task_fact.getBdhm());
		// 1.1.根据请求单标识、监管类别获取查询主体信息内容
		Br57_kzqq_mx br57_kzqq_mx = this.getQueryRequest_mx(br57_kzqq);
		//1.2  获取办案人信息
		List<Br57_kzqq_mx_policeman> mx_policeman = this.getQueryRequest_mx_policeman(br57_kzqq);
		
		//1.3 获取输入项信息
		List<Br57_kzqq_input> br57_kzqq_inputList = this.getBr57_kzqq_inputList(br57_kzqq_mx);
		String cxfkjg = "01";
		String czsbyy = "（解除）紧急止付执行成功";
		for (Br57_kzqq_input br57_kzqq_input : br57_kzqq_inputList) {
			// 1.4、获取反馈基本信息表
			Br57_kzqq_back br57_kzqq_back = this.getBr57_kzqq_back(br57_kzqq_input);
			br57_kzqq_input = this.pretreatJjzfBr57_kzqq_input(br57_kzqq_input); //为紧急止付的冻结流水号进行的预处理，置换为artery的流水号
			
			//2.初始化接口
			dataOperate = (IDataOperateGdjg) this.ac.getBean(REMOTE_DATA_OPERATE_NAME_GDJG);
			dictCoder.transcode(br57_kzqq_input, null);
			
			logger.info("执行紧急止付申请：");
			try {
				Gdjg_Request_BankCx queryControl_StopPayment = dataOperate.getStopPaymentResult(br57_kzqq_input, br57_kzqq_mx, mx_policeman);
				List<Gdjg_Request_AccCx> accList = queryControl_StopPayment.getAccs();
				if (accList != null && accList.size() > 0) {
					for (Gdjg_Request_AccCx account : accList) {
						account.setUniqueid(br57_kzqq_mx.getUniqueid());
						account.setCaseno(br57_kzqq_mx.getCaseno());
						account.setDocno(br57_kzqq_mx.getDocno());
						account.setQrydt(DtUtils.getNowDate());
						//将字段进行转码
						dictCoder.reverse(account, taskType);
						// 将解冻编号（Artery）插入Br57_kzqq_input 
						br57_kzqqMapper.updateBr57_kzqq_inputByJjzf(account);
					}
				}
				
				if (accList != null && accList.size() > 0) {
					br57_kzqqMapper.batchInsertBr57_kzqq_back_StopPaymentResult(accList);
				} else {
					cxfkjg = "01";
					czsbyy = "账号错误或信息不全，无法执行紧急止付";
				}
			} catch (DataOperateException e) {
				// 3. 错误统一采用异常处理
				logger.error("数据处理异常," + e.getMessage());
				cxfkjg = "02";
				czsbyy = e.getMessage();
			} catch (RemoteAccessException e) {
				e.printStackTrace();
				logger.error("Artery异常," + e.getMessage());
				cxfkjg = "02";
				czsbyy = "应用程序异常";
			}
			
			// 4、写反馈基本信息表 
			br57_kzqq_back.setKzfkjg(cxfkjg);
			br57_kzqq_back.setCzsbyy(czsbyy);
			updateBr57_kzqq_back(br57_kzqq_back);
		}
		return docno;
	}
	
	/**
	 * 冻结输入项的预处理
	 * 
	 * @param br57_kzqq_input
	 * @return
	 * @author Liu Ying
	 * @date 2017/07/07 10:45:56
	 */
	private Br57_kzqq_input pretreatFreezeBr57_kzqq_input(Br57_kzqq_input br57_kzqq_input) {
		if ((br57_kzqq_input.getOldfroseq() != null) && (br57_kzqq_input.getOldfroseq() != "") && ("3".equals(br57_kzqq_input.getFrotype()))) {
			String successDocno = null;
			logger.info("查询是否有与原冻结流水号匹配的位于Br57_kzqq_back成功项");
			try{
				successDocno = br57_kzqqMapper.getSuccessBackDocnoByFreeze(br57_kzqq_input);
			}catch(Exception e){
				logger.error(e.getMessage());
			}
			
			if (successDocno != null) {
				logger.info("查询与原冻结流水号匹配的原Artery冻结流水号");
				String fronumber = br57_kzqqMapper.getFroNumberByFreeze(br57_kzqq_input);
				br57_kzqq_input.setOldfroseq(fronumber);
				logger.info("fronumber(核心原冻结流水号）:" + fronumber);
			}
		}
		return br57_kzqq_input;
	}
	
	/**
	 * 解冻输入项的预处理
	 * 
	 * @param br57_kzqq_input
	 * @return
	 * @author Liu Ying
	 * @date 2017/07/07 10:45:56
	 */
	private Br57_kzqq_input pretreatUnfreezeBr57_kzqq_input(Br57_kzqq_input br57_kzqq_input) {
		if ((br57_kzqq_input.getOldfroseq() != null) && (br57_kzqq_input.getOldfroseq() != "")) {
			String successDocno = null;
			logger.info("查询是否有与原冻结流水号匹配的位于Br57_kzqq_back成功项");
			try{
				successDocno = br57_kzqqMapper.getSuccessBackDocnoByUnfreeze(br57_kzqq_input);
			}catch(Exception e){
				logger.error(e.getMessage());
			}
			if (successDocno != null) {
				logger.info("查询与原冻结流水号匹配的原Artery冻结流水号");
				String fronumber = br57_kzqqMapper.getFroNumberByUnfreeze(br57_kzqq_input);
				br57_kzqq_input.setFroseq(fronumber);
				logger.info("fronumber(核心解冻原流水号）:" + fronumber);
			}
		}
		return br57_kzqq_input;
	}
	
	/**
	 * 紧急止付输入项的预处理
	 * 
	 * @param br57_kzqq_input
	 * @return
	 * @author Liu Ying
	 * @date 2017/07/07 10:45:56
	 */
	private Br57_kzqq_input pretreatJjzfBr57_kzqq_input(Br57_kzqq_input br57_kzqq_input) {
		if ((br57_kzqq_input.getOldseq() != null) && (br57_kzqq_input.getOldseq() != "") && ("1".equals(br57_kzqq_input.getStoppayment()))) {
			String fronumber = br57_kzqqMapper.getFroNumberByJjzf(br57_kzqq_input);
			br57_kzqq_input.setOldseq(fronumber);
			logger.info("fronumber:" + fronumber);
		}
		return br57_kzqq_input;
	}
	
	/**
	 * 根据请求单标识，监管类别获取查询请求主体信息
	 * 
	 * @param mc21_task_fact
	 * @return
	 */
	private Br57_kzqq_mx getQueryRequest_mx(Br57_kzqq br57_kzqq) {
		Br57_kzqq_mx queryrequest_mx = br57_kzqqMapper.selectBr57_kzqq_mxByVo(br57_kzqq);
		if (queryrequest_mx == null) {
			queryrequest_mx = new Br57_kzqq_mx();
		}
		return queryrequest_mx;
	}
	
	/**
	 * 获取办案人信息表
	 * 
	 * @param cg_q_main
	 * @return
	 * @throws Exception
	 */
	private List<Br57_kzqq_mx_policeman> getQueryRequest_mx_policeman(Br57_kzqq br57_kzqq) {
		List<Br57_kzqq_mx_policeman> mx_policeman = br57_kzqqMapper.selectBr57_kzqq_mx_policemanByVo(br57_kzqq);
		if (mx_policeman == null) {
			mx_policeman = new ArrayList<Br57_kzqq_mx_policeman>();
		}
		return mx_policeman;
	}
	
	/**
	 * 获取反馈基本信息表
	 * 
	 * @param cg_q_main
	 * @return
	 * @throws Exception
	 */
	private Br57_kzqq_back getBr57_kzqq_back(Br57_kzqq_input br57_kzqq_input) {
		Br57_kzqq_back br57_kzqq_back = br57_kzqqMapper.selectBr57_kzqq_backByVo(br57_kzqq_input);
		if (br57_kzqq_back == null) {
			br57_kzqq_back = new Br57_kzqq_back();
		}
		return br57_kzqq_back;
	}
	
	/**
	 * 获取输入项信息表
	 * 
	 * @param br57_kzqq_mx
	 * @return
	 * @author liuxuanfei
	 * @date 2017年6月16日 上午10:04:40
	 */
	private List<Br57_kzqq_input> getBr57_kzqq_inputList(Br57_kzqq_mx br57_kzqq_mx) {
		List<Br57_kzqq_input> br57_kzqq_inputList = br57_kzqqMapper.selectBr57_kzqq_inputByVo(br57_kzqq_mx);
		if (br57_kzqq_inputList == null || br57_kzqq_inputList.size() == 0) {
			Br57_kzqq_input br57_kzqq_input = new Br57_kzqq_input();
			br57_kzqq_inputList.add(br57_kzqq_input);
		}
		return br57_kzqq_inputList;
	}
	
	/**
	 * 处理反馈信息表
	 * 
	 * @param br24_bas_info
	 * @throws Exception
	 */
	private void updateBr57_kzqq_back(Br57_kzqq_back br57_kzqq_back) {
		/** 查询结束时间,默认取当前期 */
		if (br57_kzqq_back.getKzfkjg().equals("")) {
			br57_kzqq_back.setKzfkjg("01");
		}
		br57_kzqq_back.setStatus("1");
		br57_kzqqMapper.updateBr57_kzqq_back(br57_kzqq_back);
		
	}
	
	/** 插入task3任务表 */
	public synchronized void insertMc21TaskFact3(MC21_task_fact mc21_task_fact, String docno) throws Exception {
		
		String isemployee = mc21_task_fact.getIsemployee();
		if (isemployee.equals("0")) {
			//查询同一批次下是否有未完成的
			logger.info("查询同一批次下是否有未完成的任务：");
			int isok = br57_kzqqMapper.getBr57_kzqq_backCount(docno);
			if (isok == 0) {
				//插入task3任务
				mc21_task_fact.setBdhm(docno);
				logger.info("插入task3任务表：");
				super.insertMc21TaskFact3(mc21_task_fact, "GDJG");
				//修改Br50_cxqq的状态为待生成报文
				Br57_kzqq br57_kzqq = new Br57_kzqq();
				br57_kzqq.setDocno(docno);
				br57_kzqq.setStatus("1");//0：待处理 1：待生成报文  3：反馈成功 4：反馈失败
				br57_kzqq.setLast_up_dt(Utility.currDateTime19());
				br57_kzqqMapper.updateBr57_kzqq(br57_kzqq);
			}
		}
	}
	
	/** fromode转码 */
	private void fromodeTrans(Gdjg_Request_AccCx account) {
		String mode = account.getFromode();
		if ("03".equals(mode)) {
			account.setFromode("2");
		} else if ("01".equals(mode)) {
			account.setFromode("1");
		}
		
	}
}
