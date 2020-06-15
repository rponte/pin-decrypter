package br.com.rponte.pindecrypter.keys;

import br.com.rponte.pindecrypter.util.codec.Hex;
import br.com.rponte.pindecrypter.util.crypto.TripleDESEncryptor;

/**
 * Represents a KCV (Key Check Value)
 * 
 * https://caffinc.github.io/2018/04/key-check-value/
 * https://stackoverflow.com/questions/53185930/calculate-kcv-from-3des-key
 * https://neapay.com/online-tools/hsm-keys-compose.html
 * 
 * @author rponte
 */
public class Kcv {
	
	private static final TripleDESEncryptor ENCRYPTOR = new TripleDESEncryptor();

	private final String key;

	public Kcv(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
	
	/**
	 * Calculates the KCV (Key Check Value) for the specified key
	 */
	public String calculate() {
		byte[] an8ByteNullArray = new byte[8];
		byte[] encryptedData = ENCRYPTOR.encrypt(an8ByteNullArray, key);
		String kcv = toHex(encryptedData).substring(0, 6);
		return kcv.toUpperCase();
	}

	private String toHex(byte[] encryptedData) {
		return Hex.encodeString(encryptedData);
	}
	
}
