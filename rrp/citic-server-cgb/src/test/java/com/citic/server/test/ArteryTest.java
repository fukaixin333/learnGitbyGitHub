package com.citic.server.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.citic.server.cgb.domain.request.ExternalTransferInput;
import com.citic.server.cgb.domain.request.FinancialDeferFreezeInput;
import com.citic.server.cgb.domain.request.FinancialFreezeInput;
import com.citic.server.cgb.domain.request.FinancialFrozenMeasuresInput;
import com.citic.server.cgb.domain.request.FinancialUnfreezeInput;
import com.citic.server.cgb.domain.response.CargoRecordResult;
import com.citic.server.cgb.domain.response.FinancialDeferFreezeResult;
import com.citic.server.cgb.domain.response.FinancialFreezeResult;
import com.citic.server.cgb.domain.response.FinancialFrozenMeasure;
import com.citic.server.cgb.domain.response.FinancialUnfreezeResult;
import com.citic.server.cgb.domain.response.OnlineTPFinancialDetail;
import com.citic.server.gf.domain.response.QueryResponse_Cxqq;
import com.citic.server.gf.service.IDataOperate01;
import com.citic.server.inner.domain.AccountFrozenMeasure;
import com.citic.server.inner.domain.AccountTransaction;
import com.citic.server.inner.domain.ContractAccount;
import com.citic.server.inner.domain.CustomerAddressInfo;
import com.citic.server.inner.domain.CustomerContactInfo;
import com.citic.server.inner.domain.SubAccountInfo;
import com.citic.server.inner.domain.request.Input267500;
import com.citic.server.inner.domain.request.Input267510;
import com.citic.server.inner.domain.request.Input267530;
import com.citic.server.inner.domain.request.Input267570;
import com.citic.server.inner.domain.request.Input267580;
import com.citic.server.inner.domain.request.Input358040;
import com.citic.server.inner.domain.response.AccountDetail;
import com.citic.server.inner.domain.response.AccountFrozenInfo;
import com.citic.server.inner.domain.response.AccountVerifyInfo;
import com.citic.server.inner.domain.response.CorporateCustomer;
import com.citic.server.inner.domain.response.DeductResult;
import com.citic.server.inner.domain.response.FreezeResult;
import com.citic.server.inner.domain.response.IndividualCustomer;
import com.citic.server.inner.domain.response.UnfreezeResult;
import com.citic.server.inner.service.IPrefixMessageService;
import com.citic.server.inner.service.ISOAPMessageService;
import com.citic.server.inner.service.InnerMessageService;
import com.citic.server.inner.service.SOAPMessageService;
import com.citic.server.junit.BaseJunit4Test;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.runtime.Utility;

public class ArteryTest extends BaseJunit4Test {
	
	@Autowired
	@Qualifier("innerMessageService")
	private IPrefixMessageService service;
	
	@Autowired
	@Qualifier("remoteDataOperate1")
	private IDataOperate01 operate;
	
	@Autowired
	@Qualifier("soapMessageService")
	private ISOAPMessageService ser;
	
	@Autowired
	@Qualifier("soapMessageService")
	private SOAPMessageService ser1;
	
	@Autowired
	@Qualifier("innerMessageService")
	private InnerMessageService inmessage;
	
	//	// 个人客户信息查询（025317）
	//@Test
	public void queryIndividualCustomerInfo() throws DataOperateException, RemoteAccessException {
		IndividualCustomer customer = service.queryIndividualCustomerInfo("610303198310292439");
		System.out.println("-------------------------------------------");
		System.out.println("[queryIndividualCustomerInfo]");
		System.out.println("证件类型：" + customer.getOpenIDInfo().getIdType());
		System.out.println("证件号码：" + customer.getOpenIDInfo().getIdNumber());
		System.out.println("客户名称：" + customer.getChineseName());
		
		for (CustomerContactInfo contactInfo : customer.getContactInfoList()) {
			String type = contactInfo.getContactType();
			if ("13".equals(type)) { // 联络手机号码
				System.out.println("联络手机号码：" + contactInfo.getContactInfo() + ", " + contactInfo.getContactNumber());
			} else if ("31".equals(type)) { // 电子邮箱
				System.out.println("电子邮箱：" + contactInfo.getContactInfo() + ", " + contactInfo.getContactNumber());
			}
		}
		
		for (CustomerAddressInfo addressInfo : customer.getAddressInfoList()) {
			String type = addressInfo.getAddressType();
			if ("114".equals(type)) {
				System.out.println("常驻地址：" + addressInfo.getAddressDetail());
			}
		}
		System.out.println("-------------------------------------------\r\n");
	}
	
	//	
	
	//	public void queryIndividualCustomerInfo2() throws DataOperateException, RemoteAccessException {
	//		IndividualCustomer customer = service.queryIndividualCustomerInfo("10100", "420607198511042416", "司相旭");
	//		System.out.println("-------------------------------------------");
	//		System.out.println("[queryIndividualCustomerInfo2]");
	//		System.out.println("证件类型：" + customer.getOpenIDInfo().getIdType());
	//		System.out.println("证件号码：" + customer.getOpenIDInfo().getIdNumber());
	//		System.out.println("客户名称：" + customer.getChineseName());
	//		
	//		for (CustomerContactInfo contactInfo : customer.getContactInfoList()) {
	//			String type = contactInfo.getContactType();
	//			if ("13".equals(type)) { // 联络手机号码
	//				System.out.println("联络手机号码：" + contactInfo.getContactInfo() + ", " + contactInfo.getContactNumber());
	//			} else if ("31".equals(type)) { // 电子邮箱
	//				System.out.println("电子邮箱：" + contactInfo.getContactInfo() + ", " + contactInfo.getContactNumber());
	//			}
	//		}
	//		
	//		for (CustomerAddressInfo addressInfo : customer.getAddressInfoList()) {
	//			String type = addressInfo.getAddressType();
	//			if ("114".equals(type)) {
	//				System.out.println("常驻地址：" + addressInfo.getAddressDetail());
	//			}
	//		}
	//		System.out.println("-------------------------------------------\r\n");
	//	}
	//	
	//	//公司同业客户信息查询（025327）
	//@Test
	public void queryCorporateCustomerInfo() throws DataOperateException, RemoteAccessException {
		CorporateCustomer customer = service.queryCorporateCustomerInfo("9550860052990000001"); //  101008516010004401
		System.out.println("-------------------------------------------");
		System.out.println("[queryCorporateCustomerInfo]");
		System.out.println(customer.toString());
		
		System.out.println("--------------");
		System.out.println("客户类型（2-对公客户；3-同业客户）：" + customer.getCustomerType());
		System.out.println("开户行所：" + customer.getOpenBank());
		System.out.println("开户名称：" + customer.getChineseName());
		System.out.println("企业法人证件号码：" + customer.getLpriIDNumber());
		System.out.println("企业负责人证件号码：" + customer.getBusiIDNumber());
		System.out.println("国税号：" + customer.getStateTaxIDNumber());
		System.out.println("地税号：" + customer.getLocalTaxIDNumber());
		
		System.out.println("开户证件类型：" + customer.getOpenIDType());
		System.out.println("开户证件号码：" + customer.getOpenIDNumber());
		System.out.println("-------------------------------------------\r\n");
	}
	
	//@Test
	public void queryCorporateCustomerInfo2() throws DataOperateException, RemoteAccessException {
		CorporateCustomer customer = service.queryCorporateCustomerInfo("20600", "650899409", "胆乱猿僻若火禁询金淑渊剃");
		System.out.println("-------------------------------------------");
		System.out.println("[queryCorporateCustomerInfo2]");
		System.out.println(customer.toString());
		
		System.out.println("--------------");
		System.out.println("客户类型（2-对公客户；3-同业客户）：" + customer.getCustomerType());
		System.out.println("开户行所：" + customer.getOpenBank());
		System.out.println("开户名称：" + customer.getChineseName());
		System.out.println("企业法人证件号码：" + customer.getLpriIDNumber());
		System.out.println("企业负责人证件号码：" + customer.getBusiIDNumber());
		System.out.println("国税号：" + customer.getStateTaxIDNumber());
		System.out.println("地税号：" + customer.getLocalTaxIDNumber());
		
		System.out.println("开户证件类型：" + customer.getOpenIDType());
		System.out.println("开户证件号码：" + customer.getOpenIDNumber());
		System.out.println("-------------------------------------------\r\n");
	}
	
