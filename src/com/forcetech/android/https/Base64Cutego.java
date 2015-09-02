package com.forcetech.android.https;

import android.util.Base64;

/**
 * Base64工具�? * API
 *
 * @author yule
 */
public class Base64Cutego {
    private static final int flag = Base64.NO_WRAP;

    public static String encodeToString(byte[] digest) {
        return Base64.encodeToString(digest,flag);
    }

    public static byte[] encode(byte[] encoded) {
        return Base64.encode(encoded,flag);
    }

    public static byte[] decode(String s) {
        return Base64.decode(s,flag);
    }
}
