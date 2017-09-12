package com.citic.server.service.task;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC02_rt_task_fact;
/**
 * @author hubq
 *
 */

public class RtTaskFactory {
	
	private static final Logger logger = LoggerFactory.getLogger(RtTaskFactory.class);

    /**
     * 
     * @return
     */
    public RtBaseTask getInstance(ApplicationContext _ac,MC02_rt_task_fact mC02_rt_task_fact)
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException, SecurityException, NoSuchMethodException,
            IllegalArgumentException, InvocationTargetException {
    	
        //通过文件名的方式获取对应的实例
        String pkg = RtBaseTask.class.getPackage().getName();
        
        String className = pkg + "." + ""+mC02_rt_task_fact.getExec_class();
        
        RtBaseTask taskObj   = null;

        Class taskClass;
        try {
            taskClass = Class.forName(className);
            if (taskClass != null) {
                //初始化构造函数
                Constructor cc = taskClass.getConstructor(new Class[] {ApplicationContext.class, MC02_rt_task_fact.class });
                
                taskObj = (RtBaseTask) cc.newInstance( _ac,mC02_rt_task_fact );
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
