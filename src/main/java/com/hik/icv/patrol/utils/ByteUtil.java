package com.hik.icv.patrol.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

public class ByteUtil {
    public static byte[] hexStringToBytes(String hexString) {
        if (StringUtils.isEmpty(hexString)) {
            return null;
        }
        // toUpperCase将字符串中的所有字符转换为大写
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        // toCharArray将此字符串转换为一个新的字符数组。
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    //charToByte返回在指定字符的第一个发生的字符串中的索引，即返回匹配字符
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 将字节数组转换为16进制字符串
     */
    public static String BinaryToHexString(byte[] bytes) {
        String hexStr = "0123456789ABCDEF";
        String result = "";
        String hex = "";
        for (byte b : bytes) {
            hex = String.valueOf(hexStr.charAt((b & 0xF0) >> 4));
            hex += String.valueOf(hexStr.charAt(b & 0x0F));
            result += hex;
        }
        return result;
    }

    /**
     *  将字节数组转换为16进制字符串
     * @param bytes
     * @return
     */
    public static String bytesToString(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            String sTemp = Integer.toHexString(0xFF & b);
            if (sTemp.length() < 2) {
                result.append(0);
            }
            result.append(sTemp.toUpperCase());
        }
        return result.toString();
    }

    /**
     * 在前补位
     *
     * @param str      源字符
     * @param length   补足多少位（字节数*2）
     * @param patchStr 补位符
     * @return
     */
    public static String beforePatch(String str, int length, String patchStr) {
        while (str.length() < length) {
            str = patchStr + str;
        }
        return str;
    }

    /**
     * 在后补位
     *
     * @param str      源字符
     * @param length   补足多少位
     * @param patchStr 补位符
     * @return
     */
    public static String afterPatch(String str, int length, String patchStr) {
        while (str.length() < length) {
            str = str + patchStr;
        }
        return str;
    }

    /**
     * 将数字转化 为16进制的字符
     *
     * @param num
     * @return
     */
    public static String numToHexString(Integer num) {
        return Integer.toHexString(num);
    }

    /**
     * 将两个字节的16进制字符口串 高低位互换
     *
     * @param hexStr
     * @return
     */
    public static String exchangePosition(String hexStr) {
        int length = hexStr.length();
        String handHexString ="";
        if (length % 2 != 0) {
            handHexString=beforePatch(hexStr, length + 1, "0");
        }else {
            handHexString=hexStr;
        }
        length=handHexString.length();
        StringBuilder sb=new StringBuilder();
        for (int i=0;i<length/2;i++){
            sb.append(handHexString.substring(length-(i+1)*2,length-i*2));
        }
        return sb.toString();
    }

    /**
     * 将数字转换成16进制的字符串。
     *
     * @param num    被转的数字
     * @param length 该字段要求的长度
     * @param str    长度不足时，用这个字符补位
     * @return
     */
    public static String numToLengthHexString(Integer num, int length, String str) {
        String hexString = Integer.toHexString(num);
        if (hexString.length() > length) {
            hexString = hexString.substring(0, length);
        }
        if (hexString.length() < length) {
            hexString = beforePatch(hexString, length, str);
        }
        return hexString;
    }

    /**
     * 将数字转换成16进制的字符串。
     *
     * @param num    被转的数字
     * @param length 该字段要求的长度
     * @return
     */
    public static String numToLengthHexString(Integer num, int length) {
        String hexString = Integer.toHexString(num);
        if (hexString.length() < length) {
            hexString = beforePatch(hexString, length, "0");
        }
        return hexString;
    }

    public static int hexStringToNum(String hexString) {

        return Integer.parseInt(hexString, 16);
    }

    public static Long hexStringToLong(String hexString) {
        return Long.parseLong(hexString, 16);
    }

    /**
     * 16进制字符串转float
     * @param hexString
     * @return
     */
    public static Float hexStringToFloat(String hexString){

        return Float.intBitsToFloat(Long.valueOf(hexString,16).intValue());
    }

    public static String floatToHexString(Float value){
        return Integer.toHexString(Float.floatToIntBits(value));

    }

    /**
     * float转16进制字符串
     * @param f
     * @return
     */
    public static String floatToHexString(float f){
        return Integer.toHexString(Float.floatToIntBits(f));
    }



    public static Float hexStringToFloat2(String hexString){
        Integer integer = ByteUtil.hexStringToNum(hexString);
        //将解析出来的整数 计算成温度 //double result=String/100 - 100;  保留两位小数
        BigDecimal result = new BigDecimal(integer).divide(new BigDecimal(10), 2, BigDecimal.ROUND_HALF_UP);
        return result.floatValue();
    }

    public static void main(String[] args) {
        String msg="aa55340001644af1ea030107202020205573657220202020313233342020202020202020202020204C697374656E65729d7da55a";
        byte[] bytes = ByteUtil.hexStringToBytes(msg);
        String s = ByteUtil.BinaryToHexString(bytes);
        System.out.println(s);
    }
}
