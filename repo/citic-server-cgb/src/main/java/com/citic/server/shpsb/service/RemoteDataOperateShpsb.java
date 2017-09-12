package com.citic.server.shpsb.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.citic.server.inner.domain.AccountTransaction;
import com.citic.server.inner.domain.ContractAccount;
import com.citic.server.inner.domain.SubAccountInfo;
import com.citic.server.inner.domain.response.AccountDetail;
import com.citic.server.inner.domain.response.CorporateCustomer;
import com.citic.server.inner.domain.response.IndividualCustomer;
import com.citic.server.inner.service.IPrefixMessageService;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.runtime.Utility;
import com.citic.server.shpsb.ShpsbCode;
import com.citic.server.shpsb.domain.ShpsbSadx;
import com.citic.server.shpsb.domain.request.ShpsbRequestCzrzMx;
import com.citic.server.shpsb.domain.request.ShpsbRequestDdzhMx;
import com.citic.server.shpsb.domain.request.ShpsbRequestJyglhMx;
import com.citic.server.shpsb.domain.request.ShpsbRequestJylsMx;
import com.citic.server.shpsb.domain.request.ShpsbRequestKhzlMx;
import com.citic.server.shpsb.domain.request.ShpsbRequestZhcyrMx;
import com.citic.server.shpsb.domain.request.ShpsbRequestZhxxMx;

/**
 * @author liuxuanfei
 * @date 2016年12月7日 下午3:39:06
 */
@Component("remoteDataOperateShpsb")
public class RemoteDataOperateShpsb implements IDataOperateShpsb {
	
	@Autowired
	@Qualifier("innerMessageService")
	private IPrefixMessageService service;
	
	//账户信息查询接口
	@Override
	public List<ShpsbRequestZhxxMx> getAccountInformation(ShpsbSadx sadx) throws DataOperateException, RemoteAccessException {
		List<ShpsbRequestZhxxMx> zhxxMxList = new ArrayList<ShpsbRequestZhxxMx>();
		
		String credentialType = sadx.getZzlx(); // 证件类型
		String credentialNumber = sadx.getZzhm(); // 证件号码
		String subjectName = sadx.getMc(); // 涉案对象名称
		
		// 检查证照信息是否齐全
		if (StringUtils.isBlank(credentialType) || StringUtils.isBlank(credentialNumber) || StringUtils.isBlank(subjectName)) {
			throw new DataOperateException("暂不支持证照信息不全的查询方式");
		}
		
		// 根据证件信息查询合约账户基本信息
		List<ContractAccount> accountList = service.queryContractAccountList(credentialType, credentialNumber, subjectName);
		if (accountList == null || accountList.size() == 0) {
		} else {
			for (ContractAccount account : accountList) {
				String contractType = account.getContractType();
				// CARD-借记卡、CAAC-活期存款、MMDP-定期存款
				if ("CARD".equals(contractType) || "CAAC".equals(contractType) || "MMDP".equals(contractType)) {
					String primaryNumber = account.getAccountNumber();
					if (primaryNumber == null || primaryNumber.length() == 0) {
						continue;
					}
					
					// 查询主账户详细信息
					AccountDetail accountDetail = service.queryAccountDetail(primaryNumber, account.getCurrency(), null);
					if (accountDetail == null) {
						continue;
					}
					zhxxMxList.add(copyShpsbRequestZhxxMx(accountDetail, "1", true));
					
					// 查询子账户信息
					if ("1".equals(accountDetail.getCustomerType())) { // 个人客户
						List<SubAccountInfo> subAccountInfos = service.querySubAccountInfoList(primaryNumber);
						if (subAccountInfos == null || subAccountInfos.size() == 0) {
						} else {
							// 根据子账户基本信息（子账户账号、币种）获取子账户详细信息
							for (SubAccountInfo subAccount : subAccountInfos) {
								String accountNumber = subAccount.getAccountNumber(); // 账号
								String currency = subAccount.getCurrency(); // 币种
								String cashExCode = subAccount.getCashExCode(); // 钞汇标志
								AccountDetail subAccountDetail = service.queryAccountDetail(accountNumber, currency, cashExCode);
								if (subAccountDetail == null) {
								} else {
									zhxxMxList.add(copyShpsbRequestZhxxMx(subAccountDetail, subAccount.getAccountSerial(), false));
								}
							}
						}
					}
				}
			}
		}
		
		return zhxxMxList;
	}
	
	// 账户持有人资料查询接口
	@Override
	public ShpsbRequestZhcyrMx getAccountPossessor(ShpsbSadx sadx) throws DataOperateException, RemoteAccessException {
		ShpsbRequestZhcyrMx zhcyrMx = new ShpsbRequestZhcyrMx();
		AccountDetail info = service.queryAccountDetail(sadx.getZh(), null, null);
		if (info != null) {
			zhcyrMx.setCkrxm(info.getAccountCnName()); // 客户中文名称
			zhcyrMx.setZzlx(info.getIdType()); // 证件类型
			zhcyrMx.setZzhm(info.getIdNumber()); // 证件号码
		}
		return zhcyrMx;
	}
	
