package com.citic.server.whpsb.task.taskBo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.runtime.Utility;
import com.citic.server.service.task.taskBo.BaseBo;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.DtUtils;
import com.citic.server.utils.StrUtils;
import com.citic.server.whpsb.domain.Br51_cxqq;
import com.citic.server.whpsb.domain.Br51_cxqq_back;
import com.citic.server.whpsb.domain.Br51_cxqq_back_msg;
import com.citic.server.whpsb.domain.Br51_cxqq_mx;
import com.citic.server.whpsb.domain.Whpsb_Header;
import com.citic.server.whpsb.domain.Whpsb_RequestJymx_Detail;
import com.citic.server.whpsb.domain.Whpsb_SadxBody;
import com.citic.server.whpsb.domain.request.Whpsb_RequestCkrzl;
import com.citic.server.whpsb.domain.request.Whpsb_RequestCkrzl_Detail;
import com.citic.server.whpsb.domain.request.Whpsb_RequestCkrzl_Sadx;
import com.citic.server.whpsb.domain.request.Whpsb_RequestJymx;
import com.citic.server.whpsb.domain.request.Whpsb_RequestKhzl;
import com.citic.server.whpsb.domain.request.Whpsb_RequestKhzl_Detail;
import com.citic.server.whpsb.domain.request.Whpsb_RequestKhzl_Sadx;
import com.citic.server.whpsb.domain.request.Whpsb_RequestZhxx;
import com.citic.server.whpsb.domain.request.Whpsb_RequestZhxx_Detail;
import com.citic.server.whpsb.domain.request.Whpsb_RequestZhxx_Sadx;
import com.citic.server.whpsb.mapper.BR51_cxqqMapper;

/**
 * 常规查询
 * 
 * @author dk
 */
public class Cxqq_FKBo extends BaseBo {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(Cxqq_FKBo.class);
	
	private BR51_cxqqMapper br51_cxqqMapper;
	
	public Cxqq_FKBo(ApplicationContext ac) {
		super(ac);
		br51_cxqqMapper = (BR51_cxqqMapper) ac.getBean("BR51_cxqqMapper");
	}
	
	public void delBr51_cxqq_back_msg(String msgseq) throws Exception {
		br51_cxqqMapper.delBr51_cxqq_back_msg(msgseq);
	}
	
	/**
	 * 账户信息
	 */
	public void MakeMsgCxqqWH_zhxx(String msgseq) throws Exception {
		
		// 查询主体
		Br51_cxqq br51_cxqq = br51_cxqqMapper.selBr51_cxqq(msgseq);
		// 查询明细
		List<Br51_cxqq_mx> cxqq_mx_list = br51_cxqqMapper.selBr51_cxqq_mxList(msgseq);
		
		// 查询账户
		List<Whpsb_RequestZhxx_Detail> acctList = br51_cxqqMapper.selBr51_cxqq_back_acctList(msgseq);
		HashMap<String, List<Whpsb_RequestZhxx_Detail>> acctMap = this.getAcctMap(acctList);
		
		// 拼接xmldto
		Whpsb_RequestZhxx zhxx = new Whpsb_RequestZhxx();
		Whpsb_Header header = this.makeWhpsb_Header(br51_cxqq);
		List<Whpsb_RequestZhxx_Sadx> sadxList = new ArrayList<Whpsb_RequestZhxx_Sadx>();
		String queryMode = "";
		for (Br51_cxqq_mx br51_cxqq_mx : cxqq_mx_list) {
			String bdhm = br51_cxqq_mx.getBdhm();
			queryMode = br51_cxqq_mx.getQrymode();
			List<Whpsb_RequestZhxx_Detail> detailList = acctMap.get(bdhm);
			Whpsb_RequestZhxx_Sadx sadx = new Whpsb_RequestZhxx_Sadx();
			Whpsb_SadxBody sadxBody = new Whpsb_SadxBody();
			BeanUtils.copyProperties(sadxBody, br51_cxqq_mx);
			//添加结果信息
			Br51_cxqq_back backMsg = this.getBr51_cxqq_back(bdhm);
			sadxBody.setCljg(backMsg.getCljg());
			sadxBody.setSbyy(backMsg.getCzsbyy());
			sadx.setSadxBody(sadxBody);
			sadx.setDetailList(detailList);
			sadxList.add(sadx);
		}
		zhxx.setHeader(header);
		zhxx.setSadxList(sadxList);
		
		// 生成xml插入msg
		this.MakeMsg(br51_cxqq, zhxx, queryMode);
		
		// 修改申请表
		this.updateBr51_cxqq(br51_cxqq);
		
	}
	
