package com.citic.server.net.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.citic.server.dx.domain.Bg_Q_Attach;
import com.citic.server.dx.domain.Br24_account_freeze;
import com.citic.server.dx.domain.Br24_account_holder;
import com.citic.server.dx.domain.Br24_account_info;
import com.citic.server.dx.domain.Br24_account_right;
import com.citic.server.dx.domain.Br24_bas_info;
import com.citic.server.dx.domain.Br24_card_info;
import com.citic.server.dx.domain.Br24_q_Main;
import com.citic.server.dx.domain.Br24_trans_info;
import com.citic.server.dx.domain.request.QueryRequest_Accounts;
import com.citic.server.dx.domain.request.QueryRequest_Measure;
import com.citic.server.dx.domain.request.QueryRequest_Priority;
import com.citic.server.dx.domain.request.QueryRequest_Transaction;

public interface MM24_q_mainMapper {
	
	//删除BR24_查询请求单主表
	void delBr24_q_main(String bdhm);
	
	//插入BR24_查询请求单主表
	void insertBr24_q_main(Br24_q_Main cg_q_main);
	
	//修改BR24_查询请求单主表
	void updateBr24_q_main(Br24_q_Main cg_q_main);
	
	//获取请求信息
	Br24_q_Main selectBr24_q_mainByVo(String accountnumber);
	
	//获取原请求信息
	Br24_q_Main selectBr24_q_main_OldByVo(Br24_q_Main cg_q_main);
	
	//获取请求信息中通过账卡号获取户名
	List<Br24_q_Main> selectBr24_q_main_nameList(String accountnumber);
	Br24_q_Main selectBr24_q_main_name_gf(String bdhm);
	Br24_q_Main selectBr24_q_main_name_gf_acct(String accountnumber);
	
	//删除BR24_查询请求反馈信息表
	void delBr24_bas_info(Br24_bas_info br24_bas_info);
	
	//插入BR24_查询请求反馈信息表
	void insertBr24_bas_info(Br24_bas_info br24_bas_info);
	
	//修改BR24_查询请求反馈信息表
	void updateBr24_bas_info(Br24_bas_info br24_bas_info);
	
	//获取请求反馈信息
	Br24_bas_info selectBr24_bas_infoByVo(Br24_q_Main cg_q_main);
	
	//获取请求反馈信息
	Br24_bas_info selectBr24_bas_infoById(String transserialnumber);
	
	//删除持卡主体信息表
	void delBr24_account_holder(String transserialnumber);
	
	//插入持卡主体信息表
	void insertBr24_account_holder(Br24_account_holder br24_account_holder);
	
	//删除卡折信息表
	void delBr24_card_info(String transserialnumber);
	
	//插入卡折信息表
	void insertBr24_card_info(Br24_card_info br24_card_info);
	
	//删除账户信息表
	void delBr24_account_info(String transserialnumber);
	
	//插入账户信息表
	void insertBr24_account_info(Br24_account_info br24_account_info);
	
	//删除账户交易流水表
	void delBr24_trans_info(String transserialnumber);
	
	//插入账户交易流水表
	void insertBr24_trans_info(Br24_trans_info br24_trans_info);
	
	//插入已插入交易标的动态交易
	List<Br24_trans_info> selBr24_trans_infoList(Br24_bas_info br24_bas_info);
	
	//删除账户交易流水表
	void deleteBr24_trans_info(String transserialnumber);
	
	//删除冻结信息表
	void delBr24_account_freeze(String transserialnumber);
	
	//插入冻结信息表
	void insertBr24_account_freeze(Br24_account_freeze br24_account_freeze);
	
	//删除冻结信息表
	void delBr24_account_right(String transserialnumber);
	
	//插入冻结信息表
	void insertBr24_account_right(Br24_account_right br24_account_right);
	
	//获取持卡主体信息表
	Br24_account_holder selectBr24_account_holderByVo(String transserialnumber);
	
	//获取卡折信息表
	List<QueryRequest_Accounts> getBr24_card_infoList(String transserialnumber);
	
	//获取账户信息表
	List<Br24_account_info> getBr24_account_infoList(String transserialnumber);
	
	//获取账户交易流水表
	List<QueryRequest_Transaction> getBr24_trans_infoList(String transserialnumber);
	
	//获取账户权利信息表
	List<QueryRequest_Priority> getBr24_account_rightList(String transserialnumber);
	
	//获取冻结信息表
	List<QueryRequest_Measure> getBr24_account_freezeList(String transserialnumber);
	
	//获取账户交易流水表ACCOUNTNUMBER for账户动态查询
	List<String> getAccountNumberList(String transserialnumber);
	
	//从批后获取主账户信息 (对私-卡/折号)
	Br24_card_info getCardInfo_D(String cardNumber);
	
	//从批后获取主账户信息 (对公-基本账户帐号)
	Br24_card_info getCardInfo_C(String cardNumber);
	
	//从批后获取子账户信息 (对私)
	List<Br24_account_info> getAccountInfo_D(String cardNumber);
	
	//从批后获取子账户信息 (对公)
	List<Br24_account_info> getAccountInfo_C(String cardNumber);
	
	//从批后获取账户权利人信息表
	List<Br24_account_right> getAccountRightList_D(String party_id);
	
	List<Br24_account_right> getAccountRightList_C(String party_id);
	
	//从批后获取账户冻结信息表
	List<Br24_account_freeze> getAccountFreezeList_D(String party_id);
	
	List<Br24_account_freeze> getAccountFreezeList_C(String party_id);
	
	//从批后获取卡折号信息列表（根据客户号）,对私-卡/折号
	List<Br24_card_info> getCardInfobyPartidList_D(String party_id);
	
	//从批后获取卡折号信息列表（根据客户号）,对公-账号
	List<Br24_card_info> getCardInfobyPartidList_C(String party_id);
	
	//从批后获取账户交易流水表
	List<Br24_trans_info> getTransInfoList(Br24_q_Main cg_q_main);
	
	//从批后获取客户号(根据证件号码)
	String getParty_id(String ctid);
	
	//从批后获取子账户信息（根据客户号）,对私-卡/折号
	List<Br24_account_info> getAccountInfoBypartyID_D(String party_id);
	
	//从批后获取子账户信息（根据客户号）,对公-账号
	List<Br24_account_info> getAccountInfoBypartyID_C(String party_id);
	
	//从批后获取客户信息（根据客户号）
	Br24_account_holder getCardInfo(String party_id);
	
	//从批后获取客户信息（根据客户号）对公相关信息
	Br24_account_holder getCard_CorpInfo(String party_id);
	
	//从批后获取客户号及账/卡代办人姓名（根据卡号）
	Br24_account_holder getParty_idbyCardnumber(String cardnumber);
	
	//删除BR24_附件表
	void delBg_q_attach(String bdhm);
	
	//插入BR24_附件表
	void insertBg_q_attach(Bg_Q_Attach br24_q_attach);
	
	//删除账户动态查询或解除任务表
	void delBr24_q_acct_dynamic_main(String applicationid);
	
	//修改账户动态查询或解除任务表 状态
	void updateBr24_q_acct_dynamic_main(@Param("aid") String aid, @Param("msgfrom") String msgfrom, @Param("datatime") String datatime);
	
	//插入账户动态查询或解除任务表
	void insertBr24_q_acct_dynamic_main(Br24_q_Main cg_q_main);
	//查询动态查询是否已被解除
	Br24_q_Main selBr24_q_acct_dynamic_main(@Param("aid") String aid, @Param("msgfrom") String msgfrom);
	
	
	
	
}
