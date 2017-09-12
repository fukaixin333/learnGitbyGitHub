/**
 * Copyright (c) 2017, CITIC Application Service Provider Co., Ltd. All Rights Reserved.
 * -
 * $Author: liuxuanfei, $Date: 2017/08/07 21:05:26$
 */
package com.citic.server.runtime;

/**
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/08/07 21:05:26$
 */
public enum EnvProfiles {
	
	/** 开发环境 */
	DEV("Development Environment"),
	
	/** 系统内部集成测试环境 */
	SIT("System Integration Testing Environment"),
	
	/** 用户验收测试环境 */
	UAT("User Acceptance Testing Environment"),
	
	/** 生产环境 */
	PRO("Production Environment");
	
	private String description;
	
	private EnvProfiles(String description) {
		this.description = description;
	}
	
	public String description() {
		return description;
	}
	
	public static EnvProfiles enumOf(String profilesName, EnvProfiles defaultProfiles, boolean ignoreCase) {
		if (profilesName == null || profilesName.length() == 0) {
			return defaultProfiles;
		}
		
		if (ignoreCase) {
			profilesName = profilesName.toUpperCase();
		}
		
		for (EnvProfiles profiles : EnvProfiles.values()) {
			if (profilesName.equals(profiles.name())) {
				return profiles;
			}
		}
		
		return defaultProfiles;
	}
}
