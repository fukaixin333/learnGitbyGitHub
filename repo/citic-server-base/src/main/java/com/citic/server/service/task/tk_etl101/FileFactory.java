package com.citic.server.service.task.tk_etl101;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC00_datasource;
import com.citic.server.domain.MC00_task_fact;
import com.citic.server.service.CacheService;


public class FileFactory{
	
	private static final Logger logger = LoggerFactory
			.getLogger(FileFactory.class);

    /**
     * 
     */
    public BaseFile getTaskInstance(ApplicationContext _ac,MC00_task_fact _mC00_task_fact)
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException, SecurityException, NoSuchMethodException,
            IllegalArgumentException {
        //通过文件名的方式获取对应的实例
        String pkg = BaseFile.class.getPackage().getName();
        
        
        String dsid = _mC00_task_fact.getDsid();//数据源ID
        String taskid = _mC00_task_fact.getTaskid();
        String subtaskid = _mC00_task_fact.getSubtaskid();//表任务分组编码，对应1或者多个数据表
        
        CacheService cacheService = (CacheService)_ac.getBean("cacheService");
        
		HashMap dsMap = (HashMap) cacheService.getCache("datasource",
				HashMap.class);

		MC00_datasource ds = (MC00_datasource) dsMap.get(dsid);
        
		/** 取得当前任务对应的数据表列表 */
		HashMap ds_tableMap = (HashMap)cacheService.getCache("ds_tables", HashMap.class);
		if(ds_tableMap.containsKey(taskid)){
			HashMap ds_table_subMap = (HashMap)ds_tableMap.get(taskid);
			if(ds_table_subMap.containsKey(subtaskid)){
				ArrayList tableList = (ArrayList)ds_table_subMap.get(subtaskid);
				_mC00_task_fact.setTableList(tableList);
			}
		}
		
        String classNameStr = "";
        
        //取数方式:get pull
        String dgetmethod = ds.getDgetmethod();
        String dputlocation = ds.getDputlocation();
        String dgetlocation = ds.getDgetlocation();
        
        if (dgetmethod.equalsIgnoreCase( "get")) {
        	if(dgetlocation.equalsIgnoreCase( "ftp2localfile" )){
        		classNameStr = "FTP";
        	}else if(dgetlocation.equalsIgnoreCase( "dblink2ods" )){
        		classNameStr = "DBLINK";
        	}else if(dgetlocation.equalsIgnoreCase( "jdbc2localfile" )){
        		classNameStr = "JDBC";
        	}else if(dgetlocation.equalsIgnoreCase( "cmd2localfile" )){
        		classNameStr = "CMD";
        	}
        	classNameStr = "GetFileBy"+classNameStr;
        } else if (dgetmethod.equalsIgnoreCase( "put")) {
        	if(dputlocation.equalsIgnoreCase( "ods" )){
        		classNameStr = "SendTable";
        	}else if(dputlocation.equalsIgnoreCase( "localfile" )){
        		classNameStr = "SendFile";
        	}
        } 
           
        String className = pkg + "." + classNameStr ;
        BaseFile baseFile   = null;
        Class fileClass;
		try {
			fileClass = Class.forName(className);
			if (fileClass != null) {
				// 初始化构造函数
				Constructor cc = fileClass
						.getConstructor(new Class[] { ApplicationContext.class,MC00_datasource.class,MC00_task_fact.class });

				baseFile = (BaseFile) cc
						.newInstance(new Object[] {_ac,ds, _mC00_task_fact });

			}
		} catch (java.lang.NoSuchMethodException e) {
			e.printStackTrace();
			logger.error("未能找到相应的构造方法！className=" + className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("未能找到任务工作实现类！className=" + className);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return baseFile;
    }

    public static void main(String[] args) {

    }

}