	public void MakeMsg(Br51_cxqq br51_cxqq, Object obj, String queryMode) throws Exception {
		// 生成xml
		HashMap sysParaMap = (HashMap) cacheService.getCache("sysParaDetail", HashMap.class);
		String root = StrUtils.null2String((String) sysParaMap.get("2"));
		String path = "/attach/" + Utility.currDate8() + "/whpsb";
		String filename = br51_cxqq.getMsgseq() + ".xml";
		String bingName = "binding_whpsb_" + queryMode + "fk";
		CommonUtils.marshallUTF8Document(obj, bingName, root + path, filename);
		// 插入msg
		Br51_cxqq_back_msg br51_cxqq_back_msg = new Br51_cxqq_back_msg();
		br51_cxqq_back_msg.setMsgseq(br51_cxqq.getMsgseq());
		br51_cxqq_back_msg.setXh("1");
		br51_cxqq_back_msg.setMsg_filename(filename);
		br51_cxqq_back_msg.setMsg_filepath(path + "/" + filename);
		br51_cxqq_back_msg.setStatus("0");
		br51_cxqq_back_msg.setCreate_dt(DtUtils.getNowTime());
		br51_cxqqMapper.insertBr51_cxqq_back_msg(br51_cxqq_back_msg);
	}
	
	public Whpsb_Header makeWhpsb_Header(Br51_cxqq br51_cxqq) {
		Whpsb_Header header = new Whpsb_Header();
		header.setCount(br51_cxqq.getCount());
	    header.setCzsj(Utility.currDate8());
		header.setYhdm(br51_cxqq.getYhdm());
		return header;
	}
	
	public HashMap<String, List<Whpsb_RequestZhxx_Detail>> getAcctMap(List<Whpsb_RequestZhxx_Detail> acctList) {
		HashMap<String, List<Whpsb_RequestZhxx_Detail>> map = new HashMap<String, List<Whpsb_RequestZhxx_Detail>>();
		if (acctList != null) {
			for (Whpsb_RequestZhxx_Detail acct : acctList) {
				String bdhm = acct.getBdhm();
				if (map.containsKey(bdhm)) {
					List<Whpsb_RequestZhxx_Detail> list = map.get(bdhm);
					list.add(acct);
				} else {
					List<Whpsb_RequestZhxx_Detail> newList = new ArrayList<Whpsb_RequestZhxx_Detail>();
					newList.add(acct);
					map.put(bdhm, newList);
				}
			}
		}
		return map;
	}
	
