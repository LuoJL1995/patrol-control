package com.hik.icv.patrol.utils;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author LuoJialei
 * @description 16进制转换工具
 * @date 2020/6/12
 */
public class HexStringUtil {

    /**
     * @Description 判断信息是否16进制
     * @Author LuoJiaLei
     * @Date 2020/6/12
     * @Time 10:22
     * @param s: 需处理的字符串
     * @return boolean
     */
    public static boolean isHex(String s) {
        //去空格防止转换问题
        s = s.replace(" ", "");
        boolean flag = false;
        try {
            Long.parseLong(s, 16);
            flag = true;
        } catch (Exception ignored) {
        }
        return flag;
    }

    /**
     * @Description 字符串转化成为16进制字符串
     * @Author LuoJiaLei
     * @Date 2020/6/12
     * @Time 9:58
     * @param s: 需处理的字符串
     * @return java.lang.String
     */
    public static String stringToHex(String s) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            int ch = s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str.append(s4);
        }
        return str.toString();
    }

    /**
     * @Description 16进制转换成为string类型字符串
     * @Author LuoJiaLei
     * @Date 2020/6/12
     * @Time 9:57
     * @param s: 需处理的字符串
     * @return java.lang.String
     */
    public static String hexToString(String s) {
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, StandardCharsets.UTF_8);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

}