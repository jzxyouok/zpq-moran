package com.cat14.moran.utils;

import java.security.MessageDigest;

/**
 * MD5工具
 */
public class MD5Util {

    /**
     * 将输入字符转换为MD5格式
     *
     * @param input String
     * @return String MD5
     */
    public static String getMD5(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] results = digest.digest(input.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for(byte b : results){
                int number = b&0xff;
                String hex = Integer.toHexString(number);
                if(hex.length()==1){
                    stringBuilder.append("0");
                }
                stringBuilder.append(hex);
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