	//	
	//	// 客户信息变更历史查询（025890）：每页10行
	//	public void queryCustomerInfoUpdateList() throws DataOperateException, RemoteAccessException {
	//		System.out.println("-------------------------------------------");
	//		System.out.println("[queryCustomerInfoUpdateList]");
	//		List<CustomerUpdateInfo> list = service.queryCustomerInfoUpdateList("9550890000268500716", "20160101", "20161220");
	//		
	//		for (CustomerUpdateInfo info : list) {
	//			System.out.println("新联系方式类型：" + info.getNewContactType());
	//			System.out.println("新联系方式：" + info.getNewContactInfo());
	//			System.out.println("旧联系方式类型：" + info.getOldContactType());
	//			System.out.println("旧联系方式：" + info.getOldContactInfo());
	//			System.out.println("联系方式变更类型：" + info.getContactUpdateType());
	//			System.out.println("联系方式变更日期：" + info.getContactUpdateDate());
	//			System.out.println("联系方式变更机构：" + info.getContactUpdateBranch());
	//			System.out.println("联系方式变更柜员：" + info.getContactUpdateTeller());
	//			System.out.println();
	//		}
	//		System.out.println("-------------------------------------------\r\n");
	//	}
	//	
	//	// 合约账号查询（028100）：每页25行
	//@Test
	public void queryContractAccountList() throws DataOperateException, RemoteAccessException {
		System.out.println("-------------------------------------------");
		System.out.println("[queryIndividualCustomerInfo2]"); // 9550811636948201980
		// 524544546508994091                                          卢星   610303198310292439
		List<ContractAccount> list = service.queryContractAccountList("20600", "12633543-9", "胆壹永惶皇瞄金淑渊剃");
		//List<ContractAccount> list = service.queryContractAccountList("10100", "321121197709255129", "燕室床");
		for (ContractAccount info : list) {
			System.out.println("账号合约实体类型：" + info.getEntityType());
			System.out.println("账号合约实体编号：" + info.getAccountNumber());
			
			System.out.println("合约类型（CLDD、CLGU、CLDC）：" + info.getContractType());
			System.out.println("归属应用：" + info.getFromApp());
			System.out.println("状态（0-正常；1-销户）：" + info.getStatus());
			System.out.println("币种：" + info.getCurrency());
			System.out.println("开户机构：" + info.getOpenIns());
			System.out.println("归属机构：" + info.getBelongIns());
			System.out.println("签发日期/开户日期：" + info.getOpenDate());
			
			System.out.println("客户号：" + info.getCustomerNumber());
			System.out.println("账户开户名称（中文）：" + info.getChineseName());
			System.out.println("账户辅助名称（英文）：" + info.getEnglishName());
			System.out.println();
		}
		System.out.println("-------------------------------------------\r\n");
	}
	
	// 账户信息查询（358080）
	//@Test
	public void querySubAccountDetail() throws DataOperateException, RemoteAccessException {
		// AccountDetail account = service.queryAccountDetail("9550890000268500626", "156", null);
		//V11568550891966705900001 6225687352003265370   9550811636948201980
		System.out.println("-------------------------------------------");
		AccountDetail account = service.queryAccountDetail("136601633010000531", null, null);
		System.out.println("[querySubAccountDetail]");
		System.out.println("证件类型:" + account.getIdType());
		System.out.println("证件号码:" + account.getIdNumber());
		System.out.println("名字：" + account.getAccountCnName());
		System.out.println("卡号/账号：" + account.getAccountNumber());
		System.out.println("账户类型：" + account.getAccountAttr());
		System.out.println("币种：" + account.getCurrency());
		System.out.println("钞汇标志：" + account.getCashExCode());
		System.out.println("账户状态：" + account.getAccountStatus());
		System.out.println("开户网点：" + account.getAccountOpenBranch());
		System.out.println("开户分行号" + account.getAccountOpenBank());
		System.out.println("账户中文名称：" + account.getAccountCnName());
		System.out.println("账面余额：" + account.getAccountBalance());
		System.out.println("可用余额：" + account.getAvailableBalance());
		System.out.println("冻结金额：" + account.getFrozenBalance());
		System.out.println("借贷记标志：" + account.getDrcrFlag());
		System.out.println("开卡网点：" + account.getCardOpenBranch()); // 无效
		System.out.println("开卡日期：" + account.getCardOpenDate()); // 无效
		System.out.println("失效日期：" + account.getCardExpiringDate()); // 无效
		System.out.println("默认实体账号：" + account.getDefaultSubAccount());
		System.out.println("最后交易日期：" + account.getLastTransDate());
		System.out.println("销户日期：" + account.getAccountClosingDate());
		System.out.println("销户行所：" + account.getAccountClosingBranch());
		System.out.println("主副卡标志:" + account.getCardLinkType());
		System.out.println("卡类：" + account.getCardCategory());
		System.out.println("账号标志" + account.getAccountFlag());
		System.out.println("非标准账户类型" + account.getUnstandardType());
		System.out.println(" 标准账号" + account.getStandardAccount());
		System.out.println("账户分类" + account.getAccountClass());
		System.out.println("默认实体账户" + account.getDefaultSubAccount());
		System.out.println("账户性质" + account.getAccountType()); // 账户性质
		System.out.println("-------------------------------------------\r\n");
		
	}
	
	//	
	//	// 冻结在先查询（267570）：每页10行
	//@Test
	public void queryAccountFrozenMeasures() throws DataOperateException, RemoteAccessException {
		Input267570 in = new Input267570();
		//6225680728000018168  6225683621001189368  6225683155000413297
		in.setAccountNumber("6214624821000048419");
		List<AccountFrozenMeasure> list = service.queryAccountFrozenMeasures(in);
		
		System.out.println("-------------------------------------------");
		System.out.println("[queryAccountFrozenMeasures]");
		for (AccountFrozenMeasure info : list) {
			System.out.println("冻结编号：" + info.getFrozenNumber());
			System.out.println("账号：" + info.getAccountNumber());
			System.out.println("冻结方式（1-账户冻结；2-金额冻结；3-圈存；4-受托支付）：" + info.getFrozenType());
			System.out.println("1-权利机关；2-银行内部：" + info.getFrozenInsType());
			System.out.println("币种：" + info.getCurrency());
			System.out.println("钞汇标志：" + info.getCashExCode());
			System.out.println("冻结金额：" + info.getFrozenAmount());
			System.out.println("冻结文书编号：" + info.getFrozenBookNumber());
			System.out.println("冻结机构名称：" + info.getFrozenInsName());
			System.out.println("轮候次序：" + info.getWaitingSeq());
			System.out.println("生效日期：" + info.getEffectiveDate());
			System.out.println("到期日期：" + info.getExpiringDate());
			System.out.println("冻结原因：" + info.getFrozenReason());
			System.out.println("备注：" + info.getRemark());
			System.out.println("发起行所：" + info.getFrozenBranch());
			System.out.println("执法人名称：" + info.getLawName1());
			System.out.println("执法人证件号：" + info.getLawIDNumber1());
			System.out.println("执法人名称：" + info.getLawName2());
			System.out.println("执法人证件号：" + info.getLawIDNumber2());
			System.out.println("冻结状态（N-生效；C-解除）：" + info.getFrozenStatus());
			System.out.println();
		}
		System.out.println("-------------------------------------------\r\n");
	}
	