	//开户资料查询接口
	@Override
	public ShpsbRequestKhzlMx getAccountOpenInformation(ShpsbSadx sadx) throws DataOperateException, RemoteAccessException {
		String credentialType = sadx.getZzlx(); // 证件类型
		String credentialNumber = sadx.getZzhm(); // 证件号码
		String subjectName = sadx.getMc(); // 对象名称
		
		if (StringUtils.isBlank(credentialType) || StringUtils.isBlank(credentialNumber) || StringUtils.isBlank(subjectName)) {
			throw new DataOperateException("暂不支持证照信息不全的查询方式");
		}
		
		String mode = sadx.getQrymode();
		if (ShpsbCode.GR_KHZL.equalsTo(mode)) {
			IndividualCustomer info = service.queryIndividualCustomerInfo(credentialType, credentialNumber, subjectName);
			return copyShpsbRequestKhzlMx(info);
		} else if (ShpsbCode.DW_KHZL.equalsTo(mode)) {
			CorporateCustomer info = service.queryCorporateCustomerInfo(credentialType, credentialNumber, subjectName);
			return copyShpsbRequestKhzlMx(info);
		} else {
			throw new DataOperateException("未知的请求代码");
		}
	}
	
	//交易明细查询接口
	@Override
	public List<ShpsbRequestJylsMx> getAccountTransaction(ShpsbSadx sadx) throws DataOperateException, RemoteAccessException {
		List<ShpsbRequestJylsMx> jylsMxList = new ArrayList<ShpsbRequestJylsMx>();
		String accountNumber = sadx.getZh(); // 主账户（账卡号）
		String cxkssj = sadx.getCxkssj(); // 查询起始时间
		String cxjssj = sadx.getCxjssj(); // 查询截止时间
		
		if ((cxkssj == null || cxkssj.length() == 0) || (cxjssj == null || cxjssj.length() == 0)) {
			throw new DataOperateException("查询起始时间或截止时间必须输入");
		}
		
		List<AccountTransaction> tansList = service.queryAccountTransaction(accountNumber, cxkssj, cxjssj);
		if (tansList != null && tansList.size() > 0) {
			for (AccountTransaction accountTransaction : tansList) {
				jylsMxList.add(copyShpsbRequestJylsMx(accountTransaction));
			}
		}
		
		return jylsMxList;
	}
	
	@Override
	public List<ShpsbRequestCzrzMx> getOperationLog(ShpsbSadx sadx) throws DataOperateException, RemoteAccessException {
		throw new DataOperateException(null, "暂不支持此类交易");
	}
	
	@Override
	public List<ShpsbRequestJyglhMx> getAffiliatedTransactionNo(ShpsbSadx sadx) throws DataOperateException, RemoteAccessException {
		throw new DataOperateException(null, "暂不支持此类交易");
	}
	
	@Override
	public List<ShpsbRequestDdzhMx> getReciprocalAccount(ShpsbSadx sadx) throws DataOperateException, RemoteAccessException {
		throw new DataOperateException(null, "暂不支持此类交易");
	}
	
	// ==========================================================================================
	//                     对象转换
	// ==========================================================================================
	private ShpsbRequestZhxxMx copyShpsbRequestZhxxMx(AccountDetail accountDetail, String accountSerial, boolean cc) {
		ShpsbRequestZhxxMx zhxxMx = new ShpsbRequestZhxxMx();
		zhxxMx.setZh(cc ? accountDetail.getAccountNumber() : accountDetail.getV_AccountNumber()); // 账卡号
		zhxxMx.setZhzt(accountDetail.getAccountStatus()); // 账户状态
		zhxxMx.setYhmc(accountDetail.getAccountOpenBranch()); // 开户银行名称
		zhxxMx.setCxsj(Utility.currDateTime19()); // 查询时间
		zhxxMx.setHbzl(accountDetail.getCurrency()); // 货币种类（币种）
		zhxxMx.setZhye(accountDetail.getAccountBalance()); // 账户余额
		zhxxMx.setKyye(accountDetail.getAvailableBalance()); // 可用余额
		zhxxMx.setKhrq(accountDetail.getCardOpenDate()); // 开户日期
		zhxxMx.setXhrq(accountDetail.getAccountClosingDate()); // 销户日期
		zhxxMx.setZhlb(accountDetail.getAccountAttr()); // 账户类别
		zhxxMx.setZhxh((accountSerial == null || accountSerial.length() == 0) ? "1" : accountSerial); // 账户序号
		return zhxxMx;
	}
	
