package com.citic.server.gdjg.task.taskBo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.dict.DictCoder;
import com.citic.server.gdjg.GdjgConstants;
import com.citic.server.gdjg.domain.Br57_cxqq;
import com.citic.server.gdjg.domain.Br57_cxqq_back;
import com.citic.server.gdjg.domain.Br57_cxqq_mx;
import com.citic.server.gdjg.domain.request.Gdjg_RequestLsdj;
import com.citic.server.gdjg.domain.request.Gdjg_Request_AccCx;
import com.citic.server.gdjg.domain.request.Gdjg_Request_BankCx;
import com.citic.server.gdjg.domain.request.Gdjg_Request_FinancialProductsCx;
import com.citic.server.gdjg.domain.request.Gdjg_Request_FroInfoCx;
import com.citic.server.gdjg.domain.request.Gdjg_Request_TransCx;
import com.citic.server.gdjg.mapper.MM57_cxqqMapper;
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
public class Cxqq_CLBo extends BaseBo implements GdjgConstants {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(Cxqq_CLBo.class);
	
	/** 数据获取接口 */
	private IDataOperateGdjg dataOperate;
	
	/** 码表转换接口 */
	private DictCoder dictCoder;
	
	private MM57_cxqqMapper br57_cxqqMapper;
	
	public Cxqq_CLBo(ApplicationContext ac) {
		super(ac);
		this.br57_cxqqMapper = (MM57_cxqqMapper) ac.getBean("MM57_cxqqMapper");
		this.dictCoder = (DictCoder) ac.getBean("dictCoder");
	}
	
	/**
	 * 删除查询信息
	 * 
	 * @param bdhm
	 * @throws Exception
	 */
	public void delCxqqInfo01(MC21_task_fact mc21_task_fact) throws Exception {
		br57_cxqqMapper.delBr57_cxqq_back_acct(mc21_task_fact);
		br57_cxqqMapper.delBr57_cxqq_back_froInfo(mc21_task_fact);
		br57_cxqqMapper.delBr57_cxqq_back_trans(mc21_task_fact);
	}
	
	public void delCxqqInfo02(MC21_task_fact mc21_task_fact) throws Exception {
		br57_cxqqMapper.delBr57_cxqq_back_trans(mc21_task_fact);
	}
	
	public void delCxqqInfo03(MC21_task_fact mc21_task_fact) {
		br57_cxqqMapper.delBr57_cxqq_back_financePro(mc21_task_fact);
		
	}
	
	public void delCxqqInfo04(MC21_task_fact mc21_task_fact) {
		// TODO Auto-generated method stub
		
	}
	
