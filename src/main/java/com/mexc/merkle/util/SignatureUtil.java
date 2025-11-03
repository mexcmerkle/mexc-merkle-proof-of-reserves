package com.mexc.merkle.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class SignatureUtil {

    public static String genHashId(String content) {
        String hashCode = genSha256(content);
        return hashCode != null ? hashCode.substring(0, 32) : "";
    }

    private static String genSha256(String content) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(content.getBytes(StandardCharsets.UTF_8));
            return byte2hex(messageDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("failure to calculate hash id!");
            return null;
        }
    }

    private static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String temp;
        for (int n = 0; b != null && n < b.length; n++) {
            temp = Integer.toHexString(b[n] & 0XFF);
            if (temp.length() == 1) {
                hs.append('0');
            }
            hs.append(temp);
        }
        return hs.toString();
    }
}
