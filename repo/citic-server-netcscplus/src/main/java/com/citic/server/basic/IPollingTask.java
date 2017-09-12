package com.citic.server.basic;

/**
 * 轮询任务接口
 * <p>
 * 通常，不直接实现此接口，而是继承{@link AbstractPollingTask}，并实现{@link AbstractPollingTask#executeAction()}方法。
 * 
 * @author Liu Xuanfei
 * @date 2016年4月7日 下午2:39:26
 */
public interface IPollingTask {
	
	/**
	 * 此方法应该在{@link IPollingServer#initPollingServer(String...)}中被调用。
	 */
	public IPollingTask initPollingTask() throws Exception;
	
	/**
	 * The action to be performed by this polling task.
	 */
	public void execute();
}
