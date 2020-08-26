package com.mnuo.forpink.encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;

public class EncodeUtil {
	private static final String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	/**
	 * 
	 * @param b
	 * @return
	 */
	private static String bytesToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			int n = b[i];
			if (n < 0) {
				n += 256;
			}
			int d1 = n / 16;
			int d2 = n % 16;
			resultSb.append(hexDigits[d1] + hexDigits[d2]);
		}
		return resultSb.toString();
	}

	/**
	 * MD5加密
	 * 
	 * @param origin
	 * @param charsetname
	 * @return
	 */
	public static String md5Encode(String origin, String charsetname) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			if ((charsetname == null) || ("".equals(charsetname))) {
				return bytesToHexString(md.digest(origin.getBytes()));
			} else {
				return bytesToHexString(md.digest(origin.getBytes(charsetname)));
			}
		} catch (Exception localException) {
		}
		return null;
	}

	/**
	 * sha1加密
	 * 
	 * @param origin
	 * @param charsetname
	 * @return
	 */
	public static String sha1Encode(String origin, String charsetname) {
		try {
			MessageDigest sha1 = MessageDigest.getInstance("SHA1");
			if ((charsetname == null) || ("".equals(charsetname))) {
				return bytesToHexString(sha1.digest(origin.getBytes()));
			} else {
				return bytesToHexString(sha1.digest(origin.getBytes(charsetname)));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		String msg = "郭XX-精品相声技术";
		String md5 = md5Encode(msg, null);
		String sha1 = sha1Encode(msg, null);
		System.out.println("明文是：" + msg);
		System.out.println("md5密文是：" + md5);
		System.out.println("sha1密文是：" + sha1);
	}
	
	public static MessageDigest messagedigest = null;
	/**
     * 对一个文件获取md5值
     * 
     * @return md5串
     * @throws NoSuchAlgorithmException
     */
    public static String getMD5(File file) throws IOException, NoSuchAlgorithmException {

        messagedigest = MessageDigest.getInstance("MD5");
        FileInputStream in = new FileInputStream(file);
        FileChannel ch = in.getChannel();
        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
        messagedigest.update(byteBuffer);
        return bufferToHex(messagedigest.digest());
    }

    /**
     * // * @param target 字符串 求一个字符串的md5值 // * @return md5 value //
     */
    // public static String StringMD5(String target) {
    // return DigestUtils.md5Hex(target);
    // }

    /***
     * 计算SHA1码
     * 
     * @return String 适用于上G大的文件
     * @throws NoSuchAlgorithmException
     */
    public static String getSha1(File file) throws OutOfMemoryError, IOException, NoSuchAlgorithmException {
        messagedigest = MessageDigest.getInstance("SHA-1");
        FileInputStream in = new FileInputStream(file);
        FileChannel ch = in.getChannel();
        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
        messagedigest.update(byteBuffer);
        return bufferToHex(messagedigest.digest());
    }

    /**
     * 获取文件SHA256码
     * 
     * @return String
     */
    public static String getSha256(File file) throws OutOfMemoryError, IOException, NoSuchAlgorithmException {
        messagedigest = MessageDigest.getInstance("SHA-256");
        FileInputStream in = new FileInputStream(file);
        FileChannel ch = in.getChannel();
        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
        messagedigest.update(byteBuffer);
        return bufferToHex(messagedigest.digest());
    }

    /**
     * 获取文件CRC32码
     * 
     * @return String
     */
    public static String getCRC32(File file) {
        CRC32 crc32 = new CRC32();
        // MessageDigest.get
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                crc32.update(buffer, 0, length);
            }
            return crc32.getValue() + "";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fileInputStream != null)
                    fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getMD5String(String s) {
        return getMD5String(s.getBytes());
    }

    public static String getMD5String(byte[] bytes) {
        messagedigest.update(bytes);
        return bufferToHex(messagedigest.digest());
    }

    /**
     * @Description 计算二进制数据
     * @return String
     */
    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        String c0 = hexDigits[(bt & 0xf0) >> 4];
        String c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    public static boolean checkPassword(String password, String md5PwdStr) {
        String s = getMD5String(password);
        return s.equals(md5PwdStr);
    }
}
