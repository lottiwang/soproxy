package com.forcetech.android.https;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 3DES工具�?
 * API
 *
 * @author yule
 */
public class TriDes {

    private static final String Algorithm = "DESede"; //定义 加密算法,可用 DES,DESede,Blowfish

    public static byte[] getKeyBytes(String keyString) throws UnsupportedEncodingException {
        byte[] b = new byte[24];
        byte[] key = keyString.getBytes("utf-8");
        int count = keyString.getBytes().length;
        for (int i = 0; i < count; i++) {
            b[i] = key[i];
        }
        for (int i = count; i < 24; i++) {
            b[i] = 0x20;
        }
        return b;
    }

    //keybyte为加密密钥，长度�?4字节
    //src为被加密的数据缓冲区（源�?
    public static byte[] encryptMode(byte[] keybyte, byte[] src) {
        try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

            //加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    //keybyte为加密密钥，长度�?4字节
    //src为加密后的缓冲区
    public static byte[] decryptMode(byte[] keybyte, byte[] src) {
        try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

            //解密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    //转换成十六进制字符串
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";

        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) hs = hs + "0" + stmp;
            else hs = hs + stmp;
            if (n < b.length - 1) hs = hs + ":";
        }
        return hs.toUpperCase();
    }

    public static String CreateDigest(String src) {
        String ret = "";
        try {    // Hash算法
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            sha.update(src.getBytes());
            // Base64加密
            ret = Base64Cutego.encodeToString(sha.digest());
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return ret;
    }

    public static String CreateDigestNoBASE64(String src) {
        String ret = "";
        try {    // Hash算法
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            sha.update(src.getBytes());
            ret = new String(sha.digest());
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return ret;
    }
}
