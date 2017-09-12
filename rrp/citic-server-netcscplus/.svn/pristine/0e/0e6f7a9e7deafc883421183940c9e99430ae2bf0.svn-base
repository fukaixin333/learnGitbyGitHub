package com.citic.server.dx.task;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.citic.server.ApplicationProperties;
import com.citic.server.SpringContextHolder;
import com.citic.server.cbrc.domain.Br40_cxqq;
import com.citic.server.dx.domain.OrganKeyQuery;
import com.citic.server.dx.service.DataOperate2;
import com.citic.server.dx.task.taskBo.Dx_KzqqBo;
import com.citic.server.net.mapper.MC00_common_Mapper;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.utils.StrUtils;

/**
 * 客户全账号查询反馈任务
 * 
 * @author
 * @version 1.0
 */

public class TK_ES_ORG extends NBaseTask {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ES_ORG.class);
	private JdbcTemplate jdbcTemplate = null;
	public static final String REMOTE_DATA_OPERATE_NAME_DX = "remoteDataOperate2";
	public static final String LOCAL_DATA_OPERATE_NAME_DX = "localDataOperate2";
	
	private MC00_common_Mapper common_Mapper;
	
	public TK_ES_ORG(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
		super(ac, mC21_task_fact);
		ApplicationProperties applicationProperties = (ApplicationProperties) SpringContextHolder.getBean(ApplicationProperties.class);
		jdbcTemplate = (JdbcTemplate) SpringContextHolder.getBean(applicationProperties.getJdbcTemplate_business());
		common_Mapper = (MC00_common_Mapper) ac.getBean("MC00_common_Mapper");
	}
	
	/**
	 * 
	 */
	public boolean calTask() throws Exception {
		boolean isSucc = true;
		try {
			Dx_KzqqBo kzqqBo = new Dx_KzqqBo(this.getAc());
			MC21_task_fact mc21_task_fact = this.getMC21_task_fact();
			DataOperate2 dataOperate = (DataOperate2) this.getAc().getBean(REMOTE_DATA_OPERATE_NAME_DX);
			if (mc21_task_fact.getFreq().equals("0") || mc21_task_fact.getFreq().equals("3")) { //从本地取
				dataOperate = (DataOperate2) this.getAc().getBean(LOCAL_DATA_OPERATE_NAME_DX);
			}
			String acctcardnum = mc21_task_fact.getBdhm(); //证件类型|账卡号或证件号码
			String oigTaskkey = mc21_task_fact.getTgroupid();
			//String[] regtypeStrs=regtypeStr.split("#");
			String regtype = mc21_task_fact.getTasktype(); //1高法 2电信诈骗
			String sqlStr = mc21_task_fact.getTaskobj();
			String type = mc21_task_fact.getSubtaskid(); //1账卡号  2证件号码 3账户
			String organkey = "";
			String subjectType = ""; //证件类型或客户类型
			String cardnumber = acctcardnum;
			String acctname = "";
			String organkey_r = "";
			String[] strs = acctcardnum.split("#", -1);
			if (strs.length >= 3) {
				cardnumber = strs[0];
				subjectType = strs[1];
				acctname = strs[2];
			}
			if (strs.length >= 4) {
				organkey_r = strs[3];
			}
			String bdhm = "";
			if (strs.length == 5) {
				bdhm = strs[4];
			}
			String msgCheckResult = "1";
			if (!cardnumber.equals("")) {
				OrganKeyQuery organkeyquery = null;
				try {
					if (type.equals("1")) {
						organkeyquery = dataOperate.getOrgkeyByCard(subjectType, cardnumber, acctname);
					} else if (type.equals("2")) {
						organkeyquery = dataOperate.getOrgkeyByCredentialNumber(subjectType, cardnumber, acctname, organkey_r);
					} else {
						organkeyquery = dataOperate.getOrgkeyByAcctNumber(cardnumber, acctname);
					}
				} catch (RemoteAccessException e) {
					e.printStackTrace();
					msgCheckResult = "2";
				} catch (DataOperateException e) {
					e.printStackTrace();
					msgCheckResult = "2";
				}
				
				String newAcctName = "";
				if (organkeyquery != null) {
					organkey = organkeyquery.getOrgKey();
					newAcctName = StrUtils.null2String(organkeyquery.getAcctName());
					if (!acctname.equals("") && !newAcctName.equals("") && !acctname.equals(newAcctName)) {
						msgCheckResult = "3"; //名字不符
					}
				}
				
				if (organkey != null && !organkey.equals("")) {
					
					if (organkey_r != null && !organkey_r.equals("")) {
						CacheService cacheService = SpringContextHolder.getBean("cacheService"); // 缓存服务
						HashMap repOrgHash = (HashMap<String, Object>) cacheService.getCache("Mp02_repOrgMapDetail", HashMap.class);
						HashMap orgMap = (HashMap) repOrgHash.get(organkey_r);
						if (!orgMap.containsKey(organkey)) {
							msgCheckResult = "2"; //非本行					
						}
					}
				} else {
					msgCheckResult = "2";
				}
				
			} else {
				msgCheckResult = "2";
				
			}
			this.updateOrgkeyStr(sqlStr, organkey, regtype, msgCheckResult);
			if (!mc21_task_fact.getFreq().equals("2") && !mc21_task_fact.getFreq().equals("3")) {
				String isemploye = mc21_task_fact.getIsdyna();
				MC21_task_fact mc21_task_fact1 = new MC21_task_fact();
				//查询原任务
				mc21_task_fact1 = common_Mapper.getMc21_task_fact1(oigTaskkey);
				if (null != regtype && !regtype.equals("1")) {//cbrc 
					mc21_task_fact1.setBdhm(bdhm);
				} else { //高法
					if (!bdhm.equals("") && bdhm.indexOf("$") > -1) {
						mc21_task_fact1.setTaskobj(bdhm.split("\\$")[1]);
					}
				}
				if (!msgCheckResult.equals("1") && mc21_task_fact1 != null) { //非本行客户直接生成task2	
					if (null != regtype && !regtype.equals("1")) {//cbrc 
						mc21_task_fact1.setIsemployee("0");
						this.dealCbrcTask(mc21_task_fact1, bdhm, regtype, kzqqBo);
					} else { //高法
						kzqqBo.insertMc21TaskFact2Fo(mc21_task_fact1, mc21_task_fact.getTasktype());
					}
					
				} else {
					if (isemploye.equals("0")) { //不走人工
						kzqqBo.insertMc21TaskFact2Fo(mc21_task_fact1, mc21_task_fact.getTasktype());
					}
				}
				if (isemploye.equals("1")) {
					
					if (msgCheckResult.equals("1")) { //走人工单本行客户发短信
						if (null != regtype && !regtype.equals("1") && !regtype.equals("6")) {//cbrc 
							//判断若请求下所有的任务都成功执行了则发送短信
							String qqdbs = StringUtils.substringBefore(bdhm, "$");
							int istaskok = common_Mapper.isTaskCount(qqdbs, mc21_task_fact.getTasktype());
							if (istaskok == 1) {
								kzqqBo.sendMsg(organkey, mc21_task_fact.getTaskname(), mc21_task_fact.getSubtaskid(), type, mc21_task_fact.getTasktype(), "1");
							}
							
						} else {//高法
							kzqqBo.sendMsg(organkey, mc21_task_fact.getTaskname(), mc21_task_fact.getSubtaskid(), type, mc21_task_fact.getTasktype(), "1");
						}
						
					}
				}
			}
			
		} catch (Exception e) {
			isSucc = false;
			e.printStackTrace();
			this.inertErrorLog(e);
			throw e;
		}
		
		return isSucc;
	}
	
	public void dealCbrcTask(MC21_task_fact mc21_task_fact1, String bdhm, String tasktype, Dx_KzqqBo kzqqBo) {
		try {
			
			if (tasktype.equals("6")) { //四川省公安
				kzqqBo.insertMc21TaskFact2Fo(mc21_task_fact1, tasktype);
			} else {
				//查询是否是动态查询02 03
				String qqdbs = StringUtils.substringBefore(bdhm, "$");
				Br40_cxqq cxqq = common_Mapper.getBr40_cxqq(qqdbs, tasktype);
				if (cxqq != null && (cxqq.getQqcslx().equals("02") || cxqq.getQqcslx().equals("03"))) { //由于动态查询反馈是所有一个xml所以不能插入task3
					kzqqBo.insertMc21TaskFact2Fo(mc21_task_fact1, tasktype);
				} else {
					kzqqBo.insertMc21TaskFact3(mc21_task_fact1, tasktype); //直接查入task3
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateOrgkeyStr(String upSqlStr, String orgkey, String reptype, String msgCheckResult) throws Exception {
		
		String[] objs = upSqlStr.split("&");
		String[] sqlStrs = objs[0].split(";");
		if (msgCheckResult.equals("1")) {
			for (int i = 0; i < sqlStrs.length; i++) {
				String sqlStr = sqlStrs[i];
				sqlStr = sqlStr.replaceAll("@A@", "'" + orgkey + "'");
				common_Mapper.execSql(sqlStr);
			}
			
		} else {
			if (objs.length > 1) {//修改相应表中的验证标识
				String sqlStr = objs[1];
				String[] objstrs = sqlStr.split(";");
				String objstr1 = objstrs[0].replaceAll("@D@", "'" + msgCheckResult + "'");
				jdbcTemplate.update(objstr1);
				if (objstrs.length > 1) {
					String objstr2 = objstrs[1];
					jdbcTemplate.update(objstr2);
				}
			}
		}
	}
	
}