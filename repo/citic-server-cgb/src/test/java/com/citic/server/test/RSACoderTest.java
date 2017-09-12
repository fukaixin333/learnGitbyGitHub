package com.citic.server.test;

import org.junit.Assert;
import org.junit.Test;

import com.citic.server.crypto.RSACoder;
import com.citic.server.runtime.HexCoder;
import com.citic.server.runtime.StandardCharsets;
import com.citic.server.runtime.Utility;
import com.citic.server.utils.CommonUtils;

public class RSACoderTest extends Assert {
	
	@Test
	public void testToken() throws Exception {
		String str = "中华人民共和国";
		
		byte[] byts = CommonUtils.readBinaryFile("E:\\11\\private.txt");
		byte[] privateKey = Utility.decodeMIMEBase64(byts);
		System.out.println(HexCoder.encode(privateKey, false));
		
		byte[] encryptData = RSACoder.encryptByPrivateKey(str.getBytes(StandardCharsets.UTF_8), privateKey);
		String hexData = HexCoder.encodeToString(encryptData, false);
		
		System.out.println("加密后的数据");
		System.out.println(hexData);
		
		byts = CommonUtils.readBinaryFile("E:\\11\\public.txt");
		byte[] publicKey = Utility.decodeMIMEBase64(byts);
		
		encryptData = HexCoder.decode(hexData);
		String nStr = new String(RSACoder.decryptByPublicKey(encryptData, publicKey), StandardCharsets.UTF_8);
		System.out.println("解密后的数据");
		System.out.println(nStr);
		
		assertEquals(str, nStr);
	}
}