	private ShpsbRequestKhzlMx copyShpsbRequestKhzlMx(IndividualCustomer info) {
		if (info == null) {
			return null;
		}
		
		ShpsbRequestKhzlMx khzlMx = new ShpsbRequestKhzlMx();
		khzlMx.setZzdz(info.getPermanentAddress()); // 住宅地址
		khzlMx.setZzdh(info.getFixedLineNumber()); // 住宅电话
		khzlMx.setGzdw(""); // 工作单位
		khzlMx.setDwdz(info.getRegisteredAddress()); // 单位地址 
		khzlMx.setDwdh(info.getFixedLineNumber()); // 单位电话
		khzlMx.setLxdz(info.getMailingAddress()); // 联系地址
		khzlMx.setLxdh(info.getFixedLineNumber()); // 联系固话
		khzlMx.setLxsj(info.getTelephoneNumber()); // 联系手机
		khzlMx.setEmail(info.getEmailAddress()); // email地址
		khzlMx.setKhwd(info.getOpenBank()); // 开户网点
		khzlMx.setGlsj(info.getMobilePhoneNumber());//关联手机号
		
		return khzlMx;
	}
	
	private ShpsbRequestKhzlMx copyShpsbRequestKhzlMx(CorporateCustomer info) {
		if (info == null) {
			return null;
		}
		
		ShpsbRequestKhzlMx khzlMx = new ShpsbRequestKhzlMx();
		khzlMx.setDwdz(info.getRegisteredAddress()); // 单位地址 
		khzlMx.setDwdh(info.getFixedLineNumber()); // 单位电话
		khzlMx.setFrdb(info.getLegalInfo().getName()); // 法人代表
		khzlMx.setFrdbzzlx(info.getLegalInfo().getIdType()); // 法人代表证件类型
		khzlMx.setFrdbzzhm(info.getLegalInfo().getIdNumber()); // 法人代表证件号码
		khzlMx.setLxr(info.getActualHolderInfo().getName()); // 联系人（实际控股人）
		khzlMx.setEmail(info.getEmailAddress()); // 邮箱地址
		khzlMx.setDbrxm("");
		khzlMx.setDbrzzlx("");
		khzlMx.setDbrzzh("");
		khzlMx.setShxydm(info.getUnifiedSocialCreditCode()); // 统一社会信用代码
		
		return khzlMx;
	}
	
	private ShpsbRequestJylsMx copyShpsbRequestJylsMx(AccountTransaction trans) {
		ShpsbRequestJylsMx jymx = new ShpsbRequestJylsMx();
		
		// 时间处理
		String transactionDate = trans.getTradeDate();
		if (StringUtils.isBlank(transactionDate)) {
			transactionDate = "19700101";
		}
		String transactionTime = trans.getTradeTime();
		if (StringUtils.isBlank(transactionTime)) {
			transactionTime = "000000";
		}
		
		// 处理交易流水号（"[会计日] + [日志号] + [日志顺序号]"）
		String accountingDate = trans.getAccountingDate(); // 会计日
		String logNumber = StringUtils.isBlank(trans.getLogNumber()) ? "00000000" : StringUtils.leftPad(trans.getLogNumber(), 8, '0'); // 日志号
		String logSeq = StringUtils.isBlank(trans.getLogSeq()) ? "0000" : StringUtils.leftPad(trans.getLogSeq(), 4, '0'); // 日志顺序号
		
		jymx.setZh(trans.getV_AccountNumber()); // 账号
		jymx.setJylsh(accountingDate + logNumber + logSeq); // 交易流水号
		jymx.setJyrq(transactionDate); // 交易日期
		jymx.setJysj(transactionTime); // 交易时间
		jymx.setDfzh(trans.getRelativeNumber()); // 对方账号
		jymx.setDfhm(trans.getRelativeBank()); // 对方行名
		jymx.setDfmc(trans.getRelativeName()); // 对方名称
		jymx.setBz(trans.getTradeCurrency()); // 币种
		jymx.setJyje(trans.getTradeAmount()); // 交易金额
		jymx.setJdbz(trans.getDrcrFlag()); // 借贷标记
		jymx.setJyqd(trans.getTradeChannel()); // 交易渠道
		jymx.setJywd(trans.getTradeBranch()); // 交易网点
		jymx.setIp(""); // IP地址
		jymx.setMac(""); // MAC地址
		jymx.setBeiz(trans.getRemark()); // 备注
		jymx.setJyglh(""); // 交易关联号
		jymx.setJyye(trans.getAccountBalance()); // 账户余额
		jymx.setCph(trans.getVoucherNo()); // 传票号
		jymx.setDfzzlx(""); // 对方证照类型
		jymx.setDfzzhm(""); // 对方证件号码
		
		return jymx;
	}
}