	public void delCxqqInfo08(MC21_task_fact mc21_task_fact) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 处理存款查询任务 - task2
	 * 
	 * @param mc21_task_fact
	 * @return
	 * @author liuxuanfei
	 * @throws Exception
	 * @date 2017年5月31日 下午2:20:06
	 */
	public String handleQueryResponse01(MC21_task_fact mc21_task_fact) throws Exception {
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
		Br57_cxqq br57_cxqq = new Br57_cxqq();
		br57_cxqq.setDocno(docno);
		br57_cxqq.setCaseno(caseno);
		br57_cxqq.setUniqueid(mc21_task_fact.getBdhm());
		// 1.1.根据请求单标识、监管类别获取查询主体信息内容
		Br57_cxqq_mx br57_cxqq_mx = this.getQueryRequest_mx(br57_cxqq);
		// 1.2、获取反馈基本信息表
		Br57_cxqq_back br57_cxqq_back = this.getBr57_cxqq_back(br57_cxqq);
		//1.3 判断是否处理流水信息,插入任务task3
		String queryContent = br57_cxqq_mx.getQuerycontent();
		if ("03".equals(queryContent)) {
			MC21_task_fact mc21_task_fact_copy = mc21_task_fact;
			String docno1 = handleQueryResponse02(mc21_task_fact_copy);
		}
		
		String cxfkjg = "01";
		String czsbyy = "查询成功";
		try {
			//2.初始化接口
			dataOperate = (IDataOperateGdjg) this.ac.getBean(REMOTE_DATA_OPERATE_NAME_GDJG);
			br57_cxqq_mx.setIdtype(br57_cxqq_mx.getIdtype().trim());//去空格
			
			dictCoder.transcode(br57_cxqq_mx, null);
			//3.获取账户信息 
			logger.info("获取账户信息：");
			Gdjg_Request_BankCx queryRequest_Bank = dataOperate.getAccountDetail(br57_cxqq_mx);
			if (queryRequest_Bank != null) {
				List<Gdjg_Request_AccCx> accList = queryRequest_Bank.getAccs();
				if (accList != null && accList.size() > 0) {
					for (Gdjg_Request_AccCx acc : accList) {
						acc.setUniqueid(br57_cxqq_mx.getUniqueid());
						acc.setCaseno(br57_cxqq_mx.getCaseno());
						acc.setDocno(br57_cxqq_mx.getDocno());
						acc.setQrydt(DtUtils.getNowDate());
						
						//冻结信息
						List<Gdjg_Request_FroInfoCx> froInfos = acc.getFroinfos();
						if (froInfos != null && froInfos.size() > 1) {
							for (Gdjg_Request_FroInfoCx froInfo : froInfos) {
								froInfo.setUniqueid(br57_cxqq_mx.getUniqueid());
								froInfo.setCaseno(br57_cxqq_mx.getCaseno());
								froInfo.setDocno(br57_cxqq_mx.getDocno());
								froInfo.setQrydt(DtUtils.getNowDate());
							}
						}
						//将字段进行转码
						dictCoder.reverse(acc, taskType);
						if (froInfos != null && froInfos.size() > 1) {
							br57_cxqqMapper.batchInsertBr57_cxqq_back_froInfo(froInfos);
						}
					}
				}
				if (accList != null && accList.size() > 0) {
					br57_cxqqMapper.batchInsertBr57_cxqq_back_acct(accList);
				}
			} else {
				cxfkjg = "01";
				czsbyy = "查无客户信息";
			}
			
		} catch (DataOperateException e) {
			// 3.3 错误统一采用异常处理
			logger.error("数据处理异常" + e.getMessage());
			cxfkjg = "02";
			czsbyy = "数据处理异常或客户信息不存在";
		} catch (RemoteAccessException e) {
			e.printStackTrace();
			logger.error("Artery异常" + e.getMessage());
			cxfkjg = "02";
			czsbyy = "应用程序异常";
		}
		// 4、写反馈基本信息表 
		br57_cxqq_back.setStatus("1");
		br57_cxqq_back.setCxfkjg(cxfkjg);
		br57_cxqq_back.setCzsbyy(czsbyy);
		updateBr57_cxqq_back(br57_cxqq_back);
		
		return docno;
		
	}
	
