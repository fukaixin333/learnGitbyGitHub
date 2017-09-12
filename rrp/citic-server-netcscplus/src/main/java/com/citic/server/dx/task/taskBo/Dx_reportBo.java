package com.citic.server.dx.task.taskBo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;










import com.citic.server.dx.TxConstants;
import com.citic.server.dx.domain.Attachment;
import com.citic.server.dx.domain.Br22_Msg;
import com.citic.server.dx.domain.Br22_StopPay;
import com.citic.server.dx.domain.Cs_case;
import com.citic.server.dx.domain.Response;
import com.citic.server.dx.domain.Transaction;
import com.citic.server.dx.domain.request.CaseRequest100401;
import com.citic.server.dx.domain.request.CaseRequest_Transaction;
import com.citic.server.dx.domain.request.ExceptionEventRequest_Accounts;
import com.citic.server.dx.domain.request.SuspiciousRequest100403;
import com.citic.server.dx.domain.request.SuspiciousRequest100404;
import com.citic.server.dx.domain.request.SuspiciousRequest100405;
import com.citic.server.dx.domain.request.SuspiciousRequest_Account;
import com.citic.server.dx.domain.request.SuspiciousRequest_Accounts;
import com.citic.server.dx.domain.request.SuspiciousRequest_Transaction;
import com.citic.server.dx.service.RequestMessageService;
import com.citic.server.net.mapper.BR22_caseMapper;
import com.citic.server.service.domain.MP02_rep_org_map;
import com.citic.server.service.task.taskBo.BaseBo;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.DtUtils;
import com.citic.server.utils.StrUtils;

public class Dx_reportBo extends BaseBo {
	private String rootpath="";
	private String attachpath="";
	private 	  BR22_caseMapper br22_caseMapper = (BR22_caseMapper) ac.getBean("BR22_caseMapper");	
    public Dx_reportBo(ApplicationContext ac){
        super(ac);
        HashMap sysParaMap = (HashMap) cacheService.getCache("sysParaDetail", HashMap.class);
    	//获取根路径
           rootpath=(String)sysParaMap.get("2");
    	    attachpath=(String)sysParaMap.get("attachpath");
    }
	public  String  makeXml_ajjb( CaseRequest100401 cs_case_ajjb,List<CaseRequest_Transaction> transList,List<Attachment>  acctchList)throws Exception{
		String xmlStr="";
		   HashMap organMap = (HashMap) cacheService.getCache("DorganDetail", HashMap.class);

		   // 处理交易list
		//	HashMap<String ,List<Cs_case_trans>>  transMap=this.getTransMap(transList,  organMap);

			String organkey=cs_case_ajjb.getOrgkey();
			/*	String organname=StrUtils.null2String((String)organMap.get(organkey));
			cs_case_ajjb.setReportorgname(organname);  //上报机构名称
*/			 List<CaseRequest_Transaction>  newtransList=this.getTransList_P(transList, organMap);   //案件下的交易
			 cs_case_ajjb.setTransactionList(newtransList);
			 List<Attachment>  newacctchList=this.getCaseAttachList(acctchList);
			 cs_case_ajjb.setAttachments(newacctchList);
			 //业务申请编码
			 String applicationID=  this.getApplicationID(cs_case_ajjb.getTxCode());
			 cs_case_ajjb.setApplicationID(applicationID);
			 //流水号
			String transserialnumber=  this.getTransSerialNumber("2",organkey);
			cs_case_ajjb.setTransSerialNumber(transserialnumber);
			//to机构
			if(cs_case_ajjb.getToorg()!=null&&!cs_case_ajjb.getToorg().equals("")){
				cs_case_ajjb.setTo(cs_case_ajjb.getToorg());
			}
			//经办人经办电话上报机构名称
			MP02_rep_org_map  orgmap=this.getMp02_organPerson("2",cs_case_ajjb.getOrgkey());
			cs_case_ajjb.setOperatorName(orgmap.getOperatorname());
			cs_case_ajjb.setOperatorPhoneNumber(orgmap.getOperatorphonenumber());
			cs_case_ajjb.setReportOrgName(orgmap.getOrganname());
			//xmlStr=CommonUtils.marshallContext(cs_case_ajjb);
			String filename=cs_case_ajjb.getCaseid()+cs_case_ajjb.getMsg_type_cd()+".XML";
			String filepath="/"+attachpath+"/"+DtUtils.getNowDate()+"/dx/reportXml";
			String returnpath=filepath+"/"+filename;
			//生成文件
			CommonUtils.marshallUTF8Document(cs_case_ajjb, rootpath+filepath, filename);
			//写入msg表
			Cs_case cs_case=new Cs_case();
			cs_case.setCaseid(cs_case_ajjb.getCaseid());
			cs_case.setMsg_type_cd(cs_case_ajjb.getMsg_type_cd());
			cs_case.setFeaturecodetype(cs_case_ajjb.getFeaturecodetype());
			cs_case.setApplicationid(applicationID);
			this.insertBr22_msg(cs_case, filename, returnpath);
			//4. 修改人工举报止付表改案例的applicationID
		     this.updateBr22_stop(cs_case_ajjb.getCaseid(), applicationID);
		     
	    return returnpath;
	}
	
