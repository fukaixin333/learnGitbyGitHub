package com.citic.server.runtime;

import java.io.InputStreamReader;
import java.util.Properties;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * 广东检察院加密，解密工具类
 * 使用方法
 * 参数1:加密或者解密用的byte数组key
 * 参数2:加密或者解密的byte数组数据
 * 原来的加密byte[] encode=DES3.encryptMode(DES3.keyBytes,sendXml.getBytes());
 * 改后的加密byte[] encode=EncryptUtils.encryptMode(EncryptUtils.getKeyByte(),sendXml.getBytes());
 * 原来的解密byte[] xmlData =DES3.decryptMode(DES3.keyBytes, src);
 * 改后的解密byte[] xmlData =EncryptUtils.decryptMode(EncryptUtils.getKeyByte(), src);
 * 
 * @author liuxuanfei
 * @date 2017年6月3日 下午2:32:48
 */
public class EncryptUtils {
	 //计算24位长的密码byte值,首先对原始密钥做MD5算hash值，再用前8位数据对应补全后8位
    public static byte[] GetKeyBytes(String strKey) throws Exception {
        if (null == strKey || strKey.length() < 1)
            throw new Exception("key is null or empty!");
        java.security.MessageDigest alg = java.security.MessageDigest.getInstance("MD5");
        alg.update(strKey.getBytes());
        byte[] bkey = alg.digest();
       // System.out.println("md5key.length=" + bkey.length);
       // System.out.println("md5key=" + byte2hex(bkey));
        int start = bkey.length;
        byte[] bkey24 = new byte[24];
        for (int i = 0; i < start; i++) {
            bkey24[i] = bkey[i];
        }
        for (int i = start; i < 24; i++) {//为了与.net16位key兼容
            bkey24[i] = bkey[i - start];
        }
       // System.out.println("byte24key.length=" + bkey24.length);
        //System.out.println("byte24key=" + byte2hex(bkey24));
        return bkey24;
    }
    private static final String Algorithm = "DESede"; //定义 加密算法,可用 DES,DESede,Blowfish     

    
    //keybyte为加密密钥，长度为24字节
    //src为被加密的数据缓冲区（源）  
    public static byte[] encryptMode(byte[] keybyte, byte[] src) {
        try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm); //加密 
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);
       } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }
    //keybyte为加密密钥，长度为24字节  
    //src为加密后的缓冲区
    public static byte[] decryptMode(byte[] keybyte, byte[] src) {
        try { //生成密钥   
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
            //解密     
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
        	e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }
    //转换成十六进制字符串  
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            if (n < b.length - 1)
                hs = hs + ":";
        }
        return hs.toUpperCase();
    }
    public static byte[] getKeyByte(){
    	InputStreamReader in=null;
		Properties props=new Properties();
		byte[] keyByte=null;
		try{
			//in=x.getClass().getResourceAsStream("/executionCheck/util/"+langProp);
			in=new InputStreamReader(EncryptUtils.class.getResourceAsStream("/key.properties"),"utf-8");
			props.load(in);
			//props.load(in);
			if(!props.isEmpty()){
				//System.out.println("key:"+props.getProperty("key"));
				keyByte=EncryptUtils.GetKeyBytes(props.getProperty("key"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				in.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return keyByte;
    }
}
