package br.com.rponte.pindecrypter.pin;

import br.com.rponte.pindecrypter.keys.Zpk;
import br.com.rponte.pindecrypter.pin.pinblock.PinBlock;
import br.com.rponte.pindecrypter.util.crypto.TripleDESEncryptor;

/**
 * Represents an encrypted PIN that was generated by a POS device
 */
public class EncryptedPin {
	
	private static final TripleDESEncryptor DECRYPTOR = new TripleDESEncryptor();

	private final String encryptedPin;
	
	public EncryptedPin(String encryptedPin) {
		this.encryptedPin = encryptedPin;
	}
	
	/**
	 * Decrypts this encrypted PIN using the specified <code>zpk</code>
	 * 
	 * @param zpk the ZPK that will be used to decrypt this encrypted PIN
	 * @return a <code>PinBlock</code> instance
	 */
	public PinBlock decrypt(Zpk zpk) {
		String clearPinBlock = DECRYPTOR.decrypt(encryptedPin, zpk.getKey());
		return new PinBlock(clearPinBlock);
	}

	@Override
	public String toString() {
		return this.encryptedPin;
	}
}
