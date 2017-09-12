package com.citic.server.junit;

import java.io.IOException;

import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.citic.server.runtime.ServerEnvironment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/root-application-context.xml")
@ActiveProfiles("DEV")
public abstract class BaseJunit4Test extends Assert {
	public BaseJunit4Test() {
		// 在加载Spring之前，必须优先加载service.xml配置文件。
		try {
			ServerEnvironment.initConfigProperties();
		} catch (JiBXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