	/**
	 * 持卡人信息
	 */
	public void MakeMsgCxqqWH_ckrxx(String msgseq) throws Exception {
		
		// 查询主体
		Br51_cxqq br51_cxqq = br51_cxqqMapper.selBr51_cxqq(msgseq);
		// 查询明细
		List<Br51_cxqq_mx> cxqq_backmx_list = br51_cxqqMapper.selBr51_cxqq_mxList(msgseq);
		
		// 查询持卡人信息
		List<Whpsb_RequestCkrzl_Detail> cardList = br51_cxqqMapper.selBr51_cxqq_back_cardList(msgseq);
		HashMap<String, Whpsb_RequestCkrzl_Detail> cardMap = this.getCardMap(cardList);
		
		// 拼接xmldto
		Whpsb_RequestCkrzl ckrzl = new Whpsb_RequestCkrzl();
		Whpsb_Header header = this.makeWhpsb_Header(br51_cxqq);
		List<Whpsb_RequestCkrzl_Sadx> sadxList = new ArrayList<Whpsb_RequestCkrzl_Sadx>();
		String queryMode = "";
		for (Br51_cxqq_mx br51_cxqq_mx : cxqq_backmx_list) {
			String bdhm = br51_cxqq_mx.getBdhm();
			queryMode = br51_cxqq_mx.getQrymode();
			Whpsb_RequestCkrzl_Detail detailList = cardMap.get(bdhm);
			Whpsb_RequestCkrzl_Sadx sadx = new Whpsb_RequestCkrzl_Sadx();
			Whpsb_SadxBody sadxBody = new Whpsb_SadxBody();
			BeanUtils.copyProperties(sadxBody, br51_cxqq_mx);
			//添加结果信息
			Br51_cxqq_back backMsg = this.getBr51_cxqq_back(bdhm);
			sadxBody.setCljg(backMsg.getCljg());
			sadxBody.setSbyy(backMsg.getCzsbyy());
			sadx.setSadxBody(sadxBody);
			sadx.setDetail(detailList);
			sadxList.add(sadx);
		}
		ckrzl.setHeader(header);
		ckrzl.setSadxList(sadxList);
		
		// 生成xml插入msg
		this.MakeMsg(br51_cxqq, ckrzl, queryMode);
		
		// 修改申请表
		this.updateBr51_cxqq(br51_cxqq);
		
	}
	
	public HashMap<String, Whpsb_RequestCkrzl_Detail> getCardMap(List<Whpsb_RequestCkrzl_Detail> cardList) {
		HashMap<String, Whpsb_RequestCkrzl_Detail> map = new HashMap<String, Whpsb_RequestCkrzl_Detail>();
		if (cardList != null) {
			for (Whpsb_RequestCkrzl_Detail card : cardList) {
				String bdhm = card.getBdhm();
				if (!map.containsKey(bdhm)) {
					map.put(bdhm, card);
				}
			}
		}
		return map;
	}
	
	/**
	 * 开户信息
	 */
	public void MakeMsgCxqqWH_khxx(String msgseq) throws Exception {
		
		// 查询主体
		Br51_cxqq br51_cxqq = br51_cxqqMapper.selBr51_cxqq(msgseq);
		// 查询明细
		List<Br51_cxqq_mx> cxqq_backmx_list = br51_cxqqMapper.selBr51_cxqq_mxList(msgseq);
		
		// 查询持卡人信息
		List<Whpsb_RequestKhzl_Detail> cardList = br51_cxqqMapper.selBr51_cxqq_back_partyList(msgseq);
		HashMap<String, Whpsb_RequestKhzl_Detail> partyMap = this.getPartyMap(cardList);
		
		// 拼接xmldto
		Whpsb_RequestKhzl ckrzl = new Whpsb_RequestKhzl();
		Whpsb_Header header = this.makeWhpsb_Header(br51_cxqq);
		List<Whpsb_RequestKhzl_Sadx> sadxList = new ArrayList<Whpsb_RequestKhzl_Sadx>();
		String queryMode = "";
		for (Br51_cxqq_mx br51_cxqq_mx : cxqq_backmx_list) {
			String bdhm = br51_cxqq_mx.getBdhm();
			queryMode = br51_cxqq_mx.getQrymode();
			Whpsb_RequestKhzl_Detail detailList = partyMap.get(bdhm);
			Whpsb_RequestKhzl_Sadx sadx = new Whpsb_RequestKhzl_Sadx();
			Whpsb_SadxBody sadxBody = new Whpsb_SadxBody();
			BeanUtils.copyProperties(sadxBody, br51_cxqq_mx);
			//添加结果信息
			Br51_cxqq_back backMsg = this.getBr51_cxqq_back(bdhm);
			sadxBody.setCljg(backMsg.getCljg());
			sadxBody.setSbyy(backMsg.getCzsbyy());
			sadx.setSadxBody(sadxBody);
			sadx.setDetail(detailList);
			sadxList.add(sadx);
		}
		ckrzl.setHeader(header);
		ckrzl.setSadxList(sadxList);
		
		// 生成xml插入msg
		this.MakeMsg(br51_cxqq, ckrzl, queryMode);
		
		// 修改申请表
		this.updateBr51_cxqq(br51_cxqq);
		
	}
	
