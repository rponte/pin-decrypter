package br.com.rponte.pindecrypter;

import br.com.rponte.pindecrypter.keys.EncryptedZpk;
import br.com.rponte.pindecrypter.keys.MasterKey;
import br.com.rponte.pindecrypter.keys.Zpk;
import br.com.rponte.pindecrypter.pin.EncryptedPin;
import br.com.rponte.pindecrypter.pin.pinblock.PinBlock;

/**
 * Class responsible for decrypting an encrypted PIN that was entered by the
 * user on a POS device
 * 
 * @author rponte
 */
public class PinDecrypter {

	private final KeyStorage keyStorage;
	
	/**
	 * Creates a new instance with the specified <code>keyStorage</code>
	 * 
	 * @param keyStorage key storage with all needed keys
	 */
	public PinDecrypter(KeyStorage keyStorage) {
		this.keyStorage = keyStorage;
	}

	/**
	 * Decrypts the specified <code>encryptedPin</code> and uses the
	 * <code>pan</code> to extract the plain-text PIN
	 * 
	 * @param encryptedPin the encrypted PIN entered by the user on POS device
	 * @param pan          the card number used in the transaction
	 * @return the plain-text PIN
	 */
	public String decrypt(String encryptedPin, String pan) {
		
		MasterKey zmk = keyStorage.getMasterKey();
		EncryptedZpk zpkUnderZmk = keyStorage.getEncryptedZpk();
		Zpk zpk = zpkUnderZmk.decrypt(zmk);
		
		PinBlock pinblock = new EncryptedPin(encryptedPin).decrypt(zpk);
		String pin = pinblock.getPin(pan);
		return pin;
	}
	
}
