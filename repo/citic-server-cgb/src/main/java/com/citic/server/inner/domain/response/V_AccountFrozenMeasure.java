package com.citic.server.inner.domain.response;

import java.util.List;

import com.citic.server.inner.domain.AccountFrozenMeasure;
import com.citic.server.inner.domain.ResponseMessage;

public class V_AccountFrozenMeasure extends ResponseMessage {
	private static final long serialVersionUID = -8285788818134692386L;
	
	private List<AccountFrozenMeasure> frozenMeasureList;
	
	public List<AccountFrozenMeasure> getFrozenMeasureList() {
		return frozenMeasureList;
	}
	
	public void setFrozenMeasureList(List<AccountFrozenMeasure> frozenMeasureList) {
		this.frozenMeasureList = frozenMeasureList;
	}
}