	// 账户明细列表查询（267880）：每页20行
	//@Test
	public void querySubAccountInfoList() throws DataOperateException, RemoteAccessException {
		List<SubAccountInfo> list = service.querySubAccountInfoList("6214620221000000839");
		System.out.println("-------------------------------------------");
		System.out.println("[querySubAccountInfoList]");
		for (SubAccountInfo info : list) {
			System.out.println("子账户序号：" + info.getAccountSerial());
			System.out.println("实体账号：" + info.getAccountNumber());
			System.out.println("币种：" + info.getCurrency());
			System.out.println("钞汇标志（1-钞户；2-汇户）：" + info.getCashExCode());
			System.out.println("账户类型（DD-活期；TD-定期）：" + info.getAccountType());
			System.out.println("定期账户类型：" + info.getPeriodAccountType());
			System.out.println("开户日期/起息日期：" + info.getOpenDate());
			System.out.println("到期日期：" + info.getExpiringDate());
			System.out.println("分户余额：" + info.getAccountBalance());
			System.out.println("可用余额：" + info.getAvailableBalance());
			System.out.println("分户状态（N-正常；S-睡眠户；C-结清/销户；R-冲正）：" + info.getAccountStatus());
			System.out.println();
		}
		System.out.println("-------------------------------------------\r\n");
	}
	
	//	
	//	// 交易历史查询（998070）：每页10行
	//@Test
	public void queryAccountTransaction() throws DataOperateException, RemoteAccessException {
		List<AccountTransaction> list = service.queryAccountTransaction("6214624821000048419", "2016-08-31 00:00:00", "2017-08-31 00:00:00");
		System.out.println("-------------------------------------------");
		System.out.println("[queryAccountTransaction]");
		for (AccountTransaction info : list) {
			System.out.println("交易日：" + info.getTradeDate());
			System.out.println("会计日：" + info.getAccountingDate());
			System.out.println("日志号：" + info.getLogNumber());
			System.out.println("日志顺序号：" + info.getLogSeq());
			System.out.println("账号：" + info.getAccountNumber());
			System.out.println("交易行所：" + info.getTradeBranch());
			System.out.println("交易部门：" + info.getTradeDepartment());
			System.out.println("交易柜员：" + info.getTradeTeller());
			System.out.println("传票号：" + info.getVoucherNo());
			System.out.println("借贷方向（D-借；C-贷）：" + info.getDrcrFlag());
			System.out.println("交易货币：" + info.getTradeCurrency());
			System.out.println("交易金额：" + info.getTradeAmount());
			System.out.println("账面余额：" + info.getAccountBalance());
			System.out.println("交易生效日期：" + info.getTradeEffectDate());
			System.out.println("交易状态（N-正常；C-已冲销；R-正常的冲销/抹账交易）：" + info.getTradeStatus());
			System.out.println("交易时间：" + info.getTradeTime());
			System.out.println("备注：" + info.getRemark());
			System.out.println("交易类型：" + info.getTradeType());
			System.out.println("对手账号：" + info.getRelativeNumber());
			System.out.println("对手户名：" + info.getRelativeName());
			System.out.println("凭证代码：" + info.getVoucherCode());
			System.out.println("冠字号：" + info.getHeadNumber());
			System.out.println("凭证号：" + info.getVoucherNumber());
			System.out.println("钞汇标志（1-钞；2-汇）：" + info.getCashExCode());
			System.out.println("对手支付行号：" + info.getRelativeBank());
			System.out.println();
		}
		System.out.println("-------------------------------------------\r\n");
	}
	
	//	
	//	// 账户续冻（267540）
	
	//	public void deferFreezeAccount() throws DataOperateException, RemoteAccessException {
	//		Input267540 in = new Input267540(); // 
	//		// F20200056437
	//		in.setFrozenNumber("F20200056437");
	//		in.setFreezeBookNumber("A220170725110101200039");
	//		in.setFreezeInsName("北京市西城区人民法院");
	//		in.setExpiringDate("2019-12-10");
	//		in.setLawIDNumber1("326311199502135555");
	//		in.setLawName1("刘霞");
	//		in.setLawIDNumber2("455911198905066666");
	//		in.setLawName2("顾恩");
	//		in.setFreezeBranch("178008");
	//		
	//		System.out.println("-------------------------------------------");
	//		System.out.println("[deferFreezeAccount]");
	//		DeferFreezeResult result = service.deferFreezeAccount(in);
	//		System.out.println("冻结编号：" + result.getFrozenNumber());
	//		System.out.println("账号：" + result.getAccountNumber());
	//		System.out.println("货币：" + result.getCurrency());
	//		System.out.println("钞汇标识：" + result.getCashExCode());
	//		System.out.println("冻结金额：" + result.getFrozenAmount());
	//		System.out.println("冻结文书号：" + result.getDeferBookNumber());
	//		System.out.println("冻结机构名称：" + result.getFrozenInsName());
	//		System.out.println("冻结原因：" + result.getFrozenReason());
	//		System.out.println("生效日期：" + result.getEffectiveDate());
	//		System.out.println("到期日期：" + result.getExpiringDate());
	//		System.out.println("新到期日期：" + result.getNewExpiringDate());
	//		System.out.println("备注：" + result.getRemark());
	//		System.out.println("执法人名称1：" + result.getLawName1());
	//		System.out.println("执法人证件号1：" + result.getLawIDNumber1());
	//		System.out.println("执法人名称2：" + result.getLawName2());
	//		System.out.println("执法人证件号2：" + result.getLawIDNumber2());
	//		System.out.println("已冻结金额：" + result.getFrozenAmount());
	//		System.out.println("可用余额：" + result.getAvailableAmount());
	//		System.out.println("账户余额：" + result.getAccountBalance());
	//		System.out.println("账户性质：" + result.getAccountType());
	//		System.out.println("-------------------------------------------\r\n");
	//	}
	//	
	//	// 账户解冻（267510）
	//@Test
	public void unfreezeAccount() throws DataOperateException, RemoteAccessException {
		List<String> cardNumbers = new ArrayList<String>();
		cardNumbers.add("6214624821000048419");
		//cardNumbers.add("9550880200070000173");
		//cardNumbers.add("9550880200070800176");
		//cardNumbers.add("9550880200072900154");
		//cardNumbers.add("9550880200082100159");
		//	cardNumbers.add("9550880200104200124");
		//	cardNumbers.add("9550880200271200149");
		//	cardNumbers.add("9550880200271500142");
		unFreezeAccount(cardNumbers, false);
		
		List<String> subAccountNumbers = new ArrayList<String>();
		unFreezeAccount(subAccountNumbers, false);
	}
	
	//	
	//	public void queryJointOwnership() throws DataOperateException, RemoteAccessException {
	//		List<String> cardNumbers = new ArrayList<String>();
	//		//cardNumbers.add("6214624821000048419");
	//	}
	//	
	private void unFreezeAccount(List<String> primaryNumberList, boolean sub) throws DataOperateException, RemoteAccessException {
		for (String primaryNumber : primaryNumberList) {
			if (sub) {
				// 查询子账户信息
				List<SubAccountInfo> subAccountInfoList = service.querySubAccountInfoList(primaryNumber);
				List<String> subAccountNumberList = new ArrayList<String>();
				for (SubAccountInfo subAccountInfo : subAccountInfoList) {
					subAccountNumberList.add(subAccountInfo.getAccountNumber());
				}
				unFreezeAccount(subAccountNumberList, false);
			}
			
			List<AccountFrozenMeasure> freezeMeasures = service.queryAccountFrozenMeasures(primaryNumber);
			List<String> freezeNoList = new ArrayList<String>();
			for (AccountFrozenMeasure measure : freezeMeasures) {
				if ("N".equals(measure.getFrozenStatus())) {
					freezeNoList.add(measure.getFrozenBookNumber());
					Input267510 in = new Input267510(); // 解除冻结输入项
					in.setUnfreezeType("1");
					in.setUnfreezeBranch(measure.getFrozenBranch());
					in.setUnfreezeInsName(measure.getFrozenInsName()); // 冻结机构名称
					in.setUnfreezeBookNumber(measure.getFrozenBookNumber()); // 冻结文书号
					in.setFrozenNumber(measure.getFrozenNumber()); // 原冻结编号
					in.setLawIDNumber1("326311199502135555");
					in.setLawName1("刘霞");
					in.setLawIDNumber2("455911198905066666");
					in.setLawName2("顾恩");
					UnfreezeResult result = service.unfreezeAccount(in);
					
					System.out.println("冻结编号" + result.getFrozenNumber());
					System.out.println("解冻金额" + result.getUnfreezeAmount());
					System.out.println("剩余冻结金额" + result.getFrozenBalance());
					System.out.println("可用余额" + result.getAvailableAmount());
					System.out.println();
				}
			}
		}
	}
	
