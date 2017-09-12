package com.citic.server.test;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.citic.server.dict.DictCoder;
import com.citic.server.inner.domain.response.V_ContractAccount;
import com.citic.server.runtime.Constants;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.utils.CommonUtils;

public class JibxBindingTest extends Assert implements Constants {
	
	@Autowired
	private DictCoder dictCoder;
	
	@Test
	public void dosth() throws JiBXException, FileNotFoundException {
//		String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><result errorMsg=\"QTIyMDE3MDYwNzExMDEwMTEwMDAxMw==\"><htjglist><jg bdhm=\"QTIyMDE3MDYwNzExMDEwMTEwMDAxMw==\" result=\"ZmFpbA==\" msg=\"yv2+3b/i0uyzow==\"/></htjglist></result>";
//		Result result = (Result) CommonUtils.unmarshallContext(Result.class, "binding_result1A", str);
//		System.out.println(result.getRealError());
//		for (Result_Jg jg : result.getRealJgList()) {
//			System.out.println(jg.toString());
//		}
		
		V_ContractAccount message = CommonUtils.unmarshallUTF8Document(V_ContractAccount.class, "E:\\123.xml");
		
		System.out.println(message);
	}
	
	public void configEncrypt() throws JiBXException, IOException {
		ServerEnvironment.initConfigProperties();
		
		ServerEnvironment.console();
	}
	
	public static void main(String[] args) {
	}
}
