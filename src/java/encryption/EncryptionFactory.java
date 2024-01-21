package src.java.encryption;

public class EncryptionFactory {
	private static EncryptionFactory instance = null;
	
	public static EncryptionFactory getInstance() {
		if (instance == null) {
			instance = new EncryptionFactory();
		}
		return instance;
	}
}
