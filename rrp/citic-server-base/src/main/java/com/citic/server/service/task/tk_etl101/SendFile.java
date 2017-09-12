package com.citic.server.service.task.tk_etl101;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.ApplicationCFG;
import com.citic.server.domain.MC00_datasource;
import com.citic.server.domain.MC00_flagfile;
import com.citic.server.domain.MC00_task_fact;
import com.citic.server.service.CacheService;
import com.citic.server.utils.FileUtils;

/**
 * 送数过来：
 * 对临时数据目录做初始化
 * 做好数据装载前的预处理
 * @author hubaiqing
 */
public class SendFile extends BaseFile{

	private static final Logger logger = LoggerFactory.getLogger(SendFile.class);
	
	public SendFile(ApplicationContext ac,MC00_datasource ds, MC00_task_fact tf) {
		super(ac,ds, tf);
	}

	@Override
	public  boolean run() throws Exception{
		boolean isSuccess = true;

		this.initDataFileForLoad();

		// 清理历史数据文件
		this.deleteHistoryDate();
		
		return isSuccess;
    	
    }


}
