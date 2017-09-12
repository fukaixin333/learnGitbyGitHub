/**
 * Copyright (c) 2017, CITIC Application Service Provider Co., Ltd. All Rights Reserved.
 * -
 * $Author: liuxuanfei, $Date: 2017/07/26 11:29:22$
 */
package com.citic.server.cgb.jibx;

import com.citic.server.cgb.domain.response.GatewayMessageResult;
import com.citic.server.cgb.domain.response.OnlineTPQueryFKWarnResult;

/**
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/26 11:29:22$
 */
public class OnlineTPQueryFKWarnMapper extends GatewayMessageResultMapper {
	
	public OnlineTPQueryFKWarnMapper() {
		// Do nothing
	}
	
	public OnlineTPQueryFKWarnMapper(String ns, int index, String name) {
		super(ns, index, name);
	}
	
	@Override
	public GatewayMessageResult createGatewayMessageResult() {
		return new OnlineTPQueryFKWarnResult();
	}
}
