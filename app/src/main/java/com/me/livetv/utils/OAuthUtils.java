package com.me.livetv.utils;

import android.util.Base64;

public class OAuthUtils {

    public static final String BASIC = "Basic";
    public static final String BEARER = "Bearer";

    public static String encodeCredentials(String username, String password) {
        String cred = username + ":" + password;
        String encodedValue = null;
        byte[] encodedBytes = Base64.encode(cred.getBytes(), Base64.NO_WRAP);
        encodedValue = new String(encodedBytes);
        System.out.println("encodedBytes " + new String(encodedBytes));

        byte[] decodedBytes = Base64.decode(encodedBytes, Base64.NO_WRAP);
        System.out.println("decodedBytes " + new String(decodedBytes));

        return encodedValue;
    }

    public static String getBasicAuthorizationHeader(String username, String password) {

        return BASIC + " "+ encodeCredentials(username, password);
    }

    public static String getAuthorizationHeaderForAccessToken(String accessToken) {
        return BEARER + " " + accessToken;
    }
}
