package com.connectverse.connectverse.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class RandomStringGenerator {
    public static void main(String[] args) {
        // Define the length of the random string
        int length = 64;

        // Generate a secure random byte array
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);

        // Encode the byte array to a Base64 string
        String randomString = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);

        // Print the random string
        System.out.println("Random String: " + randomString);
    }
}
