package com.citic.server.gdjc.task;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.citic.server.ApplicationProperties;
import com.citic.server.SpringContextHolder;
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

public class TK_ESJJ_ORG extends NBaseTask {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESJJ_ORG.class);
	private JdbcTemplate jdbcTemplate = null;
	public static final String REMOTE_DATA_OPERATE_NAME_DX = "remoteDataOperate2";
	public static final String REMOTE_DATA_OPERATE_NAME_GF = "remoteDataOperate1";
	public static final String LOCAL_DATA_OPERATE_NAME_DX = "localDataOperate2";
	
	private  MC00_common_Mapper common_Mapper;
	public TK_ESJJ_ORG(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
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
			  Dx_KzqqBo kzqqBo=new Dx_KzqqBo(this.getAc());
			MC21_task_fact mc21_task_fact = this.getMC21_task_fact();
			DataOperate2  dataOperate = (DataOperate2) this.getAc().getBean(REMOTE_DATA_OPERATE_NAME_DX);
			 if(mc21_task_fact.getFreq().equals("0")){ //从本地取
				 dataOperate = (DataOperate2) this.getAc().getBean(LOCAL_DATA_OPERATE_NAME_DX);
			 }
			String acctcardnum=mc21_task_fact.getBdhm(); //证件类型|账卡号或证件号码
			String oigTaskkey=mc21_task_fact.getTgroupid(); 
			//String[] regtypeStrs=regtypeStr.split("#");
			String regtype=mc21_task_fact.getTasktype(); //1高法 2电信诈骗
			String sqlStr=mc21_task_fact.getTaskobj();
			String type=mc21_task_fact.getSubtaskid(); //1账卡号  2证件号码 3账户
			String organkey="";
			String subjectType="";  //证件类型或客户类型
			String cardnumber=acctcardnum;
			String  acctname="";
			String  organkey_r="";
			String[] strs=acctcardnum.split("#");
			if(strs.length>=3){
				cardnumber=strs[0];
				subjectType=strs[1];
				acctname=strs[2];
			}
			if(strs.length==4){
				organkey_r=strs[3];
			}

			String msgCheckResult="1";
  if(!cardnumber.equals("")){
			OrganKeyQuery	organkeyquery=null;	
			try {
				if(type.equals("1")){
					organkeyquery=dataOperate.getOrgkeyByCard(subjectType, cardnumber,acctname);
				}else if(type.equals("2")){
					organkeyquery=dataOperate.getOrgkeyByCredentialNumber(subjectType, cardnumber,acctname,organkey_r);
				}else{
					organkeyquery=dataOperate.getOrgkeyByAcctNumber(cardnumber,acctname);
				}
			} catch (RemoteAccessException e) {
				e.printStackTrace();
				 msgCheckResult="2";  
			}
			catch (DataOperateException e) {
				e.printStackTrace();
				 msgCheckResult="2";  
			}

			String newAcctName="";
			if(organkeyquery!=null){
				organkey=organkeyquery.getOrgKey();
				newAcctName=StrUtils.null2String(organkeyquery.getAcctName());
				if(!acctname.equals("")&&!newAcctName.equals("")&&!acctname.equals(newAcctName)){
					 msgCheckResult="3";  //名字不符
				}
			}

			if(organkey!=null&&!organkey.equals("")){
				
				if(organkey_r!=null&&!organkey_r.equals("")){
				CacheService cacheService = SpringContextHolder.getBean("cacheService"); // 缓存服务
				HashMap repOrgHash = (HashMap<String, Object>) cacheService.getCache("Mp02_repOrgMapDetail", HashMap.class);
				HashMap orgMap=(HashMap)repOrgHash.get(organkey_r);
				   if(!orgMap.containsKey(organkey)){
					   msgCheckResult="2";  //非本行					
				    }
				}				
			}else{
				msgCheckResult="2";
			}
		 
  }else{
	  msgCheckResult="2";  
	   
  }
			this.updateOrgkeyStr(sqlStr, organkey, regtype,msgCheckResult);			
		
			String isemploye=mc21_task_fact.getIsdyna();
			 MC21_task_fact  mc21_task_fact1=new  MC21_task_fact();
			  if(isemploye.equals("1")){
					//查询原任务
				    mc21_task_fact1=common_Mapper.getMc21_task_fact1(oigTaskkey);
				    if(!msgCheckResult.equals("1")){ //走人工单非本行客户直接生成task2
				    	mc21_task_fact1.setIsemployee("0");
						  kzqqBo.insertMc21TaskFact2(mc21_task_fact1, mc21_task_fact.getTasktype());
					  }
				    if(msgCheckResult.equals("1")){ //走人工单本行客户发短信
							kzqqBo.sendMsg(organkey, mc21_task_fact.getTaskname(),mc21_task_fact.getSubtaskid(),type,mc21_task_fact.getTasktype(),"1");
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
	
public void updateOrgkeyStr(String upSqlStr, String orgkey, String reptype,String msgCheckResult) throws Exception {

		String[] objs = upSqlStr.split("&");
		String[] sqlStrs = objs[0].split(";");
  if(msgCheckResult.equals("1")){
		for (int i = 0; i < sqlStrs.length; i++) {
			String sqlStr = sqlStrs[i];
			sqlStr = sqlStr.replaceAll("@A@", "'" + orgkey + "'");
			common_Mapper.execSql(sqlStr);
		}
	
  }else{
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