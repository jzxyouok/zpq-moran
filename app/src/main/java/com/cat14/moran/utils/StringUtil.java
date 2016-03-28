package com.cat14.moran.utils;

import java.util.regex.Pattern;

/**
 * 字符串工具
 */
public class StringUtil {

    // 正则表达式
    private final static Pattern emailer = Pattern.compile(
            "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    /**
     * 判断是否为空
     *
     * @param input 需要判断的字符串
     * @return boolean 判断结果
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input)) {
            return true;
        }
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return  false;
            }
        }
        return true;
    }

    /**
     * 正则表达式判断邮箱格式
     *
     * @param email 邮箱
     * @return boolean 判断结果
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0) {
            return false;
        }
        return emailer.matcher(email).matches();
    }


    /**
     * 半角转全角
     *
     * @param input String
     * @return String
     */
    public static String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char)32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375) {
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }

}