	/*public  HashMap<String ,List<Cs_case_trans>> getTransMap(List<Cs_case_trans> transList, HashMap organMap)throws Exception{
		HashMap<String ,List<Cs_case_trans>>  transMap=new HashMap<String ,List<Cs_case_trans>>();
		for(Cs_case_trans  trans:transList){
			String caseid=trans.getCaseid();
			String organname=StrUtils.null2String((String)organMap.get(trans.getOrgankey()));
			String borrowingsigns=trans.getBorrowingsigns();  //借贷标志
			if(borrowingsigns.equals("0")){ //收,将转出的付给我方
				trans.setAccountname(trans.getOpp_name());
				trans.setAccountnumber(trans.getOpp_acctnum());
				trans.setOrgankey(trans.getOpp_openbank_id());
				trans.setOrganname(trans.getOpp_openbank());
				trans.setOpp_acctnum(trans.getAccountnumber());
				trans.setOpp_name(trans.getAccountname());
				trans.setOpp_openbank_id(trans.getOrgankey());
				trans.setOpp_openbank(organname);
			}
			if(transMap.containsKey(caseid)){
			  List<Cs_case_trans>  newtransList=transMap.get(caseid);
			  newtransList.add(trans);
			}else{
				List<Cs_case_trans> newtransList=new ArrayList<Cs_case_trans> ();
				newtransList.add(trans);
				transMap.put(caseid, newtransList);
			}
		}
		
		return transMap;
	}*/
	
	public  List<Transaction> getTransList(List<Transaction> transList, HashMap<String,String> organMap)throws Exception{
		List<Transaction> newtransList=new ArrayList<Transaction> ();
		for(Transaction  trans:transList){
		
			String organname=StrUtils.null2String((String)organMap.get(trans.getTransferOutBankID()));
			String borrowingsigns=trans.getBorrowingSigns();  //借贷标志
			if(borrowingsigns.equals("0")){ //借 收,将转出的付给我方
				trans.setTransferOutAccountName(trans.getTransferInAccountName());;
				trans.setTransferOutAccountNumber(trans.getTransferInAccountNumber());
				trans.setTransferOutBankID(trans.getTransferInBankID());;
				trans.setTransferOutBankName(trans.getTransferInBankName());
				trans.setTransferInAccountNumber(trans.getTransferOutAccountNumber());
				trans.setTransferInAccountName(trans.getTransferOutAccountName());
				trans.setTransferInBankID(trans.getTransferOutBankID());;
				trans.setTransferInBankName(trans.getTransferOutBankName());
			}
			
				newtransList.add(trans);
			
		}
		
		return newtransList;
	}
	
	public  List<CaseRequest_Transaction> getTransList_P(List<CaseRequest_Transaction> transList, HashMap<String,String> organMap)throws Exception{
		List<CaseRequest_Transaction> newtransList=new ArrayList<CaseRequest_Transaction> ();
		for(CaseRequest_Transaction  trans:transList){
		
			String organname=StrUtils.null2String((String)organMap.get(trans.getTransferOutBankID()));
			trans.setTransferOutBankName(organname);
			String borrowingsigns=trans.getBorrowingSigns();  //借贷标志
			if(borrowingsigns!=null&&borrowingsigns.equals("0")){ //借 收,将转出的付给我方
				trans.setTransferOutAccountName(trans.getTransferInAccountName());;
				trans.setTransferOutAccountNumber(trans.getTransferInAccountNumber());
				trans.setTransferOutBankID(trans.getTransferInBankID());;
				trans.setTransferOutBankName(trans.getTransferInBankName());
				trans.setTransferInAccountNumber(trans.getTransferOutAccountNumber());
				trans.setTransferInAccountName(trans.getTransferOutAccountName());
				trans.setTransferInBankID(trans.getTransferOutBankID());;
				trans.setTransferInBankName(trans.getTransferOutBankName());
			}
			
				newtransList.add(trans);
			
		}
		
		return newtransList;
	}
	
