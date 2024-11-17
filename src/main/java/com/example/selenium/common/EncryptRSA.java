package com.example.selenium.common;

import com.example.selenium.constants.CurrentDirectory;

import javax.crypto.Cipher;
import java.io.*;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class EncryptRSA {

    public static void encryption(String message, File accountFile) {
        FileInputStream fis;
        try {
            fis = new FileInputStream(CurrentDirectory.currentDirectoryFacebook + "publicKey.rsa");
            byte[] b = new byte[fis.available()];
            fis.read(b);
            fis.close();
            X509EncodedKeySpec spec = new X509EncodedKeySpec(b);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = factory.generatePublic(spec);
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptOut = c.doFinal(message.getBytes());
            String strEncrypt = Base64.getEncoder().encodeToString(encryptOut);;
            FileOutputStream fos = new FileOutputStream(accountFile);
            fos.write(strEncrypt.getBytes());
            fos.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static String decryption(String message) {
        FileInputStream fis;
        String messageDecryption;
        try {
            fis = new FileInputStream(CurrentDirectory.currentDirectoryFacebook + "privateKey.rsa");
            byte[] b = new byte[fis.available()];
            fis.read(b);
            fis.close();
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PrivateKey priKey = factory.generatePrivate(spec);
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.DECRYPT_MODE, priKey);
            byte[] decryptOut = c.doFinal(Base64.getDecoder().decode(
                    message));
            messageDecryption = new String(decryptOut);
            return messageDecryption;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


}
