/**
 * Copyright (c) 2017, CITIC Application Service Provider Co., Ltd. All Rights Reserved.
 * -
 * $Author: liuxuanfei, $Date: 2017/07/08 15:31:05$
 */
package com.citic.server.runtime.conf;

import java.util.List;

import lombok.Data;

/**
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/08 15:31:05$
 */
@Data
public class SocketListenDef implements ListenDef {
	
	private List<SocketListenCommand> commands;
}
