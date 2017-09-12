package com.citic.server.inner.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.citic.server.cgb.domain.request.ExternalTransferInput;
import com.citic.server.cgb.domain.request.FinancialDeferFreezeInput;
import com.citic.server.cgb.domain.request.FinancialFreezeInput;
import com.citic.server.cgb.domain.request.FinancialFrozenMeasuresInput;
import com.citic.server.cgb.domain.request.FinancialUnfreezeInput;
import com.citic.server.cgb.domain.request.OnlineTPQueryFKWarnInput;
import com.citic.server.cgb.domain.request.OnlineTPQueryFinancialInput;
import com.citic.server.cgb.domain.response.CargoRecordResult;
import com.citic.server.cgb.domain.response.FinancialDeferFreezeResult;
import com.citic.server.cgb.domain.response.FinancialFreezeResult;
import com.citic.server.cgb.domain.response.FinancialFrozenMeasure;
import com.citic.server.cgb.domain.response.FinancialFrozenMeasuresResult;
import com.citic.server.cgb.domain.response.FinancialUnfreezeResult;
import com.citic.server.cgb.domain.response.OnlineTPFKWarnDetail;
import com.citic.server.cgb.domain.response.OnlineTPFinancialDetail;
import com.citic.server.cgb.domain.response.OnlineTPQueryFKWarnResult;
import com.citic.server.cgb.domain.response.OnlineTPQueryFinancialResult;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.RemoteAccessException;

@Service("soapMessageService")
public class SOAPMessageService extends AbstractSOAPMessageService implements Codes {
	
	public FinancialFreezeResult invokeFreezeFinancial(FinancialFreezeInput in) throws DataOperateException, RemoteAccessException {
		String accountNumber = in.getAccountNumber();
		if (accountNumber == null || accountNumber.length() == 0) {
		} else if (accountNumber.startsWith("V") && accountNumber.length() > 5) { // 拆分组合子账号
			in.setAccountNumber(accountNumber.substring(5));
		}
		
		FinancialFreezeResult result = (FinancialFreezeResult) writeRequestMessage(in, "EFSS", FREEZE_FINANCIAL, FREEZE_FINANCIAL_BNAME_RT);
		if (result.hasException()) {
			throw new RemoteAccessException(result.getErrorNo(), result.getErrorInfo());
		}
		return result;
	}
	
	@Override
	public FinancialDeferFreezeResult invokeDeferFreezeFinancial(FinancialDeferFreezeInput in) throws DataOperateException, RemoteAccessException {
		String accountNumber = in.getAccountNumber();
		if (accountNumber == null || accountNumber.length() == 0) {
		} else if (accountNumber.startsWith("V") && accountNumber.length() > 5) { // 拆分组合子账号
			in.setAccountNumber(accountNumber.substring(5));
		}
		
		FinancialDeferFreezeResult result = (FinancialDeferFreezeResult) writeRequestMessage(in, "EFSS", DEFERFREEZE_FINANCIAL, DEFERFREEZE_FINANCIAL_BNAME_RT);
		if (result.hasException()) {
			throw new RemoteAccessException(result.getErrorNo(), result.getErrorInfo());
		}
		return result;
	}
	
	@Override
	public FinancialUnfreezeResult invokeUnfreezeFinancial(FinancialUnfreezeInput in) throws DataOperateException, RemoteAccessException {
		String accountNumber = in.getAccountNumber();
		if (accountNumber == null || accountNumber.length() == 0) {
		} else if (accountNumber.startsWith("V") && accountNumber.length() > 5) { // 拆分组合子账号
			in.setAccountNumber(accountNumber.substring(5));
		}
		
		FinancialUnfreezeResult result = (FinancialUnfreezeResult) writeRequestMessage(in, "EFSS", UNFREEZE_FINANCIAL, UNFREEZE_FINANCIAL_BNAME_RT);
		if (result.hasException()) {
			throw new RemoteAccessException(result.getErrorNo(), result.getErrorInfo());
		}
		return result;
	}
	
