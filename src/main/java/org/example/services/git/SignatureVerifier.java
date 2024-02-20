package org.example.services.git;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class SignatureVerifier {

    public static void main(String[] args) {
        String secret = "It's a Secret to Everybody";
        String payload = "Hello, World!";
        String providedSignature = "757107ea0eb2509fc211221cce984b8a37570b6d7586c22c46f4379c8b043e17";

        String calculatedSignature = calculateSignature(secret, payload);

        if (providedSignature.equals(calculatedSignature)) {
            System.out.println("Signatures match!");
        } else {
            System.out.println("Signatures do not match!");
        }
    }

    public static String calculateSignature(String secret, String payload) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            byte[] hash = sha256_HMAC.doFinal(payload.getBytes());
            return byteArrayToHexString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String byteArrayToHexString(byte[] hash) {
        try (Formatter formatter = new Formatter()) {
            for (byte b : hash) {
                formatter.format("%02x", b);
            }
            return formatter.toString();
        }
    }
}