	//	
	//金融理财信息查询（ZXZC02）（在线交易平台）
	//@Test
	public void queryFinanceProducts() throws DataOperateException, RemoteAccessException {
		// "20600", "13436798-3", "船奠叉赘冷福壹潘金淑渊剃"
		List<OnlineTPFinancialDetail> financeProdunts = ser.queryFinancialFromOnlineTP("10100", "321121197709255129", "燕室床");
		System.out.println(financeProdunts.size());
		for (OnlineTPFinancialDetail financeProduct : financeProdunts) {
			System.out.println("---------------------------------------------------");
			System.out.println("理财账号：" + financeProduct.getFinancialNumber()); // 理财账号
			System.out.println("回款账号：" + financeProduct.getAccountNumber()); // 回款账号
			System.out.println("明细类型：" + financeProduct.getProductCode());// 明细类型
			System.out.println("产品代码：" + financeProduct.getProductCode()); // 产品代码
			System.out.println("产品名称：" + financeProduct.getProductName()); // 产品名称
			System.out.println("销售类型：" + financeProduct.getSaleType()); // 销售类型
			System.out.println("币种：" + financeProduct.getCurrency()); // 币种
			System.out.println("份额：" + financeProduct.getAmount()); // 份额
			System.out.println("（基金）可用余额：" + financeProduct.getBalance()); // （基金）可用余额
			System.out.println("预留字段：" + financeProduct.getMark()); // 预留字段
			System.out.println("---------------------------------------------------");
		}
		
	}
	
	//	
	
	//	//行外转账扣划至二代
	//	public void externalTransfer() throws DataOperateException, RemoteAccessException {
	//		InputPSB120 in = new InputPSB120();
	//		in.setTransBranch("106002");// 交易提出行所号(M)
	//		in.setUserId("9999"); // 操作员(M)
	//		in.setCheckUserId("9992"); // 复核员(M)
	//		in.setFeeMode("1"); // 手续费模式(M)   0外扣  1内扣
	//		in.setFeeCal("0"); // 手续费计算 0：不收手续费；1：外系统计算；2：由支付系统计算
	//		in.setSenrBankCode("178008"); // 发起行行号(M)
	//		in.setRecrBankCode("309391000011");// 接收行行号(M)
	//		in.setEntrustDate("20170605");// 委托日期(M)
	//		in.setAmount("150000");// 金额(M)
	//		in.setDebitBankCode("178008");// 付款人开户行行号(M)
	//		in.setDebitAccount("6214624821000048419");// 付款人账号(M)   不校验但必输
	//		in.setDebitName("对公定期账户 "); // 付款人名称(M)   不校验但必输
	//		
	//		in.setCreditFlag("2");//1-挂账??2-解挂
	//		in.setCreditNumber("178008170706000003");// 挂账编号（新核心新增）
	//		in.setRealTransAccount("17800815623031021400001");//17800815623031021400001    17300115623031021400001 实际收/付帐号 17300115626119023910004
	//		in.setRealTransName("待划转款项");//待划转款项  实际收/付名称   应付待查清算款其他
	//		
	//		in.setCreditBankCode("23158455545"); // 收款人开户行行号(M)
	//		in.setCreditAccount("2214624821000048419");// 收款人账号(M)
	//		in.setCreditName("张敬轩");// 收款人名称(M)
	//		CargoRecord c = service.externalTransfer(in);
	//		System.out.println("来源系统" + c.getSystemId()); //来源系统
	//		System.out.println("查询日期" + c.getQueryDate()); //查询日期
	//		System.out.println("查询流水号" + c.getQuerySerialNum()); //查询流水号
	//		System.out.println("查询方式" + c.getQueryType()); //查询方式
	//		System.out.println(" 原系统委托日期" + c.getOriEntrustDate()); //原系统委托日期
	//		System.out.println("原交易流水号" + c.getOriSerialNum()); //原交易流水号
	//		System.out.println("原发起行号" + c.getOriBankCode()); //原发起行号
	//		System.out.println("完成标志" + c.getOverType()); //完成标志
	//		System.out.println("交易状态" + c.getTransType()); //交易状态
	//		System.out.println("发送状态" + c.getSendType()); //发送状态
	//		System.out.println("处理结果" + c.getDealResult()); //处理结果
	//		System.out.println("手续费" + c.getFeeAmount()); //手续费
	//		System.out.println("备注" + c.getRemark()); //备注
	//		System.out.println("备用2" + c.getRemark2()); // 备用2
	//		
	//	}
	// 冻结编号查询
	
	public void queryFrozenAccountInfo() throws DataOperateException, RemoteAccessException {
		Input267530 in = new Input267530("Z20000085792");
		AccountFrozenInfo accountfrozenInfo = service.queryFrozenInfoByFrozenNumber(in);
		System.out.println("冻结编号" + accountfrozenInfo.getFrozenNumber()); // 
		System.out.println("账号" + accountfrozenInfo.getAccountNumber()); // 账号
		System.out.println("冻结方式" + accountfrozenInfo.getFrozenType()); // 冻结方式（1-账户冻结；2-金额冻结；3-圈存；4-受托支付）
		System.out.println("____" + accountfrozenInfo.getFrozenInsType()); // 1-权利机关；2-银行内部
		System.out.println("币种" + accountfrozenInfo.getCurrency()); // 币种
		System.out.println("钞汇标志" + accountfrozenInfo.getCashExCode()); // 钞汇标志
		System.out.println("冻结金额" + accountfrozenInfo.getFrozenAmount()); // 冻结金额
		System.out.println("冻结文书编" + accountfrozenInfo.getFrozenBookNumber()); // 冻结文书编号
		System.out.println("冻结机构名称" + accountfrozenInfo.getFrozenInsName()); // 冻结机构名称
		System.out.println("轮候次序" + accountfrozenInfo.getWaitingSeq()); // 轮候次序
		System.out.println("生效日期" + accountfrozenInfo.getEffectiveDate()); // 生效日期
		System.out.println("到期日期" + accountfrozenInfo.getExpiringDate()); // 到期日期
		System.out.println("冻结原因" + accountfrozenInfo.getFrozenReason()); // 冻结原因
		System.out.println("备注" + accountfrozenInfo.getRemark()); // 备注
		System.out.println("发起行所" + accountfrozenInfo.getFrozenBranch()); // 发起行所
		System.out.println("执法人名称" + accountfrozenInfo.getLawName1()); // 执法人名称
		System.out.println("执法人证件号" + accountfrozenInfo.getLawIDNumber1()); // 执法人证件号
		System.out.println("执法人名称" + accountfrozenInfo.getLawName2()); // 执法人名称
		System.out.println("执法人证件号" + accountfrozenInfo.getLawIDNumber2()); // 执法人证件号
		
		System.out.println(" 账户类型" + accountfrozenInfo.getAccountType()); // 账户类型（1-VIA账户；2-实体账户）
		System.out.println("发起渠道" + accountfrozenInfo.getFrozenChannel()); // 发起渠道
		System.out.println("发起渠道种类" + accountfrozenInfo.getFrozenChannelMedium()); // 发起渠道种类
		System.out.println("发起渠道细类" + accountfrozenInfo.getFrozenChannelSmall()); // 发起渠道细类
	}
	
