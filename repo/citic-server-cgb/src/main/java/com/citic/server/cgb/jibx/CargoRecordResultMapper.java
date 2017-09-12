package com.citic.server.cgb.jibx;

import com.citic.server.cgb.domain.response.CargoRecordResult;
import com.citic.server.cgb.domain.response.GatewayMessageResult;

public class CargoRecordResultMapper extends GatewayMessageResultMapper {
	
	public CargoRecordResultMapper() {
		
	}
	
	public CargoRecordResultMapper(String ns, int index, String name) {
		super(ns, index, name);
	}
	
	@Override
	public GatewayMessageResult createGatewayMessageResult() {
		return new CargoRecordResult();
	}
	
}
