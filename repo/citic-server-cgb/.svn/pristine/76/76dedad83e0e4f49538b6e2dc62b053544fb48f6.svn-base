package com.citic.server.test;

import org.junit.Assert;
import org.junit.Test;

import com.citic.server.crypto.CertificateCoder;
import com.citic.server.crypto.DESCoder;
import com.citic.server.junit.BaseJunit4Test;
import com.citic.server.runtime.HexCoder;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.ShpsbKeys;
import com.citic.server.runtime.StandardCharsets;
import com.citic.server.runtime.Utility;
import com.citic.server.utils.CommonUtils;

/**
 * JUnit测试 com.citic.server.crypto 包中的各个组件
 * 
 * @author Liu Xuanfei
 * @date 2016年9月9日 下午4:04:13
 */
public class CryptoTest extends Assert {
	
	@Test
	public void descoder() throws Exception {
		byte[] byts = DESCoder.decryptHex("B23C7EC2D7192357EF6F1D7F0F809F59", DESCoder.definiteKey());
		System.out.println(new String(byts));
//		String cryptoKey = ServerEnvironment.getStringValue(ShpsbKeys.CRYPTO_KEY); //
//		
//		byte[] xmlData = CommonUtils.readBinaryFile("E:\\grzhxxfk2016111124003.xml");
//		byte[] mData = DESCoder.decrypt(HexCoder.decode(xmlData), cryptoKey.getBytes(StandardCharsets.UTF_8));
//		CommonUtils.writeBinaryFile(mData, "E:\\", "1.xml");
	}
	
	public void certificate1() throws Exception {
		System.err.println("公钥加密——私钥解密");
		
		String keyStorePath = "D:\\cert\\GAJZ.pfx"; // 密钥库路径
		String alias = "GAJZ"; // 别名
		String type = "PKCS12"; // 密钥库类型
		String password = "GAJZ!@#"; // 密钥库密码
		String certificatePath = "D:\\cert\\GAJZ.cer"; // 数字证书路径
		
		String inputStr = "数字证书";
		byte[] data = inputStr.getBytes();
		byte[] encrypt = CertificateCoder.encryptByPublicKey(data, certificatePath);
		byte[] decrypt = CertificateCoder.decryptByPrivateKey(encrypt, keyStorePath, alias, type, password);
		String outerStr = new String(decrypt);
		System.out.println("加密前：" + inputStr);
		System.out.println("解密后：" + outerStr);
		
		assertArrayEquals(data, decrypt);
	}
	
	public void certificate2() throws Exception {
		System.err.println("私钥加密——公钥解密");
		
		String keyStorePath = "D:\\cert\\GAJZ.pfx"; // 密钥库路径
		String alias = "GAJZ"; // 别名
		String type = "PKCS12"; // 密钥库类型
		String password = "GAJZ!@#"; // 密钥库密码
		String certificatePath = "D:\\cert\\GAJZ.cer"; // 数字证书路径
		
		String inputStr = "数字证书";
		System.out.println("加密前（明文）：" + inputStr);
		byte[] data = inputStr.getBytes();
		System.out.println("加密前（十六进制）：" + HexCoder.encodeToString(data, false));
		byte[] encrypt = CertificateCoder.encryptByPrivateKey(data, keyStorePath, alias, type, password);
		System.out.println("加密后（十六进制）：" + HexCoder.encodeToString(encrypt, false));
		byte[] decrypt = CertificateCoder.decryptByPublicKey(encrypt, certificatePath);
		String outerStr = new String(decrypt);
		System.out.println("解密后（明文）：" + outerStr);
		
		assertEquals(inputStr, outerStr);
	}
	
	public void certificate3() throws Exception {
		System.err.println("私钥签名——公钥验证");
		
		String keyStorePath = "D:\\cert\\GAJZ.pfx"; // 密钥库路径
		String alias = "GAJZ"; // 别名
		String type = "PKCS12"; // 密钥库类型
		String password = "GAJZ!@#"; // 密钥库密码
		String certificatePath = "D:\\cert\\GAJZ.cer"; // 数字证书路径
		
		String inputStr = "数字签名";
		byte[] data = inputStr.getBytes();
		byte[] sign = CertificateCoder.sign(data, keyStorePath, alias, type, password);
		System.out.println("签名值：" + HexCoder.encodeToString(sign, false));
		boolean status = CertificateCoder.verify(data, sign, certificatePath);
		System.out.println("状态：" + status);
		assertTrue(status);
		
		status = CertificateCoder.verify("数字签名1".getBytes(), sign, certificatePath);
		assertFalse(status);
	}
	
	public static void main(String[] args) {
		String code = "AG20170110130119200063";
		String base64 = Utility.encodeMIMEBase64(code, StandardCharsets.UTF_8);
		System.out.println(base64);
		
		base64 = "c3VjY2Vzcw==";
		code = Utility.decodeUTF8MIMEBase64(base64);
		System.out.println(code);
	}
}