package com.citic.server.shpsb.task.taskBo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.citic.server.SpringContextHolder;
import com.citic.server.runtime.Utility;
import com.citic.server.service.task.taskBo.BaseBo;
import com.citic.server.shpsb.domain.Br54_cxqq;
import com.citic.server.shpsb.domain.Br54_cxqq_back;
import com.citic.server.shpsb.domain.Br54_cxqq_back_msg;
import com.citic.server.shpsb.domain.ShpsbReturnReceipt;
import com.citic.server.shpsb.domain.ShpsbSadx;
import com.citic.server.shpsb.domain.ShpsbSjl;
import com.citic.server.shpsb.domain.request.ShpsbRequestCzrz;
import com.citic.server.shpsb.domain.request.ShpsbRequestCzrzMx;
import com.citic.server.shpsb.domain.request.ShpsbRequestCzrzMxjl;
import com.citic.server.shpsb.domain.request.ShpsbRequestDdzh;
import com.citic.server.shpsb.domain.request.ShpsbRequestDdzhMx;
import com.citic.server.shpsb.domain.request.ShpsbRequestDdzhMxjl;
import com.citic.server.shpsb.domain.request.ShpsbRequestJyglh;
import com.citic.server.shpsb.domain.request.ShpsbRequestJyglhMx;
import com.citic.server.shpsb.domain.request.ShpsbRequestJyglhMxjl;
import com.citic.server.shpsb.domain.request.ShpsbRequestJyls;
import com.citic.server.shpsb.domain.request.ShpsbRequestJylsMx;
import com.citic.server.shpsb.domain.request.ShpsbRequestJylsMxjl;
import com.citic.server.shpsb.domain.request.ShpsbRequestKhzl;
import com.citic.server.shpsb.domain.request.ShpsbRequestKhzlMx;
import com.citic.server.shpsb.domain.request.ShpsbRequestKhzlMxjl;
import com.citic.server.shpsb.domain.request.ShpsbRequestZhcyr;
import com.citic.server.shpsb.domain.request.ShpsbRequestZhcyrMx;
import com.citic.server.shpsb.domain.request.ShpsbRequestZhcyrMxjl;
import com.citic.server.shpsb.domain.request.ShpsbRequestZhxx;
import com.citic.server.shpsb.domain.request.ShpsbRequestZhxxMx;
import com.citic.server.shpsb.domain.request.ShpsbRequestZhxxMxjl;
import com.citic.server.shpsb.mapper.BR54_cxqqMapper;
import com.citic.server.shpsb.service.RequestMessageServiceShpsb;
import com.citic.server.utils.DtUtils;

/**
 * 常规查询
 * 
 * @author dk
 */
public class Cxqq_FKBo extends BaseBo {
	// private static final Logger logger = (Logger) LoggerFactory.getLogger(Cxqq_FKBo.class);
	
	private BR54_cxqqMapper br54_cxqqMapper;
	
	private RequestMessageServiceShpsb requestService;
	
	public Cxqq_FKBo(ApplicationContext ac) {
		super(ac);
		br54_cxqqMapper = (BR54_cxqqMapper) ac.getBean("BR54_cxqqMapper");
		requestService = (RequestMessageServiceShpsb) SpringContextHolder.getBean("requestMessageServiceShpsb");
	}
	
	public void delBr54_cxqq_back_msg(String msgseq) throws Exception {
		br54_cxqqMapper.delBr54_cxqq_back_msg(msgseq);
	}
	