	//	
	// 核心扣划
	//@Test
	public void DeDuctAccount() throws DataOperateException, RemoteAccessException {
		Input267580 in = new Input267580();
		in.setAccountNumber("121106511010000136"); // 卡号
		in.setAccountSerial(""); //账号序号
		in.setFrozenNumber("F10200058211");//冻结编号
		in.setCurrency("156");//币种
		in.setCashExCode("1"); // 钞汇标识
		in.setDeductAmount("10"); // 扣划金额
		in.setFreezeBookNumber("A220170725110101200054"); // 变动文书号
		in.setFreezeInsName("北京市西城区人民法院");// 冻结机构名称
		in.setFreezeBranch("178008");// 发起行所
		in.setBillingAccount("17800815623031021400001"); // 入账账户
		in.setMoneyFlow("2"); //  资金流向  1时  没有挂账编号 2时有挂账编号
		in.setLawIDNumber1("1111111111111111");
		in.setLawIDNumber2("1111111111111111");
		in.setLawName1("尹建");
		in.setLawName2("尹建");
		//in.setInterestAccount("128013517010011194"); // 收息账户
		//in.setRivalAccount("128013517010011194"); // 对手账号
		
		DeductResult result = service.deductAccount(in);
		
		System.out.println("冻结编号:" + result.getFrozenNumber()); // 冻结编号
		System.out.println("账号:" + result.getAccountNumber()); // 账号
		System.out.println("账户序号:" + result.getAccountSerial()); // 账户序号
		System.out.println("货币:" + result.getCurrency()); // 货币
		System.out.println("钞汇标识:" + result.getCashExCode()); // 	钞汇标识
		System.out.println("冻结方式:" + result.getFreezeType()); // 	冻结方式
		System.out.println("原冻结金额:" + result.getOriFrozenAmount()); // 	原冻结金额
		System.out.println("扣划金额:" + result.getDeductAmount()); // 扣划金额
		System.out.println("剩余冻结处理方式:" + result.getFreezeFlag()); // 剩余冻结处理方式
		System.out.println("剩余冻结金额:" + result.getRemFrozenAmount()); // 剩余冻结金额
		System.out.println(" 变动文书号:" + result.getFreezeBookNumber()); // 变动文书号
		System.out.println("变动原因:" + result.getFreezeReason()); // 变动原因
		System.out.println("资金去向:" + result.getMoneyFlow()); // 资金去向
		System.out.println("入账账户" + result.getBillingAccount()); // 入账账户
		System.out.println("收息账户" + result.getInterestAccount()); // 收息账户
		System.out.println("备注" + result.getRemark()); // 备注
		System.out.println("发起行所" + result.getFreezeBranch()); // 发起行所
		System.out.println("执法人名称1" + result.getLawName1()); // 执法人名称1
		System.out.println("执法人证件号1" + result.getLawIDNumber1()); // 执法人证件号1
		System.out.println("执法人名称2" + result.getLawName2()); // 执法人名称2
		System.out.println("执法人证件号2" + result.getLawIDNumber2()); // 执法人证件号2
		System.out.println("挂账编号" + result.getCreditNumber()); // 挂账编号
		System.out.println("附言" + result.getNarrative()); // 附言
		System.out.println("对手账号" + result.getRivalAccount()); // 对手账号
		System.out.println("对手账号名称" + result.getRivalAccountName()); // 对手账号名称
		System.out.println("对手支付行号" + result.getRivalBankCode()); // 对手支付行号
		System.out.println("对手业务编号" + result.getRivalbusinessNumber()); // 对手业务编号
		System.out.println("对手货币" + result.getRivalCurrency()); // 对手货币
		System.out.println("利息挂账编号" + result.getIntCreditNumber()); // 利息挂账编号
		System.out.println("定期利息金额" + result.getInterestAmount()); // 定期利息金额
	}
	
	//	// 账户冻结（267500）
	//@Test
	public void freezeAccount() throws DataOperateException, RemoteAccessException {
		// V11568550891966705900002
		Input267500 in = new Input267500();
		in.setFreezeType("2"); // 金额冻结        128013852140072451
		in.setAccountNumber("121106511010000136");//6225680252000000019  FTU0880400249500255 6225680255000000483 6214624821000048419
		//in.setAccountSeq("3");
		in.setCurrency("156");// 发起机构类型
		//in.setCashExCode("2");
		in.setFreezeInsName("北京市西城区人民法院");
		in.setFreezeAmount("5.00");
		in.setEffectiveDate("2017-08-16");
		in.setExpiringDate("2017-10-08");
		in.setFreezeBranch("178008");
		in.setLawIDNumber1("7");
		in.setLawName1("尹建");
		in.setLawIDNumber2("7");
		in.setLawName2("尹建");
		in.setFreezeBookNumber("201612200000001");
		
		System.out.println("-------------------------------------------");
		System.out.println("[freezeAccount]");
		FreezeResult result = service.freezeAccount(in);
		System.out.println("冻结编号：" + result.getFrozenNumber());
		System.out.println("账号：" + result.getAccountNumber());
		System.out.println("账户序号：" + result.getAccountSeq());
		System.out.println("冻结方式：" + result.getFreezeType());
		System.out.println("发起机构类型：" + result.getFreezeInsType());
		System.out.println("货币：" + result.getCurrency());
		System.out.println("钞汇标识：" + result.getCashExCode());
		System.out.println("冻结金额：" + result.getFreezeAmount());
		System.out.println("冻结文书号：" + result.getFreezeBookNumber());
		System.out.println("冻结机构名称：" + result.getFreezeInsName());
		System.out.println("冻结原因：" + result.getFreezeReason());
		System.out.println("生效日期：" + result.getEffectiveDate());
		System.out.println("到期日期：" + result.getExpiringDate());
		System.out.println("备注：" + result.getRemark());
		System.out.println("发起行所：" + result.getFreezeBranch());
		System.out.println("执法人名称1：" + result.getLawName1());
		System.out.println("执法人证件号1：" + result.getLawIDNumber1());
		System.out.println("执法人名称2：" + result.getLawName2());
		System.out.println("执法人证件号2：" + result.getLawIDNumber2());
		System.out.println("已冻结金额：" + result.getFrozenAmount());
		System.out.println("未冻结金额：" + result.getUnfrozenAmount());
		System.out.println("可用余额：" + result.getAvailableAmount());
		System.out.println("账户余额：" + result.getAccountBalance());
		System.out.println("账户性质：" + result.getAccountType());
		System.out.println("-------------------------------------------\r\n");
	}
	
	//(88888)
	//@Test
	public void sssss() throws DataOperateException, RemoteAccessException {
		Input267510 in = new Input267510(); // 解除冻结输入项
		in.setUnfreezeType("1");
		in.setUnfreezeBranch("178008");
		in.setUnfreezeInsName("北京市西城区人民法院"); // 冻结机构名称
		in.setUnfreezeBookNumber("A220170722110101200042"); // 冻结文书号
		in.setFrozenNumber("F20200057234"); // 原冻结编号
		in.setLawIDNumber1("326311199502135555");
		in.setLawName1("name");
		in.setLawIDNumber2("455911198905066666");
		in.setLawName2("顾恩");
		UnfreezeResult result = service.unfreezeAccount(in);
		
		System.out.println("冻结编号" + result.getFrozenNumber());
		System.out.println("解冻金额" + result.getUnfreezeAmount());
		System.out.println("剩余冻结金额" + result.getFrozenBalance());
		System.out.println("可用余额" + result.getAvailableAmount());
		System.out.println();
	}
	
	//	
	
	//	/*public void qwer() throws DataOperateException, RemoteAccessException {
	//		ControlResponse_Kzzh kzqq=new ControlResponse_Kzzh();
	//		
	//
	//		operate.processFreezeAccount();
	//	}*/
	//	
	
	//金融理财信息查询（ZXZC01）（在线交易平台）
	
