package com.citic.server.service.cacheload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.service.base.Base;

/**
 * 
 * @author hubq
 * @version 1.0
 */

public abstract class BaseCache extends Base{
	
	private static final Logger logger = LoggerFactory.getLogger(BaseCache.class);

	private String cachename = "";
	
	private ApplicationContext ac;
	
    public BaseCache(ApplicationContext _ac,String cachename) {
    	this.ac = _ac;
    	this.cachename = cachename;
    }
    
    public abstract Object getCacheByName() throws Exception;
    
    public String getCachename(){
    	return this.cachename;
    }
    
    public ApplicationContext getAc(){
    	return this.ac;
    }
    
}
