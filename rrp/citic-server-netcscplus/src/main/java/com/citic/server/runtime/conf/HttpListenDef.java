package com.citic.server.runtime.conf;

import java.util.List;

import lombok.Data;

/**
 * @author Liu Xuanfei
 * @date 2017年6月14日 下午8:13:11
 */
@Data
public final class HttpListenDef implements ListenDef {
	
	private int port;
	private List<HttpListenCommand> commands;
}