	public  Map<String, List<SuspiciousRequest_Transaction>> getTransListMap(List<SuspiciousRequest_Transaction> transList,String featureCode, HashMap<String,String> organMap)throws Exception{
		
		Map<String, List<SuspiciousRequest_Transaction>>  map=new HashMap<String, List<SuspiciousRequest_Transaction>>();
		for(SuspiciousRequest_Transaction  trans:transList){
		    String acct_num=trans.getAccountNumber();
			String organname=StrUtils.null2String((String)organMap.get(trans.getTransactionBranchCode()));
			  trans.setTransactionBranchName(organname);
			  trans.setFeatureCode(featureCode);
			if(map.containsKey(acct_num)){
				List<SuspiciousRequest_Transaction> newtransList=map.get(acct_num);
				newtransList.add(trans);
			}else{
			List<SuspiciousRequest_Transaction> newtransList=new ArrayList<SuspiciousRequest_Transaction> ();
				newtransList.add(trans);
				map.put(acct_num, newtransList);
			}
			
		}
		
		return map;
	}
	
	public  List<SuspiciousRequest_Accounts> getCardList(List<SuspiciousRequest_Accounts> cardList, HashMap<String,String> organMap)throws Exception{
		List<SuspiciousRequest_Accounts> newcardList=new ArrayList<SuspiciousRequest_Accounts> ();
		for(SuspiciousRequest_Accounts  card:cardList){
			String organname=StrUtils.null2String((String)organMap.get(card.getOpen_organkey()));
			card.setAccountOpenPlace(organname);			
			newcardList.add(card);
			
		}
		
		return newcardList;
	}
	
	public  SuspiciousRequest100403  makeXml_yckk( Cs_case cs_case_ajjb,List<SuspiciousRequest_Accounts> cardList)throws Exception{
		String xmlStr="";
		   HashMap organMap = (HashMap) cacheService.getCache("DorganDetail", HashMap.class);
			SuspiciousRequest100403  Cs_case_yckk=new SuspiciousRequest100403();
			String organkey=cs_case_ajjb.getOrgkey();
		/*	String organname=StrUtils.null2String((String)organMap.get(organkey));
			Cs_case_yckk.setReportOrgName(organname);  //上报机构名称
*/			 List<SuspiciousRequest_Accounts>  newcardList=this.getCardList(cardList, organMap);   //案件下的卡
			 Cs_case_yckk.setAccountsList(newcardList);
			 //业务申请编码
			 String applicationID=  this.getApplicationID(Cs_case_yckk.getTxCode());
			 Cs_case_yckk.setApplicationID(applicationID);
			 //流水号
			String transserialnumber=  this.getTransSerialNumber("2",organkey);
			Cs_case_yckk.setTransSerialNumber(transserialnumber);
			Cs_case_yckk.setIDName(cs_case_ajjb.getCasename());
			Cs_case_yckk.setIDType(cs_case_ajjb.getCitp());
			Cs_case_yckk.setIDNumber(cs_case_ajjb.getCtid());
			Cs_case_yckk.setFeatureCode(cs_case_ajjb.getFeaturecode());
			//经办人经办电话上报机构名称
			MP02_rep_org_map  orgmap=this.getMp02_organPerson("2",organkey);
			Cs_case_yckk.setOperatorName(orgmap.getOperatorname());
			Cs_case_yckk.setOperatorPhoneNumber(orgmap.getOperatorphonenumber());
			Cs_case_yckk.setReportOrgName(orgmap.getOrganname());
			Cs_case_yckk.setBankID(orgmap.getReport_organkey());

		//	xmlStr=CommonUtils.marshallContext(Cs_case_yckk);
			String filename=cs_case_ajjb.getCaseid()+cs_case_ajjb.getMsg_type_cd()+".XML";
			String filepath="/"+attachpath+"/"+DtUtils.getNowDate()+"/dx/reportXml";
			//生成文件
			CommonUtils.marshallUTF8Document(Cs_case_yckk, rootpath+filepath, filename);
			//写入msg表
			String returnpath=filepath+"/"+filename;
			this.insertBr22_msg(cs_case_ajjb, filename, returnpath);
			
	    return Cs_case_yckk ;
	}
	
