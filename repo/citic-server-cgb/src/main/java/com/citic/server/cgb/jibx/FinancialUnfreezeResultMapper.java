package com.citic.server.cgb.jibx;

import com.citic.server.cgb.domain.response.GatewayMessageResult;
import com.citic.server.cgb.domain.response.FinancialUnfreezeResult;

/**
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/26 00:54:03$
 */
public class FinancialUnfreezeResultMapper extends GatewayMessageResultMapper {
	
	public FinancialUnfreezeResultMapper() {
		// Do nothing
	}
	
	public FinancialUnfreezeResultMapper(String ns, int index, String name) {
		super(ns, index, name);
	}
	
	@Override
	public GatewayMessageResult createGatewayMessageResult() {
		return new FinancialUnfreezeResult();
	}
}
