package com.citic.server.service.tasksplit;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
/**
 * @author hubq
 *
 */

public class SplitFactory {
	
	private static final Logger logger = LoggerFactory.getLogger(SplitFactory.class);

    /**
     * 
     * @return
     */
    public BaseSplit getInstance(ApplicationContext _ac,String _taskid,String _tasksplit,String _splitparams)
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException, SecurityException, NoSuchMethodException,
            IllegalArgumentException, InvocationTargetException {
    	
        //通过文件名的方式获取对应的实例
        String pkg = BaseSplit.class.getPackage().getName();
        
        String className = pkg + "." + "Split_"+_tasksplit;
        
        BaseSplit splitObj   = null;

        Class taskClass;
        try {
            taskClass = Class.forName(className);
            if (taskClass != null) {
                //初始化构造函数
                Constructor cc = taskClass.getConstructor(new Class[] { ApplicationContext.class,String.class,String.class,String.class });
                
                splitObj = (BaseSplit) cc.newInstance(_ac,_taskid,_tasksplit,_splitparams);
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
        
        return splitObj;
    }

}
