package com.citic.server.basic;

import com.citic.server.runtime.conf.ListenCommand;
import com.citic.server.runtime.conf.SocketListenCommand;

/**
 * Basic监听任务
 * 
 * @author Liu Xuanfei
 * @date 2016年4月15日 上午11:38:18
 */
public abstract class AbstractListenTask implements IListenTask {
	
	private ListenCommand command;
	
	/** type: 监听服务类型（Outer/Inner） */
	private int type;
	
	@Override
	public void register(ListenCommand command, int type) throws Exception {
		this.command = command;
		this.type = type;
		initListenTask();
	}
	
	@Override
	public void initListenTask() throws Exception {
		// Do nothing
	}
	
	public ListenCommand getCommand() {
		return this.command;
	}
	
	public int getServerType() {
		return this.type;
	}
	
	public int getListenPort() {
		if (command instanceof SocketListenCommand) {
			return ((SocketListenCommand) command).getPort();
		}
		
		return -1;
	}
}
