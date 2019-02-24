package com.jh.xysp.util;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DESUtils {
	private static Key key;
    // ������Կkey
    private static String KEY_STR = "myKey";
    private static String CHARSETNAME = "UTF-8";
    private static String ALGORITHM = "DES";
    
    static {
        try {
            // ����DES�㷨����
            KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
            // ����SHA1��ȫ����
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            // ��������Կ����
            secureRandom.setSeed(KEY_STR.getBytes());
            // ��ʼ������SHA1���㷨����
            generator.init(secureRandom);
            // ������Կ����
            key = generator.generateKey();
            generator = null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static String getEncryptString(String str) {
        // ����BASE64���룬����byte[]��ת��ΪString
        BASE64Encoder base64encoder = new BASE64Encoder();
        try {
            // ��UTF-8����
            byte[] bytes = str.getBytes(CHARSETNAME);
            // ��ȡ���ܶ���
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // ��ʼ��������Ϣ
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // ����
            byte[] doFinal = cipher.doFinal(bytes);
            // byte[] to encode�õ�String������
            return base64encoder.encode(doFinal);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static String getDecryptString(String str) {
        // ����BASE64���룬����byte[]��ת��ΪString
        BASE64Decoder base64decoder = new BASE64Decoder();
        try {
            // ���ַ���decode��byte[]
            byte[] bytes = base64decoder.decodeBuffer(str);
            // ��ȡ���ܶ���
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // ��ʼ��������Ϣ
            cipher.init(Cipher.DECRYPT_MODE, key);
            // ����
            byte[] doFinal = cipher.doFinal(bytes);
            // ���ؽ���֮�����Ϣ
            return new String(doFinal, CHARSETNAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void main(String[] args) {
        System.out.println(getEncryptString("root"));
        System.out.println(getEncryptString("root"));
        System.out.println(getDecryptString("WnplV/ietfQ="));
        System.out.println(getDecryptString("WnplV/ietfQ="));
    }
}
