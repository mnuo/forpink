package com.mnuo.forpink.encryption;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;
/**
 * AES密码学中的高级加密标准（Advanced Encryption Standard，AES），又称 高级加密标准 
 *	Rijndael加密法，是美国联邦政府采用的一种区块加密标准。这个标准用来替代原先的DES，已经被多方分析且广为全世界所使用
 * @author administrator
 */
@Slf4j
public class AESUtil {

	public static String encrypt(String content, String AESKey){
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(AESKey.getBytes());
			
			kgen.init(128, random);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			
			//创建密码器
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器 
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] byteContent = content.getBytes("utf-8");
			
			byte[] encryptResult = cipher.doFinal(byteContent);    //加密后接口
			String result = Base64.getEncoder().encodeToString(encryptResult);
	        log.info(content + "加密后:" + result);

	        return result;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("AES加密时出现异常：【content："+content+";AESPwd："+AESKey+"】",e);
		}
		return null;
	}
	public static String decrypt(String content, String AESKey){
		//解码
		byte[] contentByte = Base64.getDecoder().decode(content);
		KeyGenerator kgen;
		try {
			kgen = KeyGenerator.getInstance("AES");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(AESKey.getBytes());
			kgen.init(128, random);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			
			//创建密码器
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器 
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(contentByte);
			return new String(result,"utf-8"); // 解密
		} catch (Exception e) {
			log.error("AES解密时出现异常：[content："+content+";AESPwd："+AESKey+"]");
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		String content = "aesabcj123321123321-a-b-c";
		String AESKey = "forpink";
		String result = encrypt(content, AESKey);
		System.out.println("加密后: " + result);
		String result1 = decrypt(result, AESKey);
		System.out.println("解密后: " + result1);
	}
}
