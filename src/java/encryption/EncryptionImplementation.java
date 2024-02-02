package encryption;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * The {@code EncryptionImplementation} class provides methods for cryptographic
 * operations, including hash generation, private key decryption, and file
 * reading for encryption purposes.
 *
 * This class utilizes various cryptographic algorithms such as MD5, RSA, and
 * AES for different operations.
 *
 * Note: The class assumes the existence of a "keysprivateKey.der" file and a
 * "mailCredentials.properties" file for private key decryption and credential
 * reading, respectively.
 *
 *
 * @author Iñigo
 * @version 1.0
 */
public class EncryptionImplementation {

    /**
     * The salt used for cryptographic operations.
     */
    private static final byte[] salt = "g3 CRUD is salt!".getBytes();

    /**
     * Generates an MD5 hash for the given password.
     *
     * @param password The password to be hashed.
     * @return The MD5 hash of the password.
     */
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

    /**
     * Decrypts an encrypted text using the private key stored in
     * "keysprivateKey.der".
     *
     * @param encryptedText The Base64-encoded encrypted text to be decrypted.
     * @return The decrypted text.
     */
    public static String decrypWithPrivateKey(String encryptedText) {

        // Load Private Key
        byte[] encryptedTextBytes = null;

        try {
            // Load Private Key
            InputStream fis = EncryptionImplementation.class.getResourceAsStream("keysprivateKey.der");
            byte[] privateKeyBytes = new byte[fis.available()];
            fis.read(privateKeyBytes);
            fis.close();

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

            // Decrypt data
            encryptedTextBytes = Base64.getDecoder().decode(encryptedText); // Decode the Base64 string back into bytes
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedData = cipher.doFinal(encryptedTextBytes);
            return new String(decryptedData, StandardCharsets.UTF_8); // Convert the decrypted bytes back into a string

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Reads the content of a file and returns it as a byte array.
     *
     * @param path The path of the file to be read.
     * @return The content of the file as a byte array.
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
     * Converts an InputStream to a byte array. Reads the content of the
     * provided InputStream and converts it into a byte array.
     *
     * @param inputStream The InputStream to be converted to a byte array.
     * @return The byte array representing the content of the InputStream.
     * @throws IOException If an I/O error occurs while reading the InputStream.
     */
    private static byte[] inputStreamToBytes(InputStream inputStream) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096]; // Adjust the buffer size as needed
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Decrypts text with AES using CBC mode and PKCS5Padding, and returns the
     * decrypted text.
     *
     * @param clave The user's key.
     * @return The decrypted text.
     * @throws IOException If an I/O error occurs while reading the credential
     * file.
     */
    public static String descifrarCredentials(String clave) throws IOException {
        String ret = null;

        byte[] fileContent = inputStreamToBytes(EncryptionImplementation.class.getResourceAsStream("mailCredentials.properties"));
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
