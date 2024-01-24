package encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;

public class EncryptionImplementation {

    public static String generateHash(String password) {
        // Convert the byte array to a hexadecimal representation
        StringBuilder hexStringBuilder = new StringBuilder();
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md5.digest(password.getBytes());

            for (byte b : hashBytes) {
                String hex = String.format("%02X", b);
                hexStringBuilder.append(hex);
            }

        } catch (NoSuchAlgorithmException e) {
            //
        }
        return hexStringBuilder.toString();
    }

    public static String decrypWithPrivateKey(String encryptedText) {
        byte[] encryptedTextBytes = null;
        try {
            // Load Private Key
            FileInputStream fis = new FileInputStream("c:\\trastero\\privateKey.der");
            byte[] privateKeyBytes = new byte[fis.available()];
            fis.read(privateKeyBytes);
            fis.close();

            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

            // Decrypt data
            encryptedTextBytes = encryptedText.getBytes();
            Cipher cipher = Cipher.getInstance("ECIES");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedData = cipher.doFinal(encryptedTextBytes);

        } catch (Exception e) {
            //throw new Exception("Error decrypting with private key:" + e.getMessage());
        }
        return new String(encryptedTextBytes);
    }

    public static void keyPairGenerator() {
        try {
            // Si no existe el archivo de clave privada o pública, se generan
            if (!new File("c:\\trastero\\publicKey.der").exists() || !new File("c:\\trastero\\privateKey.der").exists()) {
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
                keyPairGenerator.initialize(256);
                KeyPair key = keyPairGenerator.generateKeyPair();

                PublicKey publicKey = key.getPublic();
                byte[] publicKeyBytes = publicKey.getEncoded();

                try (FileOutputStream publicKeyFile = new FileOutputStream("c:\\trastero\\publicKey.der")) {
                    publicKeyFile.write(publicKeyBytes);
                }

                PrivateKey privateKey = key.getPrivate();
                byte[] privateKeyBytes = privateKey.getEncoded();
                try (FileOutputStream privateKeyFile = new FileOutputStream("c:\\trastero\\privateKey.der")) {
                    privateKeyFile.write(privateKeyBytes);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