	public  	Cs_case   makeXml_sazh( SuspiciousRequest100404 cs_case_sazh, List<SuspiciousRequest_Account>  acctList,String caseid)throws Exception{

	
			 List<SuspiciousRequest_Account>  newacctList=this.getAcctList(acctList,caseid);   //案件下的账号
			 cs_case_sazh.setAccountList(newacctList);
			 //业务申请编码
			 String applicationID=  this.getApplicationID(cs_case_sazh.getTxCode());
			 cs_case_sazh.setApplicationID(applicationID);
			 
				//经办人经办电话上报机构名称
				MP02_rep_org_map  orgmap=this.getMp02_organPerson("2",cs_case_sazh.getOrgkey());
				cs_case_sazh.setOperatorName(orgmap.getOperatorname());
				cs_case_sazh.setOperatorPhoneNumber(orgmap.getOperatorphonenumber());
				cs_case_sazh.setReportOrgName(orgmap.getOrganname());
				cs_case_sazh.setBankID(orgmap.getReport_organkey());
			  //开卡地点转码
				String openPlace=cs_case_sazh.getAccountOpenPlace();
				HashMap Mp02_organMap = (HashMap) cacheService.getCache("DorganDetail", HashMap.class);
				if(Mp02_organMap!=null&&openPlace!=null&&!openPlace.equals("")){
					cs_case_sazh.setAccountOpenPlace((String)Mp02_organMap.get(openPlace));
				}
		//	xmlStr=CommonUtils.marshallContext(cs_case_sazh);
			Cs_case cs_case=br22_caseMapper.getBr22_CaseList(caseid);
			 //流水号
			String transserialnumber=  this.getTransSerialNumber("2",cs_case.getOrgkey());
			cs_case_sazh.setTransSerialNumber(transserialnumber);
			
			String filename=cs_case.getCaseid()+cs_case.getMsg_type_cd()+".XML";
			String filepath="/"+attachpath+"/"+DtUtils.getNowDate()+"/dx/reportXml";
			String returnpath=filepath+"/"+filename;
			//生成文件
			CommonUtils.marshallUTF8Document(cs_case_sazh, rootpath+filepath, filename);
			//写入msg表		
			cs_case.setCaseid(cs_case.getCaseid());
			cs_case.setMsg_type_cd(cs_case.getMsg_type_cd());
			cs_case.setFeaturecodetype(cs_case.getFeaturecodetype());
			this.insertBr22_msg(cs_case, filename, returnpath);
	    return cs_case;
	}
	
	public  List<ExceptionEventRequest_Accounts> getExceptionEventAcctList(List<SuspiciousRequest_Account> acctList,String caseid,String featureCode)throws Exception{
		
		List<ExceptionEventRequest_Accounts> acctountsList=new ArrayList<ExceptionEventRequest_Accounts>();
		
		//查询流水信息
	    HashMap organMap = (HashMap) cacheService.getCache("DorganDetail", HashMap.class);
	    List<SuspiciousRequest_Transaction> transList=br22_caseMapper.getBr22_Case_transByCaseIdList(caseid);
    	 Map<String,List<SuspiciousRequest_Transaction>>  transMap=this.getTransListMap(transList, featureCode,organMap);   //案件下的交易
    	 HashMap<String,ExceptionEventRequest_Accounts> cardMap=new HashMap<String,ExceptionEventRequest_Accounts> ();
     	HashMap dictMap = (HashMap) cacheService.getCache("MP01_dict_stdDetail", HashMap.class);
     	HashMap statsMap = (HashMap) dictMap.get("B00013");//账户状态
     	HashMap cashMap = (HashMap) dictMap.get("B00019");//钞汇标志
		for(SuspiciousRequest_Account  acct:acctList){
			String cardnum=acct.getCardnumber();
			String acctname=acct.getAccountname();
            String acct_num=acct.getAccountNumber();
            if(statsMap!=null){
             String status=StrUtils.null2String((String)statsMap.get((StrUtils.null2String(acct.getAccountStatus()))));
            acct.setAccountStatus(status);
            }
            if(cashMap!=null){
            String cashRemit=StrUtils.null2String((String)cashMap.get((StrUtils.null2String(acct.getCashRemit()))));       
            acct.setCashRemit(cashRemit);
            }
            acct.setTransactionList(transMap.get(acct_num));
            if(!cardMap.containsKey(cardnum)){
            	ExceptionEventRequest_Accounts  accounts=new ExceptionEventRequest_Accounts();
            	accounts.setAccountName(acctname);
            	accounts.setCardNumber(cardnum);
            	accounts.setRemark("");
            	List<SuspiciousRequest_Account> newacctList=new ArrayList<SuspiciousRequest_Account> ();
            	newacctList.add(acct);            
            	accounts.setAccountList(newacctList);
            	cardMap.put(cardnum, accounts);
            }else{
            	ExceptionEventRequest_Accounts  acctounts=cardMap.get(cardnum);
            	List<SuspiciousRequest_Account> newacctList=acctounts.getAccountList();
            	newacctList.add(acct);
            	acctounts.setAccountList(newacctList);
            }

		}
		if(cardMap.size()>0){
			Iterator item=cardMap.keySet().iterator();
			while(item.hasNext()){
				String cardnum=(String)item.next();
				ExceptionEventRequest_Accounts  acctounts=cardMap.get(cardnum);
				acctountsList.add(acctounts);
			}
		}
		
		return acctountsList;
	}
	
