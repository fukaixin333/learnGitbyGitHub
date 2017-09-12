package com.citic.server.crypto;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import com.citic.server.runtime.HexCoder;

/**
 * RSA安全编码组件
 * <p>
 * Java 6 只提供 MD2withRSA、MD5withRSA 和 SHA1withRSA 共三种数字签名算法。
 * <p>
 * RSA密钥长度默认1024位，密钥长度必须是64的倍数，范围在512 ~ 65536位之间。
 * 
 * @author Liu Xuanfei
 * @date 2016年9月7日 下午3:47:14
 */
public final class RSACoder {
	
	/** RSA非对称加密算法 */
	private static final String KEY_ALGORITHM = "RSA";
	
	/** 数字签名/签证算法 */
	private static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	
	/** 加密/解密算法 / 工作模式 / 填充方式 */
	private static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
	
	/** RSA最小密钥长度（512位） */
	private static final int MIN_KEY_SIZE = 512;
	
	/**
	 * 初始化密钥，使用最小密钥长度
	 * 
	 * @return 密钥对
	 * @throws Exception
	 */
	public static KeyPair initKey() throws Exception {
		return initKey(MIN_KEY_SIZE);
	}
	
	/**
	 * 初始化密钥
	 * 
	 * @param keySize 密钥长度。必须是64的倍数，范围在512 ~ 65536位之间
	 * @return 密钥对
	 * @throws Exception
	 */
	public static KeyPair initKey(int keySize) throws Exception {
		// 创建密钥对生成器
		KeyPairGenerator generator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		// 初始化密钥对生成器
		generator.initialize(keySize);
		// 生成密钥对
		return generator.generateKeyPair();
	}
	
	/**
	 * 公钥加密
	 * 
	 * @param data 待加密数据
	 * @param encodedKey 公钥字节数组（编码密钥）
	 * @return 加密后数据
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, byte[] encodedKey) throws Exception {
		// 创建指定转换的cipher对象
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		// 初始化cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, toPublicKey(encodedKey));
		// 执行加密操作
		return cipher.doFinal(data);
	}
	
	/**
	 * 私钥加密
	 * 
	 * @param data 待加密数据
	 * @param encodedKey 私钥字节数组（编码密钥）
	 * @return 加密后数据
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, byte[] encodedKey) throws Exception {
		// 创建指定转换的cipher对象
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		// 初始化cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, toPrivateKey(encodedKey));
		// 执行加密操作
		return cipher.doFinal(data);
	}
	
	/**
	 * 公钥解密
	 * 
	 * @param data 待解密数据
	 * @param encodedKey 公钥字节数组（编码密钥）
	 * @return 解密后数据
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, byte[] encodedKey) throws Exception {
		// 创建指定转换的cipher对象
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		// 初始化cipher对象
		cipher.init(Cipher.DECRYPT_MODE, toPublicKey(encodedKey));
		// 执行解密操作
		return cipher.doFinal(data);
	}
	
	/**
	 * 私钥解密
	 * 
	 * @param data 待解密数据
	 * @param encodedKey 私钥字节数组（编码密钥）
	 * @return 解密后数据
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, byte[] encodedKey) throws Exception {
		// 创建指定转换的cipher对象
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		// 初始化cipher对象
		cipher.init(Cipher.DECRYPT_MODE, toPrivateKey(encodedKey));
		// 执行解密操作
		return cipher.doFinal(data);
	}
	
	/**
	 * 签名（MD5withRSA）
	 * 
	 * @param data 待签名数据
	 * @param privateKey 私钥
	 * @return 数字签名
	 * @throws Exception
	 */
	public static byte[] sign(byte[] data, byte[] privateKey) throws Exception {
		// 创建Signature对象
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		// 初始化用于签名的Signature对象
		signature.initSign(toPrivateKey(privateKey));
		// 更新待签名数据
		signature.update(data);
		// 完成签名操作
		return signature.sign();
	}
	
