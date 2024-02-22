package org.example.services.hashing;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashingApi {

    public static String hash(String data, String token) {
        String hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update((token  + data).getBytes());
            byte[] digest = md.digest();
            hash = bytesToHex(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }

    public static boolean verify(String hash, String data, String token) {
        String hashedData = hash(data, token);
        return hash.equalsIgnoreCase(hashedData);
    }


    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

}
