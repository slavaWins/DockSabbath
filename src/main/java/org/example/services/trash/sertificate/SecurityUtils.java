package org.example.services.trash.sertificate;

import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class SecurityUtils {

    private static final String CERTS_DIRECTORY = "certs/";

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048); // Размер ключа
        return keyGen.generateKeyPair();
    }



    public static void generateAndSaveKeyPair()   {
        KeyPair keyPair = null;
        try {
            keyPair = generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        new File(CERTS_DIRECTORY).mkdirs();

        // Сохранение приватного ключа
        saveKeyToFile(keyPair.getPrivate(), CERTS_DIRECTORY + "privatekey.der");

        // Сохранение публичного ключа
        saveKeyToFile(keyPair.getPublic(), CERTS_DIRECTORY + "publickey.der");
    }

    public static PrivateKey getPrivateKey()   {
        byte[] keyBytes = readKeyFromFile(CERTS_DIRECTORY + "privatekey.der");
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = null;
        try {
            kf = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        try {
            return kf.generatePrivate(spec);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static PublicKey getPublicKey()   {
        byte[] keyBytes = readKeyFromFile(CERTS_DIRECTORY + "publickey.der");
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = null;
        try {
            kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(spec);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    private static void saveKeyToFile(Key key, String fileName)  {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(key.getEncoded());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] readKeyFromFile(String fileName)  {
        try (FileInputStream fis = new FileInputStream(fileName)) {
            byte[] keyBytes = new byte[fis.available()];
            fis.read(keyBytes);
            return keyBytes;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}