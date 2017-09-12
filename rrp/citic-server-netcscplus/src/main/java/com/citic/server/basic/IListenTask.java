package com.citic.server.basic;

import com.citic.server.runtime.conf.ListenCommand;

/**
 * 监听任务接口
 * 
 * @author Liu Xuanfei
 * @date 2016年4月15日 上午11:30:35
 */
public interface IListenTask {
	
	/**
	 * @param command
	 * @param type Server listen type
	 * @throws Exception
	 * @author liuxuanfei
	 * @date 2017/07/08 15:34:00
	 */
	public void register(ListenCommand command, int type) throws Exception;
	
	public void initListenTask() throws Exception;
	
	public void startup() throws Exception;
}
