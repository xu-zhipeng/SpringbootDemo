package com.youjun.common.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 *  字符串工具类
 * </p>
 *
 * @author kirk
 * @since 2021/4/15
 */
public class StringUtils {
    private StringUtils() {
    }

    public static boolean isBlank(CharSequence str) {
        int length;
        if (str != null && (length = str.length()) != 0) {
            for(int i = 0; i < length; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotBlank(CharSequence str) {
        return !isBlank(str);
    }

    public static boolean hasBlank(CharSequence... strs) {
        if (strs == null || strs.length == 0) {
            return true;
        } else {
            CharSequence[] var1 = strs;
            int length = strs.length;

            for(int i = 0; i < length; ++i) {
                CharSequence str = var1[i];
                if (isBlank(str)) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean isAllBlank(CharSequence... strs) {
        if (strs == null || strs.length == 0) {
            return true;
        } else {
            CharSequence[] var1 = strs;
            int length = strs.length;

            for(int i = 0; i < length; ++i) {
                CharSequence str = var1[i];
                if (isNotBlank(str)) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence str) {
        return !isEmpty(str);
    }

    public static String fillBefore(String str, char filledChar, int len) {
        return fill(str, filledChar, len, true);
    }

    public static String fillAfter(String str, char filledChar, int len) {
        return fill(str, filledChar, len, false);
    }

    private static String fill(String str, char filledChar, int len, boolean isPre) {
        int strLen = str.length();
        if (strLen > len) {
            return str;
        } else {
            StringBuilder filledStr = new StringBuilder();
            for (int i = 0; i < len - strLen; i++) {
                filledStr.append(filledChar);
            }
            return isPre ? filledStr.toString().concat(str) : str.concat(filledStr.toString());
        }
    }

    public static String toDateFormatString(String source, String sourceFormat, String targetFormat) throws ParseException {
        SimpleDateFormat sourceFmt = new SimpleDateFormat(sourceFormat);
        Date date = sourceFmt.parse(source);
        SimpleDateFormat targetFmt = new SimpleDateFormat(targetFormat);
        String targetStr = targetFmt.format(date);
        return targetStr;
    }
}