	/**
	 * 签名验证（MD5withRSA）
	 * 
	 * @param data 待校验数据
	 * @param publicKey 公钥
	 * @param sign 数字签名
	 * @return 校验成功返回<code>true</code>，失败返回<code>false</code>
	 * @throws Exception
	 */
	public static boolean verify(byte[] data, byte[] publicKey, byte[] sign) throws Exception {
		// 创建Signature对象
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		// 初始化用于校验的Signature对象
		signature.initVerify(toPublicKey(publicKey));
		// 更新待校验数据
		signature.update(data);
		// 验证传入的签名，并返回验证结果
		return signature.verify(sign);
	}
	
	/**
	 * 还原公钥
	 * <p>
	 * 使用X.509标准作为密钥规范管理的编码格式
	 * 
	 * @param encodedKey 公钥字节数组（编码密钥）
	 * @return 公钥
	 * @throws Exception
	 */
	public static PublicKey toPublicKey(byte[] encodedKey) throws Exception {
		// 根据公钥字节数组（编码密钥）创建密钥规范（密钥材料）
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(encodedKey);
		// 生成指定算法的密钥工厂
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 根据密钥规范（密钥材料）生成公钥对象
		return keyFactory.generatePublic(x509KeySpec);
	}
	
	/**
	 * 还原私钥
	 * <p>
	 * 使用PKCS#8标准作为密钥规范管理的编码格式
	 * 
	 * @param encodedKey 私钥字节数组（编码密钥）
	 * @return 私钥
	 * @throws Exception
	 */
	public static PrivateKey toPrivateKey(byte[] encodedKey) throws Exception {
		// 根据私钥字节数组（编码密钥）创建密钥规范（密钥材料）
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(encodedKey);
		// 生成指定算法的密钥工厂
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 根据密钥规范（密钥材料）生成私钥对象
		return keyFactory.generatePrivate(pkcs8KeySpec);
	}
	
	/**
	 * 获得公钥字节数组（编码密钥）
	 * 
	 * @param keyPair 密钥对
	 * @return 公钥
	 * @throws Exception
	 */
	public static byte[] getPublicKey(KeyPair keyPair) throws Exception {
		return keyPair.getPublic().getEncoded();
	}
	
	/**
	 * 获得私钥字节数组（编码密钥）
	 * 
	 * @param keyPair 密钥对
	 * @return 私钥
	 * @throws Exception
	 */
	public static byte[] getPrivateKey(KeyPair keyPair) throws Exception {
		return keyPair.getPrivate().getEncoded();
	}
	
	/**
	 * 获得十六进制编码格式的公钥
	 * <p>
	 * 实际使用过程中公钥以十六进制或Base64编码格式存储，并传递给另一方
	 * 
	 * @param keyPair 密钥对
	 * @return 公钥（十六进制编码格式）
	 * @throws Exception
	 */
	public static String getPublicKeyHex(KeyPair keyPair, boolean toLowerCase) throws Exception {
		return HexCoder.encodeToString(getPublicKey(keyPair), toLowerCase);
	}
	
	/**
	 * 获得十六进制编码格式的私钥
	 * <p>
	 * 实际使用过程中公钥以十六进制或Base64编码格式存储
	 * 
	 * @param keyPair 密钥对
	 * @return 私钥（十六进制编码格式）
	 * @throws Exception
	 */
	public static String getPrivateKeyHex(KeyPair keyPair, boolean toLowerCase) throws Exception {
		return HexCoder.encodeToString(getPrivateKey(keyPair), toLowerCase);
	}
	
	/**
	 * 还原十六进制密钥
	 * 
	 * @param hexKey 十六进制编码格式的密钥
	 * @return 密钥字节数组（编码密钥）
	 * @throws Exception
	 */
	public static byte[] toKey(String hexKey) throws Exception {
		return HexCoder.decode(hexKey.toCharArray());
	}
}