	@Override
	public List<FinancialFrozenMeasure> queryFinancialFrozenMeasures(FinancialFrozenMeasuresInput in) throws DataOperateException, RemoteAccessException {
		if ("0".equals(in.getFlagType())) { // 客户标识为[0-账号]
			String accountNumber = in.getFlagNumber();
			if (accountNumber == null || accountNumber.length() == 0) {
			} else if (accountNumber.startsWith("V") && accountNumber.length() > 5) { // 拆分组合子账号
				in.setFlagNumber(accountNumber.substring(5));
			}
		}
		
		int pageRow = 20; // 每页输出行数
		int offSet = 1; // 定位角标
		List<FinancialFrozenMeasure> frozenMeasureList = null;
		
		in.setQueryNum(pageRow);
		boolean hasNext = true;
		while (hasNext) {
			in.setOffSet(offSet);
			
			FinancialFrozenMeasuresResult result = (FinancialFrozenMeasuresResult) writeRequestMessage(in, "EFSS", QUERY_FINANCICAL_FREEZEMEASURE, FINANCICAL_FREEZEMEASURE_BANME_RT);
			if (result.hasException()) {
				throw new RemoteAccessException(result.getErrorNo(), result.getErrorInfo());
			}
			
			if (frozenMeasureList == null) {
				frozenMeasureList = new ArrayList<FinancialFrozenMeasure>(result.getTotNum());
			}
			frozenMeasureList.addAll(result.getFrozenMeasureList());
			
			offSet += pageRow;
			hasNext = offSet < result.getTotNum();
		}
		
		return frozenMeasureList;
	}
	
	// ==========================================================================================
	//                     金融资产信息（通过在线交易平台）
	// ==========================================================================================
	
	@Override
	public List<OnlineTPFinancialDetail> queryFinancialFromOnlineTP(String idType, String idNumber, String customerName) throws DataOperateException, RemoteAccessException {
		OnlineTPQueryFinancialInput in = new OnlineTPQueryFinancialInput();
		in.setIdType(idType); // 证件类型
		in.setIdNumber(idNumber); // 证件号码
		in.setCustomerName(customerName); // 客户名称
		
		return queryFinancialFromOnlineTP(in);
	}
	
	@Override
	public List<OnlineTPFinancialDetail> queryFinancialFromOnlineTP(OnlineTPQueryFinancialInput in) throws DataOperateException, RemoteAccessException {
		int pageRow = 20; // 每页输出行数
		int pageNum = 1; // 页码
		List<OnlineTPFinancialDetail> financialDetailList = null;
		
		in.setPageSize(pageRow);
		boolean isLastPage = false;
		while (!isLastPage) {
			in.setPageNum(pageNum);
			
			OnlineTPQueryFinancialResult result = (OnlineTPQueryFinancialResult) writeRequestMessage(in, "OTSP", QUERY_FINANCIAL_DEATAIL, FINANCIAL_DEATAIL_BNAME_RT);
			if (result.hasException()) {
				throw new DataOperateException(result.getResCode(), result.getResMessage());
			}
			
			if (financialDetailList == null) {
				financialDetailList = new ArrayList<OnlineTPFinancialDetail>();
			}
			financialDetailList.addAll(result.getFinancialDetailList());
			
			isLastPage = (result.getCurrPage() == result.getTotalPage()) || (result.getCurrCount() < pageRow);
			pageNum++;
		}
		
		return financialDetailList;
	}
	
	@Override
	public List<OnlineTPFKWarnDetail> queryFKWarnFromOnlineTP(OnlineTPQueryFKWarnInput in) throws DataOperateException, RemoteAccessException {
		int pageRow = 20; // 每页输出行数
		int pageNum = 1; // 页码
		List<OnlineTPFKWarnDetail> fKWarnDetaillList = null;
		
		in.setPageSize(pageRow);
		boolean isLastPage = false;
		while (!isLastPage) {
			in.setPageNum(pageNum);
			
			OnlineTPQueryFKWarnResult result = (OnlineTPQueryFKWarnResult) writeRequestMessage(in, "OTSP", QUERY_FK_WARN_DETAIL, FK_WARN_DETAIL_BNAME_RT);
			if (result.hasException()) {
				throw new RemoteAccessException(result.getResCode(), result.getResMessage());
			}
			
			if (fKWarnDetaillList == null) {
				fKWarnDetaillList = new ArrayList<OnlineTPFKWarnDetail>();
			}
			fKWarnDetaillList.addAll(result.getFkWarnlDetailList());
			
			isLastPage = ("Y".equals(result.getLastFlg())) || (result.getCurrPage() == result.getTotalPage()) || (result.getCurrCount() < pageRow);
			pageNum++;
		}
		return fKWarnDetaillList;
	}
	
	public CargoRecordResult invokeExternalTransfer(ExternalTransferInput in) throws DataOperateException, RemoteAccessException {
		
		CargoRecordResult result = (CargoRecordResult) writeRequestMessage(in, "UPPS", UNIFIED_PAYMENT_TRANSFER, UNIFIED_PAYMENT_TRANSFER_BNAME_RT);
		if (!"0000".equals(result.getErrorCode())) {
			//测试输出 errorMessage
			System.out.println(result.getErrorMsg());
			throw new RemoteAccessException(result.getErrorCode(), result.getErrorMsg());
		}
		return result;
	}
}
