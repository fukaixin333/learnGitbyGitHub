package com.citic.server.crypto;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 * DESede安全编码组件
 * <p>
 * TripleDES 和 3DES 指的都是 DESede 算法。
 * <p>
 * Java 6 提供的 DESede 算法实现支持密钥长度112位和168位，通过 Bouncy Castle 可支持密钥长度128位和192位。
 * <p>
 * Java 6 提供 NoPadding、PKCS5Padding 和 ISO10126Padding 共三种填充方式，通过 Bouncy Castle 组件可支持
 * PKCS7Padding、ISO10126d2Padding、X932Padding、ISO7816d4Padding 和 ZeroBytePadding 共5种填充方式。
 * 
 * @author Liu Xuanfei
 * @date 2016年9月6日 下午4:44:06
 */
public final class DESedeCoder {
	
	/** 密钥算法 */
	private static final String KEY_ALGORITHM = "DESede";
	
	/** 加密/解密算法 / 工作模式 / 填充方式 */
	private static final String CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";
	
	/**
	 * 生成密钥
	 * 
	 * @return 二进制密钥
	 * @throws Exception
	 */
	public static byte[] initKey() throws Exception {
		// 创建密钥对生成器
		KeyGenerator generator = KeyGenerator.getInstance(KEY_ALGORITHM);
		// 初始化密钥生成器
		generator.init(168); // 112
		// 生成秘密（对称）密钥
		SecretKey secretKey = generator.generateKey();
		// 获取密钥的二进制编码形式
		return secretKey.getEncoded();
	}
	
	/***
	 * 转换密钥
	 * 
	 * @param key 二进制密钥
	 * @return 密钥
	 * @throws Exception
	 */
	public static Key toKey(byte[] key) throws Exception {
		// 根据密钥字节数组（编码密钥）创建密钥规范（密钥材料）
		DESedeKeySpec keySpec = new DESedeKeySpec(key);
		// 生成指定算法的密钥工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
		// 根据密钥规范生成秘密（对称）密钥
		return keyFactory.generateSecret(keySpec);
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
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
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
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		// 初始化cipher对象，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, toKey(key));
		// 执行解密操作
		return cipher.doFinal(data);
	}
	
	// ==========================================================================================
	//           生成固定密钥
	// ==========================================================================================
	
	/**
	 * 获取应用程序的固定密钥
	 * 
	 * @return 固定密钥
	 */
	public static byte[] definiteKey() {
		byte[] raw = new byte[] {0x7E, 0x26, 0x7C, 0x3C, 0x5E, 0x3E, 0x21}; // 密钥"引子"，固定的 7 字节核心密钥
		return DESKeyFactory.generateDESedeKeyByAddingParity(raw, raw, raw); // 对密钥进行加工转换为64-bit密钥
	}
}
