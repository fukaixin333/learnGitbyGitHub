package com.citic.server.dx.task;
  
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.dx.domain.Br22_Msg;
import com.citic.server.dx.domain.response.CaseResponse100402;
import com.citic.server.dx.task.taskBo.Q_mainBo;
import com.citic.server.net.mapper.BR22_caseMapper;
import com.citic.server.net.mapper.MC00_common_Mapper;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.StrUtils;
 
/**
 * 案件举报反馈报文
 * @author  
 * @version 1.0
 */

public class TK_ESDX_JS07 extends NBaseTask {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESDX_JS07.class);
	//private JdbcTemplate jdbcTemplate = null;

	private CacheService cacheService;
	
	
	
	public TK_ESDX_JS07(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
		super(ac, mC21_task_fact);
		//ApplicationCFG applicationCFG = (ApplicationCFG) this.getAc().getBean("applicationCFG");
		//jdbcTemplate = (JdbcTemplate) this.getAc().getBean(applicationCFG.getJdbctemplate_business());
		cacheService = (CacheService) ac.getBean("cacheService");
	}

	/**
	 * 
	 */
	public boolean calTask() throws Exception {
		boolean isSucc = true;
		   try {
		
			MC00_common_Mapper common_Mapper=(MC00_common_Mapper) this.getAc().getBean("MC00_common_Mapper"); 
			
			  BR22_caseMapper br22_caseMapper = (BR22_caseMapper) this.getAc().getBean("BR22_caseMapper");	

			MC21_task_fact mc21_task_fact= this.getMC21_task_fact();
			String orgkey=mc21_task_fact.getTaskobj();
			Q_mainBo q_mainBo=new Q_mainBo(this.getAc());

		    //1.取出文件地址
		    ArrayList<MC20_task_msg> taskMsgList=common_Mapper.getMC20_task_msgList(mc21_task_fact.getTaskkey());
		    //获取系统参数
			HashMap sysParaMap = (HashMap) cacheService.getCache("sysParaDetail", HashMap.class);
			String root=StrUtils.null2String((String)sysParaMap.get("2"));
			MC20_task_msg  taskmag=new MC20_task_msg();
			if(taskMsgList!=null&&taskMsgList.size()>0){
				  taskmag=taskMsgList.get(0);
			 }
			 
			//2.解析请求报文
			String flie_path = root + taskmag.getMsgpath();
			CaseResponse100402 query = (CaseResponse100402) CommonUtils.unmarshallUTF8Document(CaseResponse100402.class, flie_path);
			

			//查询出caseid
			Br22_Msg  br22_msg=new Br22_Msg();
			br22_msg.setApplicationid(query.getApplicationID());
			br22_msg=br22_caseMapper.getBr22_Msg(br22_msg);
			query.setCaseid(br22_msg.getCaseid());
			
			//删除请求单号下的数据
			br22_caseMapper.delBr22_CASE_BACK(br22_msg.getCaseid());
			//插入响应主表
			br22_caseMapper.insertBr22_CASE_BACK(query);

		
		   } catch (Exception e) {
				isSucc = false;
				e.printStackTrace();
				this.inertErrorLog(e);
				throw e;			
			}
			return isSucc;
		}
	
public String get_Org(MC21_task_fact mc21_task_fact, String type, String cardnumber, String credentialtype, String reptype) throws Exception {

	String orgkey ="";
	String answerCode="";
	/*if((cardnumber!=null&&cardnumber.equals(""))||(credentialtype!=null&&credentialtype.equals(""))){
		answerCode="0500";
		return orgkey+"&"+answerCode;
	}
	try {
		String organUpdateFlag = (String) sysParaMap.get("organUpdateFlag");
		if (organUpdateFlag != null && (organUpdateFlag.equals("1")||organUpdateFlag.equals("0"))) {
			
			if (organUpdateFlag.equals("0")) { //若是本地从本地查询
				List<OrganKeyQuery> organList = new ArrayList<OrganKeyQuery>();
				if (type.equals("1") || type.equals("3")) { //查询账户或卡表取出机构			
					organList = common_Mapper.getBb11_card_organ(cardnumber);
					if (organList == null || organList.size() == 0) {
						organList = common_Mapper.getBb11_deposit_organ(cardnumber);
					}
				} else { //查询客户表取出客户的归属机构
					organList = common_Mapper.getBb11_party_organ(cardnumber);
				}
		
				if (organList != null && organList.size() > 0) {
					OrganKeyQuery bb11_organ_query = organList.get(0);
					orgkey = bb11_organ_query.getOrgKey();
				}
				
			} else {
				DataOperate2  dataOperate = (DataOperate2) this.ac.getBean(REMOTE_DATA_OPERATE_NAME_DX);
				if (type.equals("2")) { //证件类型号码查询
					//转成核心证件号码
					HashMap etlcodeMap = (HashMap) cacheService.getCache("BB13_etl_code_mapDetail", HashMap.class);
					String codeStr="DXZJ";
					if(reptype.equals("1"))codeStr="GFZJ";
					if(reptype.equals("3"))codeStr="YJZJ";
					HashMap zjlxMap = (HashMap) etlcodeMap.get(codeStr);
					if (zjlxMap != null && zjlxMap.get(credentialtype) != null) { //证件类型转成核心需要的
						credentialtype = (String) zjlxMap.get(credentialtype);
					}
				}
				String xm=mc21_task_fact.getFacttablename();
					cardnumber = cardnumber + "#" + credentialtype+"#"+xm;			
				String subjectType="";  //证件类型或客户类型
				String  acctname="";
				String  organkey_r="";
				String[] strs=cardnumber.split("#");
				if(strs.length>=3){
					cardnumber=strs[0];
					subjectType=strs[1];
					acctname=strs[2];
				}
				if(strs.length==4){
					organkey_r=strs[3];
				}
				OrganKeyQuery	organkeyquery=null;
				if(type.equals("1")){
						organkeyquery=dataOperate.getOrgkeyByCard(subjectType, cardnumber,acctname);
				}else if(type.equals("2")){
						organkeyquery=dataOperate.getOrgkeyByCredentialNumber(subjectType, cardnumber,acctname);
			    }else{
			    		organkeyquery=dataOperate.getOrgkeyByAcctNumber(cardnumber,acctname);
			    }
				String newAcctName="";
				if(organkeyquery!=null){
					orgkey=organkeyquery.getOrgKey();
					newAcctName=organkeyquery.getAcctName();
				}
				
				if(orgkey!=null&&!orgkey.equals("")){
					
					if(organkey_r!=null&&!organkey_r.equals("")){
					CacheService cacheService = SpringContextHolder.getBean("cacheService"); // 缓存服务
					HashMap repOrgHash = (HashMap<String, Object>) cacheService.getCache("Mp02_repOrgMapDetail", HashMap.class);
					HashMap orgMap=(HashMap)repOrgHash.get(organkey_r);
					   if(!orgMap.containsKey(orgkey)){
						   orgkey=""; //不是本行数据
					    }
					}
					
				}
			
			}
		} else {
			if (organUpdateFlag.equals("2")) {
				orgkey = (String) sysParaMap.get("innerOrgKey");
			}
		}
	} catch (DataOperateException e) {
		e.printStackTrace();
	}	catch (RemoteAccessException e) {
		e.printStackTrace();
		answerCode="1199";
	}
	if(orgkey.equals("")){
		answerCode="0100";
	}*/
	return orgkey+"&"+answerCode;
}

	 

}