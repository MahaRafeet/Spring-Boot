package com.example.CourseSystem.HelperUtil;

import java.util.List;

public class Helper {

    //      NULL CHECKS
    public static <T> boolean isNull(T value) {
        return value == null;
    }

    public static <T> boolean isNotNull(T value) {
        return value != null;
    }

    //      STRING CHECKS

    // true if string is null, empty, or only spaces
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    //      LIST CHECKS

    public static <T> boolean isListNull(List<T> list) {
        return list == null;
    }

    public static <T> boolean isListNotNull(List<T> list) {
        return list != null;
    }

    public static <T> boolean isListEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    public static <T> boolean isListNotEmpty(List<T> list) {
        return list != null && !list.isEmpty();
    }

}
