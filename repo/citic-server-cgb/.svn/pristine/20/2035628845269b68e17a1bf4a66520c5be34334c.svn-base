package com.citic.server.cgb.domain.response;

import java.util.ArrayList;
import java.util.List;

import com.citic.server.runtime.Utility;

/**
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/26 00:16:05$
 */
public class OnlineTPQueryFinancialResult extends OnlineTPMessageResult {
	
	private static final String key_IdType = "idType";
	private static final String key_IdNumber = "idNo";
	private static final String key_CustomerName = "ciName";
	private static final String key_totalPage = "totalPage";
	private static final String key_totalCount = "totalCount";
	private static final String key_currPage = "currPage";
	private static final String key_currCount = "currCount";
	
	private final List<OnlineTPFinancialDetail> financialDetailList = new ArrayList<OnlineTPFinancialDetail>();
	
	private boolean eol = false;
	private OnlineTPFinancialDetail lastFinancialDetail;
	
	@Override
	public void putEntry(String key, String value) {
		if (putHeaderEntry(key, value)) {
			return;
		}
		
		if (key_IdType.equals(key) || key_IdNumber.equals(key) || key_CustomerName.equals(key) //
			|| key_totalPage.equals(key)
			|| key_totalCount.equals(key)
			|| key_currPage.equals(key)
			|| key_currCount.equals(key)) {
			putField(key, value);
		} else {
			if (OnlineTPFinancialDetail.START_KEY.equals(key)) {
				if (eol || lastFinancialDetail == null) {
					lastFinancialDetail = new OnlineTPFinancialDetail();
					financialDetailList.add(lastFinancialDetail);
					eol = false;
				}
			} else if (OnlineTPFinancialDetail.END_KEY.equals(key)) {
				eol = true;
			}
			
			if (lastFinancialDetail == null) {
				throw new IllegalArgumentException("expected target '" + OnlineTPFinancialDetail.START_KEY + "', but '" + key + "'");
			}
			lastFinancialDetail.putField(key, value);
		}
	}
	
	public String getIdType() {
		return getFieldValue(key_IdType);
	}
	
	public String getIdNumber() {
		return getFieldValue(key_IdNumber);
	}
	
	public String getCustomerName() {
		return getFieldValue(key_CustomerName);
	}
	
	public int getTotalPage() {
		String value = getFieldValue(key_totalPage);
		if (Utility.isNumeric(value, false)) {
			return Integer.parseInt(value);
		}
		return -1;
	}
	
	public int getTotalCount() {
		String value = getFieldValue(key_totalCount);
		if (Utility.isNumeric(value, false)) {
			return Integer.parseInt(value);
		}
		return -1;
	}
	
	public int getCurrPage() {
		String value = getFieldValue(key_currPage);
		if (Utility.isNumeric(value, false)) {
			return Integer.parseInt(value);
		}
		return -1;
	}
	
	public int getCurrCount() {
		String value = getFieldValue(key_currCount);
		if (Utility.isNumeric(value, false)) {
			return Integer.parseInt(value);
		}
		return -1;
	}
	
	public List<OnlineTPFinancialDetail> getFinancialDetailList() {
		return financialDetailList;
	}
}
