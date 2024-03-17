package org.example.services.trash.sertificate;

import org.example.helpers.FileHelper;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class SertificateCheckrt {

    public static String getPrivateKey() {
        File file = new File("serts/private.pem");
        if (!file.exists()) {
            Generate();
        }
        return FileHelper.readFile(file);
    }

    public static String getPublicKey() {
        File file = new File("serts/public.pem");
        if (!file.exists()) {
            Generate();
        }
        return FileHelper.readFile(file);
    }


    public static String hasingFromPublic(String text) {


        String publicKeyString = getPublicKey();


        Signature signature = null;

        try {

            System.out.println(publicKeyString);
            byte[] privateKeyBytes = Base64.getDecoder().decode(publicKeyString);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  // Или другой алгоритм, если он использовался при создании ключевой пары
            PublicKey publicKey = keyFactory.generatePublic(keySpec);


            signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(publicKey);

            byte[] data = "helo".getBytes("UTF-8");
            signature.update(data);

            byte[] signatureBytes = signature.sign();


        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

        System.out.println(signature.toString());
        return signature.toString();
    }

    public static void Generate() {

        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.genKeyPair();

        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();


        // Преобразование приватного ключа в строку
        String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());

// Преобразование публичного ключа в строку
        String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());


        FileHelper.writeFile(new File("serts/private.pem"), privateKeyString);
        FileHelper.writeFile(new File("serts/public.pem"), publicKeyString);


    }


}
