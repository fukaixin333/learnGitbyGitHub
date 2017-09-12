package com.citic.server.shpsb.mapper;

import java.util.List;

import com.citic.server.shpsb.domain.Br54_cxqq;
import com.citic.server.shpsb.domain.Br54_cxqq_back;
import com.citic.server.shpsb.domain.Br54_cxqq_back_msg;
import com.citic.server.shpsb.domain.ShpsbSadx;
import com.citic.server.shpsb.domain.request.ShpsbRequestCzrzMx;
import com.citic.server.shpsb.domain.request.ShpsbRequestDdzhMx;
import com.citic.server.shpsb.domain.request.ShpsbRequestJyglhMx;
import com.citic.server.shpsb.domain.request.ShpsbRequestJylsMx;
import com.citic.server.shpsb.domain.request.ShpsbRequestKhzlMx;
import com.citic.server.shpsb.domain.request.ShpsbRequestZhcyrMx;
import com.citic.server.shpsb.domain.request.ShpsbRequestZhxxMx;

public interface BR54_cxqqMapper {
	
	/** 删除Br54_CXQQ查询请求单表 */
	void delBr54_cxqq(String msgseq);
	
	/** 插入Br54_CXQQ查询请求主表 */
	void insertBr54_cxqq(Br54_cxqq br54_cxqq);
	
	/** 删除Br54_cxqq_mx 查询请求主体信息 */
	void delBr54_cxqq_mx(String msgseq);
	
	/** 请求单主体信息 */
	void insertBr54_cxqq_mx(ShpsbSadx qqmx);
	
	/** 删除查询请求反馈主体表 */
	void delBr54_cxqq_back(String msgseq);
	
	/** 插入查询请求反馈主体表 */
	void insertBr54_cxqq_back(Br54_cxqq_back br50_cxqq_back);
	
	/** BR54_ATTACH文书证件信息 */
	void insertBr54_attach(Br54_cxqq br54_cxqq);
	
	/** 删除账户 */
	void delBr54_cxqq_back_acct(String bdhm);
	
	/** BR54_CXQQ_BACK_PARTY 开户资料 */
	void delBr54_cxqq_back_party(String bdhm);
	
	/** BR54_CXQQ_BACK_CARD 持卡人信息 */
	void delBr54_cxqq_back_card(String bdhm);
	
	/** BR54_CXQQ_BACK_TRANS 交易信息 */
	void delBr54_cxqq_back_trans(String bdhm);
	
	/** Br54_cxqq_back_czrz 交易信息 */
	void delBr54_cxqq_back_czrz(String bdhm);
	
	void delBr54_cxqq_back_ddzh(String bdhm);
	
	void delBr54_cxqq_back_jyglh(String bdhm);
	
	/** xml信息 */
	void delBr54_cxqq_back_msg(String msgseq);
	
	//插入账户
	void insertBr54_cxqq_back_acct(ShpsbRequestZhxxMx Br54_cxqq_back_acct);
	
	//插入持卡主体
	void insertBr54_cxqq_back_card(ShpsbRequestZhcyrMx Br54_cxqq_back_card);
	
	//插入开户资料
	void insertBr54_cxqq_back_party(ShpsbRequestKhzlMx Br54_cxqq_back_party);
	
	//插入交易
	void insertBr54_cxqq_back_trans(ShpsbRequestJylsMx Br54_cxqq_back_trans);
	
	//插入日志
	void insertBr54_cxqq_back_czrz(ShpsbRequestCzrzMx Br54_cxqq_back_czrz);
	
	//插入交易关联号
	void insertBr54_cxqq_back_jyglh(ShpsbRequestJyglhMx Br54_cxqq_back_jyglh);
	
	//插入对端账户信息
	void insertBr54_cxqq_back_ddzh(ShpsbRequestDdzhMx Br54_cxqq_back_czrz);
	
	//插入msg
	void insertBr54_cxqq_back_msg(Br54_cxqq_back_msg br54_cxqq_back_msg);
	
	//查询主体明细
	ShpsbSadx selBr54_cxqq_mx(String bdhm);
	
	//查询主体
	Br54_cxqq selBr54_cxqq(String msgseq);
	
	//查询主体反馈
	Br54_cxqq_back selBr54_cxqq_back(String bdhm);
	
	//修改反馈主表
	void updateBr54_cxqq_back(Br54_cxqq_back br54_cxqq_back);
	
	//修改请求表
	void updateBr54_cxqq(Br54_cxqq br54_cxqq);
	
	//查询是否可以生成报文
	int isMakeMsg(String msgseq);
	
	//查询主体反馈
	List<ShpsbSadx> selBr54_cxqq_mxList(String msgseq);
	
	//查询账户
	List<ShpsbRequestZhxxMx> selBr54_cxqq_back_acctList(String msgseq);
	
	//查询持卡主体
	List<ShpsbRequestZhcyrMx> selBr54_cxqq_back_cardList(String msgseq);
	
	//查询开户资料
	List<ShpsbRequestKhzlMx> selBr54_cxqq_back_partyList(String msgseq);
	
	//查询交易
	List<ShpsbRequestJylsMx> selBr54_cxqq_back_transList(String msgseq);
	
	//查询操作日志
	List<ShpsbRequestCzrzMx> selBr54_cxqq_back_czrzList(String msgseq);
	
	//查询交易关联号
	List<ShpsbRequestJyglhMx> selBr54_cxqq_back_jygnhList(String msgseq);
	
	//查询对端账号
	List<ShpsbRequestDdzhMx> selBr54_cxqq_back_ddzhList(String msgseq);
	
}