	/**
	 * 处理交易流水查询任务 - task2
	 * 
	 * @param mc21_task_fact
	 * @return
	 * @throws Exception
	 * @author liuxuanfei
	 * @date 2017年5月31日 下午2:19:49
	 */
	public String handleQueryResponse02(MC21_task_fact mc21_task_fact) throws Exception {
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
		Br57_cxqq br57_cxqq = new Br57_cxqq();
		br57_cxqq.setDocno(docno);
		br57_cxqq.setCaseno(caseno);
		br57_cxqq.setUniqueid(mc21_task_fact.getBdhm());
		// 1.1.根据请求单标识、监管类别获取查询主体信息内容
		Br57_cxqq_mx br57_cxqq_mx = this.getQueryRequest_mx(br57_cxqq);
		// 1.2、获取反馈基本信息表
		Br57_cxqq_back br57_cxqq_back = this.getBr57_cxqq_back(br57_cxqq);
		docno = br57_cxqq_back.getDocno();
		
		String cxfkjg = "01";
		String czsbyy = "查询成功";
		try {
			//2.初始化接口
			dataOperate = (IDataOperateGdjg) this.ac.getBean(REMOTE_DATA_OPERATE_NAME_GDJG);
			br57_cxqq_mx.setIdtype(br57_cxqq_mx.getIdtype().trim());//去空格
			dictCoder.transcode(br57_cxqq_mx, null);
			
			//3.获取账户信息
			logger.info("获取交易流水信息：");
			Gdjg_RequestLsdj queryRequest_Trans = dataOperate.getAccountTransaction(br57_cxqq_mx);
			if (queryRequest_Trans != null) {
				List<Gdjg_Request_TransCx> transList = queryRequest_Trans.getTrans();
				if (transList != null && transList.size() > 0) {
					int i = 0;
					List<Gdjg_Request_TransCx> insertList = new ArrayList<Gdjg_Request_TransCx>();
					for (Gdjg_Request_TransCx trans : transList) {
						trans.setUniqueid(br57_cxqq_mx.getUniqueid());
						trans.setCaseno(br57_cxqq_mx.getCaseno());
						trans.setDocno(br57_cxqq_mx.getDocno());
						trans.setQrydt(DtUtils.getNowDate());
						//将字段进行转码【核心转监管】
						dictCoder.reverse(trans, taskType);
						
						insertList.add(trans);
						// 分批插入数据库（流水表字段多，一次不能插入太多数据）
						i++;
						if (i % 250 == 0 || i == transList.size()) {
							br57_cxqqMapper.batchInsertBr57_cxqq_back_trans(insertList); // 批量插入
							insertList.clear();
						}
					}
				} else {
					cxfkjg = "01";
					czsbyy = "没有查询到相关交易流水";
					logger.info("没有查询到相关交易流水,插入空的交易流水结果");
					this.InsertBr57_cxqq_back_trans_Null(taskType,br57_cxqq_mx);
				}
			}
		} catch (DataOperateException e) {
			// 3.3 错误统一采用异常处理
			logger.error("数据处理异常" + e.getMessage());
			cxfkjg = "02";
			czsbyy = e.getMessage();
			logger.info("插入失败时的交易流水结果");
			this.InsertBr57_cxqq_back_trans_Null(taskType,br57_cxqq_mx);
		} catch (RemoteAccessException e) {
			e.printStackTrace();
			logger.error("Artery异常" + e.getMessage());
			cxfkjg = "02";
			czsbyy = "应用程序异常";
			logger.info("插入失败时的交易流水结果");
			this.InsertBr57_cxqq_back_trans_Null(taskType,br57_cxqq_mx);
		}
		// 4、写反馈基本信息表 
		br57_cxqq_back.setCxfkjg(cxfkjg);
		br57_cxqq_back.setCzsbyy(czsbyy);
		updateBr57_cxqq_back(br57_cxqq_back);
		
		return docno;
	}
	
	
	/**
	 * 插入交易流水时的处理结果
	 * @param taskType
	 * @param br57_cxqq_mx
	 * @author liuying07
	 * @date 2017/08/28 16:17:37
	 */
	private void InsertBr57_cxqq_back_trans_Null(String taskType, Br57_cxqq_mx br57_cxqq_mx) {
		Gdjg_Request_TransCx tranNull = new Gdjg_Request_TransCx();
		tranNull.setUniqueid(br57_cxqq_mx.getUniqueid());
		tranNull.setCaseno(br57_cxqq_mx.getCaseno());
		tranNull.setDocno(br57_cxqq_mx.getDocno());
		tranNull.setQrydt(DtUtils.getNowDate());
		//将字段进行转码【核心转监管】
		dictCoder.reverse(tranNull, taskType);
		br57_cxqqMapper.InsertBr57_cxqq_back_trans(tranNull);
		
	}

