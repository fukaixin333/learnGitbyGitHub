package com.citic.server.service.task;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC00_task_fact;
/**
 * @author hubq
 *
 */

public class TaskFactory {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskFactory.class);

    /**
     * 
     * @return
     */
//    public BaseTask getInstance(ApplicationContext _ac,MC00_task_fact mC00_task_fact)
//            throws InstantiationException, IllegalAccessException,
//            ClassNotFoundException, SecurityException, NoSuchMethodException,
//            IllegalArgumentException, InvocationTargetException {
//    	
//        //通过文件名的方式获取对应的实例
//        String pkg = BaseTask.class.getPackage().getName();
//        
//        String className = pkg + "." + ""+mC00_task_fact.getTaskcmd();
//        
//        BaseTask taskObj   = null;
//
//        Class taskClass;
//        try {
//            taskClass = Class.forName(className);
//            if (taskClass != null) {
//                //初始化构造函数
//                Constructor cc = taskClass.getConstructor(new Class[] {ApplicationContext.class, MC00_task_fact.class });
//                
//                taskObj = (BaseTask) cc.newInstance( _ac,mC00_task_fact );
//            }
//        } catch (java.lang.NoSuchMethodException e) {
//        	e.printStackTrace();
//            logger.error("未能找到相应的构造方法！className=" + className);
//        }catch(ClassNotFoundException e){
//        	e.printStackTrace();
//            logger.error("未能找到任务工作实现类！className=" + className);
//        }catch(Exception e){
//        	e.printStackTrace();
//        }
//        
//        return taskObj;
//    }

    public BaseTask getInstance(ApplicationContext _ac,MC00_task_fact mC00_task_fact)
            throws Exception {
    	
        
        BaseTask taskObj   = null;

        try {
                taskObj = (BaseTask) _ac.getBean(mC00_task_fact.getTaskcmd());
                
         }catch(Exception e){
        	 logger.error(e.getMessage());
        	e.printStackTrace();
        }
  
        if(taskObj == null){
        	logger.error("没有获取到初始化任务对象 taskid："+mC00_task_fact.getTaskid()+"taskCmd"+mC00_task_fact.getTaskcmd());
        	throw new Exception("没有获取到初始化任务对象");
        }

        taskObj.setAc(_ac);
        taskObj.setMC00_task_fact(mC00_task_fact);
        
        return taskObj;
    }

}
