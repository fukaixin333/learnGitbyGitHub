package com.citic.server.cgb.domain.response;

import java.util.ArrayList;
import java.util.List;

import com.citic.server.runtime.Utility;

/**
 * 金融理财在先冻结查询接口响应结果
 * 
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/25 19:48:05$
 */
public class FinancialFrozenMeasuresResult extends FinancialMessageResult {
	
	private static final String key_TotNum = "TotNum"; // 总行数
	private static final String key_RetNum = "RetNum"; // 本次返回行数
	private static final String key_OffSet = "OffSet"; // 定位串
	
	private final List<FinancialFrozenMeasure> frozenMeasureList = new ArrayList<FinancialFrozenMeasure>();
	
	private boolean eol = false;
	private FinancialFrozenMeasure lastFrozenMeasure;
	
	@Override
	public void putEntry(String key, String value) {
		if (putHeaderEntry(key, value)) {
			return;
		}
		
		if (key_TotNum.equals(key) || key_RetNum.equals(key) || key_OffSet.equals(key)) {
			putField(key, value);
		} else {
			if (FinancialFrozenMeasure.START_KEY.equals(key)) {
				if (eol || lastFrozenMeasure == null) {
					lastFrozenMeasure = new FinancialFrozenMeasure();
					frozenMeasureList.add(lastFrozenMeasure);
					eol = false;
				}
			} else if (FinancialFrozenMeasure.END_KEY.equals(key)) {
				eol = true;
			}
			
			if (lastFrozenMeasure == null) {
				throw new IllegalArgumentException("expected target '" + FinancialFrozenMeasure.START_KEY + "', but '" + key + "'");
			}
			lastFrozenMeasure.putField(key, value);
		}
	}
	
	public int getTotNum() {
		String value = getFieldValue(key_TotNum);
		if (Utility.isNumeric(value, false)) {
			return Integer.parseInt(value);
		}
		return -1;
	}
	
	public int getRetNum() {
		String value = getFieldValue(key_RetNum);
		if (Utility.isNumeric(value, false)) {
			return Integer.parseInt(value);
		}
		return -1;
	}
	
	public int getOffSet() {
		String value = getFieldValue(key_OffSet);
		if (Utility.isNumeric(value, false)) {
			return Integer.parseInt(value);
		}
		return -1;
	}
	
	public List<FinancialFrozenMeasure> getFrozenMeasureList() {
		return frozenMeasureList;
	}
}