	/**
	 * 根据请求单标识，监管类别获取查询请求主体信息
	 * 
	 * @param mc21_task_fact
	 * @return
	 */
	private Br57_cxqq_mx getQueryRequest_mx(Br57_cxqq br57_cxqq) {
		Br57_cxqq_mx queryrequest_mx = br57_cxqqMapper.selectBr57_cxqq_mxByVo(br57_cxqq);
		if (queryrequest_mx == null) {
			queryrequest_mx = new Br57_cxqq_mx();
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
	private Br57_cxqq_back getBr57_cxqq_back(Br57_cxqq br57_cxqq) {
		Br57_cxqq_back br57_cxqq_back = br57_cxqqMapper.selectBr57_cxqq_backByVo(br57_cxqq);
		if (br57_cxqq_back == null) {
			br57_cxqq_back = new Br57_cxqq_back();
		}
		return br57_cxqq_back;
	}
	
	/**
	 * 处理反馈信息表
	 * 
	 * @param br24_bas_info
	 * @throws Exception
	 */
	public void updateBr57_cxqq_back(Br57_cxqq_back br57_cxqq_back) {
		/** 查询结束时间,默认取当前期 */
		if (br57_cxqq_back.getCxfkjg().equals("")) {
			br57_cxqq_back.setCxfkjg("01");
		}
		br57_cxqq_back.setStatus("1");
		br57_cxqqMapper.updateBr57_cxqq_back(br57_cxqq_back);
		
	}
	
	public synchronized void insertMc21TaskFact3(MC21_task_fact mc21_task_fact, String docno) throws Exception {
		
		String isemployee = mc21_task_fact.getIsemployee();
		if (isemployee.equals("0")) {
			//查询同一批次下是否有未完成的
			logger.info("查询同一批次下是否有未完成的任务：");
			int isok = br57_cxqqMapper.getBr57_cxqq_backCount(docno);
			if (isok == 0) {
				//插入task3任务
				mc21_task_fact.setBdhm(docno);
				logger.info("插入task3任务表：");
				super.insertMc21TaskFact3(mc21_task_fact, "GDJG");
				//修改Br50_cxqq的状态为待生成报文
				Br57_cxqq br57_cxqq = new Br57_cxqq();
				br57_cxqq.setDocno(docno);
				br57_cxqq.setStatus("1");//0：待处理 1：待生成报文  3：反馈成功 4：反馈失败
				br57_cxqq.setLast_up_dt(Utility.currDateTime19());
				br57_cxqqMapper.updateBr57_cxqq(br57_cxqq);
			}
		}
	}
	
	/**
	 * 处理金融产品查询任务 - task2
	 * 
	 * @param mc21_task_fact
	 * @return
	 * @throws Exception
	 * @author liuxuanfei
	 * @date 2017年5月31日 下午2:19:49
	 */
	public String handleQueryResponse03(MC21_task_fact mc21_task_fact) {
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
		Br57_cxqq br57_cxqq = new Br57_cxqq();
		br57_cxqq.setDocno(docno);
		br57_cxqq.setCaseno(caseno);
		br57_cxqq.setUniqueid(mc21_task_fact.getBdhm());
		// 1.1.根据请求单标识、监管类别获取查询主体信息内容
		Br57_cxqq_mx br57_cxqq_mx = this.getQueryRequest_mx(br57_cxqq);
		// 1.2、获取反馈基本信息表
		Br57_cxqq_back br57_cxqq_back = this.getBr57_cxqq_back(br57_cxqq);
		String cxfkjg = "01";
		String czsbyy = "查询成功";
		try {
			//2.初始化接口
			dataOperate = (IDataOperateGdjg) this.ac.getBean(REMOTE_DATA_OPERATE_NAME_GDJG);
			br57_cxqq_mx.setIdtype(br57_cxqq_mx.getIdtype().trim());//去空格
			dictCoder.transcode(br57_cxqq_mx, null);
			//3.获取产品信息
			logger.info("获取金融产品信息：");
			Gdjg_Request_BankCx queryRequest_Bank = dataOperate.getJrcpInfo(br57_cxqq_mx);
			if (queryRequest_Bank != null) {
				List<Gdjg_Request_FinancialProductsCx> productList = queryRequest_Bank.getFinancialPros();
				if (productList != null && productList.size() > 0) {
					for (Gdjg_Request_FinancialProductsCx product : productList) {
						product.setUniqueid(br57_cxqq_mx.getUniqueid());
						product.setCaseno(br57_cxqq_mx.getCaseno());
						product.setDocno(br57_cxqq_mx.getDocno());
						product.setQrydt(DtUtils.getNowDate());
						//将字段进行转码
						dictCoder.reverse(product, taskType);
					}
				}
				if (productList != null && productList.size() > 0) {
					br57_cxqqMapper.batchInsertBr57_cxqq_back_financePro(productList);
				} else {
					cxfkjg = "01";
					czsbyy = "查无客户信息";
				}
			}
		} catch (DataOperateException e) {
			// 3.3 错误统一采用异常处理
			logger.error("数据处理异常" + e.getMessage());
			cxfkjg = "02";
			czsbyy = "客户信息不存在或数据处理异常";
		} catch (RemoteAccessException e) {
			e.printStackTrace();
			logger.error("Artery异常" + e.getMessage());
			cxfkjg = "02";
			czsbyy = "应用程序异常";
		}
		// 4、写反馈基本信息表 
		br57_cxqq_back.setCxfkjg(cxfkjg);
		br57_cxqq_back.setCzsbyy(czsbyy);
		updateBr57_cxqq_back(br57_cxqq_back);
		
		return docno;
	}
	
	public String handleQueryResponse04(MC21_task_fact mc21_task_fact) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 处理动态查询任务 - task2
	 * 
	 * @param mc21_task_fact
	 * @return
	 * @throws Exception
	 * @author liuxuanfei
	 * @throws Exception
	 * @date 2017年5月31日 下午2:19:49
	 */
	public String handleQueryResponse08(MC21_task_fact mc21_task_fact) {
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
		Br57_cxqq br57_cxqq = new Br57_cxqq();
		br57_cxqq.setDocno(docno);
		br57_cxqq.setCaseno(caseno);
		br57_cxqq.setUniqueid(mc21_task_fact.getBdhm());
		// 1.1.根据请求单标识、监管类别获取查询主体信息内容
		Br57_cxqq_mx br57_cxqq_mx = this.getQueryRequest_mx(br57_cxqq);
		// 1.2、获取反馈基本信息表
		Br57_cxqq_back br57_cxqq_back = this.getBr57_cxqq_back(br57_cxqq);
		
		String cxfkjg = "01";
		String czsbyy = "查询成功";
		logger.info("执行动态查询：");
		String interval = br57_cxqq_mx.getIntervals();
		try {
			if ("0".equals(interval)) {
				logger.info("正在解除动态查询任务...");
				String oldseq = br57_cxqq_mx.getOldseq(); //原流水号
				if (oldseq != null && oldseq != "") {
					br57_cxqqMapper.updateBr57_cxqq_acct_dynamic_jc(br57_cxqq_mx); //解除动态查询任务
				} else {
					logger.error("原流水号缺失，无法解除动态查询任务");
					cxfkjg = "02";
					czsbyy = "原流水号缺失，无法解除动态查询任务";
				}
				cxfkjg = "01";
				czsbyy = "成功解除动态查询任务";
			} else {
				logger.info("正在插入新的动态查询任务...");
				String startDate = br57_cxqq_mx.getStartdate();
				String endDate = br57_cxqq_mx.getEnddate();
				logger.info("校验动态查询起始-结束日期是否满足要求:");
				boolean isOk = true;
				isOk = TimeValidation(interval, startDate, endDate);
				if (isOk) {
					insertBr57_cxqq_acct_dynamic_main(br57_cxqq_mx, taskType); //插入动态查询任务
					cxfkjg = "01";
					czsbyy = "成功插入动态查询任务";
				} else {
					logger.error("开始日期、结束日期存在错误，无法插入动态查询新任务");
					cxfkjg = "02";
					czsbyy = "开始日期、结束日期存在错误，无法执行动态查询";
				}
			}
		} catch (Exception e) {
			logger.error("程序运行错误,插入动态查询任务失败", e.getMessage());
			cxfkjg = "02";
			czsbyy = "程序运行错误,插入动态查询任务失败";
		}
		// 4、写反馈基本信息表 
		br57_cxqq_back.setCxfkjg(cxfkjg);
		br57_cxqq_back.setCzsbyy(czsbyy);
		this.updateBr57_cxqq_back(br57_cxqq_back); //修改
		return docno;
	}
	
	/**
	 * 校验动态查询起始-结束时间是否在给定时间区间内
	 * 
	 * @param interval
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 * @author Liu Ying
	 * @date 2017/08/07 22:21:23
	 */
	private boolean TimeValidation(String interval, String startDate, String endDate) throws Exception {
		boolean isOk = true;
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			String deadDate = DtUtils.add(startDate, 1, 100);
			if ((DtUtils.comp(endDate, deadDate, 1)) > 0) { //若结束日期比开始日期大于100天则请求无效返回失败
				isOk = false;
				logger.error("起止时间不能超过100天,startDate[{}]-endDate[{}]", startDate, endDate);
			}
		} else if (StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)) {
			isOk = false;
			logger.error("开始日期[{}] 或者 结束日期[{}] 缺失", startDate, endDate);
		}
		
		//
		//		if ("1".equals(interval)) {
		//			deadLine = DtUtils.add(startDate, 1, 31); //1表示动态查询1个月,按最大天数取
		//			if ((DtUtils.comp(deadLine, endDate, 1)) < 0) {
		//				isOk = false;
		//			}
		//		} else if ("2".equals(interval)) {
		//			deadLine = DtUtils.add(startDate, 1, 62); //2表示动态查询2个月,按最大天数取
		//			if ((DtUtils.comp(deadLine, endDate, 1)) < 0) {
		//				isOk = false;
		//			}
		//		} else if ("3".equals(interval)) {
		//			deadLine = DtUtils.add(startDate, 1, 92); //3表示动态查询3个月,按最大天数取
		//			if ((DtUtils.comp(deadLine, endDate, 1)) < 0) {
		//				isOk = false;
		//			}
		//			
		//		} else if ("4".equals(interval)) {
		//			deadLine = DtUtils.add(startDate, 1, 100); //4表示规定的最长时间100天
		//			if ((DtUtils.comp(deadLine, endDate, 1)) < 0) {
		//				isOk = false;
		//			}
		//			
		//		} else {
		//			isOk = false;
		//		}
		return isOk;
	}
	
