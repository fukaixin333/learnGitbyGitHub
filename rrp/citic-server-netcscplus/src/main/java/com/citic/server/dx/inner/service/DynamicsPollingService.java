package com.citic.server.dx.inner.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.citic.server.SpringContextHolder;
import com.citic.server.dx.domain.AccountsTransaction;
import com.citic.server.dx.domain.Br24_bas_info;
import com.citic.server.dx.domain.Br24_q_Main;
import com.citic.server.dx.domain.Br24_trans_info;
import com.citic.server.dx.service.CodeChange;
import com.citic.server.dx.service.DataOperate2;
import com.citic.server.net.mapper.MM24_q_mainMapper;
import com.citic.server.net.mapper.PollingTaskMapper;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.Utility;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.MC21_task;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.service.domain.MP02_rep_org_map;
import com.citic.server.service.task.taskBo.BaseBo;
import com.citic.server.utils.DtUtils;

/**
 * 动态查询轮询
 * 
 * @author Liu Xuanfei
 * @date 2016年4月8日 下午5:10:42
 */
@Service("dynamicsPollingService")
public class DynamicsPollingService {
	
	@Autowired
	@Qualifier("remoteDataOperate2")
	private DataOperate2 dataOperate;
	
	@Autowired
	private PollingTaskMapper service;
	
	@Autowired
	private MM24_q_mainMapper mm24_q_mainMapper;
	
	@Autowired
	@Qualifier("codeChangeImpl")
	private CodeChange codeChange;
	
	@Autowired
	private CacheService cacheService;
	
	private BaseBo baseBo;
	
	@Transactional
	public void dealDynamicsPollingTask(Br24_q_Main task) throws Exception {
		// 访问接口获取交易
		String currDataTime = Utility.currDateTime19();
		List<Br24_trans_info> transInfos = new ArrayList<Br24_trans_info>();
		String txcode = task.getTxCode();
		String cardnum = task.getAccountnumber();
		Br24_bas_info br24_bas_info = new Br24_bas_info();
		br24_bas_info.setApplicationID(task.getApplicationid());
		br24_bas_info.setToorg(task.getMessageFrom());
		br24_bas_info.setTxCode(txcode);
		String oldtransserialnumber = task.getTransSerialNumber(); //请求表的流水号
		String orgkey = "";
		try {
			Br24_q_Main br24_q_main = mm24_q_mainMapper.selectBr24_q_mainByVo(oldtransserialnumber);
			orgkey = br24_q_main.getOrgkey();
			br24_bas_info.setOrgkey(orgkey);
			AccountsTransaction accountstransaction = dataOperate.getAccountsTransactionByCardNumber(cardnum, task.getSubjecttype(), task.getLastpollingtime(), currDataTime);
			if (accountstransaction != null) {
				transInfos = accountstransaction.getTransInfoList();
			}
		} catch (DataOperateException e) {
			e.printStackTrace();
			String transserialnumberFK = getBaseBo().getTransSerialNumber("2", orgkey);
			br24_bas_info.setTransSerialNumber(transserialnumberFK);
			br24_bas_info.setResult(e.getCode());
			br24_bas_info.setFeedbackRemark(e.getDescr());
			insertBr24_bas_info(br24_bas_info);
			//写入task3
			this.insertTask3(oldtransserialnumber, transserialnumberFK, txcode);
			
			return;
		}
		
		//4.   遍历流水，归集账户下的流水
		//  CacheService cacheService = SpringContextHolder.getBean("cacheService"); // 缓存服务
			 HashMap sysParaMap = (HashMap) cacheService.getCache("sysParaDetail", HashMap.class);
			 String query_datasource = StringUtils.isEmpty((String) sysParaMap.get("query_datasource")) ? "1" : (String) sysParaMap.get("query_datasource");			
			codeChange.chgCode(transInfos, "BR24_TRANS_INFO", query_datasource);  //转码
			
		HashMap<String, List<Br24_trans_info>> transMap = this.getTransHash(transInfos,br24_bas_info,currDataTime);
		
		// 4.3 写账户交易流水表及相应表
		Iterator<Entry<String, List<Br24_trans_info>>> itertrans = transMap.entrySet().iterator();
		while (itertrans.hasNext()) {
			Entry<String, List<Br24_trans_info>> entry = (Entry<String, List<Br24_trans_info>>) itertrans.next();
			// String accountnumber = entry.getKey();
			List<Br24_trans_info> tranList = entry.getValue();
			
			//写响应表
			String transserialnumberFK = getBaseBo().getTransSerialNumber("2", orgkey);
			br24_bas_info.setTransSerialNumber(transserialnumberFK);
			insertBr24_bas_info(br24_bas_info);
			
			//  写账户交易流水表
			insertBr24_trans_info(tranList, transserialnumberFK, cardnum);
			
			//写入task3
			this.insertTask3(oldtransserialnumber, transserialnumberFK, txcode);
		}
		// <searching marker> 这里还缺少查询“联系信息变更”的逻辑
		// 更新本次处理时间
		service.updateBr24_Q_Acct_Dynamic_Main(oldtransserialnumber, currDataTime);
	}
	
