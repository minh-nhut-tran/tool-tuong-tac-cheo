package com.example.selenium.common;

import com.example.selenium.constants.CurrentDirectory;

import java.io.File;
import java.io.FileOutputStream;
import java.security.*;

public class PairKeyRSA {
    /*
        create pair key
     */
    public static void securityKeyPairGenerate() {
        try {
            SecureRandom sr = new SecureRandom();
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(12284, sr);
            KeyPair kp = kpg.genKeyPair();
            PublicKey publicKey = kp.getPublic();
            PrivateKey privateKey = kp.getPrivate();
            File publicKeyFile = FileHandler.createFile(new File(CurrentDirectory.currentDirectoryFacebook +"publicKey.rsa"));
            if(publicKeyFile == null) throw new Exception("Can not create publicKeyFile");
            File privateKeyFile = FileHandler.createFile(new File(CurrentDirectory.currentDirectoryFacebook +"privateKey.rsa"));
            if(privateKeyFile == null) throw new Exception("Can not create publicKeyFile");
            FileOutputStream fos = new FileOutputStream(publicKeyFile);
            fos.write(publicKey.getEncoded());
            fos.close();
            fos = new FileOutputStream(privateKeyFile);
            fos.write(privateKey.getEncoded());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