	/**
	 * 插入动态查询任务主表
	 * 
	 * @param br57_cxqq_mx
	 * @author Liu Ying
	 * @param taskType2
	 * @throws Exception
	 * @date 2017/08/07 22:34:32
	 */
	private void insertBr57_cxqq_acct_dynamic_main(Br57_cxqq_mx br57_mx, String taskType2) throws Exception {
		// 删除请求单号下的数据
		br57_cxqqMapper.delBr57_cxqq_acct_dynamic_main(br57_mx);
		
		Br57_cxqq_mx br57_cxqq_mx = new Br57_cxqq_mx();
		
		br57_cxqq_mx.setTasktype(taskType2);
		br57_cxqq_mx.setDocno(br57_mx.getDocno());
		br57_cxqq_mx.setCaseno(br57_mx.getCaseno());
		br57_cxqq_mx.setUniqueid(br57_mx.getUniqueid());
		br57_cxqq_mx.setOldseq(br57_mx.getOldseq());
		br57_cxqq_mx.setUrgency(br57_mx.getUrgency());
		br57_cxqq_mx.setAccount(br57_mx.getAccount());
		br57_cxqq_mx.setIntervals(br57_mx.getIntervals());
		
		String mxqssj = Utility.toDate10(br57_mx.getStartdate()) + " 00:00:00";
		String mxjzsj = Utility.toDate10(br57_mx.getEnddate()) + " 23:59:59";
		br57_cxqq_mx.setStartdate(mxqssj);
		br57_cxqq_mx.setEnddate(mxjzsj);
		
		br57_cxqq_mx.setStatus_cd("1");//状态  0：已解除  1：执行中
		br57_cxqq_mx.setLastpollingtime(mxqssj);
		br57_cxqq_mx.setPollinglock("0"); // 用于反馈时的序列计数
		br57_cxqq_mx.setQrydt(DtUtils.getNowDate());
		br57_cxqqMapper.insertBr57_cxqq_acct_dynamic_main(br57_cxqq_mx);
		
	}
	
}
