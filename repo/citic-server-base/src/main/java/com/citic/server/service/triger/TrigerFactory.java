package com.citic.server.service.triger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC00_triger_sub;
/**
 * @author hubq
 *
 */
public class TrigerFactory {
	
	private static final Logger logger = LoggerFactory.getLogger(TrigerFactory.class);

    /**
     * 
     * @return
     */
    public BaseTrigerCondition getInstance(ApplicationContext _ac,String _datatime,MC00_triger_sub _mC00_triger_sub,ArrayList _currTksList)
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException, SecurityException, NoSuchMethodException,
            IllegalArgumentException, InvocationTargetException {
    	
        //通过文件名的方式获取对应的实例
        String pkg = BaseTrigerCondition.class.getPackage().getName();
        
        String trigercondid = _mC00_triger_sub.getTrigercondid();
        
        String className = pkg + "." + "TrigerCondition_"+trigercondid;
        
        BaseTrigerCondition triger   = null;

        Class taskClass;
        try {
            taskClass = Class.forName(className);
            if (taskClass != null) {
                //初始化构造函数
                Constructor cc = taskClass.getConstructor(new Class[] { ApplicationContext.class,String.class,MC00_triger_sub.class,ArrayList.class });
                
                triger = (BaseTrigerCondition) cc.newInstance(new Object[] {_ac,_datatime, _mC00_triger_sub,_currTksList });
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
        
        return triger;
    }

}