	public HashMap<String, Whpsb_RequestKhzl_Detail> getPartyMap(List<Whpsb_RequestKhzl_Detail> partyList) {
		HashMap<String, Whpsb_RequestKhzl_Detail> map = new HashMap<String, Whpsb_RequestKhzl_Detail>();
		if (partyList != null) {
			for (Whpsb_RequestKhzl_Detail party : partyList) {
				String bdhm = party.getBdhm();
				if (!map.containsKey(bdhm)) {
					map.put(bdhm, party);
				}
			}
		}
		return map;
	}
	
	/**
	 * 交易信息
	 */
	public void MakeMsgCxqqWH_Jyxx(String msgseq) throws Exception {
		
		// 查询主体
		Br51_cxqq br51_cxqq = br51_cxqqMapper.selBr51_cxqq(msgseq);
		// 查询明细
		List<Br51_cxqq_mx> cxqq_backmx_list = br51_cxqqMapper.selBr51_cxqq_mxList(msgseq);
		
		// 查询交易
		List<Whpsb_RequestJymx_Detail> transList = br51_cxqqMapper.selBr51_cxqq_back_transList(msgseq);
		
		// 生成txt文件
		this.makeTransTxt(transList, msgseq);
		// 拼接xmldto
		Whpsb_RequestJymx jymx = new Whpsb_RequestJymx();
		Whpsb_Header header = this.makeWhpsb_Header(br51_cxqq);
		List<Whpsb_SadxBody> sadxList = new ArrayList<Whpsb_SadxBody>();
		String queryMode = "";
		for (Br51_cxqq_mx br51_cxqq_mx : cxqq_backmx_list) {
			String bdhm = br51_cxqq_mx.getBdhm();
			queryMode = br51_cxqq_mx.getQrymode();
			Whpsb_SadxBody sadxBody = new Whpsb_SadxBody();
			BeanUtils.copyProperties(sadxBody, br51_cxqq_mx);
			//添加结果信息
			Br51_cxqq_back backMsg = this.getBr51_cxqq_back(bdhm);
			sadxBody.setCljg(backMsg.getCljg());
			sadxBody.setSbyy(backMsg.getCzsbyy());
			sadxList.add(sadxBody);
		}
		jymx.setHeader(header);
		jymx.setSadxList(sadxList);
		
		// 生成xml插入msg
		this.MakeMsg(br51_cxqq, jymx, queryMode);
		
		// 修改申请表
		this.updateBr51_cxqq(br51_cxqq);
		
	}
	
	public void updateBr51_cxqq(Br51_cxqq br51_cxqq) {
		br51_cxqq.setStatus("2");//0：待处理 1：待生成报文 2：待打包反馈 3：反馈成功 4：反馈失败
		br51_cxqq.setLast_up_dt(Utility.currDateTime19());
		br51_cxqqMapper.updateBr51_cxqq(br51_cxqq);
	}
	
