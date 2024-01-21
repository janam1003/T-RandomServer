package src.java.encryption;

public interface Encryption {
	public String generateHash(String password);
	public String decrypWithPrivateKey(String encryptedText);
	public void keyPairGenerator();
}