	//	public void queryFinanceProdus() throws DataOperateException, RemoteAccessException {
	//		// "20600", "13436798-3", "船奠叉赘冷福壹潘金淑渊剃"
	//		OnlineTPQueryFKWarnInput in = new OnlineTPQueryFKWarnInput();
	//		in.setStartTime("20170701000000");//查询开始日期
	//		in.setEndTime("20170801000000");//查询截止日期
	//		in.setLstCd("ZZZ");//名单大类
	//		List<OnlineTPFKWarnDetail> financeProdunts = ser.queryFKWarnFromOnlineTP(in);
	//		for (OnlineTPFKWarnDetail financeProduct : financeProdunts) {
	//			System.out.println(financeProduct.toString());
	//			//			System.out.println("---------------------------------------------------");
	//			//			System.out.println("理财账号：" + financeProduct.getFinancialNumber()); // 理财账号
	//			//			System.out.println("回款账号：" + financeProduct.getAccountNumber()); // 回款账号
	//			//			System.out.println("明细类型：" + financeProduct.getProductCode());// 明细类型
	//			//			System.out.println("产品代码：" + financeProduct.getProductCode()); // 产品代码
	//			//			System.out.println("产品名称：" + financeProduct.getProductName()); // 产品名称
	//			//			System.out.println("销售类型：" + financeProduct.getSaleType()); // 销售类型
	//			//			System.out.println("币种：" + financeProduct.getCurrency()); // 币种
	//			//			System.out.println("份额：" + financeProduct.getAmount()); // 份额
	//			//			System.out.println("（基金）可用余额：" + financeProduct.getBalance()); // （基金）可用余额
	//			//			System.out.println("预留字段：" + financeProduct.getMark()); // 预留字段
	//			//			System.out.println("---------------------------------------------------");
	//		}
	//		
	//	}
	
	public void queryCustomerInfo() throws DataOperateException, RemoteAccessException {
		QueryResponse_Cxqq cxqq = new QueryResponse_Cxqq();
		cxqq.setXm("田圳建傻值悦届茬届蠕疆瑰金淑渊剃胆乱雅渊剃");
		cxqq.setDsrzjhm("75347640-9");
		cxqq.setZjlx("20600");
		operate.getQueryRequestObj(cxqq);
	}
	
	//	public void deduct() throws DataOperateException, RemoteAccessException {
	//		ControlResponse_Kzzh kzqq = new ControlResponse_Kzzh();
	//		kzqq.setKhzh("6225682151000574759");
	//		kzqq.setGlzhhm("V11568550890564771300022");
	//		kzqq.getZxkzhhm();
	//		operate.invokeDeductFunds(kzqq);
	//	}
	
	//	public void jiaoyan() throws DataOperateException, RemoteAccessException {
	//		//"20600", "64946555-8", "亡琴稚曌骚糟壹潘金淑渊剃"
	//		String accountNumber = "146001516010001012";
	//		String xm = "亡琴稚曌骚糟壹潘金淑渊剃";
	//		String zjlx = "20600";
	//		String dsrzhm = "64946555-8";
	//		Input358080 input = new Input358080();
	//		input.setAccountNumber(accountNumber); // 申请控制的账号/子账户
	//		input.setCheckName(true); // 校验姓名
	//		input.setName(xm); // 被控制人姓名
	//		input.setCheckID(true); // 校验证件
	//		input.setIdType(zjlx); // 被控制人证件类型
	//		input.setIdNumber(dsrzhm); // 被控制人证件号码
	//		input.setQueryBalance(false);
	//		
	//		AccountDetail accountDetail = service.queryAccountDetail(input);
	//		if (accountDetail == null) {
	//			System.out.println("校验");
	//		} else {
	//			System.out.println("成功");
	//		}
	//	}
	
	// soap
	//@Test
	public void soap() throws DataOperateException, RemoteAccessException {
		ExternalTransferInput up = new ExternalTransferInput();
		up.setBusiSysDate(Utility.currDate8());
		up.setBrno("178008");// 分行
		up.setTellerno("AAAE0005"); // 操作员
		up.setAuthTellerno("AAAE0005");// 操作员 
		up.setZoneno("178008"); // 交易行所
		up.setAmount("5");
		up.setPayerAcct("6214624821000048419");
		up.setPayerName("大庆市住房委托贷款基金户");
		up.setSuspSerno("178008170121000093"); // 挂账编号
		up.setRealPayerAcct("17800815623031021400001");
		up.setRealPayerName("临时存款-资金代收平台-安邦保险");
		up.setPayeeBank("309391000011");
		up.setPayeeAcct("6225687352010073700");
		up.setPayeeName("dsadas");
		CargoRecordResult out = ser1.invokeExternalTransfer(up);
		System.out.println("请求系统流水号原值输出:" + out.getBusiSysSerno()); // 	请求系统流水号	原值输出
		System.out.println("交易状态:" + out.getTrxStatus()); // 	交易状态	取值范围：0-失败 ,1-成功, 2-异常,3-已冲正,7-已收妥, 9-处理中
		System.out.println("出错源系统:" + out.getErrBusiSys()); // 出错源系统	当出现错误时，返回出错的原系统，如核心、统一支付、二代支付等
		System.out.println("错误信息" + out.getErrorMsg());
		System.out.println("支付平台处理日期:" + out.getPayDate()); // 支付平台处理日期	格式yyyymmdd
		System.out.println("支付平台处理时间:" + out.getPayTime()); // 支付平台处理时间	格式HHMMSS
		System.out.println("支付平台流水号:" + out.getPaySerno()); // 支付平台流水号	
		System.out.println("手续费金额:" + out.getFeeAmount()); // 手续费金额
		System.out.println("通道代码:" + out.getPayPath()); // 通道代码
		System.out.println("通道工作日期:" + out.getPayPathWkDate()); // 通道工作日期
		System.out.println("通道流水号:" + out.getPayPathSerno()); // 通道流水号
		System.out.println("通道处理状态:" + out.getPathProcStatus()); // 通道处理状态
		System.out.println(" 核心记账日期:" + out.getCoreAcctDate()); // 核心记账日期
		System.out.println("核心日志号:" + out.getCoreSerno()); // 核心日志号
		System.out.println("核心处理状态:" + out.getCoreProcStatus()); // 核心处理状态
	}
	
	//	
	
	//	public void ownInfo() throws DataOperateException, RemoteAccessException {
	//		
	//		Input265561 in = new Input265561();
	//		in.setAccountNumber("9550880013358300799");
	//		//in.setAccountSeq("");
	//		OwnershipInfo joInfo = service.queryOwnershipInfo(in);
	//		System.out.println(joInfo.getMarginType());
	//		System.out.println(joInfo.getPledgeType());
	//	}
	//	
	
	//	public void queryCargoRecord() throws DataOperateException, RemoteAccessException {
	//		
	//		InputPSB411 in = new InputPSB411();
	//		in.setQueryDate("20170706");// 	查询日期
	//		in.setQueryMode("1");// 	查询方式  1：查询PSB110往账流水   2：查询PSB250票交流水
	//		in.setOriEntrustDate("20170605");
	//		// 原系统委托日期
	//		in.setOriSerialNum("000000232861");
	//		// 原交易流水号
	//		in.setOriBankCode("178008");
	//		// 原发起行号
	//		
	//		CargoRecord c = service.queryCargoRecord(in);
	//		System.out.println("查询日期" + c.getQueryDate());
	//		System.out.println("查询流水号" + c.getQuerySerialNum());
	//		System.out.println("查询方式" + c.getQueryType());
	//		System.out.println("原系统委托日期" + c.getOriEntrustDate());
	//		System.out.println("原发起行号" + c.getOriBankCode());
	//		System.out.println("完成标志" + c.getOverType());
	//		System.out.println("交易状态" + c.getTransType());
	//		System.out.println("发送状态" + c.getSendType());//04回执错
	//		System.out.println("处理结果" + c.getDealResult());
	//		System.out.println("手续费" + c.getFeeAmount());
	//	}
	//	
	//100323（综合理财：柜台未解冻流水查询） 
	