	public void insertTask3(String oldtransserialnumber, String newtransserialnumber, String txcode) throws Exception {
		MC21_task_fact taskFact = new MC21_task_fact();
		taskFact.setBdhm(oldtransserialnumber);
		taskFact.setTgroupid(newtransserialnumber);
		taskFact.setSubtaskid(txcode);
		taskFact.setTasktype("2");
		//判断是否需要人工处理
		//   CacheService cacheService = SpringContextHolder.getBean("cacheService"); // 缓存服务
		   HashMap  taskHash= (HashMap<String, Object>) cacheService.getCache("MC21TaskDetail", HashMap.class);
		   MC21_task  task=(MC21_task)taskHash.get("TK_ESDX_CL03");
		   taskFact.setIsemployee(task.getIsEmployee());   
		getBaseBo().insertMc21TaskFact3(taskFact, "DX");
		
	}
	
	/*
	 * transInfos :交易
	 */
	public HashMap<String, List<Br24_trans_info>> getTransHash(List<Br24_trans_info> transInfos,Br24_bas_info br24_bas_info,String currTime) throws Exception {
		
		//查询同一申请编号下的交易流水
		HashMap<String, String> transKeyMap = this.getNewTransHash(br24_bas_info, currTime);
		//4.2 归集账户下的流水
		HashMap<String, List<Br24_trans_info>> transMap = new HashMap<String, List<Br24_trans_info>>();
		
		for (int i = 0; i < transInfos.size(); i++) {
			Br24_trans_info trans = (Br24_trans_info) transInfos.get(i);
			String accountnumber = trans.getAccountNumber();
			String tranSeq=trans.getTransseq();
			//过滤交易
		  if(!transKeyMap.containsKey(tranSeq)){
			if (transMap.containsKey(accountnumber)) {
				List<Br24_trans_info> n_transList = transMap.get(accountnumber);
				n_transList.add(trans);
			} else {
				List<Br24_trans_info> n_transList = new ArrayList<Br24_trans_info>();
				n_transList.add(trans);
				transMap.put(accountnumber, n_transList);
			}
		  }
		}
		
		return transMap;
	}
	
	public HashMap<String, String> getNewTransHash(Br24_bas_info br24_bas_info,String currTime) throws Exception {
		HashMap<String, String> transMap = new HashMap<String, String>();
	  String beforeDate= DtUtils.add(currTime, 1, -1);
	  br24_bas_info.setLast_up_dt(beforeDate);
		//查询已经查询出的前一天的到现在的动态查询交易，也就是查出当前已经插入的动态交易
	  List<Br24_trans_info> transList=mm24_q_mainMapper.selBr24_trans_infoList(br24_bas_info);
	  for(Br24_trans_info br24_trans_info:transList){
		  transMap.put(br24_trans_info.getTransseq(), "");
	  }
		return transMap;
	}
	
	public void insertBr24_bas_info(Br24_bas_info br24_bas_info) throws Exception {
		
		String orgkey = br24_bas_info.getOrgkey();
		MP02_rep_org_map rep_org = getBaseBo().getMp02_organPerson("2", orgkey);
		String operatorName = rep_org.getOperatorname();
		String operatorPhoneNumber = rep_org.getOperatorphonenumber();
		String feedbackOrgName = rep_org.getOrganname();
		
		br24_bas_info.setMode("01");
		br24_bas_info.setTxCode(String.valueOf(Integer.parseInt(br24_bas_info.getTxcode()) + 1));
		br24_bas_info.setOperatorName(operatorName);
		br24_bas_info.setOperatorPhoneNumber(operatorPhoneNumber);
		br24_bas_info.setFeedbackOrgName(feedbackOrgName);
		br24_bas_info.setQrydt(DtUtils.getNowDate());
		br24_bas_info.setFeedback_dt(DtUtils.getNowDate());
		br24_bas_info.setStatus("1");
		
		mm24_q_mainMapper.insertBr24_bas_info(br24_bas_info);
	}
	
	/**
	 * 写账户交易流水表
	 * 
	 * @throws Exception
	 */
	public void insertBr24_trans_info(List<Br24_trans_info> trans_infoList, String transserialnumber, String cardnumber) throws Exception {
		//先删除
		mm24_q_mainMapper.delBr24_trans_info(transserialnumber);
		 
		//再插入
		for (int i = 0; i < trans_infoList.size(); i++) {
			Br24_trans_info info = (Br24_trans_info) trans_infoList.get(i);
			if(info.getTransseq()!=null&&!info.getTransseq().equals("")){				
			}else{
			info.setTransseq((i + 1) + "");
			}
			info.setQrydt(DtUtils.getNowDate());
			info.setTransSerialNumber(transserialnumber);
			info.setCardNumber(cardnumber);
			mm24_q_mainMapper.insertBr24_trans_info(info);
		}
	}
	
	// 因为SpringContextHolder在XML中注入，在本服务类注入之后，所以只能延迟初始化BaseBo对象。
	private BaseBo getBaseBo() {
		if (baseBo == null) {
			baseBo = new BaseBo();
		}
		return baseBo;
	}
}
