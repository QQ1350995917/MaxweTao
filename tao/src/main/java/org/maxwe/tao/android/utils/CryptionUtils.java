package org.maxwe.tao.android.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Pengwei Ding on 2017-01-10 17:32.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class CryptionUtils {
    public static final String password = "PollKingTueJan10";
    private static final SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
    public static byte[] encrypt(String content) {
        try {
//            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
//            random.setSeed(password.getBytes());
//            keyGenerator.init(128, random);
//            SecretKey secretKey = keyGenerator.generateKey();
//            byte[] enCodeFormat = secretKey.getEncoded();
//            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
//            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
//            byte[] byteContent = content.getBytes("UTF-8");
//            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
//            byte[] result = cipher.doFinal(byteContent);
//            return result; // 加密

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decrypt(byte[] content) {
        try {
//            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
//            random.setSeed(password.getBytes());
//
//            keyGenerator.init(128, random);
//            SecretKey secretKey = keyGenerator.generateKey();
//            byte[] enCodeFormat = secretKey.getEncoded();
//            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
//            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
//            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
//            byte[] result = cipher.doFinal(content);
//            return result; // 加密


            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] encryptCustomer(String content,String password) throws Exception {
        SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] byteContent = content.getBytes("utf-8");
        cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
        byte[] result = cipher.doFinal(byteContent);
        return result; // 加密
    }


    public static byte[] decryptCustomer(byte[] content,String password) throws Exception {
        SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
        byte[] result = cipher.doFinal(content);
        return result; // 加密
    }

    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            String content = Base64.encodeToString("dingpengd".getBytes(),Base64.NO_PADDING);
            //加密
            System.out.println("加密前：" + content);
            byte[] encryptResult = encrypt(content);
            String encryptResultStr = parseByte2HexStr(encryptResult);
            System.out.println("加密后：" + encryptResultStr);
            //解密
            byte[] decryptFrom = parseHexStr2Byte(encryptResultStr);
            byte[] decryptResult = decrypt(decryptFrom);
            if (decryptResult != null) {
                System.out.println("解密后：" + new String(decryptResult));
            }
        }
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000);
    }
}