	public void queryFinanceTrans() throws DataOperateException, RemoteAccessException {
		
		// 用卡号
		FinancialFrozenMeasuresInput input = new FinancialFrozenMeasuresInput();
		input.setBranchNumber("178008");
		input.setFlagNumber(("660000208812"));
		input.setFlagType("1");
		//		  NoAs400BodyResponse100323 response=ser.freezeFinanceQueryTrans(input);
		//		  List<FinanceTrans>list=response.getElements();
		//		  System.out.println(list.size());
		
		//		FreezeFinanceQueryTransRequest input = new FreezeFinanceQueryTransRequest();
		//		input.setBranchNo("10000");//
		//		input.setAccount("121511516010026035"); //客户标示
		//		input.setAccType("0"); //客户类型AccType=0，填账号号码，AccType=1，填核心客户号 ,AccType=2，填证件号码
		//		//input.setIdType("0");
		List<FinancialFrozenMeasure> response = ser.queryFinancialFrozenMeasures(input);
		for (FinancialFrozenMeasure lists : response) {
			System.out.println("冻结日期 :" + lists.getTransDate());
			System.out.println("冻结流水号 :" + lists.getSerialNo());
			System.out.println(" 交易名称 :" + lists.getTransName());
			System.out.println("客户编号 :" + lists.getClientNo());
			System.out.println("理财账号 :" + lists.getAssetAcc());
			System.out.println("TA代码 :" + lists.getTACode());
			System.out.println("TA名称 :" + lists.getTAName());
			System.out.println("产品代码 :" + lists.getPrdCode());
			System.out.println("产品名称 :" + lists.getPrdName());
			System.out.println("份额 :" + lists.getVolume());
			System.out.println("冻结原因 :" + lists.getFrozenCause());
			System.out.println("法律文书号 :" + lists.getLawNo());
			System.out.println("执行机构名称 :" + lists.getOrgName());
			System.out.println("冻结截止日期 :" + lists.getEndDate());
			System.out.println("银行账号 :" + lists.getBankAcc());
		}
		System.out.println("数量 ：" + response.size());
		
	}
	
	//	
	//100222（综合理财 ：理财产品份额冻结）
	
	public void freezeFinanceShareProducts() throws DataOperateException, RemoteAccessException {
		FinancialFreezeInput input = new FinancialFreezeInput();
		input.setBranchNumber("100000");//100000
		input.setAccountNumber("9550884500174800189"); //账户
		input.setProductCode("1202F-XSFJS"); //理财产品
		input.setVolume("1"); //份额
		input.setFrozenCause("0"); //司法冻结
		input.setLawNumber("lawNo011");
		input.setOrganName("广发银行");
		input.setFreezeEndDate("20171012"); //冻结到期日
		input.setFlagNumber("660000208812"); //客户标示（核心客户号、证件号）
		input.setFlagType("1");
		; //AccType=1，填核心客户号 ,AccType=2，填证件号码
			//		input.setIdType("3");
		FinancialFreezeResult response = ser.invokeFreezeFinancial(input);
		System.out.println("系统流水号 ：" + response.getSerialNo());
		System.out.println("客户标识:" + response.getAccount());
		System.out.println("客户名称 :" + response.getClientName());
		System.out.println("TA代码 :" + response.getTACode());
		System.out.println("产品代码 :" + response.getPrdCode());
		System.out.println("产品名称 :" + response.getPrdName());
		System.out.println("冻结原因 :" + response.getFrozenCause());
		System.out.println("法律文书号:" + response.getLawNo());
		System.out.println("执行机构名称:" + response.getOrgName());
		System.out.println("冻结截止日期:" + response.getEndDate());
		System.out.println("交易状态:" + response.getStatus());
		System.out.println("TA名称:" + response.getTAName());
		System.out.println("交易状态名称:" + response.getStatusName());
		System.out.println("钞汇标志:" + response.getCashFlag());
		System.out.println("客户编号:" + response.getClientNo());
		System.out.println("实际冻结份额:" + response.getRealFrozenVol());
		
	}
	
	//	
	//100223（综合理财 ：理财产品份额解冻）
	
	public void unFreezeFinanceShareProducts() throws RemoteAccessException, DataOperateException {
		FinancialUnfreezeInput input = new FinancialUnfreezeInput();
		input.setAccountNumber("9550884500174800189"); //账号
		input.setPrdCode("1202F-XSFJS"); //理财产品
		input.setAssoSerial("20170726134936000251"); //原冻结流水号
		input.setLawNo("lawNo011");
		input.setOrgName("广发银行");
		input.setAccount("660000208812"); //客户表示
		input.setAccType("1"); //AccType=1，填核心客户号 ,AccType=2，填证件号码
		//input.setIdType("0");
		input.setVolume("1"); //份额
		input.setBranchNumber("100000");//开户行所
		FinancialUnfreezeResult response = ser.invokeUnfreezeFinancial(input);
		System.out.println("系统流水号:" + response.getSerialNo());
		System.out.println("客户标识:" + response.getAccount());
		System.out.println("客户名称:" + response.getClientName());
		System.out.println("TA名称:" + response.getTAName());
		System.out.println("产品代码:" + response.getPrdCode());
		System.out.println("产品名称:" + response.getPrdName());
		System.out.println("原冻结流水号:" + response.getAssoSerial());
		System.out.println("法律文书号:" + response.getLawNo());
		System.out.println("执行机构名称:" + response.getOrgName());
		System.out.println("交易状态:" + response.getStatus());
		System.out.println("A代码:" + response.getTACode());
		System.out.println("交易状态名称:" + response.getStatusName());
		System.out.println("钞汇标志:" + response.getCashFlag());
		System.out.println("客户编号:" + response.getClientNo());
		System.out.println("解冻份额:" + response.getFrozenVol());
	}
	
	//司法续冻交易（109224）
	
	public void deFreezeFinance() throws RemoteAccessException, DataOperateException {
		FinancialDeferFreezeInput input = new FinancialDeferFreezeInput();
		input.setAccountNumber("9550884500174800189"); //银行账号
		input.setAccount("660000208812"); //客户表示
		input.setAccType("1"); //AccType=1，填核心客户号 ,AccType=2，填证件号码
		//input.setIdType("0");
		input.setAssoSerial("20170726115537000247"); //原冻结流水号
		input.setLawNo("lawNo011");
		input.setOrgName("广发银行");
		input.setEndDate("20171026"); //冻结到期日
		input.setBranchNumber("100000"); //归属行行所
		FinancialDeferFreezeResult response = ser.invokeDeferFreezeFinancial(input);
		System.out.println("系统流水号:" + response.getSerialNo());
		System.out.println("客户编号:" + response.getClientNo());
		System.out.println("客户名称:" + response.getClientName());
		System.out.println("TA代码:" + response.getTACode());
		System.out.println("TA名称:" + response.getTAName());
		System.out.println("产品代码:" + response.getPrdCode());
		System.out.println("产品名称:" + response.getPrdName());
		System.out.println("币种:" + response.getCurrType());
		System.out.println("原冻结截止日期:" + response.getOldEndDate());
		System.out.println("新冻结截止日期:" + response.getNewEndDate());
		System.out.println("冻结份额:" + response.getVol());
		System.out.println("法律文书号:" + response.getLawNo());
		System.out.println("执行机构名称:" + response.getOrgName());
		System.out.println("交易状态:" + response.getStatus());
		System.out.println("交易状态名称:" + response.getStatusName());
		
	}
	
