package encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.ResourceBundle;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionImplementation {

    private static final byte[] salt = "g3 CRUD is salt!".getBytes();

    private static final ResourceBundle bundle = ResourceBundle.getBundle("properties.config");

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

        // Load Private Key
        String privateKeyFilePath = encryption.AsimetricEncryption.privateKeyPath;

        byte[] encryptedTextBytes = null;

        try {

            // Load Private Key
            FileInputStream fis = new FileInputStream(privateKeyFilePath);
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

    /**
     * Retorna el contenido de un fichero
     *
     * @param path Path del fichero
     * @return El texto del fichero
     */
    public static byte[] fileReader(String path) {
        byte ret[] = null;
        File file = new File(path);
        try {
            ret = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }


    /**
     * Descifra un texto con AES, modo CBC y padding PKCS5Padding (simétrica) y
     * lo retorna
     *
     * @param clave La clave del usuario
     */
    public static String descifrarCredentials(String clave) {
        String ret = null;

        String cypherMailPath = bundle.getString("CYPHERMAILPATH");

        // Fichero leído
        byte[] fileContent = fileReader(cypherMailPath);
        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;
        try {
            // Creamos un SecretKey usando la clave + salt
            keySpec = new PBEKeySpec(clave.toCharArray(), salt, 65536, 128); // AES-128
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");

            // Creamos un Cipher con el algoritmos que vamos a usar
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParam = new IvParameterSpec(Arrays.copyOfRange(fileContent, 0, 16)); // La IV est� aqu�
            cipher.init(Cipher.DECRYPT_MODE, privateKey, ivParam);
            byte[] decodedMessage = cipher.doFinal(Arrays.copyOfRange(fileContent, 16, fileContent.length));
            ret = new String(decodedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

}