	public void makeTransTxt(List<Whpsb_RequestJymx_Detail> transList, String msgseq) throws Exception {
		StringBuffer buffer = new StringBuffer();
		String fileno = "1";
		int count = 0;
		for (Whpsb_RequestJymx_Detail jymx : transList) {
			count = count + 1;
			buffer.append(replaceStr(jymx.getAh())); // 案号
			buffer.append("\t");
			buffer.append(replaceStr(jymx.getZh())); // 账（卡）号
			buffer.append("\t");
			buffer.append(replaceStr(jymx.getTransseq())); // 资金往来序号
			buffer.append("\t");
			buffer.append(replaceStr(jymx.getTransdata())); // 交易日期
			buffer.append("\t");
			buffer.append(replaceStr(jymx.getTranstime())); // 交易时间
			buffer.append("\t");
			buffer.append(replaceStr(jymx.getMatchaccou())); // 对方账号
			buffer.append("\t");
			buffer.append(replaceStr(jymx.getMatchaccna())); // 对方行名
			buffer.append("\t");
			buffer.append(replaceStr(jymx.getMatchbankname())); // 对方户名
			buffer.append("\t");
			buffer.append(replaceStr(jymx.getOppkm())); // 对方科目名称
			buffer.append("\t");
			buffer.append(replaceStr(jymx.getCurrency())); // 币种
			buffer.append("\t");
			buffer.append(replaceStr(jymx.getAmt())); // 交易金额
			buffer.append("\t");
			buffer.append(replaceStr(jymx.getDebit_credit())); // 借贷标记
			buffer.append("\t");
			buffer.append(replaceStr(jymx.getTranstype())); // 交易种类
			buffer.append("\t");
			buffer.append(replaceStr(jymx.getOrganname())); // 交易网点
			buffer.append("\t");
			buffer.append(replaceStr(jymx.getBanlance())); // 账户余额
			buffer.append("\t");
			buffer.append(replaceStr(jymx.getVoucher_no())); // 传票号
			buffer.append("\t");
			buffer.append(replaceStr(jymx.getTransnum())); // 交易流水号
			buffer.append("\t");
			buffer.append(replaceStr(jymx.getIp())); // IP地址
			buffer.append("\t");
			buffer.append(replaceStr(jymx.getMac())); // MAC地址
			buffer.append("\t");
			buffer.append(replaceStr(jymx.getTransremark())); // 备注
			// buffer.append("\t");
			buffer.append("\n");
			if (count % 50000 == 0) {
				this.MakeTxtMsg(buffer, msgseq, fileno);
				buffer = new StringBuffer();
				fileno = fileno + 1;
			}
		}
		if (buffer.toString().length() > 0) {
			// 生成文件
			this.MakeTxtMsg(buffer, msgseq, fileno);
		}
		
	}
	
	public void MakeTxtMsg(StringBuffer buffer, String msgseq, String fileno) throws Exception {
		// 获取系统参数
		HashMap sysParaMap = (HashMap) cacheService.getCache("sysParaDetail", HashMap.class);
		String root = StrUtils.null2String((String) sysParaMap.get("2"));
		String path = "/attach/" + Utility.currDate8() + "/whpsb/";
		File file = new File(root + path);
		if (!file.exists() || !file.isDirectory()) {
			file.mkdirs();
		}
		String filename = msgseq + "_" + fileno + ".txt";
		String pathname = root + path + File.separator + filename;
		file = new File(pathname);
		FileUtils.writeStringToFile(file, buffer.toString());
		// 插入msg
		Br51_cxqq_back_msg br51_cxqq_back_msg = new Br51_cxqq_back_msg();
		br51_cxqq_back_msg.setMsgseq(msgseq);
		br51_cxqq_back_msg.setXh(fileno);
		br51_cxqq_back_msg.setMsg_filename(filename);
		br51_cxqq_back_msg.setMsg_filepath(path + "/" + filename);
		br51_cxqq_back_msg.setStatus("0");
		br51_cxqq_back_msg.setCreate_dt(DtUtils.getNowTime());
		br51_cxqqMapper.insertBr51_cxqq_back_msg(br51_cxqq_back_msg);//插入txt信息
	}
	
	public String replaceStr(String str) {
		if (str != null) {
			str = str.replace("\t", "");
			str = str.replace("\r", "");
			str = str.replace("\n", "");
			if (str.equals("")) {
				str = " ";
			}
		} else {
			str = " ";
		}
		return str;
	}
	
	/**
	 * 获取反馈的基本信息
	 * 
	 * @param bdhm
	 * @return
	 */
	private Br51_cxqq_back getBr51_cxqq_back(String bdhm) {
		Br51_cxqq_back back = br51_cxqqMapper.selBr51_cxqq_back(bdhm);
		if (back == null) {
			back = new Br51_cxqq_back();
		}
		return back;
	}
}