	//	
	//	public void a() throws DataOperateException, RemoteAccessException {
	//		
	//		List<ContractAccount> cc = service.queryContractAccountList("20600", "27892395-0", "深圳市国咨土地房地产评估有限公司广州分公司");
	//		for (ContractAccount c : cc) {
	//			AccountDetail ad = service.queryAccountDetail(c.getAccountNumber(), c.getCurrency(), null);
	//			OwnershipInfo info = service.queryOwnershipInfo(c.getAccountNumber());
	//			System.out.println("-------------------------------");
	//			System.out.println(c.getAccountNumber() + ", " + ad.getCurrency() + ", " + ad.getAccountAttr() + ", " + ad.getAvailableBalance() + ", " + ad.getAccountBalance());
	//			System.out.println(info.getPledgeType() + ", " + info.getMarginType());
	//		}
	//		
	//		//		List<SubAccountInfo> sa = service.querySubAccountInfoList("101008611010000120");
	//	}
	//	
	@Test
	public void doSomething() throws DataOperateException, RemoteAccessException {
		Input358040 in = new Input358040();
		//9550880004679900120  136601633010000531
		in.setAccountNumber("9550880200000600355");
		//in.setCurrency("344");
		//in.setCashExCode("1");
		//in.setSubAccountNumber("1");
		//in.setAccountSeq("1");
		AccountVerifyInfo info = inmessage.queryAccountVerifyInfo(in);
//		boolean bool = "0".equals(info.getUnstandardType()) || "1".equals(info.getUnstandardType());
//		if (bool && ("M".equals(info.getAccountType()))) {
			System.out.println();
			System.out.println("卡号/账号" + info.getAccountNumber()); // 	卡号/账号
			System.out.println("非标准账号类型" + info.getUnstandardType()); // 	非标准账号类型
			System.out.println("账号标志" + info.getAccountFlag()); // 	账号标志
			System.out.println("账号" + info.getSubAccountNumber()); // 	账号
			System.out.println("序号" + info.getAccountSeq()); // 	序号
			System.out.println("币别" + info.getCurrency()); // 	币别
			System.out.println("钞汇标志" + info.getCashExCode()); // 	钞汇标志
			System.out.println("客户号" + info.getCustomerNumber()); // 	客户号
			System.out.println("客户状态" + info.getCustomerStatus()); // 	客户状态
			System.out.println("客户类型" + info.getType()); // 	客户类型
			System.out.println("证件类型" + info.getIDType()); // 	证件类型
			System.out.println("证件号码" + info.getIDNumber()); // 	证件号码
			System.out.println("开户证件有效日期" + info.getOpenIDEffectiveDate()); // 	开户证件有效日期
			System.out.println("客户名称" + info.getCustomerName()); // 	客户名称
			System.out.println("客户级别" + info.getCustomerLevel()); // 	客户级别
			System.out.println("性别" + info.getSex()); // 	性别
			System.out.println("手机号码" + info.getContactNumber()); // 	手机号码
			System.out.println("地址类型" + info.getAddressType()); // 	地址类型
			System.out.println("默认地址标识" + info.getAddressFlag()); // 	默认地址标识
			System.out.println("地址详细信息" + info.getAddressDetail()); // 	地址详细信息
			System.out.println("国家代码" + info.getAddressCountry()); // 	国家代码
			System.out.println("邮政编码" + info.getPostalCode()); // 	邮政编码
			System.out.println("账户产品码" + info.getAccountProductCode()); // 	账户产品码
			System.out.println("账户产品名称" + info.getAccountProductName()); // 	账户产品名称
			System.out.println("账户类型" + info.getAccountAttr()); // 	账户类型
			System.out.println("账户性质" + info.getAccountType()); // 	账户性质
			System.out.println("账户状态" + info.getAccountStatus()); // 	账户状态
			System.out.println("账户状态字" + info.getAccountStatusWord()); // 	账户状态字
			System.out.println("账户冻结状态" + info.getAccountFrozenStatus()); // 	账户冻结状态
			System.out.println("金额冻结状态" + info.getAmountFrozenStatus()); // 	金额冻结状态
			System.out.println("开户网点" + info.getOpenBranch()); // 	开户网点
			System.out.println("开户分行" + info.getOpenBank()); // 	开户分行
			System.out.println("开户日期" + info.getOpenDate()); // 	开户日期
			System.out.println("最后交易日期" + info.getLastTransDate()); // 	最后交易日期
			System.out.println("账务行" + info.getAccountingBank()); // 	账务行
			System.out.println("管理行" + info.getManagementBank()); // 	管理行
			System.out.println("存期" + info.getDepositPeriod()); // 	存期
			System.out.println("到期指示" + info.getExpirePointing()); // 	到期指示
			System.out.println("利率值" + info.getInterestRate()); // 	利率值
			System.out.println("账户英文名称" + info.getEnglishName()); // 	账户英文名称
			System.out.println("账户中文名称" + info.getChineseName()); // 	账户中文名称
			System.out.println("账面余额" + info.getAccountBalance()); // 	账面余额
			System.out.println("可用余额" + info.getAvailableBalance()); // 	可用余额
			System.out.println("	冻结金额" + info.getFrozenBalance()); // 	冻结金额
			System.out.println("协定存款标志" + info.getTreatyDepositFlag()); // 	协定存款标志
			System.out.println("协定利率" + info.getTreatyInterestRate()); // 	协定利率
			System.out.println("协定金额" + info.getTreatyAmount()); // 	协定金额
			System.out.println("支取方式" + info.getDrawType()); // 	支取方式
			System.out.println("借贷记标志" + info.getDrcrFlag()); // 	借贷记标志
			System.out.println("通兑标志" + info.getCrosDrawFlag()); // 	通兑标志
			System.out.println("通存标志" + info.getCrosDepositFlag()); // 	通存标志
			System.out.println("存折挂失状态" + info.getPassbookStatus()); // 	存折挂失状态
			System.out.println("卡介质类型" + info.getCardMedium()); //  	卡介质类型
			System.out.println("卡介质状态" + info.getCardStatus()); // 	卡介质状态
			System.out.println("	卡使用状态" + info.getCardUseStatus()); // 	卡使用状态
			System.out.println("卡产品" + info.getCradProduct()); // 	卡产品
			System.out.println("卡产品名称" + info.getCradProductName()); // 	卡产品名称
			System.out.println("卡联名标志" + info.getCardJointlySign()); // 	卡联名标志
			System.out.println("卡归属类别" + info.getCardPcType()); // 	卡归属类别
			System.out.println("卡物理属性" + info.getCardPvType()); // 	卡物理属性
			System.out.println("支持移动支付标识" + info.getCardMobileType()); // 	支持移动支付标识
			System.out.println("主副卡标志" + info.getCardLinkType()); // 	主副卡标志
			System.out.println("	卡类" + info.getCardCategory()); // 	卡类
			System.out.println("开卡网点" + info.getCardOpenBranch()); // 	开卡网点
			System.out.println("	开卡网点名称" + info.getCardOpenBranchName()); // 	开卡网点名称
			System.out.println("开卡日期" + info.getCardOpenDate()); // 	开卡日期
			System.out.println("	失效日期" + info.getCardExpiringDate()); // 	失效日期
			System.out.println("所属科目" + info.getItem()); // 	所属科目
			System.out.println("主账号" + info.getMainAccountNumber()); // 	主账号
			System.out.println("标准账号" + info.getStandardAccountNumber()); // 	标准账号
			System.out.println("专用户资金性质" + info.getSpecialKind()); // 	专用户资金性质
			System.out.println("外汇属性标注" + info.getForeignLabel()); // 	外汇属性标注
			System.out.println("企业营业执照" + info.getBusinessLicense()); // 	企业营业执照
			System.out.println("客户状态字" + info.getClientStsw()); // 	客户状态字
			System.out.println("社保金融卡标识" + info.getSocialFlag()); // 	社保金融卡标识
			System.out.println("账户用途" + info.getAccountPurpose()); // 	账户用途
			System.out.println("对公关联人类型" + info.getRelationShipType()); // 	对公关联人类型
			System.out.println("对公关联人名称" + info.getRelationShipName()); // 	对公关联人名称
			System.out.println("定期账户凭证类型" + info.getDepositPeriodFlag()); // 	定期账户凭证类型
			System.out.println("定期存单质押状态" + info.getDeposiPledgeFlag()); // 	定期存单质押状态
			System.out.println("卡定向账户交易标志 " + info.getCardDirectionalAccountFlag()); // 	卡定向账户交易标志 
			System.out.println("" + info.getReservedFlag4()); // 	预留标识位4
			System.out.println("" + info.getReservedFlag5()); // 	预留标识位5
			System.out.println("" + info.getReserved()); // 	预留栏位
			System.out.println("对公外汇账户类型" + info.getForeignAccountType()); // 	对公外汇账户类型
			System.out.println("对公外汇账户性质代码" + info.getForeignAccountCode()); // 	对公外汇账户性质代码
			System.out.println("	账户分类" + info.getAccountClass()); // 	账户分类
			System.out.println("" + info.getRemark()); // 	预留栏位
//		}else{
//			System.out.println("不是非标账且不是保证金账户");
//		}
		
		
	}
}
