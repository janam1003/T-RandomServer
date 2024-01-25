
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.ResourceBundle;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.InternalServerErrorException;

public class AsimetricEncryption {

    public static void keyPairGenerator() {
        try {
            // Get the user's desktop path in a platform-independent way
            String desktopPath = System.getProperty("user.home") + File.separator + "Desktop";

            // Define the path for the new directory on the desktop
            String directoryPath = desktopPath + File.separator + "Keys";

            // Create a File object for the directory
            File directory = new File(directoryPath);

            // Check if the directory exists
            if (!directory.exists()) {
                // Attempt to create the directory
                boolean isDirectoryCreated = directory.mkdirs();

                if (isDirectoryCreated) {
                    // Si no existe el archivo de clave privada o pública, se generan
                    String privateKeyPath = directoryPath + File.separator + "keysprivateKey.der";
                    String publicKeyPath = directoryPath + File.separator + "keyspublicKey.der";

                    File publicKeyFile = new File(privateKeyPath);
                    File privateKeyFile = new File(publicKeyPath);

                    if (!publicKeyFile.exists() && !privateKeyFile.exists()) {
                        publicKeyFile.createNewFile();
                        privateKeyFile.createNewFile();

                        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
                        keyPairGenerator.initialize(256);
                        KeyPair key = keyPairGenerator.generateKeyPair();

                        PublicKey publicKey = key.getPublic();
                        byte[] publicKeyBytes = publicKey.getEncoded();

                        try (FileOutputStream publicKeyStream = new FileOutputStream(publicKeyPath)) {
                            publicKeyStream.write(publicKeyBytes);
                        }

                        PrivateKey privateKey = key.getPrivate();
                        byte[] privateKeyBytes = privateKey.getEncoded();
                        try (FileOutputStream privateKeyStream = new FileOutputStream(privateKeyPath)) {
                            privateKeyStream.write(privateKeyBytes);
                        }
                    }
                    // Load the ResourceBundle
                    ResourceBundle bundle = ResourceBundle.getBundle("config.properties");

                    // Store the directory path in the ResourceBundle
                    bundle.put("PRIVATEKEYSPATH", directoryPath);
					bundle.put("PUBLICKEYSPATH", directoryPath);
                }
            }
        } catch (Exception e) {
            throw new InternalServerErrorException();
        }
    }
}
