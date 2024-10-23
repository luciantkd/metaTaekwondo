package com.taekwondo.metataekwondowebsite.util;

import java.security.SecureRandom;
import java.util.Base64;

public class RandomString {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    public static String make(int length) {
        byte[] randomBytes = new byte[length];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes).substring(0, length);
    }
}