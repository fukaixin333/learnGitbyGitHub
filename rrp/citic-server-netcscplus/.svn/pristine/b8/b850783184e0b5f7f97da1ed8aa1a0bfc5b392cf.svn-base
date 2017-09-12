package com.citic.server.server;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.service.domain.MC21_task_fact;
 
/**
 * @author hubq
 *
 */

public class NTaskFactory {
	
	private static final Logger logger = LoggerFactory.getLogger(NTaskFactory.class);

    /**
     * 
     * @return
     */
    public NBaseTask getInstance(ApplicationContext _ac,MC21_task_fact mC00_task_fact)
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException, SecurityException, NoSuchMethodException,
            IllegalArgumentException, InvocationTargetException {
    	
        //通过文件名的方式获取对应的实例
        String pkg = NBaseTask.class.getPackage().getName();
        
        String className = pkg.substring(0,pkg.length()-6) +   mC00_task_fact.getTaskcmd();
        
        NBaseTask taskObj   = null;

        Class taskClass;
        try {
            taskClass = Class.forName(className);
            if (taskClass != null) {
                //初始化构造函数
                Constructor cc = taskClass.getConstructor(new Class[] {ApplicationContext.class, MC21_task_fact.class });
                
                taskObj = (NBaseTask) cc.newInstance( _ac,mC00_task_fact );
            }
        } catch (java.lang.NoSuchMethodException e) {
        	e.printStackTrace();
            logger.error("未能找到相应的构造方法！className=" + className);
        }catch(ClassNotFoundException e){
        	e.printStackTrace();
            logger.error("未能找到任务工作实现类！className=" + className);
        }catch(Exception e){
        	e.printStackTrace();
        }
        
        return taskObj;
    }

}
