package com.youjun.common.util;


/**
 * <p>
 *  字符串工具类
 * </p>
 *
 * @author kirk
 * @since 2021/4/15
 */
public class StringUtils {
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
}