	public  List<SuspiciousRequest_Account> getAcctList(List<SuspiciousRequest_Account> acctList,String caseid)throws Exception{
		List<SuspiciousRequest_Account> newacctList=new ArrayList<SuspiciousRequest_Account> ();
		//查询流水信息
	//	List<QueryRequest_Transaction>
	    HashMap organMap = (HashMap) cacheService.getCache("DorganDetail", HashMap.class);
	    List<SuspiciousRequest_Transaction> transList=br22_caseMapper.getBr22_Case_transByCaseIdList(caseid);
    	 Map<String,List<SuspiciousRequest_Transaction>>  transMap=this.getTransListMap(transList, "",organMap);   //案件下的交易
     	HashMap dictMap = (HashMap) cacheService.getCache("MP01_dict_stdDetail", HashMap.class);
     	HashMap statsMap = (HashMap) dictMap.get("B00013");//账户状态
    	HashMap cashMap = (HashMap) dictMap.get("B00019");//钞汇标志
		for(SuspiciousRequest_Account  acct:acctList){
            String acct_num=acct.getAccountNumber();
            if(statsMap!=null){
            String status=StrUtils.null2String((String)statsMap.get((StrUtils.null2String(acct.getAccountStatus()))));
            acct.setAccountStatus(status);
            }
            if(cashMap!=null){
            String cashRemit=StrUtils.null2String((String)cashMap.get((StrUtils.null2String(acct.getCashRemit()))));
            acct.setCashRemit(cashRemit);
            }
           acct.setTransactionList(transMap.get(acct_num));
			newacctList.add(acct);			
		}
		
		return newacctList;
	}
	
	public  SuspiciousRequest100405  makeXml_ycsj( Cs_case cs_case_ajjb)throws Exception{
	     
		String orgkey=cs_case_ajjb.getOrgkey();
		   String caseid=cs_case_ajjb.getCaseid();
		   String featureCode=cs_case_ajjb.getFeaturecode();
		 
		   //查询账户
		   List<SuspiciousRequest_Account>  acctList=br22_caseMapper.getBr22_Case_acctByCaseIdList(caseid);
		   //账户下的交易
			List<ExceptionEventRequest_Accounts> newacctsList=this.getExceptionEventAcctList(acctList, caseid,featureCode);
					
		   SuspiciousRequest100405 cs_case_ycsj=new SuspiciousRequest100405();		
			 cs_case_ycsj.setList(newacctsList);
			 //业务申请编码
			 String applicationID=  this.getApplicationID(cs_case_ycsj.getTxCode());
			 cs_case_ycsj.setApplicationID(applicationID);
			 //流水号
			String transserialnumber=  this.getTransSerialNumber("2",orgkey);
			cs_case_ycsj.setTransSerialNumber(transserialnumber);
			cs_case_ycsj.setFeatureCode(cs_case_ajjb.getFeaturecode());
			//经办人经办电话上报机构名称
			MP02_rep_org_map  orgmap=this.getMp02_organPerson("2",orgkey);
			cs_case_ycsj.setOperatorName(orgmap.getOperatorname());
			cs_case_ycsj.setOperatorPhoneNumber(orgmap.getOperatorphonenumber());
			cs_case_ycsj.setBankID(orgmap.getReport_organkey());
		//	xmlStr=CommonUtils.marshallContext(cs_case_ycsj);
			String filename=cs_case_ajjb.getCaseid()+cs_case_ajjb.getMsg_type_cd()+".XML";
			String filepath="/"+attachpath+"/"+DtUtils.getNowDate()+"/dx/reportXml";
			//生成文件
			CommonUtils.marshallUTF8Document(cs_case_ycsj, rootpath+filepath, filename);
			//写入msg表
			this.insertBr22_msg(cs_case_ajjb, filename, filepath);
			
	    return cs_case_ycsj;
	}
	
