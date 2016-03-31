package com.cat14.moran.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具
 */
public class MD5Util {

    public static String getMD5(String password) {
        try {
            // 通过加密工具，并创建一个MD5算法加密对象，MessageDigest信息摘要
            MessageDigest digest = MessageDigest.getInstance("MD5");
            // 将密码的字节码加密成字节数组
            byte[] result = digest.digest(password.getBytes());

            StringBuffer sb = new StringBuffer();
            for (byte b : result) {
                // 获取第八位有效值,对每一个字节跟255进行和操作，转换为int类型
                int i = b & 0xff;
                // 将int类型转换为16进制字符串类型
                String hexString = Integer.toHexString(i);
                // 如果16进制长度只有一位（0~9）只有一位，在前面补0
                if (hexString.length() < 2) {
                    // 如果是一位，补零
                    hexString = "0" + hexString;
                }
                // 添加16进制字符串
                sb.append(hexString);
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }


}
