package org.example.services.sertificate;

import org.example.helpers.IoHelper;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Base64;

public class SertificateCheckrt {

    public static KeyPair Generate() {

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


        IoHelper.writeFile(new File("serts/private.pem"), privateKeyString);
        IoHelper.writeFile(new File("serts/public.pem"), publicKeyString);


        Signature signature = null;
        try {
            signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);

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
        }

        System.out.println(signature.toString());

        return keyPair;
    }


}
