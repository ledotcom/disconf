package com.baidu.disconf.web.service.config.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @Description:文件内容加密工具类
 * @Author denghong1
 * @Create time: 2016年11月7日下午2:34:37
 *
 */
public class DataSecurityUtil {
	private static final String KEY_ALGORITHM = "AES";
	private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
	private static final String DEFAULT_CHARSET = "utf-8";
	private transient static final String MAGIC_LEMALL_ENCRYKEY = "111";
	
	protected static final Logger LOG = LoggerFactory.getLogger(DataSecurityUtil.class);

	public static String encryptAES(String sourceStr){
		try {
			return bytes2hex(encryptAES(sourceStr, MAGIC_LEMALL_ENCRYKEY));
		} catch (Exception e) {
			LOG.error("加密过程失败，将返回原文信息",e);
			return sourceStr;
		}
	}

	private static byte[] encryptAES(String sourceStr, String encryKey) throws Exception {
		byte[] keyBytes = encryKey.getBytes(DEFAULT_CHARSET);
		if (keyBytes.length > 16) {
			throw new Exception("encryKey is too long!");
		}
		byte[] keyBytes2 = new byte[16];
		System.arraycopy(keyBytes, 0, keyBytes2, 0, keyBytes.length);
		Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
		// 使用密钥初始化，设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes2, KEY_ALGORITHM));
		// 执行操作
		return cipher.doFinal(sourceStr.getBytes(DEFAULT_CHARSET));
	}

	public static String decryptAES(String cypherText){
		try {
			return new String(decryptAES(hex2bytes(cypherText), MAGIC_LEMALL_ENCRYKEY), DEFAULT_CHARSET);
		}catch (Exception e) {
			LOG.error("解密过程失败，将返回原文",e);
			return cypherText;
		}
	}

	private static byte[] decryptAES(byte[] srcBytes, String decryptKey) throws Exception {
		byte[] keyBytes = decryptKey.getBytes(DEFAULT_CHARSET);
		if (keyBytes.length > 16) {
			throw new Exception("encryKey is too long!");
		}
		byte[] keyBytes2 = new byte[16];
		System.arraycopy(keyBytes, 0, keyBytes2, 0, keyBytes.length);
		Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
		// 使用密钥初始化，设置为加密模式
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyBytes2, KEY_ALGORITHM));
		// 执行操作
		return cipher.doFinal(srcBytes);
	}

	private static String bytes2hex(byte[] bytes) {
		String result = "";
		String b = "";
		for (int i = 0; i < bytes.length; i++) {
			b = Integer.toHexString(bytes[i] & 0xFF);
			if (b.length() == 1) {
				b = "0" + b;
			}
			result += b;
		}
		return result.toUpperCase();
	}

	private static byte[] hex2bytes(String hexString) {
		// 转换成大写
		hexString = hexString.toUpperCase();

		// 计算字节数组的长度
		char[] chars = hexString.toCharArray();
		byte[] bytes = new byte[chars.length / 2];

		// 数组索引
		int index = 0;

		for (int i = 0; i < chars.length; i += 2) {
			byte newByte = 0x00;

			// 高位
			newByte |= char2byte(chars[i]);
			newByte <<= 4;

			// 低位
			newByte |= char2byte(chars[i + 1]);

			// 赋值
			bytes[index] = newByte;

			index++;
		}
		return bytes;
	}

	private static byte char2byte(char ch) {
		switch (ch) {
		case '0':
			return 0x00;
		case '1':
			return 0x01;
		case '2':
			return 0x02;
		case '3':
			return 0x03;
		case '4':
			return 0x04;
		case '5':
			return 0x05;
		case '6':
			return 0x06;
		case '7':
			return 0x07;
		case '8':
			return 0x08;
		case '9':
			return 0x09;
		case 'A':
			return 0x0A;
		case 'B':
			return 0x0B;
		case 'C':
			return 0x0C;
		case 'D':
			return 0x0D;
		case 'E':
			return 0x0E;
		case 'F':
			return 0x0F;
		default:
			return 0x00;
		}
	}
}
