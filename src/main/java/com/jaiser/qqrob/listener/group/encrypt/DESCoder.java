package com.jaiser.qqrob.listener.group.encrypt;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * DES??????????
 *
 * <pre>
 * ??? DES??DESede(TripleDES,????3DES)??AES??Blowfish??RC2??RC4(ARCFOUR)
 * DES          		key size must be equal to 56
 * DESede(TripleDES) 	key size must be equal to 112 or 168
 * AES          		key size must be equal to 128, 192 or 256,but 192 and 256 bits may not be available
 * Blowfish     		key size must be multiple of 8, and can only range from 32 to 448 (inclusive)
 * RC2          		key size must be between 40 and 1024 bits
 * RC4(ARCFOUR) 		key size must be between 40 and 1024 bits
 * ???????? ?????? JDK Document http://.../docs/technotes/guides/security/SunProviders.html
 * </pre>
 *
 * @author
 * @version 1.0
 * @since 1.0
 */
public abstract class DESCoder extends Coder {

    /**
     * ALGORITHM ?? <br>
     * ???滻??????????????????key???size??????
     *
     * <pre>
     * DES          		key size must be equal to 56
     * DESede(TripleDES) 	key size must be equal to 112 or 168
     * AES          		key size must be equal to 128, 192 or 256,but 192 and 256 bits may not be available
     * Blowfish     		key size must be multiple of 8, and can only range from 32 to 448 (inclusive)
     * RC2          		key size must be between 40 and 1024 bits
     * RC4(ARCFOUR) 		key size must be between 40 and 1024 bits
     * </pre>
     *
     * ??Key toKey(byte[] key)?????????????????
     * <code>SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);</code> ?滻
     * <code>
     * DESKeySpec dks = new DESKeySpec(key);
     * SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
     * SecretKey secretKey = keyFactory.generateSecret(dks);
     * </code>
     */
    public static final String ALGORITHM = "DES";
    /**
     * ??????
     */
    private static final String DEFAULT_KEY = "FFCS_OSS_20101114";
    /**
     * DEFAULT_KEY????????????????????Coder.encryptBASE64????
     * ???????????????????????????????????汾???????????????DEFAULT_KEY????????SecretKey?????
     */
    private static final String DEFAULT_SECRET_KEY = "GmfpAfvjmF0=";

    /**
     * ??????<br>
     *
     * @param key
     * @return
     * @throws Exception
     */
    private static Key toKey(byte[] key) throws Exception {
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey secretKey = keyFactory.generateSecret(dks);

        // ??????????????????????AES??Blowfish??????????????????滻???????д???
        // SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);

        return secretKey;
    }

    /**
     * DES????
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, String key) throws Exception {
        Key k = toKey(decryptBASE64(key));

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, k);

        return cipher.doFinal(data);
    }

    /**
     * DES????
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String decrypt(String encryptData, String key) throws Exception {
        if (encryptData == null || encryptData.trim().length() == 0) {
            throw new IllegalArgumentException("??????????????");
        }
        String desStr = "";

        desStr = new String(decrypt(decryptBASE64(encryptData), key));

        return desStr;
    }

    /**
     * DES????
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String decrypt(String encryptData) throws Exception {
        if (encryptData == null || encryptData.trim().length() == 0) {
            throw new IllegalArgumentException("??????????????");
        }
        String desStr = "";

//        Key key = toKey(decryptBASE64(initKey()));
        Key key = toKey(decryptBASE64(DEFAULT_SECRET_KEY));

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte decryptByet[] = cipher.doFinal(decryptBASE64(encryptData));

        desStr = new String(decryptByet);

        return desStr;
    }

    /**
     * DES????
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, String key) throws Exception {
        Key k = toKey(decryptBASE64(key));
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, k);

        return cipher.doFinal(data);
    }

    /**
     * DES????
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
        if (data == null || data.trim().length() == 0) {
            throw new IllegalArgumentException("??????????????");
        }

        String desStr = "";

        desStr = DESCoder.encryptBASE64(encrypt(data.getBytes(), key));

        return desStr;
    }

    /**
     * DES????
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) throws Exception {
        if (data == null || data.trim().length() == 0) {
            throw new IllegalArgumentException("??????????????");
        }

        String desStr = "";

//        Key key = toKey(decryptBASE64(initKey()));
        Key key = toKey(decryptBASE64(DEFAULT_SECRET_KEY));
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        desStr = DESCoder.encryptBASE64(cipher.doFinal(data.getBytes()));
        return desStr;
    }

    /**
     * DES??????????
     *
     * @return
     * @throws Exception
     */
    public static String initKey() throws Exception {
        return initKey(DEFAULT_KEY);
    }

    /**
     * DES???????
     * ????????????????????????У?????seed?????SecretKey????????
     * @param seed
     * @return
     * @throws Exception
     */
    public static String initKey(String seed) throws Exception {
        SecureRandom secureRandom = null;

        if (seed != null) {
            secureRandom = new SecureRandom(decryptBASE64(seed));
        } else {
            secureRandom = new SecureRandom();
        }

        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
        kg.init(secureRandom);

        SecretKey secretKey = kg.generateKey();

        return encryptBASE64(secretKey.getEncoded());
    }
}