	/**
	 * 账户信息
	 */
	public void MakeMsgCxqqSH_zhxx(String msgseq) throws Exception {
		
		// 查询主体
		Br54_cxqq br54_cxqq = br54_cxqqMapper.selBr54_cxqq(msgseq);
		// 查询明细
		List<ShpsbSadx> cxqq_mx_list = br54_cxqqMapper.selBr54_cxqq_mxList(msgseq);
		
		// 查询账户
		List<ShpsbRequestZhxxMx> acctList = br54_cxqqMapper.selBr54_cxqq_back_acctList(msgseq);
		HashMap<String, List<ShpsbRequestZhxxMx>> acctMap = this.getAcctMap(acctList);
		
		// 拼接xmldto
		ShpsbRequestZhxx zhxx = new ShpsbRequestZhxx();
		int count = 0;
		List<ShpsbRequestZhxxMxjl> mxjlList = new ArrayList<ShpsbRequestZhxxMxjl>();
		for (ShpsbSadx sadx : cxqq_mx_list) {
			List<ShpsbRequestZhxxMx> detailList = acctMap.get(sadx.getBdhm());
			Br54_cxqq_back backMsg = this.getBr54_cxqq_back(sadx.getBdhm());
			
			if (detailList == null || detailList.size() == 0) {
				sadx.setCljg("1"); // 1-失败
				sadx.setSbyy("查无数据"); // 
			} else {
				sadx.setCljg(backMsg.getCljg());
				sadx.setSbyy(backMsg.getCzsbyy());
				count += detailList.size();
			}
			
			ShpsbRequestZhxxMxjl mxjl = new ShpsbRequestZhxxMxjl();
			mxjl.setSadx(sadx);
			mxjl.setZhxxList(detailList);
			mxjlList.add(mxjl);
		}
		
		zhxx.setSjl(new ShpsbSjl(String.valueOf(count), br54_cxqq.getYhdm()));
		zhxx.setMxjlList(mxjlList);
		
		try {
			ShpsbReturnReceipt rr = requestService.sendResultMessage(br54_cxqq.getMsgseq(), zhxx);
			insertBr54_cxqq_back_msg(msgseq, rr);
			updateBr54_cxqq(br54_cxqq, rr.getStatus());
		} catch (Exception e) {
			updateBr54_cxqq(br54_cxqq, "4");
			throw e;
		}
	}
	
