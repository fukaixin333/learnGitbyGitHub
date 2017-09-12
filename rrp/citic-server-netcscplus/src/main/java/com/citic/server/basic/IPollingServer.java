package com.citic.server.basic;

/**
 * 轮询服务接口
 * <p>
 * 通常，不直接实现此接口，而是继承{@link AbstractPollingServer}，并重写{@link AbstractPollingServer#call()}方法。 
 * 
 * @author Liu Xuanfei
 * @date 2016年4月6日 下午3:36:57
 */
public interface IPollingServer {
	
	/**
	 * @return delay in milliseconds before task is to be executed.
	 */
	public long getDelay();
	
	/**
	 * @return time in milliseconds between successive task executions.
	 */
	public long getPeriod();
	
	/**
	 * @param args The application arguments
	 */
	public void initPollingServer(String... args) throws Exception;
	
	/**
	 * The action to be performed by this server.
	 */
	public void call();
}
