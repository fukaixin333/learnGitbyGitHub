/**
 * Copyright (c) 2017, CITIC Application Service Provider Co., Ltd. All Rights Reserved.
 * -
 * $Author: liuxuanfei, $Date: 2017/07/25 17:32:40$
 */
package com.citic.server.cgb.jibx;

import com.citic.server.cgb.domain.response.FinancialFreezeResult;
import com.citic.server.cgb.domain.response.GatewayMessageResult;

/**
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/25 17:32:40$
 */
public class FinancialFreezeResultMapper extends GatewayMessageResultMapper {
	
	public FinancialFreezeResultMapper() {
		// Do nothing
	}
	
	public FinancialFreezeResultMapper(String ns, int index, String name) {
		super(ns, index, name);
	}
	
	@Override
	public GatewayMessageResult createGatewayMessageResult() {
		return new FinancialFreezeResult();
	}
}
