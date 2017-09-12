package com.citic.server.basic.service;

import com.citic.server.runtime.RemoteAccessException;

/**
 * @author Liu Xuanfei
 * @date 2016年5月10日 下午10:01:31
 */
public interface IRemoteAccessService {
	public String writeRequestMessage(String message) throws RemoteAccessException;
}
