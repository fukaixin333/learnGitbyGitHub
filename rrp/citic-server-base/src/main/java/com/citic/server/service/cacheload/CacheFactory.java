package com.citic.server.service.cacheload;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
/**
 * @author hubq
 *
 */

public class CacheFactory {
	
	private static final Logger logger = LoggerFactory.getLogger(CacheFactory.class);

    /**
     * 
     * @return
     */
    public BaseCache getInstance(ApplicationContext _ac,String _key)
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException, SecurityException, NoSuchMethodException,
            IllegalArgumentException, InvocationTargetException {
    	
        //通过文件名的方式获取对应的实例
        String pkg = BaseCache.class.getPackage().getName();
        
        String className = pkg + "." + "Cache_"+_key;
        
        BaseCache baseCache   = null;

        Class taskClass;
        try {
            taskClass = Class.forName(className);
            if (taskClass != null) {
                //初始化构造函数
                Constructor cc = taskClass.getConstructor(new Class[] { ApplicationContext.class,String.class });
                
                baseCache = (BaseCache) cc.newInstance(_ac,_key);
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
        
        return baseCache;
    }

}
