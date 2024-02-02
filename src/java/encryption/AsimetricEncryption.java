package encryption;

import java.io.File;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.ws.rs.InternalServerErrorException;

/**
 * The {@code AsimetricEncryption} class provides methods for generating RSA key
 * pairs and storing them as files on the user's desktop.
 *
 * This class uses the Java Cryptography Architecture (JCA) to generate key
 * pairs for asymmetric encryption.
 *
 * The generated key pairs consist of a public key and a private key, which are
 * stored as separate DER-encoded files.
 *
 * The public key is stored in a file named "keyspublicKey.der" and the private
 * key is stored in a file named "keysprivateKey.der".
 *
 * Note: The generated key pair is of 2048 bits.
 *
 *
 * @author Iñigo
 * @version 1.0
 */
public class AsimetricEncryption {

    /**
     * The path to the private key file.
     */
    public static String privateKeyPath;

    /**
     * Generates an RSA key pair and stores the public and private keys as
     * DER-encoded files on the user's desktop.
     *
     * If the "Keys" directory does not exist on the desktop, it will be
     * created. If the public and private key files do not exist, they will be
     * generated and saved in the "Keys" directory.
     *
     * The public key is stored in a file named "keyspublicKey.der" and the
     * private key is stored in a file named "keysprivateKey.der".
     *
     * If the "Keys" directory or the key files already exist, the method will
     * not overwrite or regenerate them.
     *
     * Note: This method uses the RSA algorithm and generates key pairs of 2048
     * bits.
     *
     *
     * @throws InternalServerErrorException If an internal server error occurs
     * during key pair generation or file handling.
     */
    public static void keyPairGenerator() {
        try {
            // Get the user's desktop path in a platform-independent way
            String desktopPath = System.getProperty("user.home") + File.separator + "Desktop";

            // Define the path for the new directory on the desktop
            String directoryPath = desktopPath + File.separator + "Keys";

            // Create a File object for the directory
            File directory = new File(directoryPath);
            privateKeyPath = directoryPath + File.separator + "keysprivateKey.der";

            // Check if the directory exists
            if (!directory.exists()) {
                // Attempt to create the directory
                boolean isDirectoryCreated = directory.mkdirs();

                if (isDirectoryCreated) {
                    // Si no existe el archivo de clave privada o pública, se generan
                    String publicKeyPath = directoryPath + File.separator + "keyspublicKey.der";

                    File publicKeyFile = new File(privateKeyPath);
                    File privateKeyFile = new File(publicKeyPath);

                    if (!publicKeyFile.exists() && !privateKeyFile.exists()) {
                        publicKeyFile.createNewFile();
                        privateKeyFile.createNewFile();

                        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
                        keyPairGenerator.initialize(2048);
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

                }
            }
        } catch (Exception e) {
            throw new InternalServerErrorException();
        }
    }
}
