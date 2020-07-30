package com.well.studio.util.mybatisUtil;

/**
 * 命名驼峰/下划线互转工具
 *
 */
public final class CamelCaseUtil {

    private static final char SEPARATOR = '_';

    private CamelCaseUtil() {
    }

    /**
     * 驼峰转下划线
     */
    public static String toUnderlineName(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i >= 0) && shouldSeparate(c)) {
                if (!upperCase || !nextUpperCase) {
                    if (i > 0) {
                        sb.append(SEPARATOR);
                    }
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    private static boolean shouldSeparate(char c) {
        return Character.isUpperCase(c) || isNumber(c);
    }

    private static boolean isNumber(char c) {
        return '0' <= c && c <= '9';
    }

    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }

        s = s.toLowerCase();

        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 下划线转驼峰
     */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * 小写第一个字母
     */
    public static String lowerFirstWord(String s) {
        if (s == null) {
            return null;
        }
        return s.substring(0, 1).toLowerCase() + s.substring(1);
    }

    /**
     * 大写第一个字母
     */
    public static String upperFirstWord(String s) {
        if (s == null) {
            return null;
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

}