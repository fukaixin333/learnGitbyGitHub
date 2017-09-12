package com.citic.server.crypto;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.citic.server.runtime.HexCoder;

/**
 * DES安全编码组件
 * <p>
 * Java 6 只支持56位长度的密钥，第三方组件包 Bouncy Castle 支持64位密钥。
 * 
 * @author Liu Xuanfei
 * @date 2016年9月6日 下午2:10:41
 */
public final class DESCoder {
	
	/** 密钥算法 */
	private static final String KEY_ALGORITHM = "DES";
	
	/** 加密/解密算法 / 工作模式 / PKCS5填充方式 */
	public static final String CIPHER_ALGORITHM_PKCS5 = "DES/ECB/PKCS5Padding";
	
	/** 加密/解密算法 / 工作模式 / 无填充方式 */
	public static final String CIPHER_ALGORITHM_NOPADDING = "DES/ECB/NoPadding";
	
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
		generator.init(56); // generator.init(new SecureRandom())
		// 生成秘密（对称）密钥
		SecretKey secretKey = generator.generateKey();
		// 获取密钥的二进制编码形式
		return secretKey.getEncoded();
	}
	
	/**
	 * 还原密钥
	 * 
	 * @param key 二进制密钥
	 * @return 密钥
	 * @throws Exception
	 */
	public static Key toKey(byte[] key) throws Exception {
		// 根据密钥字节数组（编码密钥）创建密钥规范（密钥材料）
		DESKeySpec keySpec = new DESKeySpec(key);
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
		return encrypt(data, key, CIPHER_ALGORITHM_PKCS5);
	}
	
	public static byte[] encrypt(byte[] data, byte[] key, String cipherAlgorithm) throws Exception {
		// 创建指定转换的cipher对象
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		// 初始化cipher对象，设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, toKey(key));
		// 执行加密操作
		return cipher.doFinal(data);
	}
	
	public static String encryptHex(byte[] data, byte[] key, boolean toLowerCase) throws Exception {
		return HexCoder.encodeToString(encrypt(data, key, CIPHER_ALGORITHM_PKCS5), toLowerCase);
	}
	
	public static String encryptHex(byte[] data, byte[] key, String cipherAlgorithm, boolean toLowerCase) throws Exception {
		return HexCoder.encodeToString(encrypt(data, key, cipherAlgorithm), toLowerCase);
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
		return decrypt(data, key, CIPHER_ALGORITHM_PKCS5);
	}
	
	public static byte[] decrypt(byte[] data, byte[] key, String cipherAlgorithm) throws Exception {
		// 创建指定转换的cipher对象
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		// 初始化cipher对象，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, toKey(key));
		// 执行解密操作
		return cipher.doFinal(data);
	}
	
	public static byte[] decryptHex(String data, byte[] key) throws Exception {
		return decrypt(HexCoder.decode(data), key, CIPHER_ALGORITHM_PKCS5);
	}
	
	public static byte[] decryptHex(String data, byte[] key, String cipherAlgorithm) throws Exception {
		return decrypt(HexCoder.decode(data), key, cipherAlgorithm);
	}
	
	// ==========================================================================================
	//           两种生成固定密钥的方式，其中一种与 JDK 的密钥转换方式相同
	// ==========================================================================================
	
	/**
	 * 获取应用程序的固定密钥
	 * 
	 * @return 固定密钥
	 */
	public static byte[] definiteKey() {
		byte[] raw = new byte[] {0x7E, 0x26, 0x7C, 0x3C, 0x5E, 0x3E, 0x21}; // 密钥"引子"，固定的 7 字节核心密钥
		return DESKeyFactory.generateDESKeyByAddingParity(raw); // 对密钥进行加工转换为64-bit密钥
	}
}
