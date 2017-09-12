package com.citic.server.test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.citic.server.inner.service.AnswerCodeService;
import com.citic.server.junit.BaseJunit4Test;
import com.citic.server.runtime.ServerEnvironment;

public class AnswerCodeServiceTest extends BaseJunit4Test {
	
	@Autowired
	@Qualifier("answerCodeService")
	private AnswerCodeService service;
	
	@Test
	public void testGetTCode() {
		String tcode = service.getTCode("IG0000", ServerEnvironment.TASK_TYPE_DX);
		Assert.assertNotNull(tcode);
		Assert.assertEquals(tcode, "0000");
	}
}
