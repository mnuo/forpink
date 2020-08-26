package com.mnuo.forpink.encryption;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
/**
 *  DSA加密
 * @author administrator
 */
@Slf4j
public class DSAUtil {
	private static Map<String, String> keyMap = new HashMap<>();  //用于封装随机产生的公钥与私钥
	
	public static void genKeyPair() throws Exception{
		//创建秘钥生成器
		try {
			KeyPairGenerator kgen = KeyPairGenerator.getInstance("DSA");
			kgen.initialize(1024);
			KeyPair keypair = kgen.generateKeyPair();//生成秘钥对
			DSAPublicKey publicKey = (DSAPublicKey)keypair.getPublic();
			DSAPrivateKey privateKey = (DSAPrivateKey)keypair.getPrivate();
			
			//base64编码
			String priKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());
			String pubKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
			
			// 将公钥和私钥保存到Map
			keyMap.put("privateKey", priKey);  //表示公钥
			keyMap.put("publicKey", pubKey);  //表示私钥
			log.info("privateKey: " + priKey);
			log.info("publicKey: " + pubKey);
		} catch (NoSuchAlgorithmException e) {
			log.error("生成公钥和私钥失败.", e);
			throw new Exception("RSAEncrypt");
		}
	}
	/**
	 * 使用私钥进行签名
	 * @param content
	 * @param privateKey
	 * @return
	 */
	public static String sign(String content, String privateKey){
		try {
			// 解密由base64编码的私钥
			byte[] keyBytes = Base64.getDecoder().decode(privateKey);
			// 构造PKCS8EncodedKeySpec对象
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			// KEY_ALGORITHM 指定的加密算法
			KeyFactory keyFactory = KeyFactory.getInstance("DSA");
			// 取私钥匙对象
			PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
			// 用私钥对信息生成数字签名
			Signature signature = Signature.getInstance(keyFactory.getAlgorithm());
			signature.initSign(priKey);
			signature.update(content.getBytes());
			return Base64.getEncoder().encodeToString(signature.sign());
		} catch (Exception e) {
			log.error("DSA签名时出现异常：【content："+content+";privateKey："+privateKey+"】",e);
		}
		return null;
	}
	
	/**
	 * 校验数字签名
	 * @param content
	 * @param publicKey
	 * @param sign 数字签名
	 * @return
	 */
	public static boolean verify(String content, String publicKey, String sign){
		try {
			// 解密由base64编码的公钥
			byte[] keyBytes = Base64.getDecoder().decode(publicKey);
			// 构造X509EncodedKeySpec对象
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			// ALGORITHM 指定的加密算法
			KeyFactory keyFactory = KeyFactory.getInstance("DSA");
			// 取公钥匙对象
			PublicKey pubKey = keyFactory.generatePublic(keySpec);
			Signature signature = Signature.getInstance(keyFactory.getAlgorithm());
			signature.initVerify(pubKey);
			signature.update(content.getBytes());
			// 验证签名是否正常
			return signature.verify(Base64.getDecoder().decode(sign));
		}catch (Exception e) {
			log.error("DSA校验签名时出现异常：【content："+content+";privateKey："+publicKey+"】" +";sign："+sign+"】" ,e);
		}
		return false;
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
		String message = "这里是签名TEST123321123";
		
		String result = sign(message, keyMap.get("privateKey"));
		System.out.println("签名后: " + result);
		boolean decryResult = verify(message, keyMap.get("publicKey"), result);
		System.out.println("校验签名结果: " + decryResult);
	}
}