	public  List<Attachment> getCaseAttachList( List<Attachment> acctchList)throws Exception{
		
		List<Attachment> newacctchList=new ArrayList<Attachment> ();
		HashMap sysParaMap = (HashMap) cacheService.getCache("sysParaDetail", HashMap.class);
		 String root=StrUtils.null2String((String)sysParaMap.get("2"));
		for(Attachment  acctch:acctchList){
          
			String filepath=acctch.getFilepath();
			acctch.setContent(CommonUtils.readBinaryFile(root+filepath));
			newacctchList.add(acctch);
			
		}
		
		return newacctchList;
	}
	
public  void insertBr22_msg( Cs_case cs_case_ajjb,String filename,String filepath)throws Exception{

	  Br22_Msg msg=new  Br22_Msg();
	  msg.setCaseid(cs_case_ajjb.getCaseid());
	  msg.setMsg_type_cd(cs_case_ajjb.getMsg_type_cd());  
		//删除msg
	  br22_caseMapper.deleteBr22Msg(msg);	  
		//插入msg
		String msgkey=this.geSequenceNumber("SEQ_BR22_MSG") ;
	  msg.setFeaturecodetype(cs_case_ajjb.getFeaturecodetype());
	  msg.setMsg_filename(filename);
	  msg.setMsg_filepath(filepath);
	  msg.setCreate_dt(DtUtils.getNowDate());
	  msg.setMsgkey(msgkey);
	  msg.setApplicationid(cs_case_ajjb.getApplicationid());
	  br22_caseMapper.insertBr22Msg(msg);
	  
	}

public  Response send_msg_ajjb( CaseRequest100401 cs_case_ajjb)throws Exception{
	Response res=new Response();
	  res=requestMessageService.sendCaseRequest100401(cs_case_ajjb);	
	  res.setTransSerialNumber(cs_case_ajjb.getCaseid()+cs_case_ajjb.getMsg_type_cd());
	  return res;
	}

public  void updateBr22_stop(String caseid,String applicationid)throws Exception{
	    Br22_StopPay  br22_stoppay=new Br22_StopPay();
	    br22_stoppay.setCaseid(caseid);
	    br22_stoppay.setApplicationID(applicationid);
	    br22_caseMapper.updateBr22_Stop(br22_stoppay);
	}

public  Response send_msg_yckk( SuspiciousRequest100403 cs_case_yckk,String caseid,String msg_type_cd)throws Exception{
	Response res=new Response();
	  res=requestMessageService.sendSuspiciousRequest100403(cs_case_yckk);	
	  res.setTransSerialNumber(caseid+msg_type_cd);
	  return res;
	}

public  Response send_msg_sazh( SuspiciousRequest100404 cs_case_yczh,String caseid,String msg_type_cd)throws Exception{
	Response res=new Response();
	  res=requestMessageService.sendSuspiciousRequest100404(cs_case_yczh);	
	  res.setTransSerialNumber(caseid+msg_type_cd);
	  return res;
	}
public  Response send_msg_ycsj( SuspiciousRequest100405 cs_case_ycsj,String caseid,String msg_type_cd)throws Exception{
	Response res=new Response();
	  res=requestMessageService.sendSuspiciousRequest100405(cs_case_ycsj);	
	  res.setTransSerialNumber(caseid+msg_type_cd);
	  return res;
	}

public void  updateCase_status(String caseid,String code) throws Exception {

	Cs_case  cs_case=new Cs_case();
	 String status_cd="7";
	if (!code.equals(TxConstants.CODE_OK)) { 
		status_cd="8";   //失败
	}
	cs_case.setStatus_cd(status_cd);
	cs_case.setCaseid(caseid);
	br22_caseMapper.updateCaseStatus(cs_case);
	
}

public void  updateCase_status_qx(String caseid,String code) throws Exception {

	Cs_case  cs_case=new Cs_case();
	 String status_cd="9"; //取消成功
	if (!code.equals(TxConstants.CODE_OK)) { 
		status_cd="8";   //失败
	}
	cs_case.setStatus_cd(status_cd);
	cs_case.setCaseid(caseid);
	br22_caseMapper.updateCaseStatus(cs_case);
	
}

public Response makeXml_qx(String caseid) throws Exception {
	Cs_case cs_case=br22_caseMapper.getBr22_CaseList(caseid);
	Response response=new Response();
	//查询msg得到正常报文
	Br22_Msg br22_msg=new Br22_Msg();
	br22_msg.setCaseid(caseid);
	br22_msg.setMsg_type_cd("N");
	br22_msg=br22_caseMapper.getBr22_Msg(br22_msg);
   String featurecodetype=br22_msg.getFeaturecodetype();
	//解析报文
   HashMap sysParaMap = (HashMap) cacheService.getCache("sysParaDetail", HashMap.class);
  String  root=StrUtils.null2String((String)sysParaMap.get("2"));
  String filename=caseid+"D.XML";
	String filepath="/"+attachpath+"/"+DtUtils.getNowDate()+"/dx/reportXml";
	String returnpath=filepath+"/"+filename;
   String file_name=root+br22_msg.getMsg_filepath();
   String transnum=this.getTransSerialNumber("2",cs_case.getOrgkey());
   if(featurecodetype.equals("1")){ //异常开卡
	   SuspiciousRequest100403 suspiciousrequest100403=(SuspiciousRequest100403)CommonUtils.unmarshallContext(SuspiciousRequest100403.class, file_name);
	   suspiciousrequest100403.setFeatureCode("0000");
	   suspiciousrequest100403.setTransSerialNumber(transnum);
	   CommonUtils.marshallUTF8Document(suspiciousrequest100403,  rootpath+filepath, filename);
	   response=this.send_msg_yckk(suspiciousrequest100403, caseid, "D");
   }
   if(featurecodetype.equals("2")){ //涉案账户
	   SuspiciousRequest100404 suspiciousrequest100404=(SuspiciousRequest100404)CommonUtils.unmarshallContext(SuspiciousRequest100404.class, file_name);
	   suspiciousrequest100404.setFeatureCode("0000");
	   suspiciousrequest100404.setTransSerialNumber(transnum);
	   CommonUtils.marshallUTF8Document(suspiciousrequest100404,  rootpath+filepath, filename);
	   response=this.send_msg_sazh(suspiciousrequest100404, caseid, "D");
   }
   if(featurecodetype.equals("3")){ //异常事件
	   SuspiciousRequest100405 suspiciousrequest100405=(SuspiciousRequest100405)CommonUtils.unmarshallContext(SuspiciousRequest100405.class, file_name);
	   //suspiciousrequest100405.setFeatureCode("0000");
	   suspiciousrequest100405.setTransSerialNumber(transnum);
	   List<ExceptionEventRequest_Accounts>  cardList=suspiciousrequest100405.getList();
	   for(ExceptionEventRequest_Accounts  card:cardList){
		   List<SuspiciousRequest_Account> acctList=card.getAccountList();
		   for(SuspiciousRequest_Account acct:acctList){
			   List<SuspiciousRequest_Transaction> transactionList=acct.getTransactionList();
			   for(SuspiciousRequest_Transaction trans:transactionList){
				   trans.setFeatureCode("0000");
			   }
		   }
	   }
	   CommonUtils.marshallUTF8Document(suspiciousrequest100405,  rootpath+filepath, filename);
	   response=this.send_msg_ycsj(suspiciousrequest100405, caseid, "D");
   }
	//写入msg表
	cs_case.setCaseid(caseid);
	cs_case.setMsg_type_cd("D");
	cs_case.setFeaturecodetype("0000");
	this.insertBr22_msg(cs_case, filename, returnpath);
	

	
	return response;
	
}
	
}
