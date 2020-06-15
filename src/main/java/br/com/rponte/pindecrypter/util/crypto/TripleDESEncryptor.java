package br.com.rponte.pindecrypter.util.crypto;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import br.com.rponte.pindecrypter.util.codec.Hex;

/**
 * Simple implementation of 3DES encrypter and decrypter
 */
public class TripleDESEncryptor {
	
	private static String ALGORITHM = "DESede";
	private static String TRIPLE_DES_TRANSFORMATION = "DESede/ECB/NoPadding";
	private static String TRIPLE_DES_TRANSFORMATION_WITH_PADDING = "DESede/ECB/PKCS5Padding";
	private static final int TRIPLE_DES_KEY_SIZE = 48;
	
	/**
	 * Encrypts the hex-encoded text <code>hexText</code> using the specified
	 * <code>key</code>
	 * 
	 * @param hexText the hex-encoded text to be encrypted
	 * @param key     the key that will be used to encrypt this data
	 * @return returns the encrypted data in hex format
	 */
	public String encrypt(String hexText, String key) {
		
		byte[] textData = Hex.decode(hexText);
		
		byte[] encryptedData = encrypt(textData, key);
		
		String hexEncodedText = Hex.encodeString(encryptedData);
		return hexEncodedText;
	}
	
	/**
	 * Encrypts the byte array <code>data</code> using the specified
	 * <code>key</code>
	 * 
	 * @param rawData the raw data to be encrypted
	 * @param key     the key that will be used to encrypt this data
	 * @return returns the encrypted data
	 */
	public byte[] encrypt(byte[] rawData, String key) {
		
		// creates a key with 24 bytes
		String keyWith24Bytes = keyPadding(key);
		
		try {
			SecretKey skey = new SecretKeySpec(Hex.decode(keyWith24Bytes), ALGORITHM);
			Cipher encrypter = Cipher.getInstance(TRIPLE_DES_TRANSFORMATION_WITH_PADDING);
			encrypter.init(Cipher.ENCRYPT_MODE, skey);
			return encrypter.doFinal(rawData);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Decrypts the hex-encoded text <code>hexEncryptedText</code> using the
	 * specified <code>key</code>
	 * 
	 * @param hexEncryptedText the encrypted data
	 * @param key              the key that will be used to decrypt this data
	 * @return returns the decrypted data in hex format
	 */
	public String decrypt(String hexEncryptedText, String key) {
		byte[] encryptedData = Hex.decode(hexEncryptedText);
		
		byte[] decryptedData = decrypt(encryptedData, key);
		
		String hexEncodedText = Hex.encodeString(decryptedData);
		return hexEncodedText;
	}
	
	
	/**
	 * Decrypts the byte array <code>encryptedData</code> using the specified
	 * <code>key</code>
	 * 
	 * @param encryptedData the encrypted data
	 * @param key           the key that will be used to decrypt this data
	 * @return returns the decrypted data
	 */
	public byte[] decrypt(byte[] encryptedData, String key) {
		
		// creates a key with 24 bytes
		String keyWith24Bytes = keyPadding(key);
		
		try {
			SecretKey skey = new SecretKeySpec(Hex.decode(keyWith24Bytes), ALGORITHM);
			Cipher decrypter = Cipher.getInstance(TRIPLE_DES_TRANSFORMATION);
			decrypter.init(Cipher.DECRYPT_MODE, skey);
			byte[] decryptedData = decrypter.doFinal(encryptedData);
			return decryptedData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Makes sure the key will be 24 bytes long
	 */
	private String keyPadding(String key) {
		String keyWith24Bytes = key;
		if (keyWith24Bytes.length() < TRIPLE_DES_KEY_SIZE) {
			keyWith24Bytes = key + key.substring(0, 16); // concatenates with the first 8 bytes
		}
		return keyWith24Bytes;
	}
	
}