	public HashMap<String, List<ShpsbRequestZhxxMx>> getAcctMap(List<ShpsbRequestZhxxMx> acctList) {
		HashMap<String, List<ShpsbRequestZhxxMx>> map = new HashMap<String, List<ShpsbRequestZhxxMx>>();
		if (acctList != null) {
			for (ShpsbRequestZhxxMx acct : acctList) {
				String bdhm = acct.getBdhm();
				if (map.containsKey(bdhm)) {
					List<ShpsbRequestZhxxMx> list = map.get(bdhm);
					list.add(acct);
				} else {
					List<ShpsbRequestZhxxMx> newList = new ArrayList<ShpsbRequestZhxxMx>();
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
	public void MakeMsgCxqqSH_ckrxx(String msgseq) throws Exception {
		
		// 查询主体
		Br54_cxqq br54_cxqq = br54_cxqqMapper.selBr54_cxqq(msgseq);
		// 查询明细
		List<ShpsbSadx> cxqq_backmx_list = br54_cxqqMapper.selBr54_cxqq_mxList(msgseq);
		
		// 查询持卡人信息
		List<ShpsbRequestZhcyrMx> cardList = br54_cxqqMapper.selBr54_cxqq_back_cardList(msgseq);
		HashMap<String, List<ShpsbRequestZhcyrMx>> cardMap = this.getCardMap(cardList);
		
		// 拼接xmldto
		ShpsbRequestZhcyr zhcyr = new ShpsbRequestZhcyr();
		int count = 0;
		List<ShpsbRequestZhcyrMxjl> mxjlList = new ArrayList<ShpsbRequestZhcyrMxjl>();
		for (ShpsbSadx sadx : cxqq_backmx_list) {
			String bdhm = sadx.getBdhm();
			List<ShpsbRequestZhcyrMx> detailList = cardMap.get(bdhm);
			Br54_cxqq_back backMsg = this.getBr54_cxqq_back(bdhm);
			
			if (detailList == null || detailList.size() == 0) {
				sadx.setCljg("1"); // 1-失败
				sadx.setSbyy("查无数据"); // 
			} else {
				sadx.setCljg(backMsg.getCljg());
				sadx.setSbyy(backMsg.getCzsbyy());
				count += detailList.size();
			}
			
			ShpsbRequestZhcyrMxjl mxjl = new ShpsbRequestZhcyrMxjl();
			mxjl.setSadx(sadx);
			mxjl.setCyrzlList(detailList);
			mxjlList.add(mxjl);
		}
		
		zhcyr.setSjl(new ShpsbSjl(String.valueOf(count), br54_cxqq.getYhdm()));
		zhcyr.setMxjlList(mxjlList);
		
		try {
			ShpsbReturnReceipt rr = requestService.sendResultMessage(br54_cxqq.getMsgseq(), zhcyr);
			insertBr54_cxqq_back_msg(msgseq, rr);
			updateBr54_cxqq(br54_cxqq, rr.getStatus());
		} catch (Exception e) {
			updateBr54_cxqq(br54_cxqq, "4");
			throw e;
		}
	}
	
	public HashMap<String, List<ShpsbRequestZhcyrMx>> getCardMap(List<ShpsbRequestZhcyrMx> cardList) {
		HashMap<String, List<ShpsbRequestZhcyrMx>> map = new HashMap<String, List<ShpsbRequestZhcyrMx>>();
		if (cardList != null) {
			for (ShpsbRequestZhcyrMx card : cardList) {
				String bdhm = card.getBdhm();
				if (map.containsKey(bdhm)) {
					List<ShpsbRequestZhcyrMx> list = map.get(bdhm);
					list.add(card);
				} else {
					List<ShpsbRequestZhcyrMx> newList = new ArrayList<ShpsbRequestZhcyrMx>();
					newList.add(card);
					map.put(bdhm, newList);
				}
			}
		}
		return map;
	}
	
	/**
	 * 开户信息
	 */
	public void MakeMsgCxqqSH_khxx(String msgseq) throws Exception {
		
		// 查询主体
		Br54_cxqq br54_cxqq = br54_cxqqMapper.selBr54_cxqq(msgseq);
		// 查询明细
		List<ShpsbSadx> cxqq_backmx_list = br54_cxqqMapper.selBr54_cxqq_mxList(msgseq);
		
		// 查询持卡人信息
		List<ShpsbRequestKhzlMx> cardList = br54_cxqqMapper.selBr54_cxqq_back_partyList(msgseq);
		HashMap<String, List<ShpsbRequestKhzlMx>> partyMap = this.getPartyMap(cardList);
		
		// 拼接xmldto
		ShpsbRequestKhzl khxx = new ShpsbRequestKhzl();
		int count = 0;
		List<ShpsbRequestKhzlMxjl> mxjlList = new ArrayList<ShpsbRequestKhzlMxjl>();
		for (ShpsbSadx sadx : cxqq_backmx_list) {
			String bdhm = sadx.getBdhm();
			List<ShpsbRequestKhzlMx> detailList = partyMap.get(bdhm);
			Br54_cxqq_back backMsg = this.getBr54_cxqq_back(bdhm);
			
			if (detailList == null || detailList.size() == 0) {
				sadx.setCljg("1"); // 1-失败
				sadx.setSbyy("查无数据"); // 
			} else {
				sadx.setCljg(backMsg.getCljg());
				sadx.setSbyy(backMsg.getCzsbyy());
				count += detailList.size();
			}
			
			ShpsbRequestKhzlMxjl mxjl = new ShpsbRequestKhzlMxjl();
			mxjl.setSadx(sadx);
			mxjl.setKhzlList(detailList);
			mxjlList.add(mxjl);
		}
		
		khxx.setSjl(new ShpsbSjl(String.valueOf(count), br54_cxqq.getYhdm()));
		khxx.setMxjlList(mxjlList);
		
		try {
			ShpsbReturnReceipt rr = requestService.sendResultMessage(br54_cxqq.getMsgseq(), khxx);
			insertBr54_cxqq_back_msg(msgseq, rr);
			updateBr54_cxqq(br54_cxqq, rr.getStatus());
		} catch (Exception e) {
			updateBr54_cxqq(br54_cxqq, "4");
			throw e;
		}
	}
	
	public HashMap<String, List<ShpsbRequestKhzlMx>> getPartyMap(List<ShpsbRequestKhzlMx> partyList) {
		HashMap<String, List<ShpsbRequestKhzlMx>> map = new HashMap<String, List<ShpsbRequestKhzlMx>>();
		if (partyList != null) {
			for (ShpsbRequestKhzlMx party : partyList) {
				String bdhm = party.getBdhm();
				if (map.containsKey(bdhm)) {
					List<ShpsbRequestKhzlMx> list = map.get(bdhm);
					list.add(party);
				} else {
					List<ShpsbRequestKhzlMx> newList = new ArrayList<ShpsbRequestKhzlMx>();
					newList.add(party);
					map.put(bdhm, newList);
				}
			}
		}
		return map;
	}
	
	/**
	 * 交易信息
	 */
	public void MakeMsgCxqqSH_Jyxx(String msgseq) throws Exception {
		// 查询主体
		Br54_cxqq br54_cxqq = br54_cxqqMapper.selBr54_cxqq(msgseq);
		// 查询明细
		List<ShpsbSadx> cxqq_backmx_list = br54_cxqqMapper.selBr54_cxqq_mxList(msgseq);
		// 查询交易
		List<ShpsbRequestJylsMx> transList = br54_cxqqMapper.selBr54_cxqq_back_transList(msgseq);
		
		// 拼接xmldto
		ShpsbRequestJyls jymx = new ShpsbRequestJyls();
		int count = 0;
		List<ShpsbRequestJylsMxjl> mxjlList = new ArrayList<ShpsbRequestJylsMxjl>();
		for (ShpsbSadx sadx : cxqq_backmx_list) {
			String bdhm = sadx.getBdhm();
			Br54_cxqq_back backMsg = this.getBr54_cxqq_back(bdhm);
			
			if (transList == null || transList.size() == 0) {
				sadx.setCljg("1"); // 1-失败
				sadx.setSbyy("查无数据"); // 
			} else {
				sadx.setCljg(backMsg.getCljg());
				sadx.setSbyy(backMsg.getCzsbyy());
				count += transList.size();
			}
			
			ShpsbRequestJylsMxjl mxjl = new ShpsbRequestJylsMxjl();
			mxjl.setSadx(sadx);
			mxjlList.add(mxjl);
		}
		
		jymx.setSjl(new ShpsbSjl(String.valueOf(count), br54_cxqq.getYhdm()));
		jymx.setMxjlList(mxjlList);
		
		try {
			List<ShpsbReturnReceipt> rrs = requestService.sendResultMessage(br54_cxqq.getMsgseq(), jymx, transList);
			int i = 1;
			for (ShpsbReturnReceipt rr : rrs) {
				insertBr54_cxqq_back_msg(msgseq, rr, String.valueOf(i++));
			}
			updateBr54_cxqq(br54_cxqq, "3"); // 3-反馈成功
		} catch (Exception e) {
			updateBr54_cxqq(br54_cxqq, "4"); // 4-反馈失败
			throw e;
		}
	}
	
	public void updateBr54_cxqq(Br54_cxqq br54_cxqq, String status) {
		br54_cxqq.setStatus(status);//0：待处理 1：待生成报文 2：待打包反馈 3：反馈成功 4：反馈失败
		br54_cxqq.setLast_up_dt(Utility.currDateTime19());
		br54_cxqqMapper.updateBr54_cxqq(br54_cxqq);
	}
	
	/**
	 * 获取反馈的基本信息
	 * 
	 * @param bdhm
	 * @return
	 */
	private Br54_cxqq_back getBr54_cxqq_back(String bdhm) {
		Br54_cxqq_back back = br54_cxqqMapper.selBr54_cxqq_back(bdhm);
		if (back == null) {
			back = new Br54_cxqq_back();
		}
		return back;
	}
	
	/**
	 * 操作日志信息
	 */
	public void MakeMsgCxqqSH_czrz(String msgseq) throws Exception {
		
		// 查询主体
		Br54_cxqq br54_cxqq = br54_cxqqMapper.selBr54_cxqq(msgseq);
		// 查询明细
		List<ShpsbSadx> cxqq_backmx_list = br54_cxqqMapper.selBr54_cxqq_mxList(msgseq);
		
		// 操作日志信息
		List<ShpsbRequestCzrzMx> cardList = br54_cxqqMapper.selBr54_cxqq_back_czrzList(msgseq);
		HashMap<String, List<ShpsbRequestCzrzMx>> czrzMap = this.getCzrzMap(cardList);
		
		// 拼接xmldto
		ShpsbRequestCzrz czrz = new ShpsbRequestCzrz();
		int count = 0;
		List<ShpsbRequestCzrzMxjl> sadxList = new ArrayList<ShpsbRequestCzrzMxjl>();
		for (ShpsbSadx sadx : cxqq_backmx_list) {
			List<ShpsbRequestCzrzMx> detailList = czrzMap.get(sadx.getBdhm());
			Br54_cxqq_back backMsg = this.getBr54_cxqq_back(sadx.getBdhm());
			
			if (detailList == null || detailList.size() == 0) {
				sadx.setCljg("1"); // 1-失败
				sadx.setSbyy("查无数据"); // 
			} else {
				sadx.setCljg(backMsg.getCljg());
				sadx.setSbyy(backMsg.getCzsbyy());
				count += detailList.size();
			}
			
			ShpsbRequestCzrzMxjl mxjl = new ShpsbRequestCzrzMxjl();
			mxjl.setSadx(sadx);
			mxjl.setCzrzList(detailList);
			sadxList.add(mxjl);
		}
		
		czrz.setSjl(new ShpsbSjl(String.valueOf(count), br54_cxqq.getYhdm()));
		czrz.setMxjlList(sadxList);
		
		try {
			ShpsbReturnReceipt rr = requestService.sendResultMessage(br54_cxqq.getMsgseq(), czrz);
			insertBr54_cxqq_back_msg(msgseq, rr);
			updateBr54_cxqq(br54_cxqq, rr.getStatus());
		} catch (Exception e) {
			updateBr54_cxqq(br54_cxqq, "4"); // 4-反馈失败
			throw e;
		}
	}
	
	public HashMap<String, List<ShpsbRequestCzrzMx>> getCzrzMap(List<ShpsbRequestCzrzMx> czrzList) {
		HashMap<String, List<ShpsbRequestCzrzMx>> map = new HashMap<String, List<ShpsbRequestCzrzMx>>();
		if (czrzList != null) {
			for (ShpsbRequestCzrzMx czrz : czrzList) {
				String bdhm = czrz.getBdhm();
				if (map.containsKey(bdhm)) {
					List<ShpsbRequestCzrzMx> list = map.get(bdhm);
					list.add(czrz);
				} else {
					List<ShpsbRequestCzrzMx> newList = new ArrayList<ShpsbRequestCzrzMx>();
					newList.add(czrz);
					map.put(bdhm, newList);
				}
			}
		}
		return map;
	}
	
	/**
	 * 交易关联号信息
	 */
	public void MakeMsgCxqqSH_jyglh(String msgseq) throws Exception {
		
		// 查询主体
		Br54_cxqq br54_cxqq = br54_cxqqMapper.selBr54_cxqq(msgseq);
		// 查询明细
		List<ShpsbSadx> cxqq_backmx_list = br54_cxqqMapper.selBr54_cxqq_mxList(msgseq);
		
		// 交易关联号信息
		List<ShpsbRequestJyglhMx> jygnlList = br54_cxqqMapper.selBr54_cxqq_back_jygnhList(msgseq);
		HashMap<String, List<ShpsbRequestJyglhMx>> czrzMap = this.getJyglhMap(jygnlList);
		
		// 拼接xmldto
		ShpsbRequestJyglh jyglh = new ShpsbRequestJyglh();
		int count = 0;
		List<ShpsbRequestJyglhMxjl> mxjlList = new ArrayList<ShpsbRequestJyglhMxjl>();
		for (ShpsbSadx sadx : cxqq_backmx_list) {
			List<ShpsbRequestJyglhMx> detailList = czrzMap.get(sadx.getBdhm());
			Br54_cxqq_back backMsg = this.getBr54_cxqq_back(sadx.getBdhm());
			
			if (detailList == null || detailList.size() == 0) {
				sadx.setCljg("1"); // 1-失败
				sadx.setSbyy("查无数据"); // 
			} else {
				sadx.setCljg(backMsg.getCljg());
				sadx.setSbyy(backMsg.getCzsbyy());
				count += detailList.size();
			}
			
			ShpsbRequestJyglhMxjl mxjl = new ShpsbRequestJyglhMxjl();
			mxjl.setSadx(sadx);
			mxjl.setJyglhList(detailList);
			mxjlList.add(mxjl);
		}
		
		jyglh.setSjl(new ShpsbSjl(String.valueOf(count), br54_cxqq.getYhdm()));
		jyglh.setMxjlList(mxjlList);
		
		ShpsbReturnReceipt rr = requestService.sendResultMessage(br54_cxqq.getMsgseq(), jyglh);
		insertBr54_cxqq_back_msg(msgseq, rr);
		updateBr54_cxqq(br54_cxqq, rr.getStatus());
	}
	
	public HashMap<String, List<ShpsbRequestJyglhMx>> getJyglhMap(List<ShpsbRequestJyglhMx> jyglhList) {
		HashMap<String, List<ShpsbRequestJyglhMx>> map = new HashMap<String, List<ShpsbRequestJyglhMx>>();
		if (jyglhList != null) {
			for (ShpsbRequestJyglhMx jyglh : jyglhList) {
				String bdhm = jyglh.getBdhm();
				if (map.containsKey(bdhm)) {
					List<ShpsbRequestJyglhMx> list = map.get(bdhm);
					list.add(jyglh);
				} else {
					List<ShpsbRequestJyglhMx> newList = new ArrayList<ShpsbRequestJyglhMx>();
					newList.add(jyglh);
					map.put(bdhm, newList);
				}
			}
		}
		return map;
	}
	
	/**
	 * 对端账号信息
	 */
	public void MakeMsgCxqqSH_ddzh(String msgseq) throws Exception {
		
		// 查询主体
		Br54_cxqq br54_cxqq = br54_cxqqMapper.selBr54_cxqq(msgseq);
		// 查询明细
		List<ShpsbSadx> cxqq_backmx_list = br54_cxqqMapper.selBr54_cxqq_mxList(msgseq);
		
		// 查询持卡人信息
		List<ShpsbRequestDdzhMx> ddzhList = br54_cxqqMapper.selBr54_cxqq_back_ddzhList(msgseq);
		HashMap<String, List<ShpsbRequestDdzhMx>> ddzhMap = this.getDdzhMap(ddzhList);
		
		// 拼接xmldto
		ShpsbRequestDdzh ddzh = new ShpsbRequestDdzh();
		int count = 0;
		List<ShpsbRequestDdzhMxjl> mxjlList = new ArrayList<ShpsbRequestDdzhMxjl>();
		for (ShpsbSadx sadx : cxqq_backmx_list) {
			List<ShpsbRequestDdzhMx> detailList = ddzhMap.get(sadx.getBdhm());
			Br54_cxqq_back backMsg = this.getBr54_cxqq_back(sadx.getBdhm());
			
			if (detailList == null || detailList.size() == 0) {
				sadx.setCljg("1"); // 1-失败
				sadx.setSbyy("查无数据"); // 
			} else {
				sadx.setCljg(backMsg.getCljg());
				sadx.setSbyy(backMsg.getCzsbyy());
				count += detailList.size();
			}
			
			ShpsbRequestDdzhMxjl mxjl = new ShpsbRequestDdzhMxjl();
			mxjl.setSadx(sadx);
			mxjl.setDdzhList(detailList);
			mxjlList.add(mxjl);
		}
		
		ddzh.setSjl(new ShpsbSjl(String.valueOf(count), br54_cxqq.getYhdm()));
		ddzh.setMxjlList(mxjlList);
		
		ShpsbReturnReceipt rr = requestService.sendResultMessage(br54_cxqq.getMsgseq(), ddzh);
		insertBr54_cxqq_back_msg(msgseq, rr);
		updateBr54_cxqq(br54_cxqq, rr.getStatus());
	}
	
	public HashMap<String, List<ShpsbRequestDdzhMx>> getDdzhMap(List<ShpsbRequestDdzhMx> ddzhList) {
		HashMap<String, List<ShpsbRequestDdzhMx>> map = new HashMap<String, List<ShpsbRequestDdzhMx>>();
		if (ddzhList != null) {
			for (ShpsbRequestDdzhMx dzzh : ddzhList) {
				String bdhm = dzzh.getBdhm();
				if (map.containsKey(bdhm)) {
					List<ShpsbRequestDdzhMx> list = map.get(bdhm);
					list.add(dzzh);
				} else {
					List<ShpsbRequestDdzhMx> newList = new ArrayList<ShpsbRequestDdzhMx>();
					newList.add(dzzh);
					map.put(bdhm, newList);
				}
			}
		}
		return map;
	}
	
	private void insertBr54_cxqq_back_msg(String msgseq, ShpsbReturnReceipt rr) {
		insertBr54_cxqq_back_msg(msgseq, rr, "1");
	}
	
	private void insertBr54_cxqq_back_msg(String msgseq, ShpsbReturnReceipt rr, String xh) {
		Br54_cxqq_back_msg br54_cxqq_back_msg = new Br54_cxqq_back_msg();
		br54_cxqq_back_msg.setMsgseq(msgseq);
		br54_cxqq_back_msg.setXh(xh);
		br54_cxqq_back_msg.setMsg_filename(rr.getFileName());
		br54_cxqq_back_msg.setMsg_filepath(rr.getRelativeFilePath());
		br54_cxqq_back_msg.setStatus(rr.getStatus());
		br54_cxqq_back_msg.setCreate_dt(DtUtils.getNowTime());
		br54_cxqqMapper.insertBr54_cxqq_back_msg(br54_cxqq_back_msg);
	}
}
