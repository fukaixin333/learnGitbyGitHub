package com.citic.server.crypto;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.crypto.Cipher;

/**
 * 数字证书组件
 * <p>
 * Java 6 目前仅支持X.509类型的数字证书。
 * <p>
 * Java 6 提供了完善的数字证书管理实现，可以仅通过操作密钥库和数字证书即可完成相应的加密/解密和签名/验证操作。
 * 
 * @author Liu Xuanfei
 * @date 2016年9月8日 下午8:01:43
 */
public final class CertificateCoder {
	
	/** 证书类型：X509 */
	public static final String CERT_TYPE = "X.509";
	
	/** 数字签名/签证算法 - SHA1withRSA */
	public static final String SIGNATURE_ALGORITHM_0 = "SHA1withRSA";
	
	/** 数字签名/签证算法 - MD5withRSA */
	public static final String SIGNATURE_ALGORITHM_1 = "MD5withRSA";
	
	/**
	 * 私钥加密
	 * 
	 * @param data 待加密数据
	 * @param keyStorePath 密钥库路径
	 * @param alias 别名
	 * @param type 密钥库类型
	 * @param password 密钥库密码
	 * @return 加密后数据
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String keyStorePath, String alias, String type, String password) throws Exception {
		// 由密钥库获得私钥
		PrivateKey privateKey = getPrivateKeyByKeyStore(keyStorePath, alias, type, password);
		// 使用私钥加密数据
		Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}
	
	/**
	 * 私钥解密
	 * 
	 * @param data 待解密数据
	 * @param keyStorePath 密钥库路径
	 * @param alias 别名
	 * @param type 密钥库类型
	 * @param password 密钥库密码
	 * @return 解密后数据
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, String keyStorePath, String alias, String type, String password) throws Exception {
		// 由密钥库获得私钥
		PrivateKey privateKey = getPrivateKeyByKeyStore(keyStorePath, alias, type, password);
		// 使用私钥加密数据
		Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm()); // "RSA/ECB/PKCS1Padding"
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}
	
	/**
	 * 公钥加密
	 * 
	 * @param data 待加密数据
	 * @param certificatePath 数字证书路径
	 * @return 加密后数据
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String certificatePath) throws Exception {
		// 由数字证书获得公钥
		PublicKey publicKey = getPublicKeyByCertificate(certificatePath);
		// 使用公钥加密数据
		Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}
	
	/**
	 * 公钥解密
	 * 
	 * @param data 待解密数据
	 * @param certificatePath 数字证书路径
	 * @return 解密后数据
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, String certificatePath) throws Exception {
		// 由数字证书获得公钥
		PublicKey publicKey = getPublicKeyByCertificate(certificatePath);
		// 使用公钥解密数据
		Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}
	
	/**
	 * 数字签名
	 * 
	 * @param data 待签名数据
	 * @param keyStorePath 密钥库路径
	 * @param alias 别名
	 * @param type 密钥库类型
	 * @param password 密钥库密码
	 * @return
	 * @throws Exception
	 */
	public static byte[] sign(byte[] data, String keyStorePath, String alias, String type, String password) throws Exception {
		// 加载密钥库
		KeyStore keyStore = loadKeyStore(keyStorePath, type, password);
		// 由密钥库获得私钥
		PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, password.toCharArray());
		// 由密钥库获得数字证书
		X509Certificate x509Certificate = (X509Certificate) keyStore.getCertificate(alias);
		// 构建数字签名对象，使用数字证书获得对应的签名算法
		Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());
		// 使用私钥初始化签名
		signature.initSign(privateKey);
		// 更新待签名数据
		signature.update(data);
		// 完成签名操作
		return signature.sign();
	}
	
	public static byte[] sign(byte[] data, String keyStorePath, String alias, String type, String password, String signAlgorithm) throws Exception {
		// 加载密钥库
		KeyStore keyStore = loadKeyStore(keyStorePath, type, password);
		// 由密钥库获得私钥
		PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, password.toCharArray());
		// 构建数字签名对象，使用指定的签名算法
		Signature signature = Signature.getInstance(signAlgorithm);
		// 使用私钥初始化签名
		signature.initSign(privateKey);
		// 更新待签名数据
		signature.update(data);
		// 完成签名操作
		return signature.sign();
	}
	
	/**
	 * 签名验证
	 * <p>
	 * 使用数字证书对应的签名算法构建数字签名对象。
	 * 
	 * @param data 待验证数据
	 * @param sign 数字签名
	 * @param certificatePath 数字证书地址
	 * @return 校验成功返回<code>true</code>，失败返回<code>false</code>
	 * @throws Exception
	 */
	public static boolean verify(byte[] data, byte[] sign, String certificatePath) throws Exception {
		// 加载数字证书 
		X509Certificate x509Certificate = (X509Certificate) loadCertificate(certificatePath);
		// 构建数字签名对象，使用数字证书获得对应的签名算法
		Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());
		// 使用数字证书初始化签名（内部实际上使用数字证书的公钥）
		signature.initVerify(x509Certificate);
		// 更新待验证数据
		signature.update(data);
		// 完成签名操作
		return signature.verify(sign);
	}
	
	/**
	 * 签名验证
	 * 
	 * @param data 待验证数据
	 * @param sign 数字签名
	 * @param certificatePath 数字证书地址
	 * @param signAlgorithm 数字签名算法名
	 * @return 校验成功返回<code>true</code>，失败返回<code>false</code>
	 * @throws Exception
	 */
	public static boolean verify(byte[] data, byte[] sign, String certificatePath, String signAlgorithm) throws Exception {
		// 加载数字证书 
		X509Certificate x509Certificate = (X509Certificate) loadCertificate(certificatePath);
		// 构建数字签名对象，使用指定的签名算法
		Signature signature = Signature.getInstance(signAlgorithm);
		// 使用数字证书初始化签名（内部实际上使用数字证书的公钥）
		signature.initVerify(x509Certificate);
		// 更新待验证数据
		signature.update(data);
		// 完成签名操作
		return signature.verify(sign);
	}
	
	/**
	 * 加载数字证书
	 * <p>
	 * 通过数字证书可以直接获取公钥，实现“公钥加密”、“公钥解密”两项操作。
	 * <p>
	 * 使用数字证书进行验证签名操作时，需要将其强转为 X509Certificate 实例。
	 * 
	 * @param certificatePath 数字证书路径
	 * @return 数字证书
	 * @throws Exception
	 */
	private static Certificate loadCertificate(String certificatePath) throws Exception {
		Certificate certificate = null;
		FileInputStream in = null;
		try {
			// 创建数字证书工厂
			CertificateFactory certificateFactory = CertificateFactory.getInstance(CERT_TYPE);
			// 初始化数字证书文件流
			in = new FileInputStream(certificatePath);
			// 生成数字证书
			certificate = certificateFactory.generateCertificate(in);
		} finally {
			// 关闭数字证书文件流
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
		return certificate;
	}
	
	/**
	 * 由数字证书获得公钥
	 * 
	 * @param certificatePath 数字证书路径
	 * @return 公钥
	 * @throws Exception
	 */
	public static PublicKey getPublicKeyByCertificate(String certificatePath) throws Exception {
		// 加载数字证书
		Certificate certificate = loadCertificate(certificatePath);
		// 直接获取公钥
		return certificate.getPublicKey();
	}
	
	/**
	 * 加载密钥库
	 * <p>
	 * 加载密钥库后，即可通过相应的方法获得私钥和数字证书。
	 * <p>
	 * 使用私钥实现“私钥加密”和“私钥解密”两项操作。
	 * <p>
	 * 使用数字证书构建数字签名操作时，需要将其强转为 X509Certificate 实例。
	 * 
	 * @param keyStorePath 密钥库路径
	 * @param type 密钥库类型
	 * @param password 密钥库密码
	 * @return 密钥库
	 * @throws Exception
	 */
	private static KeyStore loadKeyStore(String keyStorePath, String type, String password) throws Exception {
		KeyStore keyStore = null;
		FileInputStream in = null;
		try {
			// 创建密钥库
			keyStore = KeyStore.getInstance(type);
			// 初始化密钥库文件流
			in = new FileInputStream(keyStorePath);
			// 加载密钥库
			keyStore.load(in, password.toCharArray());
		} finally {
			// 关闭密钥库文件流
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
		return keyStore;
	}
	
	/**
	 * 由密钥库获得私钥
	 * 
	 * @param keyStorePath 密钥库路径
	 * @param alias 别名
	 * @param type 密钥库类型
	 * @param password 密钥库密钥
	 * @return 私钥
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKeyByKeyStore(String keyStorePath, String alias, String type, String password) throws Exception {
		// 加载密钥库
		KeyStore keyStore = loadKeyStore(keyStorePath, type, password);
		// 获取私钥
		return (PrivateKey) keyStore.getKey(alias, password.toCharArray());
	}
	
	/**
	 * 由密钥库获得数字证书
	 * 
	 * @param keyStorePath 密钥库路径
	 * @param alias 别名
	 * @param type 密钥库类型
	 * @param password 密钥库密码
	 * @return 数字证书
	 * @throws Exception
	 */
	public static Certificate getCertificate(String keyStorePath, String alias, String type, String password) throws Exception {
		// 加载密钥库
		KeyStore keyStore = loadKeyStore(keyStorePath, type, password);
		// 获得数字证书
		return keyStore.getCertificate(alias);
	}
}
