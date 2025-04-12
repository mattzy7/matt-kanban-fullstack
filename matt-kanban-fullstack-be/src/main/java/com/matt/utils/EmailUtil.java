package com.matt.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public class EmailUtil {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static Boolean isValidEmail(String email) {
        if (!StringUtils.hasLength(email)) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
