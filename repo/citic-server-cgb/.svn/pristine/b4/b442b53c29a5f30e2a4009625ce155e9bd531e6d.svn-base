package com.citic.server.cgb.domain.response;

import java.util.ArrayList;
import java.util.List;

import com.citic.server.runtime.Utility;

/**
 * @author yinxiong
 * @version $Revision: 1.0.0, $Date: 2017/07/26 00:16:05$
 */
public class OnlineTPQueryFKWarnResult extends OnlineTPMessageResult {
	
	private static final String key_totalPage = "totalPage"; // 总页数
	private static final String key_totalCount = "totalCount"; // 总记录数
	private static final String key_currPage = "currPage"; // 当前页码
	private static final String key_currCount = "currCount"; // 当前页条数
	private static final String key_lastFlg = "lastFlg"; // 末页标识
	
	private final List<OnlineTPFKWarnDetail> fkWarnlDetailList = new ArrayList<OnlineTPFKWarnDetail>();
	
	private boolean eol = false;
	private OnlineTPFKWarnDetail lastFkWarnlDetail;
	
	@Override
	public void putEntry(String key, String value) {
		if (putHeaderEntry(key, value)) {
			return;
		}
		
		if (key_totalPage.equals(key) || key_totalCount.equals(key) || key_currPage.equals(key) || key_currCount.equals(key) || key_lastFlg.equals(key)) {
			putField(key, value);
		} else {
			if (OnlineTPFKWarnDetail.START_KEY.equals(key)) {
				if (eol || lastFkWarnlDetail == null) {
					lastFkWarnlDetail = new OnlineTPFKWarnDetail();
					fkWarnlDetailList.add(lastFkWarnlDetail);
					eol = false;
				}
			} else if (OnlineTPFKWarnDetail.END_KEY.equals(key)) {
				eol = true;
			}
			
			if (lastFkWarnlDetail == null) {
				throw new IllegalArgumentException("expected target '" + OnlineTPFKWarnDetail.START_KEY + "', but '" + key + "'");
			}
			lastFkWarnlDetail.putField(key, value);
		}
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
	
	public String getLastFlg() {
		return getFieldValue(key_lastFlg);
	}
	
	public List<OnlineTPFKWarnDetail> getFkWarnlDetailList() {
		return fkWarnlDetailList;
	}
}
