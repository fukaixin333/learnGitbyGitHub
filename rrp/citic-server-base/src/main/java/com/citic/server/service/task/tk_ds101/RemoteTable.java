package com.citic.server.service.task.tk_ds101;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC00_datasource;
import com.citic.server.domain.MC00_task_fact;

/**
 * 表标识，对于本地与远程是一致的
 * @author hubq
 *
 */
public class RemoteTable extends LocalTable {

	private static final Logger logger = LoggerFactory
			.getLogger(RemoteTable.class);
   
	public RemoteTable(ApplicationContext ac,MC00_datasource ds, MC00_task_fact tf) {
		super(ac,ds, tf);
	} 


}
