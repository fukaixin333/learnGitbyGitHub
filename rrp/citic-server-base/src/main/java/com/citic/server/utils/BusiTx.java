/**========================================================
 * Copyright (c) 2014-2015 by CITIC All rights reserved.
 * Created Date : 2015年3月12日 下午5:04:52
 * Description: 
 * 
 *=========================================================
 */
package com.citic.server.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.transaction.annotation.Transactional;

/**
 * @author gaojianxin
 *  管理库事务管理器
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Transactional("busiTransactionManager")
public @interface BusiTx {
	
}
