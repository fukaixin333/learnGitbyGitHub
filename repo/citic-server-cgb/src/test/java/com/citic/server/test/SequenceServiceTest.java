package com.citic.server.test;

import java.io.IOException;
import java.util.concurrent.FutureTask;

import org.jibx.runtime.JiBXException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.citic.server.SpringContextHolder;
import com.citic.server.gf.mapper.ControlTaskMapper1A;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.service.time.SequenceService;

@Component
public class SequenceServiceTest {
	//	@Autowired
	//	@Qualifier("outerApplicationID")
	//	private SequenceService applicationIdService;
	//	
	//	@Autowired
	//	@Qualifier("outerTransSerialNumber")
	//	private SequenceService transSerialNumberService;
	
	@Autowired
	@Qualifier("innerTransSerialNumber")
	private SequenceService innerTransSerialNumber;
	
	@Autowired
	private ControlTaskMapper1A mapper;
	
	public static void main(String[] args) throws JiBXException, IOException {
		ServerEnvironment.initConfigProperties();
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/root-application-context.xml");
		SpringContextHolder springContextHolder = (SpringContextHolder) applicationContext.getBean("springContextHolder");
		springContextHolder.setApplicationContext(applicationContext);
		
		SequenceServiceTest test = SpringContextHolder.getBean(SequenceServiceTest.class);
		test.testGetNextDBSequenceValue();
	}
	
	public void testGetNextDBSequenceValue() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = SpringContextHolder.getBean("netcscServerThreadPoolTaskExecutor");
		
		int i = 10000;
		while (i > 0) {
			FutureTask<String> futureTask = new FutureTask<String>(new Runnable() {
				Logger logger = LoggerFactory.getLogger(Runnable.class);
				
				@Override
				public void run() {
					try {
						String nextValue = innerTransSerialNumber.next();
						logger.info("Next Value：{}", nextValue);
					} catch (Exception e) {
						logger.error("{}", e.getMessage());
					}
				}
			}, null);
			threadPoolTaskExecutor.execute(futureTask);
			i--;
		}
	}
}
