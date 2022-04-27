package com.jaiser.qqrob.listener.group.encrypt;


import java.security.MessageDigest;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * �����������
 *
 * @author
 * @version 1.0
 * @since 1.0
 */
public abstract class Coder {

    public static final String KEY_SHA = "SHA";
    public static final String KEY_MD5 = "MD5";
    /**
     * MAC�㷨��ѡ���¶����㷨
     *
     * <pre>
     * HmacMD5
     * HmacSHA1
     * HmacSHA256
     * HmacSHA384
     * HmacSHA512
     * </pre>
     */
    public static final String KEY_MAC = "HmacMD5";

    /**
     * BASE64����
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64����
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    /**
     * MD5����
     *
     * @param data �ֽ�����
     * @return ��������ֽ�����
     * @throws Exception
     */
    public static byte[] encryptMD5(byte[] data) throws Exception {

        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data);

        return md5.digest();

    }

    /**
     * MD5���ܣ��������16���Ƶ��ַ�����
     *
     * @param data �ַ���
     * @return �������16���Ƶ��ַ���
     * @throws Exception
     */
    public static String encryptMD5(String data) throws Exception {
        if (data == null || data.trim().length() == 0) {
            throw new IllegalArgumentException("�����������Ϊ��");
        }
        String md5Str = "";

        // MD5 �ļ�������һ�� 128 λ�ĳ����������ֽڱ�ʾ���� 16 ���ֽ�
        byte md5byte[] = encryptMD5(data.getBytes());

        // �������ֽ�ת���� 16 ���Ʊ�ʾ���ַ�
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        // ÿ���ֽ��� 16 ���Ʊ�ʾ�Ļ���ʹ�������ַ������Ա�ʾ�� 16 ������Ҫ 32 ���ַ�
        char str[] = new char[16 * 2];
        // ��ʾת������ж�Ӧ���ַ�λ��
        int k = 0;
        // �ӵ�һ���ֽڿ�ʼ���� MD5 ��ÿһ���ֽ�ת���� 16 �����ַ���ת��
        for (int i = 0; i < 16; i++) {
            // ȡ�� i ���ֽ�
            byte byte0 = md5byte[i];
            // ȡ�ֽ��и� 4 λ������ת��, >>> Ϊ�߼����ƣ�������λһ������
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            // ȡ�ֽ��е� 4 λ������ת��
            str[k++] = hexDigits[byte0 & 0xf];
        }
        md5Str = new String(str);
        return md5Str;
    }

    /**
     * SHA����
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptSHA(byte[] data) throws Exception {

        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
        sha.update(data);

        return sha.digest();

    }

    /**
     * ��ʼ��HMAC��Կ
     *
     * @return
     * @throws Exception
     */
    public static String initMacKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);

        SecretKey secretKey = keyGenerator.generateKey();
        return encryptBASE64(secretKey.getEncoded());
    }

    /**
     * HMAC����
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptHMAC(byte[] data, String key) throws Exception {

        SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);

        return mac.doFinal(data);

    }
}
