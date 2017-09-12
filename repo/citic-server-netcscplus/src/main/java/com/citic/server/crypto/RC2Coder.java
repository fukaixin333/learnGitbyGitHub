package com.citic.server.crypto;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * RC2安全编码组件
 * <p>
 * RC2密钥长度默认128位，密钥长度必须是8的倍数，范围在40 ~ 1024位之间。
 * <p>
 * RC2/ECB/NoPadding
 * 
 * @author Liu Xuanfei
 * @date 2016年9月13日 下午8:23:34
 */
public final class RC2Coder {
	
	/** 密钥算法 */
	private static final String ALGORITHM = "RC2";
	
	/** 默认密钥长度（128位） */
	private static final int DEF_KEY_SIZE = 128;
	
	/**
	 * 生成密钥
	 * 
	 * @return 二进制密钥
	 * @throws Exception
	 */
	public static byte[] initKey() throws Exception {
		// 创建密钥对生成器
		KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
		// 初始化密钥生成器
		generator.init(DEF_KEY_SIZE); // generator.init(new SecureRandom())
		// 生成秘密（对称）密钥
		SecretKey secretKey = generator.generateKey();
		// 获取密钥的二进制编码形式
		return secretKey.getEncoded();
	}
	
	/**
	 * 转换（还原）密钥
	 * 
	 * @param key 二进制密钥
	 * @return 密钥
	 * @throws Exception
	 */
	public static Key toKey(byte[] key) throws Exception {
		// 生成秘密（对称）密钥
		return new SecretKeySpec(key, ALGORITHM); // 无须考虑密钥规范（材料）实现类;
	}
	
	/**
	 * 加密
	 * 
	 * @param data 待加密数据
	 * @param key 二进制密钥
	 * @return 加密后数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		// 创建指定转换的cipher对象
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		// 初始化cipher对象，设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, toKey(key));
		// 执行加密操作
		return cipher.doFinal(data);
	}
	
	/**
	 * 解密
	 * 
	 * @param data 待解密数据
	 * @param key 二进制密钥
	 * @return 解密后数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		// 创建指定转换的cipher对象
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		// 初始化cipher对象，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, toKey(key));
		// 执行解密操作
		return cipher.doFinal(data);
	}
}
