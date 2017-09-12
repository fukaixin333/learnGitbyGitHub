package com.citic.server.http.service;

/**
 * <tt>HTTP</tt> 服务
 * 
 * @author Liu Xuanfei
 * @date 2016年9月13日 下午4:08:47
 */
public abstract class HttpServer {
	/**
	 * 启动 <tt>HTTP</tt> 服务
	 * 
	 * @throws Exception
	 */
	public abstract void startup() throws Exception;
}
