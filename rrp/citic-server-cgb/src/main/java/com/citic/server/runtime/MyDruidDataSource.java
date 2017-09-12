package com.citic.server.runtime;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;
import com.citic.server.crypto.DESedeCoder;

/**
 * 数据源驱动管理类 <br>
 * 继承DriverManagerDataSource处理配置文件中密码加密情况 <br>
 * 使用说明：在数据源配置文件中（eg：datasource-config.xml）中替代DriverManagerDataSource类 <br>
 * 配置文件中的密码是DES加密后16进制［大写］转换
 * 
 * @author yinxiong
 */
public class MyDruidDataSource extends DruidDataSource {
	private static final long serialVersionUID = 1019529792286808757L;
	
	private static final Logger logger = LoggerFactory.getLogger(MyDruidDataSource.class);
	
	public MyDruidDataSource() {
		// Do nothing
	}
	
	@Override
	public void setPassword(String password) {
		try {
			byte[] byts = DESedeCoder.decrypt(HexCoder.decode(password), DESedeCoder.definiteKey());
			password = new String(byts, "UTF-8"); // 16进制逆转后再DES解密
		} catch (Exception e) {
			logger.error("Exception: 数据源连接密码解密异常", e);
		}
		super.setPassword(password);
	}
	
	private static void encrypt(String password) throws Exception {
		byte[] byts = DESedeCoder.encrypt(password.getBytes(StandardCharsets.UTF_8), DESedeCoder.definiteKey());
		System.out.println(HexCoder.encode(byts));
	}
	
	private static void decrypt(String password) throws Exception {
		byte[] key = DESedeCoder.definiteKey();
		byte[] byts = DESedeCoder.decrypt(HexCoder.decode(password), key);
		System.out.println(new String(byts, StandardCharsets.UTF_8));
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException, Exception {
		encrypt("Kfptafp123");
		decrypt("A12E3307AF2A6E9B272E654320A9695F");
	}
}
