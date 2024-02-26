package com.cc.auth;

import java.util.Random;

public class SecurityUtil {

    private static final String LOWERCASE_CHARS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*";

    private static final String ALL_CHARS = LOWERCASE_CHARS + UPPERCASE_CHARS + NUMBERS + SPECIAL_CHARS;
    private static final Random random = new Random();

    public static String generatePassword(int length) {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(ALL_CHARS.length());
            password.append(ALL_CHARS.charAt(randomIndex));
        }
        return password.toString();
    }

}
