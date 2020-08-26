package com.mnuo.forpink.encryption;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RSAUtil {
	private static Map<String, String> keyMap = new HashMap<>();  //用于封装随机产生的公钥与私钥
	
	public static void genKeyPair() throws Exception {
		try {
			KeyPairGenerator kgen = KeyPairGenerator.getInstance("RSA");
			// 初始化密钥对生成器，密钥大小为96-1024位  
			kgen.initialize(1024,new SecureRandom()); 
			// 生成一个密钥对，保存在keyPair中  
			KeyPair keyPair = kgen.generateKeyPair(); 
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥  
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥  
			//base64编码
			String priKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());
			String pubKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
			
			// 将公钥和私钥保存到Map
			keyMap.put("privateKey", priKey);  //表示公钥
			keyMap.put("publicKey", pubKey);  //表示私钥
			log.info("privateKey: " + priKey);
			log.info("publicKey: " + pubKey);
		} catch (Exception e) {
			log.error("生成公钥和私钥失败.", e);
			throw new Exception("RSAEncrypt");
		}
	}
	/**
	 * RSA公钥加密 
	 * @param content
	 * @param publicKey
	 * @return
	 */
	public static String encrypt(String content, String publicKey) {
		//base64编码的公钥
		byte[] decoded = Base64.getDecoder().decode(publicKey);
		try {
			RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
			//RSA加密
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			byte[] encryptResult = cipher.doFinal(content.getBytes("UTF-8"));
			String result = Base64.getEncoder().encodeToString(encryptResult);
			
			return result;
		} catch (Exception e) {
			log.error("RSA加密时出现异常：【content："+content+";publicKey："+publicKey+"】",e);
		}
		return null;
	}

	/**
	 * RSA私钥解密
	 * @param content
	 * @param privateKey
	 * @return
	 */
	public static String decrypt(String content, String privateKey) {
		//64位解码加密后的字符串
		try {
			byte[] decryptContent = Base64.getDecoder().decode(content.getBytes("UTF-8"));
			//base64编码的私钥
			byte[] decoded =  Base64.getDecoder().decode(privateKey);  
			RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));  
			//RSA解密
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, priKey);
			byte[] decryptResult = cipher.doFinal(decryptContent);
			return new String(decryptResult);
		} catch (Exception e) {
			log.error("RSA解密时出现异常：【content："+content+";privateKey："+privateKey+"】",e);
		}
		return null;
	}
	public static void main(String[] args) {
		try {
			//生成公钥和私钥
			genKeyPair();
			System.out.println("随机生成的公钥为:" + keyMap.get("publicKey"));
			System.out.println("随机生成的私钥为:" + keyMap.get("privateKey"));
		} catch (Exception e) {
			log.error("生成公钥和私钥异常: ", e);
		}
		
		//加密字符串
		String message = "这里是加密字符串TEST123321123";
		
		String result = encrypt(message, keyMap.get("publicKey"));
		System.out.println("加密后: " + result);
		
		String decryResult = decrypt(result, keyMap.get("privateKey"));
		System.out.println("解密后: " + decryResult);
		
	}
}
